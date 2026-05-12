package phases.phase4_advanced.segment_tree_bit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1353F — Decreasing Heights
 * Link   : https://codeforces.com/problemset/problem/1353/F
 * Rating : 1800  |  Tags: dp, BFS, grid
 * Topic  : Phase 4: Advanced > Segment Tree / BIT
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * n×m grid, each cell has height h[i][j]. You can assign new heights to cells.
 * Cost of changing cell (i,j) from h[i][j] to new value = |h[i][j] - new|.
 * You can only move right or down. The heights along any valid path from (0,0)
 * to (n-1,m-1) must be strictly decreasing. Minimize total cost of height changes.
 *
 * EXAMPLE
 * -------
 * Input:  2x2, heights=[[4,3],[2,1]]
 * Output: 0  (already decreasing)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Let new[i][j] = h[i][j] - (i+j) to normalize. After normalization, we need
 *    new[i][j] >= new[i'][j'] when (i,j) -> (i',j') (right or down). So all paths
 *    must be non-increasing in normalized values.
 * 2. But original strictly decreasing = after subtracting (i+j), we need equal or
 *    decreasing... Actually: strict decrease of at least 1 each step. Normalize:
 *    val[i][j] = h[i][j] - (i+j). We want new_val[i][j] to be non-increasing along paths.
 *    So we want to choose new_val[i][j] such that new_val is non-increasing (all paths).
 *    Cost[i][j] = |val[i][j] - new_val[i][j]| = |h[i][j] - (i+j) - new_val[i][j]|.
 * 3. To minimize total cost: new_val[i][j] should be as close to val[i][j] as possible
 *    but non-increasing. Optimal: for each cell, new_val[i][j] = min over all reachable
 *    cells (0,0) to (i,j) of val values? No.
 * 4. DP: process in BFS order (left-to-right, top-to-bottom). For each cell,
 *    new_val[i][j] = floor(val[i][j]) subject to <= new_val of cell above and left.
 *    But "best" is min(val[i][j], new_val[i-1][j], new_val[i][j-1]).
 * 5. new_val[i][j] = min(h[i][j] - (i+j), new_val coming from above/left).
 *    Cost += max(0, new_val[i][j] - (h[i][j] - (i+j))) ... wait.
 *    If new_val[i][j] < val[i][j], cost = val[i][j] - new_val[i][j] = h[i][j]-(i+j) - new_val.
 *    We want to DECREASE the normalized value only, not increase.
 *    So new_val[i][j] = min(val[i][j], min_predecessor).
 *    Cost = val[i][j] - new_val[i][j] = max(0, ...) -> this is always >= 0 since we only decrease.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n*m)
 * Space: O(n*m)
 * ============================================================
 */

public class CF_1353F_DecreasingHeights {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            long[][] h = new long[n][m];
            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < m; j++) h[i][j] = Long.parseLong(st.nextToken());
            }

            // Normalize: val[i][j] = h[i][j] - (i+j)
            // new_val[i][j] <= val[i][j] (can only decrease normalized value)
            // new_val[i][j] <= new_val[i-1][j] and new_val[i][j] <= new_val[i][j-1]
            // Optimal: new_val[i][j] = min(val[i][j], new_val[i-1][j], new_val[i][j-1])
            long[][] nv = new long[n][m];
            long totalCost = 0;

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    long val = h[i][j] - (i + j);
                    long best = val;
                    if (i > 0) best = Math.min(best, nv[i - 1][j]);
                    if (j > 0) best = Math.min(best, nv[i][j - 1]);
                    nv[i][j] = best;
                    totalCost += val - best; // we only decrease, so val >= best
                }
            }

            sb.append(totalCost).append('\n');
        }

        System.out.print(sb);
    }
}
