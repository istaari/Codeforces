package phases.phase1_foundations.math_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1798A — Showstopper
 * Link   : https://codeforces.com/problemset/problem/1798/A
 * Rating : 1000  |  Tags: strings, math, implementation
 * Topic  : Phase 1: Foundations > Math Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given two strings s and t of the same length n. Determine if s can be transformed
 * into t by changing at most 1 character. Output YES if 0 or 1 positions differ, NO otherwise.
 *
 * EXAMPLE
 * -------
 * Input:  abc / abd
 * Output: YES
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Count the number of positions i where s[i] != t[i].
 * 2. If count <= 1, output YES; else NO.
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
            int diff = 0;
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) != target.charAt(i)) diff++;
            }
            sb.append(diff <= 1 ? "YES" : "NO").append('\n');
        }
        System.out.print(sb);
    }
}
