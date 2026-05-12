package phases.phase3_core_patterns.basic_dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/*
 * ============================================================
 * CF 1398C — Good Subarrays (DP version)
 * Link   : https://codeforces.com/problemset/problem/1398/C
 * Rating : 1300  |  Tags: dp, prefix sums, strings
 * Topic  : Phase 3: Core Patterns > Basic DP
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given a string of digits. Count the number of contiguous subarrays
 * where every pair of adjacent elements differs by at most 1.
 *
 * EXAMPLE
 * -------
 * Input:  1
 *         4 25
 * Output: 6
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. DP approach: dp[i] = number of good subarrays ending at position i.
 * 2. Transition: if |s[i] - s[i-1]| <= 1, dp[i] = dp[i-1] + 1 (extend
 *    all subarrays ending at i-1, plus the single element subarray [i,i]).
 *    Else dp[i] = 1 (only the single element subarray [i,i] is good).
 * 3. Answer = sum of all dp[i].
 * 4. This DP is O(n) and computes the answer directly without prefix sums.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(1)
 * ============================================================
 */

public class CF_1398C_GoodSubarrays {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            String s = br.readLine().trim();
            int n = s.length();
            long ans = 1;
            long dp = 1;
            for (int i = 1; i < n; i++) {
                if (Math.abs((s.charAt(i) - '0') - (s.charAt(i - 1) - '0')) <= 1) {
                    dp++;
                } else {
                    dp = 1;
                }
                ans += dp;
            }
            sb.append(ans).append('\n');
        }

        System.out.print(sb);
    }
}
