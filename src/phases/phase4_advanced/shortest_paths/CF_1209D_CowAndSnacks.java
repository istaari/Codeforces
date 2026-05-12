package phases.phase4_advanced.shortest_paths;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1209D — Cow and Snacks
 * Link   : https://codeforces.com/problemset/problem/1209/D
 * Rating : 1700  |  Tags: DSU, graphs, greedy
 * Topic  : Phase 4: Advanced > Shortest Paths
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * n students and k snack flavors. Each snack i has two associated flavors
 * f[i] and g[i]. A student will take snack i only if at least one of f[i] or g[i]
 * has not been tasted before (by them). Students are processed in order;
 * each student greedily takes the first untaken snack they desire.
 * Actually: each snack has two flavors. A student takes the snack if they
 * haven't had both flavors before. Count total snacks NOT taken.
 *
 * PROBLEM RESTATEMENT: n snacks, each with flavors (a[i], b[i]). Process snacks
 * one by one. A snack is "eaten" if at least one of its two flavors hasn't been
 * seen. Mark both flavors as seen when eaten. Count uneaten snacks.
 *
 * EXAMPLE
 * -------
 * Input:  n=5, k=3, snacks: (1,2),(1,2),(2,3),(1,3),(2,3)
 * Output: 2
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Model as graph: each flavor is a node. Each snack i = edge (a[i], b[i]).
 * 2. A snack is eaten iff it connects two different components (like Kruskal's MST).
 * 3. Snack is NOT eaten iff a[i] and b[i] are in the same component (already connected).
 * 4. Use DSU: for each snack, if find(a[i]) != find(b[i]), eat it (union). Else don't eat.
 * 5. Count uneaten = snacks where a[i] and b[i] in same component.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n * alpha(k))
 * Space: O(k)
 * ============================================================
 */

public class CF_1209D_CowAndSnacks {

    static int[] parent, rank;

    static int find(int x) {
        if (parent[x] != x) parent[x] = find(parent[x]);
        return parent[x];
    }

    static boolean union(int a, int b) {
        a = find(a); b = find(b);
        if (a == b) return false;
        if (rank[a] < rank[b]) { int t = a; a = b; b = t; }
        parent[b] = a;
        if (rank[a] == rank[b]) rank[a]++;
        return true;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        parent = new int[k + 1];
        rank = new int[k + 1];
        for (int i = 0; i <= k; i++) parent[i] = i;

        int uneaten = 0;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            if (!union(a, b)) uneaten++;
        }

        System.out.println(uneaten);
    }
}
