package phases.phase1_foundations.c2_900rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1352A — Sum of Round Numbers
 * Link   : https://codeforces.com/problemset/problem/1352/A
 * Rating : 900  |  Tags: implementation, math
 * Topic  : Phase 1: Foundations > Math & Implementation
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * A round number is a positive integer that has exactly one non-zero digit
 * (e.g. 1, 2, 10, 700, 5000). Given n, decompose it into a sum of round
 * numbers — one for each non-zero digit position. Output the count of terms
 * and the terms themselves.
 *
 * EXAMPLE
 * -------
 * Input:  5432
 * Output: 4
 *         5000 400 30 2
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Iterate through each digit position (ones, tens, hundreds, ...).
 * 2. If the digit d at position i is > 0, add d * 10^i as a term.
 * 3. Collect all non-zero contributions and output them.
 *
 * COMPLEXITY
 * ----------
 * Time : O(log n)
 * Space: O(log n)
 * ============================================================
 */

public class CF_1352A_SumOfRoundNumbers {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            List<Integer> parts = new ArrayList<>();
            int multiplier = 1;
            int temp = n;
            while (temp > 0) {
                int digit = temp % 10;
                if (digit > 0) {
                    parts.add(digit * multiplier);
                }
                temp /= 10;
                multiplier *= 10;
            }
            sb.append(parts.size()).append('\n');
            for (int i = parts.size() - 1; i >= 0; i--) {
                sb.append(parts.get(i));
                if (i > 0) sb.append(' ');
            }
            sb.append('\n');
        }
        System.out.print(sb);
    }
}
