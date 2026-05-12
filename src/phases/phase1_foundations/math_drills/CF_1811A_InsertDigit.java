package phases.phase1_foundations.math_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1811A — Insert Digit
 * Link   : https://codeforces.com/problemset/problem/1811/A
 * Rating : 900  |  Tags: greedy, strings, math
 * Topic  : Phase 1: Foundations > Math Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given a number n (as a string of digits) and a single digit d. Insert d into n
 * at any position to maximize the resulting number. Output the maximum number.
 *
 * EXAMPLE
 * -------
 * Input:  27 / 5
 * Output: 527
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Scan the number left to right.
 * 2. Insert d before the first digit that is strictly less than d.
 * 3. If no such position (all digits >= d), append d at the end.
 * 4. This maximizes the number by placing d as far left as possible while still
 *    being non-decreasing from the left perspective.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1811A_InsertDigit {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            String n = br.readLine().trim();
            int d = Integer.parseInt(br.readLine().trim());
            int pos = n.length();
            for (int i = 0; i < n.length(); i++) {
                if (n.charAt(i) - '0' < d) {
                    pos = i;
                    break;
                }
            }
            sb.append(n, 0, pos).append(d).append(n.substring(pos)).append('\n');
        }
        System.out.print(sb);
    }
}
