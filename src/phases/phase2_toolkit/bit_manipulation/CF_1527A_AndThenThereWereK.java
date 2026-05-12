package phases.phase2_toolkit.bit_manipulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/*
 * ============================================================
 * CF 1527A — And Then There Were K
 * Link   : https://codeforces.com/problemset/problem/1527/A
 * Rating : 800  |  Tags: bitmasks, math
 * Topic  : Phase 2: Toolkit > Bit Manipulation
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given n, find the largest k such that (n & (n-1) & ... & k) > 0.
 * In other words, find the largest k such that the bitwise AND of all
 * integers from k to n is nonzero.
 *
 * EXAMPLE
 * -------
 * Input:  5
 * Output: 4
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. The AND of all integers k..n is nonzero iff there exists a bit
 *    position b such that all numbers from k to n have bit b set.
 * 2. For bit b to be set in all k..n: we need k >= 2^b and n < 2^(b+1)
 *    (i.e., all numbers are in range [2^b, 2^(b+1)-1]).
 * 3. Equivalently: find the highest set bit of n, say 2^b. Then
 *    k = 2^b gives AND of [2^b, n] = 2^b (since bit b is set in all).
 *    Any k < 2^b would include some number < 2^b with bit b clear,
 *    making AND = 0 for that bit. So answer is 2^b (highest power of 2 <= n).
 * 4. Formula: k = Integer.highestOneBit(n).
 *
 * COMPLEXITY
 * ----------
 * Time : O(log n)
 * Space: O(1)
 * ============================================================
 */

public class CF_1527A_AndThenThereWereK {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            // Largest k = highest power of 2 <= n
            sb.append(Integer.highestOneBit(n)).append('\n');
        }

        System.out.print(sb);
    }
}
