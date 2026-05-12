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
 * CF 1343E — Weights Distributing
 * Link   : https://codeforces.com/problemset/problem/1343/E
 * Rating : 1800  |  Tags: BFS, trees, shortest path
 * Topic  : Phase 4: Advanced > Shortest Paths
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Unweighted tree with n nodes. Given m distinct weights w[0] <= w[1] <= ... <= w[m-1].
 * Assign weights to EDGES. Source s, two destinations a and b.
 * Minimize sum of path weights from s->a and s->b, where each weight used at most once
 * on edges on these paths. The paths share the prefix from s to LCA(a,b) w.r.t. s.
 *
 * EXAMPLE
 * -------
 * Input:  n=4, m=4, s=1, a=3, b=4, weights=[1,2,3,4], edges: 1-2,2-3,2-4
 * Output: 9
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. BFS from s, a, b to get dist_s[], dist_a[], dist_b[] (hop counts).
 * 2. The optimal strategy: let the common path s->v have length d_sv hops.
 *    Then s->a uses (d_sv + dist_a[v]) edges = total path length.
 *    Similarly s->b uses (d_sv + dist_b[v]) edges.
 *    Total edges used = d_sv + dist_a[v] + dist_b[v] (if paths through v share prefix).
 *    But we can only use an edge once in the shared prefix.
 *    Actual edge count = dist_s[v] + dist_a[v] + dist_b[v] - (we double-count shared edges?).
 *    No: d_sv edges are shared (counted once), then dist_a[v] and dist_b[v] separate edges.
 *    Total distinct edges = dist_s[v] + dist_a[v] + dist_b[v].
 * 3. For each candidate branch point v, total edges = dist_s[v] + dist_a[v] + dist_b[v].
 *    Assign the smallest weights to these edges. Cost = sum of top-k smallest weights.
 * 4. Precompute prefix sums of sorted weights. For each v, cost = prefix[dist_s[v] + dist_a[v] + dist_b[v]].
 * 5. Minimize over all v.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1343E_WeightsDistributing {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            long[] w = new long[m];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < m; i++) w[i] = Long.parseLong(st.nextToken());
            Arrays.sort(w);

            long[] prefix = new long[m + 1];
            for (int i = 0; i < m; i++) prefix[i + 1] = prefix[i] + w[i];

            List<Integer>[] adj = new List[n + 1];
            for (int i = 1; i <= n; i++) adj[i] = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) {
                st = new StringTokenizer(br.readLine());
                int u = Integer.parseInt(st.nextToken());
                int v = Integer.parseInt(st.nextToken());
                adj[u].add(v);
                adj[v].add(u);
            }

            int[] distS = bfs(s, n, adj);
            int[] distA = bfs(a, n, adj);
            int[] distB = bfs(b, n, adj);

            long ans = Long.MAX_VALUE;
            for (int v = 1; v <= n; v++) {
                int total = distS[v] + distA[v] + distB[v];
                if (total <= m) {
                    ans = Math.min(ans, prefix[total]);
                }
            }

            sb.append(ans).append('\n');
        }

        System.out.print(sb);
    }

    static int[] bfs(int src, int n, List<Integer>[] adj) {
        int[] dist = new int[n + 1];
        Arrays.fill(dist, -1);
        dist[src] = 0;
        Queue<Integer> q = new ArrayDeque<>();
        q.offer(src);
        while (!q.isEmpty()) {
            int u = q.poll();
            for (int v : adj[u]) {
                if (dist[v] == -1) {
                    dist[v] = dist[u] + 1;
                    q.offer(v);
                }
            }
        }
        return dist;
    }
}
