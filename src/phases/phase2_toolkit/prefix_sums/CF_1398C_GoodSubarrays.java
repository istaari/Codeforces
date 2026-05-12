package phases.phase2_toolkit.prefix_sums;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/*
 * ============================================================
 * CF 1398C — Good Subarrays
 * Link   : https://codeforces.com/problemset/problem/1398/C
 * Rating : 1200  |  Tags: prefix sums, hashing, strings
 * Topic  : Phase 2: Toolkit > Prefix Sums
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given a string s of digits. A subarray s[l..r] is "good" if for every
 * two adjacent elements |s[i] - s[i+1]| <= 1. Count the number of good
 * subarrays (pairs l <= r).
 *
 * EXAMPLE
 * -------
 * Input:  4 25
 * Output: 6
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. A subarray s[l..r] is good iff ALL consecutive pairs in it satisfy
 *    |s[i]-s[i+1]| <= 1. This means each good subarray is a maximal run
 *    of a "good" sequence.
 * 2. Find maximal consecutive "good" segments: scan left to right,
 *    maintaining current run length. When |s[i]-s[i-1]| > 1, the run
 *    breaks. Count subarrays within each maximal run.
 * 3. For a run of length L, number of subarrays = L*(L+1)/2.
 * 4. Alternative dp[i] approach: dp[i] = number of good subarrays ending
 *    at i. dp[i] = dp[i-1] + 1 if |s[i]-s[i-1]| <= 1, else dp[i] = 1.
 *    Answer = sum of all dp[i].
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
            long ans = 0;
            long dp = 1; // dp[i] = good subarrays ending at position i
            ans = 1;
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
