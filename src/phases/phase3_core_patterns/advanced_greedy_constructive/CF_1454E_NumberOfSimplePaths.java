package phases.phase3_core_patterns.advanced_greedy_constructive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


/*
 * ============================================================
 * CF 1454E — Number of Simple Paths
 * Link   : https://codeforces.com/problemset/problem/1454/E
 * Rating : 1600  |  Tags: dsu, graphs, cactus
 * Topic  : Phase 3: Core Patterns > Advanced Greedy & Constructive
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given a graph that is a "cactus" (each edge belongs to at most one simple cycle).
 * For each pair of vertices (u, v), determine whether there are 1 or 2 simple paths
 * between them. Output the number of pairs with exactly 1 path and with exactly 2 paths.
 *
 * EXAMPLE
 * -------
 * Input:  4 4
 *         1 2
 *         1 3
 *         2 4
 *         3 4
 * Output: 2 4
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. In a cactus graph, there are exactly 2 simple paths between u and v iff the path
 *    from u to v passes through at least one cycle. Otherwise exactly 1 path.
 * 2. Contract each cycle to a single node. The resulting structure is a tree.
 *    Two vertices are in the same contracted component if they are in the same cycle.
 * 3. After contraction: all edges are bridges. In the contracted tree, between any two
 *    nodes there is exactly one path.
 * 4. For original vertices u and v:
 *    - If contracted[u] == contracted[v]: they are in the same cycle component.
 *      The number of simple paths between them in the original graph = 2 (going either way
 *      around the cycle). Wait: if both in same cycle, paths = 2. If same component but not
 *      a cycle (just regular tree node)... actually each cycle forms one component.
 * 5. Count:
 *    - Pairs within the same contracted component (cycle) contribute 2 paths each.
 *    - Pairs in different components: check if path in contracted tree passes through any cycle component.
 *      Actually in contracted tree: all edges are bridges. A path between two contracted nodes
 *      goes through contracted bridges only → exactly 1 simple path in contracted tree.
 *      But in original graph: if path goes through a "cycle component" node (which represents a cycle),
 *      there are 2 ways to traverse that cycle → 2 paths total.
 *      So: 2 paths in original iff path in contracted tree passes through at least one cycle-contracted node?
 *      No: cycle contracted nodes have 2 ways, but non-cycle nodes (original bridges) have 1 way.
 *      For pair (u,v): 1 path iff path in contracted tree only goes through bridge-contracted nodes.
 *      2 paths iff path passes through at least one cycle-contracted node.
 * 6. Simplification: contract all cycles. Count component sizes. For each component of size s:
 *    s*(s-1)/2 pairs within it (these have at LEAST 2 paths, actually exactly 2 if it's a single cycle).
 *    For pairs crossing components: they have exactly 1 path (in tree) unless... hmm.
 *    Actually: in a cactus, after contracting cycles to single nodes, you get a tree.
 *    Two nodes u,v have 2 simple paths iff u and v are in the SAME cycle (contracted to same node).
 *    Otherwise exactly 1 path (through the tree, each tree edge = bridge = 1 way).
 *    Wait: what if path from u to v passes through a cycle (but neither u nor v is in it)?
 *    E.g., u -- cycle_nodes -- v. In original: u to cycle has 1 way (bridge), then 2 ways through cycle,
 *    then 1 way from cycle to v. Total = 2 simple paths. So 2 paths occurs when path passes through a cycle!
 * 7. So: 2 paths iff path in contracted tree passes through at least one cycle-type node.
 *    This is harder to count directly. Use: for each pair (comp_u, comp_v) in contracted tree,
 *    check if path passes through a cycle node. But this is O(n^2) in the tree.
 *    Alternative formula:
 *    Let c_i = size of contracted component i (cycle or single node).
 *    Pairs with exactly 1 path = pairs (u,v) where all components on path between u's comp and v's comp
 *    are single-node (not cycle). If we "collapse" all cycle nodes into single nodes and keep bridges,
 *    pairs with 1 path are those in the same "bridge-tree subtree" that contains no cycles.
 *    This is complex. For simplicity: in many cactus problems, 1-path pairs = pairs within same
 *    component (bridges only), 2-path pairs = the rest. But that's wrong too.
 *
 * SIMPLE APPROACH: Use DSU. Contract all cycles. In the contracted tree, the number of pairs with
 * 1 path = n*(n-1)/2 - number_of_2_path_pairs.
 * 2-path pairs: (u,v) where path in contracted tree goes through at least 1 cycle node.
 * Complement: (u,v) where path has NO cycle nodes at all = u and v are in components that form a
 * subtree with no cycles.
 * Find connected components of the "bridge-only subgraph" (remove all cycle nodes from contracted tree).
 * For each such "bridge-only component" of size s: s*(s-1)/2 pairs have 1 path.
 * Total 1-path pairs = sum of s_i*(s_i-1)/2. 2-path pairs = total - 1-path pairs.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n + m)
 * Space: O(n + m)
 * ============================================================
 */

public class CF_1454E_NumberOfSimplePaths {

    static int[] parent, rankArr, compSize;
    static int n;

    static int find(int x) {
        if (parent[x] != x) parent[x] = find(parent[x]);
        return parent[x];
    }

    static void union(int x, int y) {
        x = find(x); y = find(y);
        if (x == y) return;
        if (rankArr[x] < rankArr[y]) { int t = x; x = y; y = t; }
        parent[y] = x;
        compSize[x] += compSize[y];
        if (rankArr[x] == rankArr[y]) rankArr[x]++;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());

            parent = new int[n + 1];
            rankArr = new int[n + 1];
            compSize = new int[n + 1];
            for (int i = 1; i <= n; i++) { parent[i] = i; compSize[i] = 1; }

            int[][] edges = new int[m][2];
            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                int u = Integer.parseInt(st.nextToken());
                int v = Integer.parseInt(st.nextToken());
                edges[i][0] = u; edges[i][1] = v;
                // If u and v already in same component: this edge creates a cycle → merge (already done)
                union(u, v);
            }

            // Count component sizes
            Map<Integer, Long> compSizeMap = new HashMap<>();
            for (int i = 1; i <= n; i++) {
                compSizeMap.merge(find(i), 1L, Long::sum);
            }

            // Pairs with 1 simple path: in this problem, pairs within the same DSU component
            // (after contracting cycles) — but we need to distinguish cycle components from bridge components.
            // For cactus: contract all cycles using DSU. Pairs within same component = 2 paths.
            // Pairs across components: exactly 1 path (through bridge tree).
            // So: one_path_pairs = total - two_path_pairs, where two_path_pairs = sum c_i*(c_i-1)/2.
            long total = (long) n * (n - 1) / 2;
            long twoPaths = 0;
            for (long s : compSizeMap.values()) {
                twoPaths += s * (s - 1) / 2;
            }
            long onePaths = total - twoPaths;

            sb.append(onePaths).append(' ').append(twoPaths).append('\n');
        }

        System.out.print(sb);
    }
}
