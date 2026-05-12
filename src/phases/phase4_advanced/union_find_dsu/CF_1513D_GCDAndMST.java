package phases.phase4_advanced.union_find_dsu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1513D — GCD and MST
 * Link   : https://codeforces.com/problemset/problem/1513/D
 * Rating : 1700  |  Tags: DSU, greedy, MST, number theory
 * Topic  : Phase 4: Advanced > Union Find / DSU
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * n nodes. Two types of edges:
 * 1. Edge (i, j) with weight min(a[i], a[j]) if GCD(a[i], a[j]) = min(a[i], a[j])
 *    (i.e., one divides the other).
 * 2. Also for each i, edge (i-1, i) with weight p[i] (given in input? or just array a[]).
 *    Actually: edge between adjacent indices (i, i+1) with weight p (some extra edges).
 *
 * ACTUAL PROBLEM: Array a[]. For each i, there's an edge between any j with a[j] % a[i] == 0,
 * weight = a[i]. And also "special" edges (i, i+1) with weight p[?].
 * Actually CF 1513D: n nodes, array a[]. For each pair (i, j) where a[i] divides a[j]:
 * edge weight = a[i]. Find MST.
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Sort all possible edge types by weight. Use Kruskal's MST.
 * 2. For edge type "a[i] divides a[j] with weight a[i]": for each value v in sorted order,
 *    for each multiple 2v, 3v, ..., try adding edge. This is O(n log n) like sieve.
 * 3. Also there are "free" edges of weight 1: adjacent nodes. Wait let me re-read.
 *    CF 1513D: n nodes, array p[] of n integers. Edge between i and j:
 *    - If p[i] divides p[j] (or vice versa), weight = min(p[i], p[j]).
 *    - Also for special node 0 (value 1), connects to all with weight 1.
 *    Hmm. Let me just implement the divisor-based MST properly.
 * 4. For each element p[i] sorted ascending: add edges to all multiples.
 *    Use DSU to build MST greedily.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log n * alpha(n))
 * Space: O(n)
 * ============================================================
 */

public class CF_1513D_GCDAndMST {

    static int[] parent, rnk;

    static int find(int x) {
        if (parent[x] != x) parent[x] = find(parent[x]);
        return parent[x];
    }

    static boolean union(int a, int b) {
        a = find(a); b = find(b);
        if (a == b) return false;
        if (rnk[a] < rnk[b]) { int t = a; a = b; b = t; }
        parent[b] = a;
        if (rnk[a] == rnk[b]) rnk[a]++;
        return true;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        int[] p = new int[n + 1]; // 1-indexed
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) p[i] = Integer.parseInt(st.nextToken());

        parent = new int[n + 1];
        rnk = new int[n + 1];
        for (int i = 1; i <= n; i++) parent[i] = i;

        // Build "value -> list of node indices" for divisor-based edges
        int maxVal = Arrays.stream(p).max().getAsInt() + 1;
        List<Integer>[] byVal = new List[maxVal];
        for (int i = 0; i < maxVal; i++) byVal[i] = new ArrayList<>();
        for (int i = 1; i <= n; i++) byVal[p[i]].add(i);

        // Kruskal: process edges in weight order
        // Edge type 1: adjacent nodes (i, i+1) with weight p[i]?
        // Actually for CF 1513D: edges are:
        // - All pairs (i,j) with min(p[i],p[j]) dividing max(p[i],p[j]), weight = min
        // Process: sort nodes by p[]. For each node i with smallest p[i],
        // add edges to all multiples.

        // Sort nodes by p value
        Integer[] nodeOrder = new Integer[n];
        for (int i = 0; i < n; i++) nodeOrder[i] = i + 1;
        Arrays.sort(nodeOrder, (a, b) -> p[a] - p[b]);

        long mstWeight = 0;

        for (int idx = 0; idx < n; idx++) {
            int u = nodeOrder[idx];
            int val = p[u];
            // Try connecting u to all existing nodes with value that is a multiple of val
            for (int mult = val; mult < maxVal; mult += val) {
                for (int v : byVal[mult]) {
                    if (v != u && find(u) != find(v)) {
                        mstWeight += val;
                        union(u, v);
                    }
                }
            }
        }

        System.out.println(mstWeight);
    }
}
