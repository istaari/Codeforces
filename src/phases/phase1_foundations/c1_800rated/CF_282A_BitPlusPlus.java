package phases.phase1_foundations.c1_800rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * ============================================================
 * CF 282A — Bit++
 * Link   : https://codeforces.com/problemset/problem/282/A
 * Rating : 800  |  Tags: implementation
 * Topic  : Phase 1: Foundations > Implementation
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * A variable X starts at 0. Given n operations, each either "++X", "X++",
 * "--X", or "X--". Process all operations in order and print the final
 * value of X. Increment operations add 1; decrement operations subtract 1.
 *
 * EXAMPLE
 * -------
 * Input:  6
 *         ++X
 *         X++
 *         --X
 *         X--
 *         X++
 *         --X
 * Output: 0
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Check the second character of the string (index 1).
 * 2. If it is '+', increment X; if it is '-', decrement X.
 * 3. Print the final value after all operations.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(1)
 * ============================================================
 */

public class CF_282A_BitPlusPlus {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        int x = 0;
        for (int i = 0; i < n; i++) {
            String op = br.readLine().trim();
            if (op.charAt(1) == '+') {
                x++;
            } else {
                x--;
            }
        }
        System.out.println(x);
    }
}
