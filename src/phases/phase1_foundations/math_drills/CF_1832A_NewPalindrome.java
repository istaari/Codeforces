package phases.phase1_foundations.math_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1832A — New Palindrome
 * Link   : https://codeforces.com/problemset/problem/1832/A
 * Rating : 900  |  Tags: math, strings, constructive
 * Topic  : Phase 1: Foundations > Math Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given a palindrome string s. Rearrange its characters to form a different palindrome.
 * Output YES if a different palindrome exists, NO otherwise.
 *
 * EXAMPLE
 * -------
 * Input:  abba / aaa / abacaba
 * Output: YES / NO / YES
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. A palindrome can be rearranged into a different palindrome iff there exist at least
 *    two distinct characters that appear an even number of times (both >= 2) in the half.
 * 2. More precisely: look at the first half of the palindrome. If the first half has at
 *    least 2 distinct characters, we can swap their positions to get a different palindrome.
 * 3. Equivalently: count distinct characters that appear >= 2 times (even count).
 *    If at least 2 such distinct characters exist in the first half, YES.
 * 4. Even simpler: in the original palindrome's first half (length n/2), count distinct chars.
 *    If distinct chars in first half >= 2, then YES (we can rearrange them differently).
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(1)
 * ============================================================
 */

public class CF_1832A_NewPalindrome {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            String s = br.readLine().trim();
            int n = s.length();
            // Look at first half
            HashSet<Character> chars = new HashSet<>();
            for (int i = 0; i < n / 2; i++) {
                chars.add(s.charAt(i));
            }
            // If first half has >= 2 distinct chars, we can make a different palindrome
            sb.append(chars.size() >= 2 ? "YES" : "NO").append('\n');
        }
        System.out.print(sb);
    }
}
