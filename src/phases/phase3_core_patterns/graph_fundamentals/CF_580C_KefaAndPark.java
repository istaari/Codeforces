package phases.phase3_core_patterns.graph_fundamentals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 580C — Kefa and Park
 * Link   : https://codeforces.com/problemset/problem/580/C
 * Rating : 1500  |  Tags: dfs, trees
 * Topic  : Phase 3: Core Patterns > Graph Fundamentals
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * A tree of n nodes rooted at node 1. Each node has 0 or 1 cat(s).
 * Find the number of leaves such that the path from root to that leaf
 * contains at most m consecutive cats (no chain of m+1 or more cats).
 *
 * EXAMPLE
 * -------
 * Input:  6 1
 *         1 1 0 0 0 0
 *         1 2
 *         1 3
 *         2 4
 *         2 5
 *         3 6
 * Output: 4
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. DFS from root (node 1). Track the current streak of consecutive cats
 *    on the path from root to current node.
 * 2. At each node: if current node has a cat, increment streak; else reset streak to 0.
 *    If streak > m, prune this subtree (cannot lead to valid leaf).
 * 3. When we reach a leaf (degree 1 and not root, or node with no unvisited children)
 *    with streak <= m, increment answer.
 * 4. Use iterative DFS to avoid stack overflow for large n.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_580C_KefaAndPark {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[] cats = new int[n + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) cats[i] = Integer.parseInt(st.nextToken());

        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) adj.add(new ArrayList<>());
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        int ans = 0;
        // Iterative DFS: (node, parent, current_cat_streak)
        ArrayDeque<int[]> stack = new ArrayDeque<>();
        stack.push(new int[]{1, 0, cats[1] == 1 ? 1 : 0});

        while (!stack.isEmpty()) {
            int[] cur = stack.pop();
            int node = cur[0], parent = cur[1], streak = cur[2];

            if (streak > m) continue; // prune

            boolean isLeaf = true;
            for (int neighbor : adj.get(node)) {
                if (neighbor == parent) continue;
                isLeaf = false;
                int newStreak = cats[neighbor] == 1 ? streak + 1 : 0;
                stack.push(new int[]{neighbor, node, newStreak});
            }

            if (isLeaf) ans++;
        }

        System.out.println(ans);
    }
}
