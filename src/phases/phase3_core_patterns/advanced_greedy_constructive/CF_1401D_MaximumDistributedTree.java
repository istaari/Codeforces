package phases.phase3_core_patterns.advanced_greedy_constructive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


/*
 * ============================================================
 * CF 1401D — Maximum Distributed Tree
 * Link   : https://codeforces.com/problemset/problem/1401/D
 * Rating : 1600  |  Tags: greedy, trees, prime sieve
 * Topic  : Phase 3: Core Patterns > Advanced Greedy & Constructive
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * A tree of n nodes. Assign a positive integer to each edge such that each integer
 * is either 1 or a distinct prime, and we use exactly the first k primes (p1<p2<...<pk)
 * each at most once. Maximize the sum of f(u,v) over all pairs (u,v) where f(u,v)
 * is the product of edge values on the path from u to v.
 * Actually: maximize sum over all paths of the product of edge weights on the path?
 * That's very complex. Re-read: maximize sum over ALL edges of (edge_weight * times_edge_appears_in_paths).
 * Each edge appears in exactly cnt[e] * (n - cnt[e]) paths where cnt[e] = subtree size.
 * So contribution of edge e with weight w = w * cnt[e] * (n - cnt[e]).
 *
 * EXAMPLE
 * -------
 * Input:  4 2
 *         1 2
 *         1 3
 *         1 4
 * Output: 17
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. For each edge e, its "multiplier" = cnt[e] * (n - cnt[e]) where cnt[e] = size of subtree
 *    on one side when edge is removed. Compute these multipliers.
 * 2. Sort multipliers descending. Sort primes descending.
 * 3. Assign largest prime to largest multiplier. If more edges than primes, assign 1 to remaining.
 *    If more primes than edges, multiply the edge with smallest multiplier by remaining primes product.
 *    (Since we have to "use" all primes? Actually the problem says we can use each prime at most once,
 *    and we don't have to use all primes. Or do we? Let me assume we must use exactly k primes.)
 * 4. If k >= n-1 (more primes than edges): multiply leftover primes onto the biggest edge.
 *    Answer = sum(mult[i] * prime[i]) for i=0..n-2, plus extra primes multiplied into top edge.
 *    Extra primes product: product of primes[n-1..k-1] modulo MOD.
 *    Top edge gets: mult[0] * (prime[0] * prime[1] * ... * prime[k-(n-1)-1+0])?
 *    Actually: assign prime[0]*prime[1]*...*prime[j] to the edge with largest mult.
 *    Wait: we want to maximize. Optimal: assign as many large primes as possible to the largest
 *    multiplier edge. The rest get one prime each or 1.
 *    If k <= n-1: assign top-k primes to top-k multipliers. Rest get weight 1.
 *    If k >= n-1: top n-1 edges each get one prime (the largest). But leftover k-(n-1) primes
 *    must be assigned somewhere. Since each edge gets AT MOST one prime, leftover primes are wasted?
 *    Or can one edge get multiple primes? Re-read: "each integer is either 1 or a distinct prime".
 *    So each edge gets exactly one value: either 1 or one specific prime. Primes used at most once.
 *    If k > n-1: we can't use all primes. Use top n-1 primes.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log n + k)
 * Space: O(n + k)
 * ============================================================
 */

public class CF_1401D_MaximumDistributedTree {

    static final long MOD = 1_000_000_007L;
    static int n;
    static List<List<Integer>> adj;
    static long[] subtreeSize;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        // Sieve of primes
        final int MAXP = 200010;
        boolean[] isComposite = new boolean[MAXP];
        List<Long> primes = new ArrayList<>();
        for (int i = 2; i < MAXP; i++) {
            if (!isComposite[i]) {
                primes.add((long) i);
                for (int j = i * 2; j < MAXP; j += i) isComposite[j] = true;
            }
        }

        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());

            adj = new ArrayList<>();
            for (int i = 0; i <= n; i++) adj.add(new ArrayList<>());
            for (int i = 0; i < n - 1; i++) {
                st = new StringTokenizer(br.readLine());
                int u = Integer.parseInt(st.nextToken());
                int v = Integer.parseInt(st.nextToken());
                adj.get(u).add(v);
                adj.get(v).add(u);
            }

            // Compute subtree sizes via DFS from node 1
            subtreeSize = new long[n + 1];
            computeSubtreeSize(1, 0);

            // Multipliers for each edge (subtreeSize[child] * (n - subtreeSize[child]))
            List<Long> multipliers = new ArrayList<>();
            collectMultipliers(1, 0, multipliers);
            multipliers.sort(Collections.reverseOrder());

            // Assign primes to top multipliers
            int edges = multipliers.size(); // = n-1
            long ans = 0;

            if (k <= edges) {
                // Top k edges get primes, rest get 1
                for (int i = 0; i < edges; i++) {
                    long w = (i < k) ? primes.get(i) : 1L;
                    ans = (ans + multipliers.get(i) % MOD * (w % MOD)) % MOD;
                }
            } else {
                // k > edges: top edge gets product of primes[0..k-edges], rest get primes[k-edges+1..k-1]
                // Largest multiplier gets primes[0] * primes[1] * ... * primes[k-edges]
                long topProduct = 1;
                for (int i = 0; i <= k - edges; i++) {
                    topProduct = topProduct * (primes.get(i) % MOD) % MOD;
                }
                ans = multipliers.get(0) % MOD * topProduct % MOD;
                for (int i = 1; i < edges; i++) {
                    long w = primes.get(k - edges + i);
                    ans = (ans + multipliers.get(i) % MOD * (w % MOD)) % MOD;
                }
            }

            sb.append(ans).append('\n');
        }

        System.out.print(sb);
    }

    static void computeSubtreeSize(int u, int parent) {
        subtreeSize[u] = 1;
        for (int v : adj.get(u)) {
            if (v != parent) {
                computeSubtreeSize(v, u);
                subtreeSize[u] += subtreeSize[v];
            }
        }
    }

    static void collectMultipliers(int u, int parent, List<Long> multipliers) {
        for (int v : adj.get(u)) {
            if (v != parent) {
                long s = subtreeSize[v];
                multipliers.add(s * (n - s));
                collectMultipliers(v, u, multipliers);
            }
        }
    }
}
