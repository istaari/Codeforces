package phases.phase3_core_patterns.graph_fundamentals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


/*
 * ============================================================
 * CF 1144F — Graph Without Long Directed Paths
 * Link   : https://codeforces.com/problemset/problem/1144/F
 * Rating : 1500  |  Tags: dfs, bipartite, 2-coloring
 * Topic  : Phase 3: Core Patterns > Graph Fundamentals
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given an undirected graph. Orient each edge such that there is no directed
 * path of length >= 2 (i.e., no directed path with 2 or more edges). This
 * means: if u → v, then v cannot have any outgoing edges.
 *
 * EXAMPLE
 * -------
 * Input:  3 3
 *         1 2
 *         2 3
 *         1 3
 * Output: Yes
 *         1 2 1
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. No directed path of length 2 means: for any edge u→v, all edges from v
 *    must point toward v (not away). Equivalently, in the orientation, no
 *    vertex can have both incoming and outgoing edges... no that's stronger.
 *    Actually: no vertex v can have an outgoing edge if it has an incoming edge.
 *    This means the edges form a "DAG of depth 1": all edges go from "level 0"
 *    to "level 1" nodes. Level 0 nodes have no incoming edges, level 1 nodes
 *    have no outgoing edges.
 * 2. This is exactly a 2-coloring (bipartite) condition! If we 2-color the graph
 *    (color 0 and 1), orient all edges from color-0 to color-1. Then no directed
 *    path of length 2 exists (path would need color-0 → color-1 → color-0, but
 *    edges only go color-0 → color-1).
 * 3. If the graph is bipartite: 2-color it, orient edges from partition 0 to 1.
 *    If not bipartite: impossible (output "No").
 *
 * COMPLEXITY
 * ----------
 * Time : O(n + m)
 * Space: O(n + m)
 * ============================================================
 */

public class CF_1144F_GraphWithoutLongDirectedPaths {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        List<List<int[]>> adj = new ArrayList<>(); // [neighbor, edgeIndex]
        for (int i = 0; i <= n; i++) adj.add(new ArrayList<>());

        int[] eu = new int[m], ev = new int[m];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            eu[i] = Integer.parseInt(st.nextToken());
            ev[i] = Integer.parseInt(st.nextToken());
            adj.get(eu[i]).add(new int[]{ev[i], i});
            adj.get(ev[i]).add(new int[]{eu[i], i});
        }

        int[] color = new int[n + 1];
        Arrays.fill(color, -1);
        int[] edgeDir = new int[m]; // 0 = eu[i]→ev[i], 1 = ev[i]→eu[i]
        boolean bipartite = true;

        for (int start = 1; start <= n && bipartite; start++) {
            if (color[start] != -1) continue;
            color[start] = 0;
            Queue<Integer> q = new ArrayDeque<>();
            q.add(start);
            while (!q.isEmpty() && bipartite) {
                int u = q.poll();
                for (int[] nb : adj.get(u)) {
                    int v = nb[0], ei = nb[1];
                    if (color[v] == -1) {
                        color[v] = 1 - color[u];
                        q.add(v);
                        // Orient edge from color-0 to color-1
                        if (color[u] == 0) edgeDir[ei] = 0; // u→v
                        else edgeDir[ei] = 1; // v→u
                    } else if (color[v] == color[u]) {
                        bipartite = false;
                    } else {
                        // Already colored correctly, just set direction
                        if (color[u] == 0) edgeDir[ei] = 0;
                        else edgeDir[ei] = 1;
                    }
                }
            }
        }

        if (!bipartite) {
            System.out.println("No");
            return;
        }

        System.out.println("Yes");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            if (i > 0) sb.append(' ');
            sb.append(edgeDir[i]);
        }
        System.out.println(sb);
    }
}
