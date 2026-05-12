package phases.phase1_foundations.c1_800rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 71A — Way Too Long Words
 * Link   : https://codeforces.com/problemset/problem/71/A
 * Rating : 800  |  Tags: strings
 * Topic  : Phase 1: Foundations > Sheet C1 (800-rated)
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given t words, abbreviate each word that has more than 10 characters
 * as: first_char + (length - 2) + last_char.
 * Words with 10 or fewer characters remain unchanged.
 *
 * EXAMPLE
 * -------
 * Input:  4 / word / localization / internationalization / pneumonoultramicroscopicsilicovolcanoconiosis
 * Output: word / l10n / i18n / p43s
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Read each word.
 * 2. If length > 10: print first char + (len-2) + last char.
 * 3. Else: print word as-is.
 *
 * COMPLEXITY
 * ----------
 * Time : O(t * L) where L is max word length
 * Space: O(L)
 * ============================================================
 */

public class CF_71A_WayTooLongWords {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            String word = br.readLine().trim();
            if (word.length() > 10) {
                sb.append(word.charAt(0))
                  .append(word.length() - 2)
                  .append(word.charAt(word.length() - 1));
            } else {
                sb.append(word);
            }
            sb.append('\n');
        }
        System.out.print(sb);
    }
}
