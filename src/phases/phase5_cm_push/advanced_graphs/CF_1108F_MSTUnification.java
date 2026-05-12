package phases.phase5_cm_push.advanced_graphs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1108F — MST Unification
 * Link   : https://codeforces.com/problemset/problem/1108/F
 * Rating : 1800  |  Tags: mst, dsu, graphs
 * Topic  : Phase 5: CM Push > Advanced Graphs
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given a weighted undirected graph with n nodes and m edges.
 * Check if the MST is unique. If not unique, find the minimum
 * number of edges whose weights need to be increased by 1 so
 * that the MST becomes unique. It is guaranteed the graph is
 * connected and a spanning tree exists.
 *
 * EXAMPLE
 * -------
 * Input:  4 5
 *         1 2 1
 *         2 3 2
 *         3 4 1
 *         4 1 2
 *         1 3 3
 * Output: 1
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Sort edges by weight. Process edges in groups of equal weight.
 * 2. For each weight group, use a temporary DSU to see how many
 *    edges in this group actually get included in some MST.
 * 3. An edge (u,v,w) is in some MST iff it connects two different
 *    components in the DSU built with all edges of weight < w.
 * 4. Among those "useful" edges in the group, run DSU again to
 *    count how many components they merge. If useful > merged,
 *    there are multiple ways → MST is not unique for this group.
 * 5. The answer is (number of useful edges) - (number of merges)
 *    across all weight groups: these are the "extra" edges that
 *    could substitute each other. Increase them to break ties.
 *
 * COMPLEXITY
 * ----------
 * Time : O(m log m * alpha(n))
 * Space: O(n + m)
 * ============================================================
 */

public class CF_1108F_MSTUnification {

    static int[] parent, rank;

    static int find(int x) {
        while (parent[x] != x) {
            parent[x] = parent[parent[x]]; // path compression
            x = parent[x];
        }
        return x;
    }

    static boolean unite(int a, int b) {
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
        int m = Integer.parseInt(st.nextToken());

        int[][] edges = new int[m][3];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            edges[i][0] = Integer.parseInt(st.nextToken()); // u
            edges[i][1] = Integer.parseInt(st.nextToken()); // v
            edges[i][2] = Integer.parseInt(st.nextToken()); // w
        }

        // Sort edges by weight
        Arrays.sort(edges, (a, b) -> a[2] - b[2]);

        // Initialize DSU for main Kruskal
        parent = new int[n + 1];
        rank = new int[n + 1];
        for (int i = 1; i <= n; i++) parent[i] = i;

        int answer = 0;
        int i = 0;
        while (i < m) {
            // Find the end of the current weight group
            int j = i;
            int w = edges[i][2];
            while (j < m && edges[j][2] == w) j++;

            // Edges [i, j) all have weight w.
            // Step 1: find which edges in this group are "useful"
            // (connect different components in the current DSU)
            int useful = 0;
            int[] usefulEdges = new int[j - i];
            for (int k = i; k < j; k++) {
                int u = edges[k][0], v = edges[k][1];
                if (find(u) != find(v)) {
                    usefulEdges[useful++] = k;
                }
            }

            // Step 2: among useful edges, count how many merges happen
            // Save DSU state by recording parents before this group
            // We use the same DSU but count merges with a temp approach:
            // Create a snapshot of parent/rank for the useful edges check
            int[] savedParent = Arrays.copyOf(parent, n + 1);
            int[] savedRank = Arrays.copyOf(rank, n + 1);

            int merges = 0;
            for (int k = 0; k < useful; k++) {
                int idx = usefulEdges[k];
                int u = edges[idx][0], v = edges[idx][1];
                if (unite(u, v)) merges++;
            }

            // If useful > merges → some edges in this group are interchangeable
            // We need (useful - merges) extra edge weight increases
            answer += useful - merges;

            i = j; // move to next weight group
        }

        System.out.println(answer);
    }
}
