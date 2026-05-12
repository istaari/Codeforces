package phases.phase4_advanced.union_find_dsu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1559D1 — Mocha and Diana (Easy Version)
 * Link   : https://codeforces.com/problemset/problem/1559/D1
 * Rating : 1600  |  Tags: DSU, two forests
 * Topic  : Phase 4: Advanced > Union Find / DSU
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Two forests (undirected acyclic graphs) on the same n nodes. Add as many
 * edges as possible such that each added edge doesn't create a cycle in
 * either forest. Maximize the number of added edges.
 *
 * EXAMPLE
 * -------
 * Input:  n=4, forest1 edges: (1,2), forest2 edges: (2,3)
 * Output: 1  (can add edge (1,4) or (3,4): doesn't create cycle in either)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Use two separate DSU structures, one for each forest.
 * 2. For each potential edge (u, v), add it if:
 *    - find1(u) != find1(v) (no cycle in forest 1), AND
 *    - find2(u) != find2(v) (no cycle in forest 2).
 * 3. Greedily iterate over all O(n^2) potential edges, adding valid ones.
 * 4. But n can be up to 10^3 in easy version, O(n^2) is fine.
 * 5. Count and output added edges.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n^2 * alpha(n))
 * Space: O(n)
 * ============================================================
 */

public class CF_1559D1_MochaAndDianaEasy {

    static int[] p1, r1, p2, r2;

    static int find1(int x) {
        if (p1[x] != x) p1[x] = find1(p1[x]);
        return p1[x];
    }

    static int find2(int x) {
        if (p2[x] != x) p2[x] = find2(p2[x]);
        return p2[x];
    }

    static void union1(int a, int b) {
        a = find1(a); b = find1(b);
        if (a == b) return;
        if (r1[a] < r1[b]) { int t = a; a = b; b = t; }
        p1[b] = a;
        if (r1[a] == r1[b]) r1[a]++;
    }

    static void union2(int a, int b) {
        a = find2(a); b = find2(b);
        if (a == b) return;
        if (r2[a] < r2[b]) { int t = a; a = b; b = t; }
        p2[b] = a;
        if (r2[a] == r2[b]) r2[a]++;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m1 = Integer.parseInt(st.nextToken());
        int m2 = Integer.parseInt(st.nextToken());

        p1 = new int[n + 1]; r1 = new int[n + 1];
        p2 = new int[n + 1]; r2 = new int[n + 1];
        for (int i = 1; i <= n; i++) { p1[i] = i; p2[i] = i; }

        for (int i = 0; i < m1; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            union1(u, v);
        }
        for (int i = 0; i < m2; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            union2(u, v);
        }

        int added = 0;
        StringBuilder sb = new StringBuilder();

        for (int u = 1; u <= n; u++) {
            for (int v = u + 1; v <= n; v++) {
                if (find1(u) != find1(v) && find2(u) != find2(v)) {
                    union1(u, v);
                    union2(u, v);
                    added++;
                    sb.append(u).append(' ').append(v).append('\n');
                }
            }
        }

        System.out.println(added);
        System.out.print(sb);
    }
}
