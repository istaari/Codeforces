package phases.phase1_foundations.sorting_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1800A — Is It a Cat?
 * Link   : https://codeforces.com/problemset/problem/1800/A
 * Rating : 900  |  Tags: implementation, strings
 * Topic  : Phase 1: Foundations > Sorting Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given a string, check if it represents a "cat sound": it must match the pattern
 * M+E+O+W+ (case-insensitive), where each letter can appear one or more times
 * consecutively in order. That is: one or more M/m, followed by one or more E/e,
 * followed by one or more O/o, followed by one or more W/w, and nothing else.
 *
 * EXAMPLE
 * -------
 * Input:  meow / mmeeoow / meoW / mew
 * Output: YES / YES / YES / NO
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Convert string to lowercase.
 * 2. Check that the string matches regex: m+e+o+w+ by iterating through expected
 *    groups in order.
 * 3. Use a pointer: consume all 'm's, then all 'e's, then all 'o's, then all 'w's.
 * 4. If the pointer reaches the end exactly and each group had at least one char, YES.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1800A_IsItACat {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        String target = "meow";
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            String s = br.readLine().trim().toLowerCase();
            int n = s.length();
            int i = 0;
            boolean valid = true;
            for (int g = 0; g < 4; g++) {
                char expected = target.charAt(g);
                int start = i;
                while (i < n && s.charAt(i) == expected) i++;
                if (i == start) { valid = false; break; } // at least one required
            }
            if (i != n) valid = false;
            sb.append(valid ? "YES" : "NO").append('\n');
        }
        System.out.print(sb);
    }
}
