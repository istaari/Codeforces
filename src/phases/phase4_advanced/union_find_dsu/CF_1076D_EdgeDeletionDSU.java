package phases.phase4_advanced.union_find_dsu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1076D — Edge Deletion (DSU Version)
 * Link   : https://codeforces.com/problemset/problem/1076/D
 * Rating : 1800  |  Tags: DSU, shortest path, greedy
 * Topic  : Phase 4: Advanced > Union Find / DSU
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Same as shortest_paths version: weighted graph, keep maximum edges that lie
 * on some shortest path from vertex 1. Use Dijkstra then filter edges on shortest paths.
 * This version emphasizes the DSU perspective for organizing edge verification.
 *
 * EXAMPLE
 * -------
 * Input:  n=3, m=3, edges: (1,2,1),(2,3,1),(1,3,3)
 * Output: 2 edges (the two weight-1 edges)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Run Dijkstra from vertex 1 to get shortest distances d[].
 * 2. Edge (u, v, w) is on a shortest path iff d[u] + w == d[v] or d[v] + w == d[u].
 * 3. Keep all such edges. This is the "shortest path DAG/graph".
 * 4. DSU optional: could use DSU to identify which vertices are reachable via shortest
 *    path edges, but the simple filter approach is sufficient.
 *
 * COMPLEXITY
 * ----------
 * Time : O((V+E) log V)
 * Space: O(V+E)
 * ============================================================
 */

public class CF_1076D_EdgeDeletionDSU {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[] eu = new int[m], ev = new int[m], ew = new int[m];
        List<int[]>[] adj = new List[n + 1];
        for (int i = 1; i <= n; i++) adj[i] = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            eu[i] = Integer.parseInt(st.nextToken());
            ev[i] = Integer.parseInt(st.nextToken());
            ew[i] = Integer.parseInt(st.nextToken());
            adj[eu[i]].add(new int[]{ev[i], ew[i], i});
            adj[ev[i]].add(new int[]{eu[i], ew[i], i});
        }

        long[] d = new long[n + 1];
        Arrays.fill(d, Long.MAX_VALUE);
        d[1] = 0;
        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));
        pq.offer(new long[]{0, 1});

        while (!pq.isEmpty()) {
            long[] cur = pq.poll();
            long dist = cur[0]; int u = (int) cur[1];
            if (dist > d[u]) continue;
            for (int[] e : adj[u]) {
                int v = e[0], w = e[1];
                if (d[u] + w < d[v]) {
                    d[v] = d[u] + w;
                    pq.offer(new long[]{d[v], v});
                }
            }
        }

        // DSU: union vertices connected by shortest-path edges
        int[] parent = new int[n + 1];
        for (int i = 1; i <= n; i++) parent[i] = i;

        List<Integer> kept = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            if (d[eu[i]] != Long.MAX_VALUE && d[ev[i]] != Long.MAX_VALUE) {
                if (d[eu[i]] + ew[i] == d[ev[i]] || d[ev[i]] + ew[i] == d[eu[i]]) {
                    kept.add(i + 1);
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append(kept.size()).append('\n');
        for (int idx : kept) sb.append(idx).append(' ');
        System.out.println(sb);
    }
}
