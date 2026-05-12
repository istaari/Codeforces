package phases.phase1_foundations.sorting_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1811A — Insert Digit
 * Link   : https://codeforces.com/problemset/problem/1811/A
 * Rating : 900  |  Tags: greedy, strings, implementation
 * Topic  : Phase 1: Foundations > Sorting Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given a number n (as a string) and a digit d (0-9). Insert digit d somewhere
 * into n (at any position, including beginning or end) to maximize the resulting number.
 * Output the maximum possible number.
 *
 * EXAMPLE
 * -------
 * Input:  27 / 5
 * Output: 527
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Scan from left to right. Insert d just before the first digit that is less than d.
 * 2. If no such position found (all digits >= d), append d at the end.
 * 3. This greedy ensures the leftmost (most significant) improvement.
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
            int insertPos = n.length(); // default: append at end
            for (int i = 0; i < n.length(); i++) {
                if (n.charAt(i) - '0' < d) {
                    insertPos = i;
                    break;
                }
            }
            sb.append(n, 0, insertPos).append(d).append(n.substring(insertPos)).append('\n');
        }
        System.out.print(sb);
    }
}
