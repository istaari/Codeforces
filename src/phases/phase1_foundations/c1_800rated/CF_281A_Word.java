package phases.phase1_foundations.c1_800rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 281A — Word
 * Link   : https://codeforces.com/problemset/problem/281/A
 * Rating : 800  |  Tags: strings
 * Topic  : Phase 1: Foundations > Strings
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given a word consisting of lowercase and uppercase English letters.
 * If the number of uppercase letters is strictly greater than the number
 * of lowercase letters, convert the entire word to uppercase. Otherwise
 * (including ties), convert the entire word to lowercase. Output the result.
 *
 * EXAMPLE
 * -------
 * Input:  HoLLy
 * Output: HOLLY
 *
 * Input:  wOrLd
 * Output: world
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Count uppercase and lowercase letters in the string.
 * 2. If uppercase count > lowercase count → toUpperCase(); else → toLowerCase().
 * 3. Print the transformed string.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_281A_Word {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine().trim();
        int upper = 0, lower = 0;
        for (char c : s.toCharArray()) {
            if (Character.isUpperCase(c)) upper++;
            else lower++;
        }
        System.out.println(upper > lower ? s.toUpperCase() : s.toLowerCase());
    }
}
