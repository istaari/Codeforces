package phases.phase1_foundations.c3_1000rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1343B — Balanced Array
 * Link   : https://codeforces.com/problemset/problem/1343/B
 * Rating : 1000  |  Tags: constructive
 * Topic  : Phase 1: Foundations > Constructive
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Construct an array of n elements (n is even) such that:
 * - Exactly n/2 elements are even and n/2 elements are odd.
 * - All elements are distinct positive integers.
 * - The sum of the even elements equals the sum of the odd elements.
 * If no such array exists, print "NO".
 *
 * EXAMPLE
 * -------
 * Input:  2
 * Output: NO   (need 1 even and 1 odd with equal sums, but must be distinct positive ints)
 *
 * Input:  6
 * Output: YES
 *         1 3 5 4 2 12   (odds: 1+3+5=9, evens: 4+2+12=18? Not equal. Need to adjust.)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Use k = n/2 elements of each parity.
 * 2. Start with k smallest odd numbers: 1, 3, 5, ..., 2k-1. Sum_odd = k^2.
 * 3. Start with k smallest even numbers: 2, 4, ..., 2k. Sum_even = k*(k+1).
 * 4. Difference = sum_even - sum_odd = k*(k+1) - k^2 = k.
 * 5. We need sum_odd = sum_even. Sum_even > sum_odd by k.
 * 6. Adjust: increase the largest odd by k (replace 2k-1 with 2k-1+k = 3k-1).
 *    3k-1 must be odd (3k-1 is odd iff k is even).
 *    If k is odd: increase the largest even by k (replace 2k with 2k+k = 3k).
 *    But 3k must be even → k must be even. Contradiction if k is odd.
 *    If k is odd: answer is NO? Let's check k=1 (n=2): odds={1}, evens={2}, diff=1. NO.
 * 7. For k even: add k to the last odd (2k-1 → 2k-1+k=3k-1, which is odd when k even).
 *    Check: 3k-1 must not be in evens {2,4,...,2k}. Since 3k-1 is odd, it's not there. YES.
 * 8. For k odd: NO.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1343B_BalancedArray {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            int k = n / 2;
            if (k % 2 != 0) {
                sb.append("NO\n");
                continue;
            }
            sb.append("YES\n");
            // Odds: 1, 3, 5, ..., 2k-3, 2k-1+k (= 3k-1, replacing last odd)
            for (int i = 0; i < k - 1; i++) {
                sb.append(2 * i + 1).append(' ');
            }
            sb.append(3 * k - 1); // last odd adjusted up by k
            sb.append('\n');
            // Evens: 2, 4, ..., 2k
            for (int i = 1; i <= k; i++) {
                sb.append(2 * i);
                if (i < k) sb.append(' ');
            }
            sb.append('\n');
        }
        System.out.print(sb);
    }
}
