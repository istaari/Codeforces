package phases.phase3_core_patterns.basic_dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1195C — Basketball Exercise
 * Link   : https://codeforces.com/problemset/problem/1195/C
 * Rating : 1400  |  Tags: dp, greedy
 * Topic  : Phase 3: Core Patterns > Basic DP
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Two rows of n players, values given. In each column i (1..n), you may
 * pick at most one player (from row 1 or row 2). Additionally, you cannot
 * pick players from the same row in consecutive columns. Maximize the sum.
 *
 * EXAMPLE
 * -------
 * Input:  5
 *         1 3 5 4 2
 *         2 4 6 3 1
 * Output: 16
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. DP with states: dp[i][0] = max sum ending at column i, having picked
 *    nobody at column i; dp[i][1] = max sum picking row1 at col i;
 *    dp[i][2] = max sum picking row2 at col i.
 * 2. Transitions from column i-1 to i:
 *    dp[i][0] = max(dp[i-1][0], dp[i-1][1], dp[i-1][2])  (skip col i)
 *    dp[i][1] = max(dp[i-1][0], dp[i-1][2]) + a[i]  (can't come from row1 prev)
 *    dp[i][2] = max(dp[i-1][0], dp[i-1][1]) + b[i]  (can't come from row2 prev)
 * 3. Answer = max(dp[n][0], dp[n][1], dp[n][2]).
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(1)
 * ============================================================
 */

public class CF_1195C_BasketballExercise {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        long[] a = new long[n], b = new long[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) a[i] = Long.parseLong(st.nextToken());
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) b[i] = Long.parseLong(st.nextToken());

        // dp0 = max sum with nobody picked at current col
        // dp1 = max sum with row1 picked at current col
        // dp2 = max sum with row2 picked at current col
        long dp0 = 0, dp1 = a[0], dp2 = b[0];

        for (int i = 1; i < n; i++) {
            long newDp0 = Math.max(dp0, Math.max(dp1, dp2));
            long newDp1 = Math.max(dp0, dp2) + a[i];
            long newDp2 = Math.max(dp0, dp1) + b[i];
            dp0 = newDp0;
            dp1 = newDp1;
            dp2 = newDp2;
        }

        System.out.println(Math.max(dp0, Math.max(dp1, dp2)));
    }
}
