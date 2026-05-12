package phases.phase1_foundations.c3_1000rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1228B — Filling the Grid
 * Link   : https://codeforces.com/problemset/problem/1228/B
 * Rating : 1000  |  Tags: implementation, math
 * Topic  : Phase 1: Foundations > Implementation
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * An n×n grid must be filled with 0s (white) and 1s (black). Constraints
 * are given as sequences: for each row i, the first cnt[i] cells are black
 * followed by white, and for each column j, the first cnt[j] cells are black
 * followed by white. Fill the grid consistently or output -1 if contradiction.
 *
 * EXAMPLE
 * -------
 * Input:  3 / 3 1 2 / 1 2 1 (rows then cols)
 * Output: grid or -1
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. For each row i with parameter r[i]: cells (i, 0..r[i]-1) = 1 (black),
 *    cells (i, r[i]..n-1) = 0 (white) if r[i] < n.
 * 2. For each col j with parameter c[j]: cells (0..c[j]-1, j) = 1,
 *    cells (c[j]..n-1, j) = 0 if c[j] < n.
 * 3. Build constraint arrays: row_force[i][j] and col_force[i][j].
 *    Each cell has row-forced value (1, 0, or unknown) and col-forced value.
 * 4. If row says 1 and col says 0 (or vice versa) → contradiction → -1.
 * 5. If unknown from both → free, fill with 0 (or any valid choice).
 *
 * COMPLEXITY
 * ----------
 * Time : O(n^2)
 * Space: O(n^2)
 * ============================================================
 */

public class CF_1228B_FillingTheGrid {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            int[] row = new int[n]; // first row[i] are black
            int[] col = new int[n]; // first col[j] are black
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) row[i] = Integer.parseInt(st.nextToken());
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) col[j] = Integer.parseInt(st.nextToken());

            int[][] grid = new int[n][n];
            boolean valid = true;
            outer:
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    int rowVal = (j < row[i]) ? 1 : (j == row[i] && row[i] < n ? 0 : -1);
                    int colVal = (i < col[j]) ? 1 : (i == col[j] && col[j] < n ? 0 : -1);
                    // -1 means "unknown" from that constraint
                    int forced;
                    if (rowVal == -1 && colVal == -1) forced = 0;
                    else if (rowVal == -1) forced = colVal;
                    else if (colVal == -1) forced = rowVal;
                    else if (rowVal == colVal) forced = rowVal;
                    else { valid = false; break outer; }
                    grid[i][j] = forced;
                }
            }
            if (!valid) {
                sb.append(-1).append('\n');
            } else {
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) sb.append(grid[i][j]);
                    sb.append('\n');
                }
            }
        }
        System.out.print(sb);
    }
}
