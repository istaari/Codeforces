package phases.phase5_cm_push.advanced_graphs;

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
 * Rating : 1800  |  Tags: dfs, euler tour, segment tree, trees
 * Topic  : Phase 5: CM Push > Advanced Graphs
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given a tree rooted at node 1 with n nodes. Initially all
 * node values are 0. Process q operations, each: add v to all
 * nodes in the subtree of u that have depth exactly d.
 * After all operations, print the value of each node.
 *
 * EXAMPLE
 * -------
 * Input:  5
 *         1 2
 *         1 3
 *         2 4
 *         2 5
 *         3
 *         1 2 4
 *         2 3 1
 *         4 2 3
 * Output: 0 4 1 3 3
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Compute DFS in/out times (Euler tour) and depth of each node.
 * 2. For each query (u, d, v): add v to all nodes in subtree of u
 *    with depth d. The subtree of u spans [in[u], out[u]] in the
 *    Euler tour. Among these, those with depth d.
 * 3. Idea: process queries during DFS. Use a stack of queries
 *    active at the current depth. For each node, accumulate the
 *    value from all active queries that match its depth.
 * 4. More specifically: sort/group queries by the node u.
 *    When DFS enters node u, push all queries for this node onto
 *    a depth-indexed structure (BIT or difference array indexed
 *    by depth). When DFS exits node u, pop them.
 *    At each node v with depth d, answer[v] += query_at_depth[d].
 * 5. Use a Fenwick tree (BIT) on depth with point-add and point-query.
 *    On enter u: for each query(u, d, v), do BIT.add(d, v).
 *    At node v (depth dep[v]): ans[v] += BIT.query(dep[v]).
 *    On exit u: for each query(u, d, v), do BIT.add(d, -v) to undo.
 * 6. Edge case: depth d in query must be >= depth[u], else no nodes.
 *
 * COMPLEXITY
 * ----------
 * Time : O((n + q) log n)
 * Space: O(n + q)
 * ============================================================
 */

public class CF_1076E_VasyaAndATree {

    static List<List<Integer>> tree;
    static List<List<int[]>> queries; // queries[u] = list of [d, v]
    static int[] depth, ans;
    static long[] bit; // Fenwick tree indexed by depth
    static int n;

    static void bitAdd(int pos, long val) {
        for (pos++; pos < bit.length; pos += pos & (-pos))
            bit[pos] += val;
    }

    static long bitQuery(int pos) {
        long sum = 0;
        for (pos++; pos > 0; pos -= pos & (-pos))
            sum += bit[pos];
        return sum;
    }

    // Iterative DFS to avoid stack overflow
    static void dfs() {
        int[] stack = new int[n + 1];
        int[] parentNode = new int[n + 1];
        boolean[] visited = new boolean[n + 1];
        int top = 0;

        stack[top++] = 1;
        depth[1] = 0;
        parentNode[1] = 0;

        // We need to process enter/exit events.
        // Use explicit enter/exit with a deque trick: push negative for exit.
        int[] eventStack = new int[2 * n + 2];
        int eTop = 0;
        eventStack[eTop++] = 1; // enter node 1
        depth[1] = 0;

        // Track if we've pushed children for a node
        boolean[] childrenPushed = new boolean[n + 1];

        // Re-do with proper iterative DFS:
        // Stack stores (node, parentNode) with sign indicating enter(+) or exit(-).
        // Use int array where negative value means "exit node |val|".

        int[] dfsStack = new int[2 * n + 2];
        int[] depthStack = new int[2 * n + 2];
        int dfsTop = 0;

        dfsStack[dfsTop] = 1;
        depthStack[dfsTop] = 0;
        dfsTop++;

        int[] par = new int[n + 1];
        par[1] = 0;

        // Iterative DFS with enter/exit tracking
        // Negative values in stack represent exit events
        int[] stk = new int[2 * n + 5];
        int sp = 0;
        stk[sp++] = 1; // push node 1 (enter)
        depth[1] = 0;
        boolean[] inStack = new boolean[n + 1];

        // Use a simpler approach: iterative DFS with explicit child iteration
        int[] childIdx = new int[n + 1];
        int[] nodeStack = new int[n + 1];
        int nsTop = 0;
        nodeStack[nsTop++] = 1;
        depth[1] = 0;
        par[1] = -1;

        while (nsTop > 0) {
            int u = nodeStack[nsTop - 1];
            if (childIdx[u] == 0) {
                // Entering node u for the first time
                // Apply queries for node u: add to BIT
                for (int[] q : queries.get(u)) {
                    bitAdd(q[0], q[1]); // add v at depth d
                }
                // Record current answer contribution
                ans[u] += bitQuery(depth[u]);
            }

            // Try to visit next unvisited child
            List<Integer> children = tree.get(u);
            boolean pushed = false;
            while (childIdx[u] < children.size()) {
                int v = children.get(childIdx[u]);
                childIdx[u]++;
                if (v != par[u]) {
                    depth[v] = depth[u] + 1;
                    par[v] = u;
                    nodeStack[nsTop++] = v;
                    pushed = true;
                    break;
                }
            }

            if (!pushed) {
                // All children visited → exit node u
                // Undo queries for node u
                for (int[] q : queries.get(u)) {
                    bitAdd(q[0], -q[1]); // undo
                }
                nsTop--;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine().trim());

        tree = new ArrayList<>();
        queries = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            tree.add(new ArrayList<>());
            queries.add(new ArrayList<>());
        }

        for (int i = 0; i < n - 1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            tree.get(u).add(v);
            tree.get(v).add(u);
        }

        int q = Integer.parseInt(br.readLine().trim());
        for (int i = 0; i < q; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            queries.get(u).add(new int[]{d, v});
        }

        // BIT indexed by depth (0 to n-1)
        bit = new long[n + 2];
        depth = new int[n + 1];
        ans = new int[n + 1];

        dfs();

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            if (i > 1) sb.append(' ');
            sb.append(ans[i]);
        }
        System.out.println(sb);
    }
}
