package phases.phase3_core_patterns.basic_dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1183H — Subsequences (Easy version CF 1183E)
 * Link   : https://codeforces.com/problemset/problem/1183/H
 * Rating : 1400  |  Tags: dp, combinatorics, strings
 * Topic  : Phase 3: Core Patterns > Basic DP
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given a string s of length n and an integer k. Count the number of
 * subsequences of s that have length exactly k. Output modulo 1e9+7.
 *
 * EXAMPLE
 * -------
 * Input:  4 2
 *         abcd
 * Output: 6
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. dp[i][j] = number of subsequences of length j using the first i characters.
 * 2. Transition: dp[i][j] = dp[i-1][j] + dp[i-1][j-1].
 *    Either don't include s[i] (dp[i-1][j]) or include it (dp[i-1][j-1]).
 * 3. Base: dp[0][0] = 1, dp[i][0] = 1 for all i.
 * 4. Answer: dp[n][k].
 * 5. Optimize space: since dp[i] only depends on dp[i-1], use 1D array.
 *    Process j from k down to 1 (like 0/1 knapsack).
 *
 * COMPLEXITY
 * ----------
 * Time : O(n * k)
 * Space: O(k)
 * ============================================================
 */

public class CF_1183H_SubsequencesEasy {

    static final long MOD = 1_000_000_007L;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        br.readLine(); // read the string (not needed for counting all subseqs)

        // dp[j] = number of subsequences of length j using elements seen so far
        long[] dp = new long[k + 1];
        dp[0] = 1;

        for (int i = 0; i < n; i++) {
            // Process from k down to 1 to avoid using same element twice
            for (int j = Math.min(i + 1, k); j >= 1; j--) {
                dp[j] = (dp[j] + dp[j - 1]) % MOD;
            }
        }

        System.out.println(dp[k]);
    }
}
