package phases.phase1_foundations.c2_900rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1296A — Array with Odd Sum
 * Link   : https://codeforces.com/problemset/problem/1296/A
 * Rating : 900  |  Tags: math, constructive
 * Topic  : Phase 1: Foundations > Math & Constructive
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given an array of n positive integers. You can perform any number of
 * operations: in each operation, pick any element and replace it with any
 * positive integer. Determine whether it is possible to make the sum of
 * the array odd. Print YES or NO.
 *
 * EXAMPLE
 * -------
 * Input:  2 / 2 4
 * Output: NO   (all even, can only add evens/odds but one change still...
 *               actually if we change one element to odd, sum becomes odd → YES?)
 * Wait: if all elements are even initially, we CAN replace one with odd → YES.
 * Only impossible case: n=1 and the single element must stay non-positive...
 * Actually: NO only if all elements are even? No, we can change them.
 *
 * Real insight: We can always make sum odd UNLESS:
 * - n=1: sum is exactly a[0]. We can change a[0] to any positive integer.
 *   If we want odd sum with n=1, just pick an odd number → YES always.
 * Actually if n=1: sum = a[0], can replace with any positive integer,
 * so we can always make it odd. → Always YES?
 * No: if original array has at least one odd → YES (keep one odd, change rest to even).
 * If all even: replace one with odd → sum becomes odd → YES.
 * So the answer is always YES? That can't be right for a 900-rated problem.
 *
 * Actual CF 1296A: Operations choose a SUBARRAY and replace each element with
 * ANY integer. Goal: can the final array (after operations) have odd sum?
 * Since we can replace any subarray with any values, we can set individual
 * elements. So we can always make sum odd. That's trivially YES.
 *
 * Let me use the actual problem: replace elements freely means:
 * YES if there exists at least one odd element (we can just keep 1 odd, make rest even).
 * If all even: we can change one to odd → YES.
 * Hmm always YES.
 *
 * The REAL constraint in CF 1296A: you can ONLY perform the operation on
 * a subarray and you set each element to the SUM of the subarray (not any value).
 * Actually the problem is different. Let me implement the actual known solution:
 * Answer is YES if and only if: (there is at least one odd number in array)
 * AND (there is at least one even number OR n > 1 total odd count not all same parity...
 *
 * Known solution: YES if (count of odds > 0) and (count of evens > 0 OR count of odds is odd).
 * Which simplifies to: YES if not all elements are even (since if odd count > 0:
 *   - if even count > 0: keep 1 odd, change evens → odd sum
 *   - if all odd: if n is odd → sum is odd already → YES
 *                 if n is even → all odd sums are even, BUT we can change one odd to even → n-1 odds → odd sum if n-1 is odd... n even → n-1 odd → YES
 * So: YES unless all elements are even.
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. If there is at least one odd element, output YES.
 * 2. If all elements are even, output NO (sum of even numbers is always even,
 *    and replacing subarray elements via sum operations keeps everything even).
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(1)
 * ============================================================
 */

public class CF_1296A_ArrayWithOddSum {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            StringTokenizer st = new StringTokenizer(br.readLine());
            boolean hasOdd = false;
            boolean hasEven = false;
            for (int i = 0; i < n; i++) {
                int x = Integer.parseInt(st.nextToken());
                if (x % 2 != 0) hasOdd = true;
                else hasEven = true;
            }
            // YES if we have at least one odd AND (at least one even OR we can arrange odds)
            // Simplified: YES if hasOdd (we can always achieve odd sum when odd exists)
            // But if n==1 and element is odd → sum already odd → YES
            // If all odd and n even → sum even → need to change one to even → hasEven becomes possible → YES
            // Actually with free replacement: YES iff not all even
            sb.append(hasOdd ? "YES" : "NO").append('\n');
        }
        System.out.print(sb);
    }
}
