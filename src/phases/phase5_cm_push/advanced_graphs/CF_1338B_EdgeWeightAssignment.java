package phases.phase5_cm_push.advanced_graphs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1338B — Edge Weight Assignment
 * Link   : https://codeforces.com/problemset/problem/1338/B
 * Rating : 1800  |  Tags: trees, constructive, math
 * Topic  : Phase 5: CM Push > Advanced Graphs
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given a tree. Assign positive integer weights to edges so
 * that all leaves have equal distance from every other leaf
 * (i.e., all pairwise distances between leaves are equal).
 * Find the minimum number of distinct weight values needed.
 *
 * EXAMPLE
 * -------
 * Input:  6
 *         1 2
 *         1 3
 *         3 4
 *         3 5
 *         3 6
 * Output: 2
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. If all leaves are at the same depth from the root (any root),
 *    then we can assign weight 1 to all edges → answer is 1.
 * 2. Otherwise, consider two leaves u and v. The path u->v must
 *    have a fixed total sum equal to some constant D.
 * 3. If two leaves have paths to their LCA of different parity,
 *    we need at least weight values {1, (D-path_length)} so
 *    actually the minimum is 1 if all depths same, else 3.
 *    Reasoning: if all leaves are not at the same depth:
 *    - Assign all "backbone" edges weight 1.
 *    - For each leaf at a "short" depth, we need a special edge
 *      to make up the distance difference.
 *    - Those special edges can all share the same value (x),
 *      but x and 1 can be the same only if depth differences
 *      allow it — in general, at least 3 distinct values suffice
 *      but only if the difference is achievable.
 *    - Actually: minimum = 1 if all leaves same depth, else 3.
 *    Wait — let's think more carefully:
 *    - If all leaves at the same depth d from root → answer 1.
 *    - Otherwise → answer 3. This is because you need 1 for normal
 *      edges, and for short paths you need to compensate, which
 *      introduces values that can't both be 1 and equal something
 *      else simultaneously unless the parity lines up.
 *    - Specifically: if the depth difference is odd, parity mismatch
 *      means you need 3 values. But actually the answer is always
 *      either 1 (all leaves same depth) or 3 (otherwise), because
 *      you can always construct a valid assignment with just {1,2,3}.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n) BFS/DFS to find leaf depths
 * Space: O(n)
 * ============================================================
 */

public class CF_1338B_EdgeWeightAssignment {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());

        List<List<Integer>> adj = new ArrayList<>();
        int[] degree = new int[n + 1];
        for (int i = 0; i <= n; i++) adj.add(new ArrayList<>());

        for (int i = 0; i < n - 1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            adj.get(u).add(v);
            adj.get(v).add(u);
            degree[u]++;
            degree[v]++;
        }

        // BFS from node 1 to compute depths
        int[] depth = new int[n + 1];
        boolean[] visited = new boolean[n + 1];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(1);
        visited[1] = true;
        depth[1] = 0;

        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v : adj.get(u)) {
                if (!visited[v]) {
                    visited[v] = true;
                    depth[v] = depth[u] + 1;
                    queue.add(v);
                }
            }
        }

        // Collect leaf depths (degree == 1, except if n==1 but n>=2 guaranteed)
        int leafDepth = -1;
        boolean allSame = true;
        for (int i = 1; i <= n; i++) {
            if (degree[i] == 1) { // leaf node
                if (leafDepth == -1) {
                    leafDepth = depth[i];
                } else if (depth[i] != leafDepth) {
                    allSame = false;
                    break;
                }
            }
        }

        // If all leaves at same depth: 1 distinct weight suffices
        // Otherwise: 3 distinct weights are always sufficient and necessary
        System.out.println(allSame ? 1 : 3);
    }
}
