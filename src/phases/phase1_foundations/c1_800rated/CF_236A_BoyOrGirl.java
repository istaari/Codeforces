package phases.phase1_foundations.c1_800rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 236A — Boy or Girl
 * Link   : https://codeforces.com/problemset/problem/236/A
 * Rating : 800  |  Tags: implementation
 * Topic  : Phase 1: Foundations > Implementation
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given a username string of lowercase Latin letters. If the number of
 * distinct characters in the username is even, print "CHAT WITH HER!".
 * If the number of distinct characters is odd, print "IGNORE HIM!".
 *
 * EXAMPLE
 * -------
 * Input:  wjmzbmr
 * Output: CHAT WITH HER!
 *
 * Input:  xiaodao
 * Output: IGNORE HIM!
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Insert all characters of the string into a HashSet to find distinct count.
 * 2. Check if the set size is even or odd.
 * 3. Print the appropriate message.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(1) — at most 26 distinct lowercase letters
 * ============================================================
 */

public class CF_236A_BoyOrGirl {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine().trim();
        HashSet<Character> set = new HashSet<>();
        for (char c : s.toCharArray()) {
            set.add(c);
        }
        System.out.println(set.size() % 2 == 0 ? "CHAT WITH HER!" : "IGNORE HIM!");
    }
}
