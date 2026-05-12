package phases.phase5_cm_push.advanced_graphs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1328E — Tree Queries
 * Link   : https://codeforces.com/problemset/problem/1328/E
 * Rating : 1800  |  Tags: lca, dfs, trees
 * Topic  : Phase 5: CM Push > Advanced Graphs
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given a tree with n nodes rooted at node 1. Process q queries,
 * each giving a set of k nodes. Find if these k nodes can be
 * covered by a connected subtree of minimum number of edges.
 * Actually: find the minimum number of edges in a subtree that
 * contains all k given nodes (i.e., the Steiner tree size).
 * The answer is: sum of pairwise consecutive distances when nodes
 * are sorted by DFS order, divided by 2, but specifically:
 * sort nodes by DFS in-time, then the answer is
 * (sum of dist(v_i, v_{i+1}) for consecutive pairs + dist(v_k, v_1)) / 2
 * which equals (sum of all edges in the minimal Steiner tree) =
 * (sum_{consecutive} dist / 2).
 *
 * EXAMPLE
 * -------
 * Input:  7 2
 *         1 2
 * ...
 * Output: 2
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Preprocess: DFS order (in-time), depth, LCA with binary lifting.
 * 2. For each query with nodes v_1, ..., v_k:
 *    a. Sort nodes by DFS in-time.
 *    b. The minimal subtree (virtual tree) has edge count:
 *       [sum of dist(v_i, v_{i+1}) for i=1..k-1 + dist(v_k, v_1)] / 2
 *    c. dist(u, v) = depth[u] + depth[v] - 2*depth[LCA(u,v)]
 * 3. This formula gives the exact number of edges in the Steiner tree.
 * 4. LCA with binary lifting: O(n log n) preprocessing, O(log n) query.
 *
 * COMPLEXITY
 * ----------
 * Time : O((n + q*k) log n) where k is nodes per query
 * Space: O(n log n)
 * ============================================================
 */

public class CF_1328E_TreeQueries {

    static int LOG = 18;
    static int n;
    static int[] depth, tin;
    static int[][] up; // up[v][k] = 2^k-th ancestor of v
    static List<List<Integer>> adj;
    static int timer = 0;

    // Iterative DFS for LCA preprocessing
    static void dfs() {
        int[] stack = new int[n + 1];
        int[] parent = new int[n + 1];
        int[] childIdx = new int[n + 1];
        int top = 0;

        stack[top] = 1;
        parent[1] = 0;
        depth[1] = 0;
        up[1][0] = 1; // root's parent is itself

        while (top >= 0) {
            int u = stack[top];
            if (childIdx[u] == 0) {
                tin[u] = timer++;
                // Build binary lifting table
                for (int k = 1; k < LOG; k++) {
                    up[u][k] = up[up[u][k - 1]][k - 1];
                }
            }

            List<Integer> children = adj.get(u);
            boolean pushed = false;
            while (childIdx[u] < children.size()) {
                int v = children.get(childIdx[u]);
                childIdx[u]++;
                if (v != parent[u]) {
                    parent[v] = u;
                    depth[v] = depth[u] + 1;
                    up[v][0] = u;
                    stack[++top] = v;
                    pushed = true;
                    break;
                }
            }

            if (!pushed) {
                top--;
            }
        }
    }

    static int lca(int u, int v) {
        if (depth[u] < depth[v]) { int t = u; u = v; v = t; }
        // Bring u to same depth as v
        int diff = depth[u] - depth[v];
        for (int k = 0; k < LOG; k++) {
            if (((diff >> k) & 1) == 1) u = up[u][k];
        }
        if (u == v) return u;
        // Move both up together
        for (int k = LOG - 1; k >= 0; k--) {
            if (up[u][k] != up[v][k]) {
                u = up[u][k];
                v = up[v][k];
            }
        }
        return up[u][0];
    }

    static int dist(int u, int v) {
        return depth[u] + depth[v] - 2 * depth[lca(u, v)];
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) adj.add(new ArrayList<>());

        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        depth = new int[n + 1];
        tin = new int[n + 1];
        up = new int[n + 1][LOG];
        // Initialize root's ancestors to itself
        for (int i = 0; i <= n; i++) Arrays.fill(up[i], i == 0 ? 0 : i);
        up[1][0] = 1;

        dfs();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int k = Integer.parseInt(st.nextToken());
            int[] nodes = new int[k];
            for (int j = 0; j < k; j++) {
                nodes[j] = Integer.parseInt(st.nextToken());
            }

            if (k == 1) {
                sb.append(0).append('\n');
                continue;
            }

            // Sort by DFS in-time
            Integer[] idx = new Integer[k];
            for (int j = 0; j < k; j++) idx[j] = j;
            Arrays.sort(idx, (a, b) -> tin[nodes[a]] - tin[nodes[b]]);

            // Compute virtual tree edge count:
            // sum dist(v_i, v_{i+1}) for consecutive + dist(v_last, v_first), then / 2
            long total = 0;
            for (int j = 0; j < k - 1; j++) {
                total += dist(nodes[idx[j]], nodes[idx[j + 1]]);
            }
            total += dist(nodes[idx[k - 1]], nodes[idx[0]]);
            sb.append(total / 2).append('\n');
        }

        System.out.print(sb);
    }
}
