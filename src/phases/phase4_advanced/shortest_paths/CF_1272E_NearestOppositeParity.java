package phases.phase4_advanced.shortest_paths;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1272E — Nearest Opposite Parity
 * Link   : https://codeforces.com/problemset/problem/1272/E
 * Rating : 1700  |  Tags: BFS, reverse graph, parity
 * Topic  : Phase 4: Advanced > Shortest Paths
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Array a[] of n integers. From index i, you can jump to i+a[i] or i-a[i]
 * (if within bounds). For each index i, find the minimum number of jumps
 * to reach an index j where a[j] has a different parity than a[i].
 * Output -1 if impossible.
 *
 * EXAMPLE
 * -------
 * Input:  n=5, a=[1,4,2,3,1]
 * Output: 1 -1 3 1 1
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Build the directed jump graph: i -> i+a[i] and i -> i-a[i].
 * 2. Build the REVERSE graph: edge j -> i if original has i -> j.
 * 3. Multi-source BFS on reverse graph:
 *    - Source set for "even": all odd-parity nodes reachable from even nodes in 1 step.
 *      Actually: for each i, find if any direct neighbor has different parity -> dist[i]=1.
 *    - For nodes without direct different-parity neighbor, BFS on reverse graph propagates.
 * 4. Two separate BFS runs: one for even-parity nodes (finding nearest odd), one for odd.
 * 5. Initialize BFS with all nodes that directly reach a different-parity node (dist=1).
 *    Then on reverse graph, if node k can reach node i in original graph (i->k), and
 *    dist[k] is known, then dist[i] = dist[k] + 1 (if not yet set).
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1272E_NearestOppositeParity {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        int[] a = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) a[i] = Integer.parseInt(st.nextToken());

        // Build reverse graph
        List<Integer>[] rev = new List[n];
        for (int i = 0; i < n; i++) rev[i] = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int j1 = i + a[i];
            int j2 = i - a[i];
            if (j1 < n) rev[j1].add(i); // i -> j1 means rev: j1 -> i
            if (j2 >= 0) rev[j2].add(i);
        }

        int[] dist = new int[n];
        Arrays.fill(dist, -1);

        // Two BFS: one for even parity indices, one for odd parity indices
        // For even parity indices: find nearest odd parity
        // For odd parity indices: find nearest even parity
        // We run BFS for each parity group separately using reverse graph

        // BFS for parity p: find dist from each node of parity p to nearest node of parity 1-p
        int[] result = new int[n];
        Arrays.fill(result, -1);

        for (int parity = 0; parity <= 1; parity++) {
            int[] d = new int[n];
            Arrays.fill(d, -1);
            Queue<Integer> queue = new ArrayDeque<>();

            // Initialize: nodes of parity p that directly jump to a node of parity 1-p
            for (int i = 0; i < n; i++) {
                if (a[i] % 2 != parity) continue; // only nodes with this parity
                int j1 = i + a[i];
                int j2 = i - a[i];
                if ((j1 < n && a[j1] % 2 != parity) || (j2 >= 0 && a[j2] % 2 != parity)) {
                    d[i] = 1;
                    queue.offer(i);
                }
            }

            // BFS on reverse graph for same-parity nodes
            while (!queue.isEmpty()) {
                int u = queue.poll();
                for (int v : rev[u]) {
                    if (a[v] % 2 == parity && d[v] == -1) {
                        d[v] = d[u] + 1;
                        queue.offer(v);
                    }
                }
            }

            // Write results for parity p nodes
            for (int i = 0; i < n; i++) {
                if (a[i] % 2 == parity) result[i] = d[i];
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(result[i]);
            if (i < n - 1) sb.append(' ');
        }
        System.out.println(sb);
    }
}
