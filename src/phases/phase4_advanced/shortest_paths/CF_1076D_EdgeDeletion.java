package phases.phase4_advanced.shortest_paths;

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
 * CF 1076D — Edge Deletion
 * Link   : https://codeforces.com/problemset/problem/1076/D
 * Rating : 1800  |  Tags: dijkstra, greedy, graphs
 * Topic  : Phase 4: Advanced > Shortest Paths
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Weighted undirected graph with n vertices and m edges. Remove as few
 * edges as possible such that every remaining edge lies on at least one
 * shortest path from vertex 1 to some vertex. Equivalently, keep the
 * maximum number of edges such that each kept edge (u,v,w) satisfies
 * d[u] + w = d[v] or d[v] + w = d[u] (where d = shortest distances from 1).
 *
 * EXAMPLE
 * -------
 * Input:  n=3, m=3, edges: (1,2,1),(2,3,1),(1,3,3)
 * Output: keep edges (1,2,1) and (2,3,1); remove (1,3,3) since d[1]+3=3=d[3] -> keep it too? Actually d[3]=2.
 *         d[3]=2, edge(1,3,3): d[1]+3=3 != 2=d[3]. So remove it. Keep 2 edges.
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Run Dijkstra from vertex 1 to get d[v] for all vertices.
 * 2. For each edge (u, v, w): keep it if d[u] + w == d[v] OR d[v] + w == d[u].
 * 3. Count kept edges, output that count then the edge indices.
 *
 * COMPLEXITY
 * ----------
 * Time : O((V+E) log V)
 * Space: O(V+E)
 * ============================================================
 */

public class CF_1076D_EdgeDeletion {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[] eu = new int[m], ev = new int[m], ew = new int[m];
        List<int[]>[] adj = new List[n + 1]; // {neighbor, weight, edgeIdx}
        for (int i = 1; i <= n; i++) adj[i] = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            eu[i] = Integer.parseInt(st.nextToken());
            ev[i] = Integer.parseInt(st.nextToken());
            ew[i] = Integer.parseInt(st.nextToken());
            adj[eu[i]].add(new int[]{ev[i], ew[i], i});
            adj[ev[i]].add(new int[]{eu[i], ew[i], i});
        }

        // Dijkstra from vertex 1
        long[] d = new long[n + 1];
        Arrays.fill(d, Long.MAX_VALUE);
        d[1] = 0;
        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));
        pq.offer(new long[]{0, 1});

        while (!pq.isEmpty()) {
            long[] cur = pq.poll();
            long dist = cur[0];
            int u = (int) cur[1];
            if (dist > d[u]) continue;
            for (int[] e : adj[u]) {
                int v = e[0], w = e[1];
                if (d[u] + w < d[v]) {
                    d[v] = d[u] + w;
                    pq.offer(new long[]{d[v], v});
                }
            }
        }

        // Keep edges on shortest paths
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
