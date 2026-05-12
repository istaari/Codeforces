package phases.phase4_advanced.advanced_dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1437D — Minimal Height Tree
 * Link   : https://codeforces.com/problemset/problem/1437/D
 * Rating : 1700  |  Tags: dp, trees, greedy, BFS order
 * Topic  : Phase 4: Advanced > Advanced DP
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given a BFS (level-order) traversal sequence of a tree with n nodes.
 * The tree is k-ary (each node has at most k children). Reconstruct the
 * tree such that the height is minimized. Output the parent array.
 *
 * EXAMPLE
 * -------
 * Input:  n=10, k=3, bfs=[1,2,3,4,5,6,7,8,9,10]
 * Output: parent = [-1, 1, 1, 1, 2, 2, 2, 3, 3, 3]
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Process nodes in BFS order. Maintain a queue of current parents.
 * 2. For each parent, greedily assign as many children as possible (up to k)
 *    from the remaining BFS sequence, but we must assign consecutively.
 * 3. Key: to minimize height, assign children as greedily as possible —
 *    fill each level fully before going to next level.
 * 4. Greedily: current parent gets children from the sequence as long as
 *    the next value is greater than the current parent's value (to maintain
 *    BFS order property) or simply assign k children to each parent.
 *    Actually: we must assign values in given BFS order. Greedy = assign
 *    max children to each parent (up to k) before moving to next parent.
 * 5. Use a queue: first element is root. For each parent dequeued, assign
 *    next children from sequence (up to k), enqueue those children.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1437D_MinimalHeightTree {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());
            int[] bfs = new int[n];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) bfs[i] = Integer.parseInt(st.nextToken());

            int[] parent = new int[n + 1];
            parent[bfs[0]] = -1; // root

            Queue<Integer> queue = new ArrayDeque<>();
            queue.offer(bfs[0]);
            int idx = 1;

            while (idx < n) {
                int par = queue.poll();
                // Assign up to k children greedily
                for (int c = 0; c < k && idx < n; c++) {
                    int child = bfs[idx++];
                    parent[child] = par;
                    queue.offer(child);
                }
            }

            for (int i = 1; i <= n; i++) {
                sb.append(parent[i]);
                if (i < n) sb.append(' ');
            }
            sb.append('\n');
        }

        System.out.print(sb);
    }
}
