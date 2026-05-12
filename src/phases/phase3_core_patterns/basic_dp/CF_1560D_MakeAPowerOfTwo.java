package phases.phase3_core_patterns.basic_dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/*
 * ============================================================
 * CF 1560D — Make a Power of Two
 * Link   : https://codeforces.com/problemset/problem/1560/D
 * Rating : 1400  |  Tags: dp, greedy, strings, implementation
 * Topic  : Phase 3: Core Patterns > Basic DP
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given a number n (as a string). In one operation you can either:
 * (1) remove one digit, or (2) duplicate one digit (insert a copy adjacent).
 * Find the minimum number of operations to make n a power of 2.
 *
 * EXAMPLE
 * -------
 * Input:  1
 * Output: 0
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Powers of 2 up to about 10^18 are: 1, 2, 4, 8, ..., 2^62 ≈ 4.6e18.
 *    There are only about 63 such powers. Enumerate each target power of 2.
 * 2. For each target T (as string), compute the minimum edits from n to T
 *    using only deletions and duplications. Deletions reduce length; duplications
 *    increase length. We want to transform n into T using minimum total ops.
 * 3. Key: to go from n to T, we can:
 *    - Remove digits from n that don't appear in T (cost: 1 per removal).
 *    - Duplicate digits in n to match T (cost: 1 per duplication).
 *    This is essentially: find the longest common subsequence (LCS) of n and T
 *    as strings. The minimum ops = (len(n) - LCS) + (len(T) - LCS)
 *    = len(n) + len(T) - 2*LCS.
 *    The (len(n)-LCS) deletions remove n's extra digits.
 *    The (len(T)-LCS) duplications create T's extra digits from matched ones.
 * 4. Since T has at most 19 digits and n has at most 19 digits (per constraints),
 *    LCS can be computed in O(|n|*|T|) which is at most 19*19 = 361 per target.
 *
 * COMPLEXITY
 * ----------
 * Time : O(63 * |n|^2)
 * Space: O(|n|^2)
 * ============================================================
 */

public class CF_1560D_MakeAPowerOfTwo {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        // Precompute all powers of 2 up to 2^62 as strings
        String[] powers = new String[63];
        long pw = 1;
        for (int i = 0; i < 63; i++) {
            powers[i] = Long.toString(pw);
            if (i < 62) pw *= 2;
        }

        while (t-- > 0) {
            String n = br.readLine().trim();
            int ans = Integer.MAX_VALUE;

            for (String target : powers) {
                int lcs = lcs(n, target);
                int ops = (n.length() - lcs) + (target.length() - lcs);
                ans = Math.min(ans, ops);
            }

            sb.append(ans).append('\n');
        }

        System.out.print(sb);
    }

    static int lcs(String a, String b) {
        int m = a.length(), n = b.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[m][n];
    }
}
