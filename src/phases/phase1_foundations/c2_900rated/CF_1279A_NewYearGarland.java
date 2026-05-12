package phases.phase1_foundations.c2_900rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1279A — New Year Garland
 * Link   : https://codeforces.com/problemset/problem/1279/A
 * Rating : 900  |  Tags: math, combinatorics
 * Topic  : Phase 1: Foundations > Math & Combinatorics
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * A garland is organized in layers. There are n snowflakes to place in k layers
 * (rows). Each layer must have at least one snowflake. Count the number of ways
 * to partition n distinct snowflakes into k non-empty ordered groups (layers).
 * Output the answer modulo 10^9 + 7.
 *
 * EXAMPLE
 * -------
 * Input:  3 2
 * Output: 6
 *
 * Input:  2 1
 * Output: 1
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. This is the Stirling numbers of the second kind S(n, k) multiplied by k!
 *    to account for distinguishable snowflakes in ordered layers.
 *    But since layers are ordered (garland has rows top to bottom), it's
 *    actually just S(n, k) * k! = the number of surjections from n to k...
 *    Wait, the layers are distinguishable (top layer, second layer, etc.) and
 *    we place snowflakes into layers preserving relative order.
 *    The count equals S(n, k) (Stirling 2nd kind) because items within a layer
 *    are unordered (a layer is a set) but layers are ordered.
 * 2. DP: dp[i][j] = ways to place i snowflakes in j layers.
 *    dp[i][j] = dp[i-1][j-1] + j * dp[i-1][j].
 * 3. Base case: dp[0][0] = 1.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n * k)
 * Space: O(n * k)
 * ============================================================
 */

public class CF_1279A_NewYearGarland {

    static final long MOD = 1_000_000_007L;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // dp[i][j] = Stirling(i, j) mod MOD
        long[][] dp = new long[n + 1][k + 1];
        dp[0][0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= Math.min(i, k); j++) {
                dp[i][j] = (dp[i - 1][j - 1] + (long) j * dp[i - 1][j]) % MOD;
            }
        }
        System.out.println(dp[n][k]);
    }
}
