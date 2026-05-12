package phases.phase2_toolkit.two_pointers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1409D — Decrease the Sum of Digits
 * Link   : https://codeforces.com/problemset/problem/1409/D
 * Rating : 1300  |  Tags: greedy, strings, math
 * Topic  : Phase 2: Toolkit > Two Pointers
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given a number n (as a string) and integer k. Find the minimum number
 * of operations to make the digit sum of n at most k. Each operation
 * decreases n by 1. Output the minimum number of operations.
 *
 * EXAMPLE
 * -------
 * Input:  30
 *         6
 * Output: 3
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. We want to reduce digit sum to <= k by subtracting some value x from n.
 *    Minimize x. The result n - x should have digit sum <= k.
 * 2. Key insight: to minimize x, we want n - x to be as large as possible.
 *    The best approach: round down the last m digits to 0 (i.e., truncate
 *    to a multiple of 10^m). Find the smallest m such that the digit sum
 *    of the prefix (first len-m digits) is <= k. Then x = n mod 10^m.
 *    But after rounding down, prefix might need adjustment.
 * 3. Actually: try each possible number of suffix digits to zero out (0 to len).
 *    If we zero out the last m digits: new number = floor(n / 10^m) * 10^m.
 *    Digit sum of new number = digit sum of first (len-m) digits of n.
 *    Find the largest prefix length p = len - m where digit sum of first p
 *    digits <= k (i.e., smallest m where this holds).
 * 4. Answer = n - floor(n / 10^m) * 10^m = last m digits as a number.
 *    Use big integer or string arithmetic since n can be huge.
 *
 * COMPLEXITY
 * ----------
 * Time : O(len^2) but len <= 100 so fine
 * Space: O(len)
 * ============================================================
 */

public class CF_1409D_DecreaseTheSum {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder out = new StringBuilder();

        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String n = st.nextToken();
            int k = Integer.parseInt(st.nextToken());

            int len = n.length();

            // Find minimum m (digits zeroed from right) such that
            // digit sum of n[0..len-m-1] <= k
            int m = 0;
            // Compute digit sum of entire number first
            int totalSum = 0;
            for (char c : n.toCharArray()) totalSum += c - '0';

            if (totalSum <= k) {
                out.append(0).append('\n');
                continue;
            }

            // Try increasing m from 0; accumulate prefix digit sum from left
            // When we zero last m digits, prefix sum = sum of first (len-m) digits
            int prefixSum = 0;
            for (int i = 0; i < len; i++) prefixSum += n.charAt(i) - '0';

            // Remove digits from right one by one until prefixSum <= k
            // After zeroing last m digits: prefix is first (len - m) digits
            // prefix sum decreases by removing last included digit
            m = 0;
            int curSum = totalSum;
            while (curSum > k) {
                curSum -= (n.charAt(len - 1 - m) - '0');
                m++;
            }

            // Answer = n mod 10^m = last m digits of n as number
            // But if prefix needs to be decremented (if we just set last m to 0,
            // the prefix is unchanged — only valid if we're subtracting to nearest
            // multiple of 10^m). Actually: floor(n/10^m)*10^m uses first (len-m) digits
            // unchanged (no carry), so digit sum is just sum of first (len-m) digits.
            // x = n - floor(n/10^m)*10^m = value of last m digits
            String suffix = n.substring(len - m);
            BigInteger x = new BigInteger(suffix);
            // But we might need to round up the prefix by 1 to push digit sum further.
            // Wait: current digit sum of prefix = curSum after loop above. If curSum <= k, done.
            // x = value of last m digits (this is the subtraction needed to zero last m digits).
            out.append(x).append('\n');
        }

        System.out.print(out);
    }
}
