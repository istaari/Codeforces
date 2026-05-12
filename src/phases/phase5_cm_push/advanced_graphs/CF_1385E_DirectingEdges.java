package phases.phase5_cm_push.advanced_graphs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1385E — Directing Edges
 * Link   : https://codeforces.com/problemset/problem/1385/E
 * Rating : 1800  |  Tags: graphs, topological sort, dfs
 * Topic  : Phase 5: CM Push > Advanced Graphs
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given a directed graph with n nodes and m edges.
 * Some edges are already directed. Some edges are undirected
 * (can be oriented either way). Orient all undirected edges
 * such that the resulting directed graph is a DAG (no cycles).
 * If possible, print the edges with their chosen orientations.
 * Otherwise print -1.
 *
 * EXAMPLE
 * -------
 * Input:  3 2
 *         1 1 2
 *         1 2 3
 * Output: 1 2
 *         2 3
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. First, find a topological ordering of all nodes using only
 *    the already-directed edges (detect cycle → output -1).
 * 2. For undirected edges (u, v): orient them according to the
 *    topological order — if topo_pos[u] < topo_pos[v], orient
 *    u→v; else orient v→u.
 * 3. This always works because: the directed edges define a
 *    partial order. If they contain a cycle → -1. Otherwise,
 *    topological sort gives a linear extension. Undirected edges
 *    oriented with this order cannot create new cycles (they go
 *    strictly in one direction in the topological order).
 * 4. Topological sort via DFS: color nodes white/gray/black.
 *    If DFS reaches a gray node → cycle detected.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n + m)
 * Space: O(n + m)
 * ============================================================
 */

public class CF_1385E_DirectingEdges {

    static List<List<Integer>> adj;
    static int[] color; // 0=white, 1=gray, 2=black
    static int[] topoOrder;
    static int topoIdx;
    static boolean hasCycle;

    static void dfs(int u) {
        color[u] = 1; // gray (in progress)
        for (int v : adj.get(u)) {
            if (color[v] == 1) {
                hasCycle = true;
                return;
            }
            if (color[v] == 0) {
                dfs(v);
                if (hasCycle) return;
            }
        }
        color[u] = 2; // black (done)
        topoOrder[topoIdx--] = u;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) adj.add(new ArrayList<>());

        // Store undirected edges separately
        List<int[]> undirected = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int type = Integer.parseInt(st.nextToken());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            if (type == 1) {
                // Directed edge u -> v
                adj.get(u).add(v);
            } else {
                // Undirected edge between u and v
                undirected.add(new int[]{u, v});
            }
        }

        // Topological sort using only directed edges
        color = new int[n + 1];
        topoOrder = new int[n + 1];
        topoIdx = n - 1;
        hasCycle = false;

        for (int i = 1; i <= n; i++) {
            if (color[i] == 0) {
                dfs(i);
                if (hasCycle) {
                    System.out.println(-1);
                    return;
                }
            }
        }

        // topo_pos[node] = position in topological order (smaller = earlier)
        int[] topoPos = new int[n + 1];
        for (int i = 0; i < n; i++) {
            topoPos[topoOrder[i]] = i;
        }

        // Output: first directed edges (they were added as given)
        // Then orient undirected edges by topo order
        StringBuilder sb = new StringBuilder();

        // Print all directed edges
        for (int u = 1; u <= n; u++) {
            for (int v : adj.get(u)) {
                sb.append(u).append(' ').append(v).append('\n');
            }
        }

        // Orient undirected edges
        for (int[] e : undirected) {
            int u = e[0], v = e[1];
            if (topoPos[u] < topoPos[v]) {
                sb.append(u).append(' ').append(v).append('\n');
            } else {
                sb.append(v).append(' ').append(u).append('\n');
            }
        }

        System.out.print(sb);
    }
}
