package phases.phase1_foundations.sorting_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/*
 * ============================================================
 * CF 1807C — Find & Replace
 * Link   : https://codeforces.com/problemset/problem/1807/C
 * Rating : 900  |  Tags: sorting, strings, constructive
 * Topic  : Phase 1: Foundations > Sorting Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * You are given two strings s and t. You must determine whether you
 * can make string s equal to string t by repeatedly applying the
 * following operation: choose a character c in s, and replace every
 * occurrence of c in s with any single character.
 *
 * In other words, each distinct character in s must be mapped to
 * exactly one character in t at the corresponding position — a
 * consistent character substitution must exist.
 *
 * EXAMPLE
 * -------
 * Input:  s = "abb", t = "xyz"
 * Output: YES  (a→x, b→y... wait b→y and b→z contradiction → NO)
 * Input:  s = "aa", t = "bc"
 * Output: NO   (a must map to both b and c — inconsistent)
 * Input:  s = "ab", t = "cc"
 * Output: YES  (a→c, b→c — many-to-one is fine)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Sorting angle: group characters of s by their value. Within each
 *    group, all corresponding t-characters must be identical (one-to-one
 *    value constraint per source character).
 * 2. Build a map: for each character c in s, record the t-character it
 *    maps to. If we see c mapping to two different t-characters → NO.
 * 3. Walk through s and t simultaneously; check consistency.
 * 4. Multiple s-chars mapping to the same t-char is allowed (surjective ok).
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(26) — fixed alphabet size
 * ============================================================
 */

public class CF_1807C_FindAndReplace {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (T-- > 0) {
            String s = br.readLine().trim();
            String t = br.readLine().trim();

            // map[c] = -1 means unset, otherwise stores the mapped t-char
            int[] map = new int[26];
            java.util.Arrays.fill(map, -1);

            boolean ok = true;
            for (int i = 0; i < s.length(); i++) {
                int sc = s.charAt(i) - 'a';
                int tc = t.charAt(i) - 'a';
                if (map[sc] == -1) {
                    map[sc] = tc;
                } else if (map[sc] != tc) {
                    ok = false;
                    break;
                }
            }

            sb.append(ok ? "YES" : "NO").append('\n');
        }

        System.out.print(sb);
    }
}
