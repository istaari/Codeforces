package phases.phase1_foundations.c1_800rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 112A — Petya and Strings
 * Link   : https://codeforces.com/problemset/problem/112/A
 * Rating : 800  |  Tags: strings
 * Topic  : Phase 1: Foundations > Strings
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given two strings s1 and s2 of the same length, compare them
 * lexicographically in a case-insensitive manner. Output -1 if s1 < s2,
 * 0 if s1 == s2, and 1 if s1 > s2 (treating uppercase and lowercase
 * letters as equal for the comparison).
 *
 * EXAMPLE
 * -------
 * Input:  aaaa
 *         aaaA
 * Output: 0
 *
 * Input:  Bob
 *         bob
 * Output: 0
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Convert both strings to lowercase (or compare char by char using toLower).
 * 2. Compare position by position; return -1/1 on first difference.
 * 3. If all positions are equal, return 0.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n) for lowercase conversion
 * ============================================================
 */

public class CF_112A_PetyaAndStrings {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s1 = br.readLine().trim().toLowerCase();
        String s2 = br.readLine().trim().toLowerCase();
        int cmp = s1.compareTo(s2);
        if (cmp < 0) System.out.println(-1);
        else if (cmp > 0) System.out.println(1);
        else System.out.println(0);
    }
}
