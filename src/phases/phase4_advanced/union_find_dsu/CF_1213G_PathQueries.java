package phases.phase4_advanced.union_find_dsu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1213G — Path Queries
 * Link   : https://codeforces.com/problemset/problem/1213/G
 * Rating : 1800  |  Tags: DSU, offline, sorting, Kruskal order
 * Topic  : Phase 4: Advanced > Union Find / DSU
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Weighted tree with n nodes. For each query q[i], count the number of
 * pairs (u, v) (u < v) where the maximum edge weight on the path u->v is <= q[i].
 *
 * EXAMPLE
 * -------
 * Input:  n=7 (star with all edges weight 1), queries: [1,2,3]
 * Output: 21, 21, 21  (all pairs connected by path with max weight 1)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Sort edges by weight. Sort queries offline.
 * 2. Use DSU (Kruskal order): add edges in increasing weight. When adding edge with weight w,
 *    if it connects components of sizes s1 and s2, new pairs added = s1 * s2.
 * 3. For each query q, answer = total pairs where max edge weight <= q =
 *    sum of pairs added by edges with weight <= q.
 * 4. Process queries in increasing order; for each query, add all edges with weight <= q,
 *    accumulate pair count.
 *
 * COMPLEXITY
 * ----------
 * Time : O((n + q) log(n + q))
 * Space: O(n + q)
 * ============================================================
 */

public class CF_1213G_PathQueries {

    static int[] parent, size;

    static int find(int x) {
        if (parent[x] != x) parent[x] = find(parent[x]);
        return parent[x];
    }

    // Returns number of new pairs when merging a and b
    static long union(int a, int b) {
        a = find(a); b = find(b);
        if (a == b) return 0;
        long pairs = (long) size[a] * size[b];
        if (size[a] < size[b]) { int t = a; a = b; b = t; }
        parent[b] = a;
        size[a] += size[b];
        return pairs;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        parent = new int[n + 1];
        size = new int[n + 1];
        for (int i = 1; i <= n; i++) { parent[i] = i; size[i] = 1; }

        int[][] edges = new int[n - 1][3]; // {u, v, w}
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            edges[i][0] = Integer.parseInt(st.nextToken());
            edges[i][1] = Integer.parseInt(st.nextToken());
            edges[i][2] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(edges, (a, b) -> a[2] - b[2]);

        long[] queries = new long[q];
        int[] queryIdx = new int[q];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < q; i++) {
            queries[i] = Long.parseLong(st.nextToken());
            queryIdx[i] = i;
        }
        // Sort queries
        Integer[] sortedQ = new Integer[q];
        for (int i = 0; i < q; i++) sortedQ[i] = i;
        Arrays.sort(sortedQ, (a, b) -> Long.compare(queries[a], queries[b]));

        long[] ans = new long[q];
        long totalPairs = 0;
        int ei = 0;

        for (int qi = 0; qi < q; qi++) {
            int qidx = sortedQ[qi];
            long limit = queries[qidx];

            // Add all edges with weight <= limit
            while (ei < n - 1 && edges[ei][2] <= limit) {
                totalPairs += union(edges[ei][0], edges[ei][1]);
                ei++;
            }
            ans[qidx] = totalPairs;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            sb.append(ans[i]);
            if (i < q - 1) sb.append(' ');
        }
        System.out.println(sb);
    }
}
