package phases.phase3_core_patterns.graph_fundamentals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1559D2 — Mocha and Diana (Hard)
 * Link   : https://codeforces.com/problemset/problem/1559/D2
 * Rating : 1700  |  Tags: dsu, trees, binary search
 * Topic  : Phase 3: Core Patterns > Graph Fundamentals
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Two forests on the same n vertices. Add the maximum number of edges such that:
 * - Each added edge is added to BOTH forests simultaneously.
 * - Both forests remain forests (no cycles in either).
 * Find the maximum number of edges that can be added.
 *
 * EXAMPLE
 * -------
 * Input:  5 2 3
 *         1 2
 *         3 4
 *         1 3
 *         2 4
 *         3 5
 * Output: 1
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Adding edge (u, v) is valid if: u and v are in different components in both forests.
 * 2. After adding the edge, u and v are merged in both forests.
 * 3. The maximum number of edges we can add = number of times we can find a pair (u, v)
 *    disconnected in both forests.
 * 4. Use Union-Find (DSU) for both forests. Greedily: find any pair (u, v) disconnected
 *    in both; add edge, union in both. Repeat until no such pair exists.
 * 5. How many such edges: the answer = n - max(components1, components2)?
 *    Actually: both start with c1 and c2 components respectively. We can reduce components
 *    in both simultaneously. Each added edge reduces components in both by 1 (if both had
 *    the endpoints disconnected). The number of edges we can add is bounded by:
 *    - At most c1 - 1 edges in forest 1 (to make it a tree).
 *    - At most c2 - 1 edges in forest 2 (to make it a tree).
 *    So answer <= min(c1-1, c2-1).
 *    Can we always achieve this? We use a constructive approach with DSU.
 * 6. Greedy: find roots r1 of forest1, r2 of forest2 that are in different components
 *    in both forests. Add edge (r1, r2). Repeat.
 *    To find such a pair efficiently: iterate all pairs of nodes? Too slow.
 *    Better: find two nodes u, v: find(u) in forest1 != find(v) in forest1 AND
 *    find(u) in forest2 != find(v) in forest2. A simple greedy: use any node from
 *    the largest component of forest1 and any node from a different component of both.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n * alpha(n))
 * Space: O(n)
 * ============================================================
 */

public class CF_1559D2_MochaAndDianaHard {

    static int[] parent1, rank1, parent2, rank2;

    static int find(int[] parent, int x) {
        if (parent[x] != x) parent[x] = find(parent, parent[x]);
        return parent[x];
    }

    static boolean union(int[] parent, int[] rank, int x, int y) {
        int px = find(parent, x), py = find(parent, y);
        if (px == py) return false;
        if (rank[px] < rank[py]) { int t = px; px = py; py = t; }
        parent[py] = px;
        if (rank[px] == rank[py]) rank[px]++;
        return true;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m1 = Integer.parseInt(st.nextToken());
        int m2 = Integer.parseInt(st.nextToken());

        parent1 = new int[n + 1]; rank1 = new int[n + 1];
        parent2 = new int[n + 1]; rank2 = new int[n + 1];
        for (int i = 1; i <= n; i++) { parent1[i] = i; parent2[i] = i; }

        for (int i = 0; i < m1; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken()), v = Integer.parseInt(st.nextToken());
            union(parent1, rank1, u, v);
        }
        for (int i = 0; i < m2; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken()), v = Integer.parseInt(st.nextToken());
            union(parent2, rank2, u, v);
        }

        // Greedily add edges
        int ans = 0;
        StringBuilder sb = new StringBuilder();

        // Find pairs: iterate i from 1 to n, try to pair with node 1 if different in both
        // More systematic: two pointers over components
        int i = 1, j = 2;
        while (i < n) {
            // Find j > i such that find1[i] != find1[j] and find2[i] != find2[j]
            while (j <= n && (find(parent1, i) == find(parent1, j) || find(parent2, i) == find(parent2, j))) {
                j++;
            }
            if (j > n) {
                i++;
                j = i + 1;
                continue;
            }
            // Add edge (i, j)
            union(parent1, rank1, i, j);
            union(parent2, rank2, i, j);
            sb.append(i).append(' ').append(j).append('\n');
            ans++;
            j++;
        }

        System.out.println(ans);
        System.out.print(sb);
    }
}
