package phases.phase4_advanced.shortest_paths;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;


/*
 * ============================================================
 * CF 1243D — 0-1 MST
 * Link   : https://codeforces.com/problemset/problem/1243/D
 * Rating : 1800  |  Tags: BFS, complement graph, MST
 * Topic  : Phase 4: Advanced > Shortest Paths
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * n nodes. Complement graph: edge weight 0 between every pair NOT in given
 * edge set, weight 1 for every edge in given edge set. Find MST weight of
 * this complement graph.
 *
 * EXAMPLE
 * -------
 * Input:  n=5, m=4, edges: (1,2),(2,3),(3,4),(4,5)
 * Output: 0  (can connect all via complement edges (0-weight))
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. MST of complement graph with 0/1 weights: the MST weight equals
 *    n - 1 - (number of 0-weight edges used in spanning tree) effectively,
 *    or simpler: count connected components reachable using only 0-weight edges.
 *    MST weight = (number of components via 0-edges) - 1.
 * 2. 0-weight edges = all edges NOT in given set. So we need to find
 *    connected components of the complement graph (using only non-given edges).
 * 3. BFS on complement graph: maintain set of unvisited nodes. For each BFS step
 *    from node u, visit all unvisited nodes EXCEPT those connected to u by a
 *    given (weight-1) edge.
 * 4. Use a sorted set of unvisited nodes. For each u, iterate unvisited nodes
 *    not in adj[u]. Remove visited nodes from the set. O((n+m) log n) total.
 *
 * COMPLEXITY
 * ----------
 * Time : O((n + m) log n)
 * Space: O(n + m)
 * ============================================================
 */

public class CF_1243D_01MST {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        Set<Integer>[] blocked = new Set[n + 1]; // given edges (weight-1, blocked in complement)
        for (int i = 1; i <= n; i++) blocked[i] = new HashSet<>();

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            blocked[u].add(v);
            blocked[v].add(u);
        }

        // BFS on complement graph using unvisited set trick
        TreeSet<Integer> unvisited = new TreeSet<>();
        for (int i = 1; i <= n; i++) unvisited.add(i);

        int components = 0;
        Queue<Integer> queue = new ArrayDeque<>();

        while (!unvisited.isEmpty()) {
            int start = unvisited.first();
            unvisited.remove(start);
            queue.offer(start);
            components++;

            while (!queue.isEmpty()) {
                int u = queue.poll();
                TreeSet<Integer> nextUnvisited = new TreeSet<>();

                for (int v : unvisited) {
                    if (!blocked[u].contains(v)) {
                        // v is reachable from u via 0-edge
                        queue.offer(v);
                    } else {
                        nextUnvisited.add(v);
                    }
                }
                unvisited = nextUnvisited;
            }
        }

        // MST weight = (number of components - 1) * 1 (each component-join costs 1)
        // But we need 1-weight edges to connect components. MST weight = components - 1.
        System.out.println(components - 1);
    }
}
