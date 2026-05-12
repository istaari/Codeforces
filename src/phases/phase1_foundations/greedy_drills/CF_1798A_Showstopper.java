package phases.phase1_foundations.greedy_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1798A — Showstopper
 * Link   : https://codeforces.com/problemset/problem/1798/A
 * Rating : 1000  |  Tags: greedy, strings, implementation
 * Topic  : Phase 1: Foundations > Greedy Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * You have two strings s and t of the same length n. You want to find a position
 * (1-indexed) i such that you can replace s[i] with any character to make s equal
 * to t, OR the strings are already equal. If such a single position exists (or
 * already equal), output YES and the position (or 0 if already equal); else NO.
 * Re-reading actual CF 1798A: given strings s and t, determine if s can become t
 * by changing exactly one character (or is already equal with 0 changes needed).
 * Count mismatches; if 0 or 1, output YES.
 *
 * EXAMPLE
 * -------
 * Input:  3 / abc / abc / abc / abd / abc / xyz
 * Output: YES / YES / NO
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Count the number of positions where s[i] != t[i].
 * 2. If count == 0: already equal, YES.
 * 3. If count == 1: change that one position, YES.
 * 4. If count > 1: NO.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1798A_Showstopper {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            String s = br.readLine().trim();
            String target = br.readLine().trim();
            int mismatches = 0;
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) != target.charAt(i)) mismatches++;
            }
            sb.append(mismatches <= 1 ? "YES" : "NO").append('\n');
        }
        System.out.print(sb);
    }
}
