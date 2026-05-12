package phases.phase5_cm_push.advanced_graphs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1245D — Shichikuji and Power Grid
 * Link   : https://codeforces.com/problemset/problem/1245/D
 * Rating : 1700  |  Tags: mst, graphs, dsu
 * Topic  : Phase 5: CM Push > Advanced Graphs
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given n cities on a 2D grid. You can either:
 *   (a) Build a generator at city i with cost p[i].
 *   (b) Connect city i and j with a power cable at cost
 *       c[i][j] = (|xi-xj| + |yi-yj|) * (q[i] + q[j]).
 * All cities must be powered (reachable from some generator).
 * Find the minimum total cost.
 *
 * EXAMPLE
 * -------
 * Input:  3
 *         2 1
 *         1 2
 *         3 3
 *         3 2 3
 *         3 1 2 1
 *           2 1
 *             1
 * Output: 7
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Model as MST with a virtual node 0 representing "powered".
 * 2. Edge from virtual node 0 to city i costs p[i] (build generator).
 * 3. Edge from city i to city j costs (|xi-xj|+|yi-yj|)*(q[i]+q[j]).
 * 4. Run Kruskal's or Prim's on this augmented graph.
 * 5. The MST of this (n+1)-node graph gives minimum cost to
 *    connect all cities to at least one generator.
 * 6. Use Prim's with adjacency for dense graphs (n ≤ 2000).
 *
 * COMPLEXITY
 * ----------
 * Time : O(n^2) for Prim's on dense graph
 * Space: O(n^2) for edge list
 * ============================================================
 */

public class CF_1245D_ShichikujiAndPowerGrid {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());

        int[] x = new int[n + 1], y = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            x[i] = Integer.parseInt(st.nextToken());
            y[i] = Integer.parseInt(st.nextToken());
        }

        long[] p = new long[n + 1]; // generator cost at city i
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            p[i] = Long.parseLong(st.nextToken());
        }

        long[] q = new long[n + 1]; // cable cost multiplier at city i
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            q[i] = Long.parseLong(st.nextToken());
        }

        // Prim's algorithm on (n+1) nodes: 0 is virtual "power source"
        // dist[i] = min edge cost to connect city i to current MST
        long[] dist = new long[n + 1];
        boolean[] inMST = new boolean[n + 1];
        Arrays.fill(dist, Long.MAX_VALUE);

        // Virtual node 0 connects to each city i with cost p[i]
        for (int i = 1; i <= n; i++) {
            dist[i] = p[i];
        }

        long totalCost = 0;

        // Prim's: pick the minimum-cost node n times
        for (int iter = 0; iter < n; iter++) {
            // Find the city not in MST with minimum dist
            int u = -1;
            for (int i = 1; i <= n; i++) {
                if (!inMST[i] && (u == -1 || dist[i] < dist[u])) {
                    u = i;
                }
            }

            inMST[u] = true;
            totalCost += dist[u];

            // Relax edges from u to all other non-MST cities
            for (int v = 1; v <= n; v++) {
                if (!inMST[v]) {
                    long edgeCost = (long)(Math.abs(x[u] - x[v]) + Math.abs(y[u] - y[v])) * (q[u] + q[v]);
                    if (edgeCost < dist[v]) {
                        dist[v] = edgeCost;
                    }
                }
            }
        }

        System.out.println(totalCost);
    }
}
