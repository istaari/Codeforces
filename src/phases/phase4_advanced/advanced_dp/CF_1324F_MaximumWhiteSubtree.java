package phases.phase4_advanced.advanced_dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1324F — Maximum White Subtree
 * Link   : https://codeforces.com/problemset/problem/1324/F
 * Rating : 1800  |  Tags: dp, trees, rerooting
 * Topic  : Phase 4: Advanced > Advanced DP
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given a tree with n nodes. Each node is colored white (1) or black (0).
 * For each node v, find the maximum value of (white nodes - black nodes)
 * in any connected subgraph containing v. Output n values.
 *
 * EXAMPLE
 * -------
 * Input:  n=9, colors=[0,1,1,1,1,0,0,0,1], edges form a tree
 * Output: 2 2 2 2 2 2 0 0 2
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. First DFS (root at 1): compute down[v] = max (white-black) score
 *    achievable in v's subtree including v.
 *    down[v] = (color[v]==1 ? 1 : -1) + sum over children c of max(0, down[c])
 * 2. Second DFS (rerooting): compute ans[v] considering the "up" direction.
 *    up[v] = max (white-black) from v going upward through parent.
 *    ans[v] = (color[v]==1?1:-1) + sum of max(0, down[c]) for all children c
 *             + max(0, up[v])
 *    up[child] = (color[parent]==1?1:-1) + max(0, up[parent])
 *              + sum of max(0, down[c]) for all other children c
 *              (i.e., total down contribution of parent minus child's contribution)
 * 3. During rerooting, carefully subtract child's contribution from parent's total.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1324F_MaximumWhiteSubtree {

    static int[] color;
    static int[] down;
    static int[] ans;
    static List<Integer>[] adj;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        StringTokenizer st = new StringTokenizer(br.readLine());
        color = new int[n + 1];
        for (int i = 1; i <= n; i++) color[i] = Integer.parseInt(st.nextToken());

        adj = new List[n + 1];
        for (int i = 1; i <= n; i++) adj[i] = new ArrayList<>();
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            adj[u].add(v);
            adj[v].add(u);
        }

        down = new int[n + 1];
        ans = new int[n + 1];

        // DFS1: compute down values (iterative to avoid stack overflow)
        int[] parent = new int[n + 1];
        int[] order = new int[n];
        int orderIdx = 0;
        parent[1] = -1;
        int[] stack = new int[n + 1];
        int top = 0;
        stack[top++] = 1;
        boolean[] visited = new boolean[n + 1];
        visited[1] = true;
        while (top > 0) {
            int u = stack[--top];
            order[orderIdx++] = u;
            for (int v : adj[u]) {
                if (!visited[v]) {
                    visited[v] = true;
                    parent[v] = u;
                    stack[top++] = v;
                }
            }
        }

        // Process in reverse order for down DP
        for (int i = orderIdx - 1; i >= 0; i--) {
            int u = order[i];
            down[u] = (color[u] == 1 ? 1 : -1);
            for (int v : adj[u]) {
                if (v != parent[u]) {
                    down[u] += Math.max(0, down[v]);
                }
            }
        }

        // DFS2: rerooting for ans
        // up[v] = contribution from going UP through parent
        int[] up = new int[n + 1];
        up[1] = 0;

        for (int i = 0; i < orderIdx; i++) {
            int u = order[i];
            // Compute ans[u]
            int base = (color[u] == 1 ? 1 : -1);
            int sumChildren = 0;
            for (int v : adj[u]) {
                if (v != parent[u]) sumChildren += Math.max(0, down[v]);
            }
            ans[u] = base + sumChildren + Math.max(0, up[u]);

            // Compute up for each child v
            for (int v : adj[u]) {
                if (v != parent[u]) {
                    // up[v] = base(u) + max(0, up[u]) + sumChildren - max(0, down[v])
                    up[v] = base + Math.max(0, up[u]) + sumChildren - Math.max(0, down[v]);
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            sb.append(ans[i]);
            if (i < n) sb.append(' ');
        }
        System.out.println(sb);
    }
}
