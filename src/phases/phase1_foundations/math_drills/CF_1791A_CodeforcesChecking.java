package phases.phase1_foundations.math_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1791A — Codeforces Checking
 * Link   : https://codeforces.com/problemset/problem/1791/A
 * Rating : 800  |  Tags: implementation, strings
 * Topic  : Phase 1: Foundations > Math Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given a single character c (lowercase Latin letter). Output "YES" if c appears
 * in the string "codeforces", otherwise output "NO".
 *
 * EXAMPLE
 * -------
 * Input:  a / c / z
 * Output: NO / YES / NO
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Check if the character is in the set {'c','o','d','e','f','r','s'} (the
 *    distinct characters of "codeforces").
 * 2. Use String.contains or a precomputed boolean array.
 *
 * COMPLEXITY
 * ----------
 * Time : O(1)
 * Space: O(1)
 * ============================================================
 */

public class CF_1791A_CodeforcesChecking {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String cf = "codeforces";
        boolean[] inCF = new boolean[26];
        for (char c : cf.toCharArray()) inCF[c - 'a'] = true;

        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            char c = br.readLine().trim().charAt(0);
            sb.append(inCF[c - 'a'] ? "YES" : "NO").append('\n');
        }
        System.out.print(sb);
    }
}
