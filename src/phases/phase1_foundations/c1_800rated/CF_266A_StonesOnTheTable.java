package phases.phase1_foundations.c1_800rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * ============================================================
 * CF 266A — Stones on the Table
 * Link   : https://codeforces.com/problemset/problem/266/A
 * Rating : 800  |  Tags: implementation
 * Topic  : Phase 1: Foundations > Implementation
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * n stones are placed one by one onto a table. Each stone has a color.
 * Before placing each stone, if the top stone on the table has the same
 * color as the new stone, remove the new stone (don't place it). Count
 * how many stones are removed (not placed).
 *
 * EXAMPLE
 * -------
 * Input:  3
 *         RRB
 * Output: 1
 *
 * Input:  5
 *         RRRRR
 * Output: 4
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Iterate through the color string character by character.
 * 2. Track the last placed stone's color.
 * 3. If current stone's color equals the last placed color, increment remove count.
 * 4. Otherwise, update last placed color to current.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(1)
 * ============================================================
 */

public class CF_266A_StonesOnTheTable {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        String s = br.readLine().trim();
        int removed = 0;
        char last = s.charAt(0);
        for (int i = 1; i < n; i++) {
            char cur = s.charAt(i);
            if (cur == last) {
                removed++;
            } else {
                last = cur;
            }
        }
        System.out.println(removed);
    }
}
