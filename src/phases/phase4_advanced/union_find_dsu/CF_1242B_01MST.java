package phases.phase4_advanced.union_find_dsu;

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
 * CF 1242B — 0-1 MST (DSU + BFS Version)
 * Link   : https://codeforces.com/problemset/problem/1242/B
 * Rating : 1800  |  Tags: DSU, BFS, complement graph
 * Topic  : Phase 4: Advanced > Union Find / DSU
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * n nodes, some edges (weight 1). Complement graph: edges NOT present have weight 0.
 * Find the MST weight of the complete graph where given edges have weight 1,
 * all other edges (complement) have weight 0. Equivalently: number of connected
 * components in complement graph minus 1.
 *
 * EXAMPLE
 * -------
 * Input:  n=4, m=2, edges: (1,2),(3,4)
 * Output: 0  (complement has edges 1-3,1-4,2-3,2-4: all connected, 1 component, MST=0)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. MST weight = (# components in complement graph - 1) since 0-weight edges connect
 *    nodes within components, 1-weight edges connect components.
 * 2. BFS on complement graph: maintain set of unvisited nodes.
 *    For each BFS from source u, visit all unvisited nodes EXCEPT those blocked by weight-1 edges.
 * 3. Use a TreeSet of unvisited nodes. For each u, iterate over unvisited not adjacent in original.
 *
 * COMPLEXITY
 * ----------
 * Time : O((n + m) log n)
 * Space: O(n + m)
 * ============================================================
 */

public class CF_1242B_01MST {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        Set<Integer>[] blocked = new Set[n + 1];
        for (int i = 1; i <= n; i++) blocked[i] = new HashSet<>();

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            blocked[u].add(v);
            blocked[v].add(u);
        }

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
                        queue.offer(v); // reachable via 0-edge
                    } else {
                        nextUnvisited.add(v); // blocked, keep for later
                    }
                }
                unvisited = nextUnvisited;
            }
        }

        System.out.println(components - 1);
    }
}
