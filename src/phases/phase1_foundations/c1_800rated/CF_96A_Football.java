package phases.phase1_foundations.c1_800rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 96A — Football Kit
 * Link   : https://codeforces.com/problemset/problem/96/A
 * Rating : 800  |  Tags: strings
 * Topic  : Phase 1: Foundations > Strings
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given a string consisting of '0's and '1's representing a football
 * match commentary (0 = team A attack, 1 = team B attack). If there
 * are 7 or more consecutive identical characters anywhere in the string,
 * print "YES" (the team is dominating), otherwise print "NO".
 *
 * EXAMPLE
 * -------
 * Input:  0000000
 * Output: YES
 *
 * Input:  1010101010
 * Output: NO
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Iterate through the string tracking the current run length.
 * 2. If the current character equals the previous, increment run length.
 * 3. If the current character differs, reset run length to 1.
 * 4. If run length reaches 7 at any point, output YES and stop.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(1)
 * ============================================================
 */

public class CF_96A_Football {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine().trim();
        int run = 1;
        boolean found = false;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                run++;
                if (run >= 7) {
                    found = true;
                    break;
                }
            } else {
                run = 1;
            }
        }
        if (s.length() >= 7 && run >= 7) found = true;
        System.out.println(found ? "YES" : "NO");
    }
}
