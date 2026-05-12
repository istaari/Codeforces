package phases.phase1_foundations.c1_800rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 118A — String Task
 * Link   : https://codeforces.com/problemset/problem/118/A
 * Rating : 800  |  Tags: strings
 * Topic  : Phase 1: Foundations > Strings
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given a string, perform the following two operations in order:
 * 1. Delete all vowels (a, e, i, o, u, y — both upper and lowercase).
 * 2. For each remaining character, prepend a period (".") before it
 *    and convert it to lowercase.
 * Print the resulting string.
 *
 * EXAMPLE
 * -------
 * Input:  tour
 * Output: .t.r
 *
 * Input:  CodeForces
 * Output: .c.d.f.r.c.s
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Define a set of vowels: {a, e, i, o, u, y} (case-insensitive).
 * 2. Iterate through each character of the string.
 * 3. If it is NOT a vowel, append "." + lowercase(char) to the result.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_118A_StringTask {

    private static final String VOWELS = "aeiouyAEIOUY";

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine().trim();
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (VOWELS.indexOf(c) == -1) {
                sb.append('.').append(Character.toLowerCase(c));
            }
        }
        System.out.println(sb);
    }
}
