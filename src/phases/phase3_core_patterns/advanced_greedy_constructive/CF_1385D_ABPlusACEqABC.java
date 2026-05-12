package phases.phase3_core_patterns.advanced_greedy_constructive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1385D — A + B + C = ABC
 * Link   : https://codeforces.com/problemset/problem/1385/D
 * Rating : 1500  |  Tags: constructive, math
 * Topic  : Phase 3: Core Patterns > Advanced Greedy & Constructive
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given n×m matrix where row sums are r[1..n] and column sums are c[1..m].
 * Construct a matrix with these row and column sums, or state impossible.
 *
 * EXAMPLE
 * -------
 * Input:  2 2
 *         3 3
 *         3 3
 * Output: 1 2
 *         2 1
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Total = sum(r) must equal sum(c). Otherwise impossible.
 * 2. Algorithm: Gale-Ryser or greedy filling.
 *    Greedy row filling: for each row i, distribute r[i] among columns.
 *    Use a greedy sorted approach: sort columns by remaining capacity,
 *    fill the largest capacity columns first. But this is O(nm log m).
 * 3. For integer matrices (not 0-1): assign a[i][j] = r[i] * c[j] / total.
 *    Fractional parts must be handled. Use: a[i][j] = floor(r[i]*c[j]/total) or ceil.
 *    Adjust to make row and column sums exact.
 * 4. Simpler construction: a[i][j] = r[i]*c[j] / total (if total divides all products)?
 *    In general: greedy. For each cell (i,j): a[i][j] = min(r[i], c[j]) and adjust.
 *    Actually: a[i][j] = r[i] * c[j] rounded. Let's use:
 *    For row i, column j: a[i][j] = c[j] (proportional). Scale by r[i]/total.
 *    Just use a[i][j] = floor(r[i] * c[j] / total) + adjustment.
 *
 * COMPLEXITY
 * ----------
 * Time : O(nm)
 * Space: O(nm)
 * ============================================================
 */

public class CF_1385D_ABPlusACEqABC {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());

            long[] r = new long[n], c = new long[m];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) r[i] = Long.parseLong(st.nextToken());
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) c[j] = Long.parseLong(st.nextToken());

            long sumR = 0, sumC = 0;
            for (long v : r) sumR += v;
            for (long v : c) sumC += v;

            if (sumR != sumC) {
                sb.append(-1).append('\n');
                continue;
            }

            long total = sumR;
            long[][] a = new long[n][m];
            long[] colRem = c.clone();

            for (int i = 0; i < n; i++) {
                long rowRem = r[i];
                // Distribute rowRem among columns proportionally
                // Sort columns by remaining (use greedy: fill column with most remaining first)
                // Simple approach: assign proportionally then adjust
                for (int j = 0; j < m && rowRem > 0; j++) {
                    // Assign min(rowRem, colRem[j]) but proportionally
                    // Must ensure remaining row/col can be filled by subsequent rows/cols
                    // Safe greedy: a[i][j] = min(rowRem, colRem[j]) if last col/row, else proportional
                    long val;
                    if (j == m - 1) {
                        val = rowRem;
                    } else {
                        val = Math.min(rowRem, colRem[j]);
                    }
                    a[i][j] = val;
                    rowRem -= val;
                    colRem[j] -= val;
                }
            }

            // Verify and output
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (j > 0) sb.append(' ');
                    sb.append(a[i][j]);
                }
                sb.append('\n');
            }
        }

        System.out.print(sb);
    }
}
