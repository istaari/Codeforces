package phases.phase1_foundations.constructive_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1791C — Prepend and Append
 * Link   : https://codeforces.com/problemset/problem/1791/C
 * Rating : 800  |  Tags: constructive algorithms, strings
 * Topic  : Phase 1: Foundations > Constructive Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * You start with an empty string. Each step you either prepend '0' and append '1',
 * or prepend '1' and append '0'. After n steps you have a string of length 2n.
 * Given the final string, determine the number of steps taken (n), i.e., find the
 * maximum number of matching outer pairs ('0'...'1' or '1'...'0') that can be
 * peeled from both ends.
 *
 * EXAMPLE
 * -------
 * Input:  0110
 * Output: 2
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Use two pointers l=0, r=len-1.
 * 2. While l < r and s[l]+s[r] == '0'+'1' (i.e., they are different characters),
 *    increment count, move l right, r left.
 * 3. The count is the answer (number of steps n).
 * 4. If s[l] == s[r] the pair is invalid and we stop.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n) for the string
 * ============================================================
 */

public class CF_1791C_PrependAndAppend {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            String s = br.readLine().trim();
            int l = 0, r = s.length() - 1;
            int count = 0;
            while (l < r && s.charAt(l) != s.charAt(r)) {
                count++;
                l++;
                r--;
            }
            sb.append(count).append('\n');
        }
        System.out.print(sb);
    }
}
