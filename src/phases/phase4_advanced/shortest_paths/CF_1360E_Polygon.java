package phases.phase4_advanced.shortest_paths;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1360E — Polygon
 * Link   : https://codeforces.com/problemset/problem/1360/E
 * Rating : 1500  |  Tags: graphs, implementation, geometry
 * Topic  : Phase 4: Advanced > Shortest Paths
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given a convex n-gon (polygon) with vertices 1..n. Two vertices are
 * "visible" to each other if the segment between them is either a side
 * or a diagonal of the polygon that doesn't cross any given diagonal.
 * Actually: given a set of diagonals. For each vertex, find the nearest
 * visible vertex (smallest absolute difference in index, wrapping around).
 *
 * EXAMPLE
 * -------
 * Input:  n=4, diagonals: (1,3)
 * Output: 1 2 3 4  (each vertex sees its neighbors, vertex 1 also sees 3 via diagonal)
 *         Nearest to 1: 2 (dist 1); nearest to 3: 2 or 4 (dist 1).
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Build a graph where vertices i and j are connected if they are adjacent
 *    in the polygon OR if there's a diagonal between them.
 * 2. Run BFS from each vertex on this graph.
 * 3. But n can be large. Instead, note that adjacent vertices (i, i+1 mod n)
 *    are always visible. Diagonals add extra direct connections.
 * 4. The "nearest visible" is the minimum graph distance (1 hop) = direct neighbor.
 *    So: for each vertex v, the nearest visible vertex is simply the closest one
 *    connected by an edge (side or given diagonal). Since sides connect i to i+1 and i-1,
 *    and diagonals connect specific pairs, the nearest is always at distance 1.
 *    Just find the minimum label difference among all direct connections.
 * 5. For each vertex, output the nearest vertex by polygon distance (index difference
 *    considering circular arrangement).
 *
 * COMPLEXITY
 * ----------
 * Time : O(n + m) where m = number of diagonals
 * Space: O(n + m)
 * ============================================================
 */

public class CF_1360E_Polygon {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        List<Integer>[] adj = new List[n + 1];
        for (int i = 1; i <= n; i++) adj[i] = new ArrayList<>();

        // Add polygon sides
        for (int i = 1; i <= n; i++) {
            int next = (i % n) + 1;
            adj[i].add(next);
            adj[next].add(i);
        }

        // Add diagonals
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            adj[u].add(v);
            adj[v].add(u);
        }

        // For each vertex, find nearest visible vertex
        // "nearest" = minimum polygon distance (min |i-j| or n-|i-j|) among direct neighbors
        StringBuilder sb = new StringBuilder();
        for (int v = 1; v <= n; v++) {
            int minDist = Integer.MAX_VALUE;
            int nearest = -1;
            for (int u : adj[v]) {
                if (u == v) continue;
                int diff = Math.abs(u - v);
                int polyDist = Math.min(diff, n - diff);
                if (polyDist < minDist) {
                    minDist = polyDist;
                    nearest = u;
                }
            }
            sb.append(nearest);
            if (v < n) sb.append(' ');
        }
        System.out.println(sb);
    }
}
