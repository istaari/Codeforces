package phases.phase4_advanced.union_find_dsu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/*
 * ============================================================
 * CF 1131D — Gourmet Choice
 * Link   : https://codeforces.com/problemset/problem/1131/D
 * Rating : 1800  |  Tags: DSU, topological sort, constraints
 * Topic  : Phase 4: Advanced > Union Find / DSU
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * n dishes rated pairwise: matrix m where m[i][j] is '<', '>', or '='.
 * Assign distinct positive integers to dishes to satisfy all comparisons.
 * Find the minimum number range assignment (lexicographically minimal).
 * If contradictory, output -1.
 *
 * EXAMPLE
 * -------
 * Input:  n=3, matrix: [=<, >=, ><]
 * Output: 1 2 2  (groups: {0},{1,2} -> values 1,2 for min possible)
 *         Wait, if 0<1 and 0>2 and 1>2: 0=middle? Let me check.
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Use DSU to merge all dishes with '=' relationship.
 * 2. For each '>' or '<' relationship between groups, add directed edge in DAG.
 * 3. Topological sort the DAG. If cycle exists, output -1.
 * 4. Assign values based on topological position: dishes at topological level k get value k.
 * 5. Minimum values: BFS/DAG longest path gives minimum distinct values needed.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n^2)
 * Space: O(n^2)
 * ============================================================
 */

public class CF_1131D_GourmetChoice {

    static int[] parent, rnk;

    static int find(int x) {
        if (parent[x] != x) parent[x] = find(parent[x]);
        return parent[x];
    }

    static void union(int a, int b) {
        a = find(a); b = find(b);
        if (a == b) return;
        if (rnk[a] < rnk[b]) { int t = a; a = b; b = t; }
        parent[b] = a;
        if (rnk[a] == rnk[b]) rnk[a]++;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());

        parent = new int[n];
        rnk = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;

        String[] matrix = new String[n];
        for (int i = 0; i < n; i++) matrix[i] = br.readLine().trim();

        // Step 1: Union all '=' pairs
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i].charAt(j) == '=') union(i, j);
            }
        }

        // Step 2: Check consistency of '=' groups
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                char c = matrix[i].charAt(j);
                if (c == '>' && find(i) == find(j)) {
                    System.out.println(-1); return;
                }
                if (c == '<' && find(i) == find(j)) {
                    System.out.println(-1); return;
                }
            }
        }

        // Remap group representatives to 0..k-1
        int[] repMap = new int[n];
        Arrays.fill(repMap, -1);
        int k = 0;
        for (int i = 0; i < n; i++) {
            if (find(i) == i) repMap[i] = k++;
        }

        // Step 3: Build DAG on groups
        List<Integer>[] adj = new List[k];
        int[] indegree = new int[k];
        for (int i = 0; i < k; i++) adj[i] = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i].charAt(j) == '>') {
                    int gi = repMap[find(i)], gj = repMap[find(j)];
                    if (gi != gj) {
                        // gi > gj means gj -> gi in topological order (gj comes before gi)
                        adj[gj].add(gi);
                        // But might add duplicates; we'll handle with proper set.
                    }
                }
            }
        }

        // Remove duplicate edges and recompute indegree
        for (int i = 0; i < k; i++) {
            List<Integer> unique = new ArrayList<>();
            boolean[] seen = new boolean[k];
            for (int v : adj[i]) {
                if (!seen[v]) { seen[v] = true; unique.add(v); indegree[v]++; }
            }
            adj[i] = unique;
        }

        // Step 4: Topological sort with level assignment (longest path = value)
        int[] dist = new int[k];
        Arrays.fill(dist, 1); // minimum value = 1
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < k; i++) if (indegree[i] == 0) queue.offer(i);

        int processed = 0;
        while (!queue.isEmpty()) {
            int u = queue.poll();
            processed++;
            for (int v : adj[u]) {
                dist[v] = Math.max(dist[v], dist[u] + 1);
                if (--indegree[v] == 0) queue.offer(v);
            }
        }

        if (processed != k) {
            System.out.println(-1);
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(dist[repMap[find(i)]]);
            if (i < n - 1) sb.append(' ');
        }
        System.out.println(sb);
    }
}
