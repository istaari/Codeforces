package phases.phase4_advanced.segment_tree_bit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1311E — Construct the Binary Tree
 * Link   : https://codeforces.com/problemset/problem/1311/E
 * Rating : 1800  |  Tags: constructive, trees, dp
 * Topic  : Phase 4: Advanced > Segment Tree / BIT
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Construct a binary tree with exactly n nodes and sum of depths of all
 * nodes equal to s. A node's depth = distance from root. Root has depth 0.
 * If possible, output any such tree; else -1.
 *
 * EXAMPLE
 * -------
 * Input:  n=3, s=2
 * Output: parent = [0, 1, 1] (root=1, children 2,3 at depth 1; sum=0+1+1=2)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Build tree by maintaining the count of nodes at each depth level.
 * 2. Start with a "path" (chain): levels[i]=1 for i=0..n-1. Sum = 0+1+...+(n-1) = n(n-1)/2.
 * 3. Or start with a "complete binary tree"-like structure. Sum = n-1 for fully shallow tree.
 * 4. The minimum sum for n nodes = n-1 (star: all nodes at depth 1 except root).
 *    The maximum sum = n(n-1)/2 (path/chain).
 * 5. If s < n-1 or s > n*(n-1)/2: output -1.
 * 6. Start from chain (max sum), decrease sum by moving a leaf from deepest to shallowest valid position.
 *    Or start from star (min sum), increase sum by moving a leaf deeper.
 * 7. DP approach: dp[i][j] = true if can build binary tree with i nodes and sum j.
 *    Transition: combine left and right subtrees.
 *    Too slow for large n.
 * 8. Greedy construction: maintain levels array. Move nodes between levels to hit target sum.
 *    Start with all at depth 1 (sum = n-1). Need to reach s. Increase sum by moving
 *    leaf from level d to level d+1 (increases sum by 1). But must maintain binary tree property.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n * s) or O(n^2) for construction
 * Space: O(n)
 * ============================================================
 */

public class CF_1311E_ConstructTheBinaryTree {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());

            // Min sum: n-1 (root + n-1 children, all at depth 1)
            // But binary tree constraint: each node has at most 2 children
            // Min sum for binary tree: must fit n-1 nodes into a complete binary tree
            // Level 0: 1 node, level 1: 2, level 2: 4, ... Level k: 2^k
            // Min sum = min sum of complete binary tree with n nodes

            // Compute min sum (full tree)
            long[] levels = new long[50]; // levels[i] = nodes at depth i
            levels[0] = 1;
            long minSum = 0;
            int remaining = n - 1;
            int depth = 0;
            while (remaining > 0) {
                depth++;
                long cap = Math.min(remaining, (long) Math.pow(2, depth));
                levels[depth] = cap;
                minSum += cap * depth;
                remaining -= cap;
            }

            // Max sum (chain): 0+1+...+(n-1)
            long maxSum = (long) n * (n - 1) / 2;

            if (s < minSum || s > maxSum) {
                sb.append(-1).append('\n');
                continue;
            }

            // Start from chain, move leaves up to decrease sum
            // Or start from full tree (minSum), move leaves down to increase sum
            // Let's start from chain (max sum) and decrease
            int[] lev = new int[n]; // level of each node (node 0=root at depth 0)
            for (int i = 0; i < n; i++) lev[i] = i; // chain: root at 0, ..., leaf at n-1

            long curSum = maxSum;

            // Decrease sum by moving deepest leaf up
            while (curSum > s) {
                long need = curSum - s;
                // Find deepest leaf that can be moved up
                // In chain: deepest leaf is at lev[n-1] = n-1
                // Can move leaf at depth d to depth d' < d
                // Move to shallowest possible depth that:
                //   - has a node with < 2 children (available parent slot)
                //   - and reduces sum maximally (move as high as possible)
                // For chain: node at depth d-1 has 1 child (the chain continues)
                // We can attach the leaf at depth d to some node at depth d' if that node has < 2 children

                // Find the node to move: deepest leaf
                // For chain structure: last node (depth n-1) can be moved to be child of
                // shallowest node with only 1 child.
                // A node at depth i has 1 child (i+1) in chain. So can add second child to any depth i.
                // Best: attach to depth 0 (root) at depth 1 -> saves n-2 in sum.
                // Clamp: min(n-1 - 1, need) = how much we can save by moving deepest leaf to shallowest slot.

                // Find shallowest available parent (node with < 2 children)
                // Count children at each depth from count of nodes at that depth
                // Use levels array approach

                // Rebuild levels count from lev[]
                int maxDepth = 0;
                for (int d : lev) maxDepth = Math.max(maxDepth, d);

                int[] cnt = new int[maxDepth + 2];
                for (int d : lev) cnt[d]++;

                // Find shallowest depth with "available parent slot"
                // A node at depth d can be parent if cnt[d] > 0 and children at d+1 < 2*cnt[d]
                int targetDepth = -1;
                for (int d = 0; d < maxDepth; d++) {
                    if (cnt[d] > 0 && cnt[d + 1] < 2 * cnt[d]) {
                        // Can place a child at depth d+1
                        if (d + 1 < maxDepth) { // moving to d+1 from maxDepth saves maxDepth-(d+1)
                            targetDepth = d + 1;
                            break;
                        }
                    }
                }

                if (targetDepth == -1 || targetDepth >= maxDepth) break;

                long save = maxDepth - targetDepth;
                if (save <= 0) break;
                // Move one node from maxDepth to targetDepth
                for (int i = n - 1; i >= 0; i--) {
                    if (lev[i] == maxDepth) {
                        lev[i] = targetDepth;
                        break;
                    }
                }
                curSum -= save;
            }

            if (curSum != s) {
                sb.append(-1).append('\n');
                continue;
            }

            // Build parent array from levels
            // Sort nodes by level, assign parents
            // Parent of node at level d = a node at level d-1 with free child slot
            Arrays.sort(lev);
            int[] parent = new int[n];
            parent[0] = 0; // root's parent is itself (or 0)
            int[] childCount = new int[n];
            int[] nodeAtDepth = new int[n]; // next available node at each depth
            // Map: for each depth, list of node indices
            List<List<Integer>> byDepth = new ArrayList<>();
            for (int i = 0; i < n; i++) byDepth.add(new ArrayList<>());
            for (int i = 0; i < n; i++) byDepth.get(lev[i]).add(i);

            // Assign parents
            for (int d = 1; d < n; d++) {
                if (byDepth.get(d).isEmpty()) break;
                for (int node : byDepth.get(d)) {
                    // Find parent at depth d-1 with free slot
                    for (int par : byDepth.get(d - 1)) {
                        if (childCount[par] < 2) {
                            parent[node] = par;
                            childCount[par]++;
                            break;
                        }
                    }
                }
            }

            // Output parent array (1-indexed, root parent = 0)
            for (int i = 0; i < n; i++) {
                sb.append(parent[i] + 1);
                if (i < n - 1) sb.append(' ');
            }
            sb.append('\n');
        }

        System.out.print(sb);
    }
}
