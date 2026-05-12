package phases.phase3_core_patterns.graph_fundamentals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


/*
 * ============================================================
 * CF 1092E — Minimal Diameter Forest
 * Link   : https://codeforces.com/problemset/problem/1092/E
 * Rating : 1600  |  Tags: trees, bfs, greedy
 * Topic  : Phase 3: Core Patterns > Graph Fundamentals
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given a forest of k trees (n total nodes). Add exactly k-1 edges to connect
 * all trees into one, minimizing the diameter of the resulting tree.
 *
 * EXAMPLE
 * -------
 * Input:  6 2
 *         1 2
 *         2 3
 *         4 5
 * Output: 3
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. For each tree, compute its diameter and its center (middle node of
 *    the diameter path, which minimizes the eccentricity = tree radius).
 *    Center is 1 or 2 nodes at distance floor(diameter/2) from each end.
 * 2. To minimize diameter when connecting trees: connect each tree's center
 *    to the center of another tree. The resulting diameter is at most
 *    max(d1, d2, ceil(d1/2)+ceil(d2/2)+1) for two trees with diameters d1, d2.
 * 3. When connecting k trees: connect all centers to the center of the largest
 *    diameter tree (tree with largest radius). The diameter of the result is
 *    max(max_diameter, max_radius1 + max_radius2 + 1, max_radius2 + max_radius3 + 2)
 *    where radii are sorted descending.
 * 4. Strategy: sort trees by radius descending. Connect all to tree[0]'s center.
 *    Resulting diameter = max(d[0], r[0]+r[1]+1, r[1]+r[2]+2)?
 *    Simpler: when connecting c1 to c2 (centers), new diameter could be
 *    max(d1, d2, r1 + r2 + 1). When connecting 3 trees via star at c1:
 *    max(d1, r1+r2+1, r1+r3+1, r2+r3+2)?? No: connecting c2 and c3 to c1:
 *    diameter = max(d1, d2, d3, r1+r2+1, r1+r3+1, r2+2+r3).
 * 5. Sort trees by radius descending: r[0] >= r[1] >= r[2] >= ...
 *    Connect all to tree[0]'s center. Diameter = max(d[0], r[0]+r[1]+1,
 *    r[1]+r[2]+2 if there are >= 3 trees? No—connecting c2 and c3 to c1:
 *    path from deepest in tree2 to deepest in tree3 goes through c1:
 *    length = r[1] + 1 + r[2] (? or r[1]+1+1+r[2]?). It goes:
 *    deepest in T2 → c2 → c1 → c3 → deepest in T3 = r2 + 1 + 1 + r3.
 *    So diameter = max(d[0], r[0]+r[1]+1, r[0]+r[2]+1, r[1]+2+r[2]).
 *    = max(d[0], r[0]+r[1]+1, r[1]+r[2]+2) [since r[0]>=r[1]>=r[2]].
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1092E_MinimalDiameterForest {

    static List<List<Integer>> adj;
    static int n;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) adj.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        // Find all trees (connected components)
        boolean[] visited = new boolean[n + 1];
        List<int[]> trees = new ArrayList<>(); // {diameter, radius} per tree

        for (int start = 1; start <= n; start++) {
            if (!visited[start]) {
                // Find diameter: BFS from start to find farthest, BFS again from farthest
                int far1 = bfsFarthest(start, visited, false);
                int far2 = bfsFarthest(far1, null, false);
                // Distance between far1 and far2 = diameter
                int[] dist = bfsDist(far1);
                int diameter = dist[far2];
                int radius = (diameter + 1) / 2;
                trees.add(new int[]{diameter, radius});
                // Mark all nodes in this tree as visited
                bfsFarthest(start, visited, true);
            }
        }

        if (trees.size() == 1) {
            System.out.println(trees.get(0)[0]);
            return;
        }

        // Sort by radius descending
        trees.sort((a, b) -> b[1] - a[1]);

        int k = trees.size();
        int ans = trees.get(0)[0]; // diameter of largest tree
        ans = Math.max(ans, trees.get(0)[1] + trees.get(1)[1] + 1);
        if (k >= 3) ans = Math.max(ans, trees.get(1)[1] + trees.get(2)[1] + 2);

        System.out.println(ans);
    }

    static int bfsFarthest(int start, boolean[] visited, boolean markVisited) {
        boolean[] seen = new boolean[n + 1];
        Queue<Integer> q = new ArrayDeque<>();
        q.add(start);
        seen[start] = true;
        if (markVisited && visited != null) visited[start] = true;
        int last = start;
        while (!q.isEmpty()) {
            int u = q.poll();
            last = u;
            for (int nb : adj.get(u)) {
                if (!seen[nb]) {
                    seen[nb] = true;
                    if (markVisited && visited != null) visited[nb] = true;
                    q.add(nb);
                }
            }
        }
        return last;
    }

    static int[] bfsDist(int start) {
        int[] dist = new int[n + 1];
        Arrays.fill(dist, -1);
        Queue<Integer> q = new ArrayDeque<>();
        q.add(start);
        dist[start] = 0;
        while (!q.isEmpty()) {
            int u = q.poll();
            for (int nb : adj.get(u)) {
                if (dist[nb] == -1) {
                    dist[nb] = dist[u] + 1;
                    q.add(nb);
                }
            }
        }
        return dist;
    }
}
