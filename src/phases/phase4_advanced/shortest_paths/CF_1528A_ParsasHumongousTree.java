package phases.phase4_advanced.shortest_paths;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1528A — Parsa's Humongous Tree
 * Link   : https://codeforces.com/problemset/problem/1528/A
 * Rating : 1800  |  Tags: dp, trees
 * Topic  : Phase 4: Advanced > Shortest Paths
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Tree with n nodes. Each node i can be assigned a value from [l[i], r[i]].
 * The score is the sum of |val[u] - val[v]| over all edges (u,v).
 * Maximize the total score.
 *
 * EXAMPLE
 * -------
 * Input:  n=2, l=[1,3], r=[4,8], edge: 1-2
 * Output: 7  (assign 1 to node 1, 8 to node 2: |1-8|=7)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. For each edge (u,v), the contribution |val[u]-val[v]| is maximized when
 *    one endpoint takes its min and the other takes its max.
 * 2. Tree DP: dp[v][0] = max score in subtree of v when v is assigned l[v] (minimum).
 *             dp[v][1] = max score in subtree of v when v is assigned r[v] (maximum).
 * 3. For each child c of v:
 *    dp[v][0] += max(dp[c][0] + |l[v]-l[c]|, dp[c][1] + |l[v]-r[c]|)
 *    dp[v][1] += max(dp[c][0] + |r[v]-l[c]|, dp[c][1] + |r[v]-r[c]|)
 * 4. Answer = max(dp[root][0], dp[root][1]).
 * 5. Use iterative DFS to avoid stack overflow.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1528A_ParsasHumongousTree {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            long[] l = new long[n + 1], r = new long[n + 1];
            for (int i = 1; i <= n; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                l[i] = Long.parseLong(st.nextToken());
                r[i] = Long.parseLong(st.nextToken());
            }

            List<Integer>[] adj = new List[n + 1];
            for (int i = 1; i <= n; i++) adj[i] = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                int u = Integer.parseInt(st.nextToken());
                int v = Integer.parseInt(st.nextToken());
                adj[u].add(v);
                adj[v].add(u);
            }

            long[] dp0 = new long[n + 1]; // assign l[v]
            long[] dp1 = new long[n + 1]; // assign r[v]
            int[] parent = new int[n + 1];
            int[] order = new int[n];
            int idx = 0;
            boolean[] vis = new boolean[n + 1];
            int[] stack = new int[n + 1];
            int top = 0;
            stack[top++] = 1;
            vis[1] = true;
            parent[1] = 0;

            while (top > 0) {
                int u = stack[--top];
                order[idx++] = u;
                for (int v : adj[u]) {
                    if (!vis[v]) {
                        vis[v] = true;
                        parent[v] = u;
                        stack[top++] = v;
                    }
                }
            }

            // Process in reverse BFS order
            for (int i = idx - 1; i >= 0; i--) {
                int v = order[i];
                dp0[v] = 0; dp1[v] = 0;
                for (int c : adj[v]) {
                    if (c == parent[v]) continue;
                    // child c
                    dp0[v] += Math.max(dp0[c] + Math.abs(l[v] - l[c]),
                                       dp1[c] + Math.abs(l[v] - r[c]));
                    dp1[v] += Math.max(dp0[c] + Math.abs(r[v] - l[c]),
                                       dp1[c] + Math.abs(r[v] - r[c]));
                }
            }

            sb.append(Math.max(dp0[1], dp1[1])).append('\n');
        }

        System.out.print(sb);
    }
}
