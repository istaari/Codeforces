package phases.phase4_advanced.shortest_paths;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1076E — Vasya and a Tree
 * Link   : https://codeforces.com/problemset/problem/1076/E
 * Rating : 1800  |  Tags: trees, DFS, difference arrays
 * Topic  : Phase 4: Advanced > Shortest Paths
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Tree rooted at vertex 1. Process q queries each of the form (v, d, x):
 * add x to all vertices u in the subtree of v where depth[u] <= d.
 * After all queries, output the value of each vertex.
 *
 * EXAMPLE
 * -------
 * Input:  n=4, tree: 1-2,1-3,2-4, queries: (1,2,1),(2,3,2)
 * Output: 1 3 1 2
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. DFS to compute depths. For each vertex v, subtree is entered at DFS enter time
 *    and exited at DFS exit time.
 * 2. For query (v, d, x): add x to all nodes in subtree of v with depth <= d.
 *    This is a "depth-bounded subtree add".
 * 3. Use DFS + difference array on depth. During DFS traversal of v's subtree:
 *    at vertex u (depth du), if du <= d, add x.
 * 4. Efficient approach: use a stack-based DFS. At entry of v, mark "at this depth
 *    level, remember to add x until depth d". Use a per-depth BIT or segment tree?
 * 5. Better: offline approach. Sort queries by vertex. For each DFS visit of vertex u:
 *    check all queries (v, d, x) where v is an ancestor of u (or v=u) and d >= depth[u].
 *    Use DFS + a difference array on the DFS depth stack.
 * 6. Key trick: use a BIT/array indexed by depth. On DFS entry of v, apply all its
 *    queries by adding x to [depth[v], d] in the depth-difference array. On DFS exit
 *    of v, undo these queries. At each node, query current depth to get total.
 *
 * COMPLEXITY
 * ----------
 * Time : O((n + q) log n)
 * Space: O(n + q)
 * ============================================================
 */

public class CF_1076E_VasyaAndATree {

    static int[] depth;
    static long[] val;
    static List<Integer>[] children;
    static List<int[]>[] queries; // queries[v] = list of {d, x}
    static long[] bit;
    static int N;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        N = n;

        children = new List[n + 1];
        queries = new List[n + 1];
        for (int i = 1; i <= n; i++) {
            children[i] = new ArrayList<>();
            queries[i] = new ArrayList<>();
        }

        // Build tree (rooted at 1, BFS to assign parent)
        List<Integer>[] adj = new List[n + 1];
        for (int i = 1; i <= n; i++) adj[i] = new ArrayList<>();
        for (int i = 0; i < n - 1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            adj[u].add(v);
            adj[v].add(u);
        }

        // BFS to find parents and build rooted tree
        depth = new int[n + 1];
        int[] parent = new int[n + 1];
        int[] bfsOrder = new int[n];
        boolean[] visited = new boolean[n + 1];
        int head = 0, tail = 0;
        bfsOrder[tail++] = 1;
        visited[1] = true;
        parent[1] = 0;
        depth[1] = 1;
        while (head < tail) {
            int u = bfsOrder[head++];
            for (int v : adj[u]) {
                if (!visited[v]) {
                    visited[v] = true;
                    parent[v] = u;
                    depth[v] = depth[u] + 1;
                    children[u].add(v);
                    bfsOrder[tail++] = v;
                }
            }
        }

        int q = Integer.parseInt(br.readLine().trim());
        for (int i = 0; i < q; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int v = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            long x = Long.parseLong(st.nextToken());
            queries[v].add(new int[]{d, (int)x});
        }

        // DFS with difference array on depth
        bit = new long[n + 2]; // difference array for depths
        val = new long[n + 1];

        // Iterative DFS
        int[] stack = new int[n + 1];
        boolean[] processed = new boolean[n + 1];
        int top = 0;
        stack[top++] = 1;

        // Use explicit stack with entry/exit
        int[] stackV = new int[2 * n + 2];
        int[] stackState = new int[2 * n + 2]; // 0=enter, 1=exit
        int sp = 0;
        stackV[sp] = 1; stackState[sp] = 0; sp++;

        while (sp > 0) {
            sp--;
            int u = stackV[sp];
            int state = stackState[sp];

            if (state == 0) {
                // Entry: apply queries for u, push exit, push children
                stackV[sp] = u; stackState[sp] = 1; sp++;
                for (int child : children[u]) {
                    stackV[sp] = child; stackState[sp] = 0; sp++;
                }
                // Apply queries: add x to depths [depth[u], d]
                for (int[] qry : queries[u]) {
                    int d = qry[0]; long x = qry[1];
                    if (d >= depth[u]) {
                        addDiff(depth[u], d, x);
                    }
                }
            } else {
                // Exit: read current value at depth[u], undo queries
                val[u] = queryPrefix(depth[u]);
                for (int[] qry : queries[u]) {
                    int d = qry[0]; long x = qry[1];
                    if (d >= depth[u]) {
                        addDiff(depth[u], d, -x);
                    }
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            sb.append(val[i]);
            if (i < n) sb.append(' ');
        }
        System.out.println(sb);
    }

    // Difference array on depth: add x to [l, r]
    static void addDiff(int l, int r, long x) {
        if (l <= N) bit[l] += x;
        if (r + 1 <= N) bit[r + 1] -= x;
    }

    // Prefix sum at position p
    static long queryPrefix(int p) {
        long sum = 0;
        for (int i = 1; i <= p; i++) sum += bit[i];
        return sum;
    }
}
