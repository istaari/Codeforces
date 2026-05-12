package phases.phase1_foundations.greedy_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1829A — Love Story
 * Link   : https://codeforces.com/problemset/problem/1829/A
 * Rating : 800  |  Tags: strings, implementation
 * Topic  : Phase 1: Foundations > Greedy Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given a string s of length n (n <= 10), count the minimum number of character
 * replacements needed to make s equal to the prefix of "codeforces" of length n.
 * In other words, count positions i where s[i] != "codeforces"[i].
 *
 * EXAMPLE
 * -------
 * Input:  3 / code / code / dode
 * Output: 0 / 0 / 1
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Compare s[i] with "codeforces"[i] for each position i.
 * 2. Count mismatches.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(1)
 * ============================================================
 */

public class CF_1829A_LoveStory {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String target = "codeforces";
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            String s = br.readLine().trim();
            int count = 0;
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) != target.charAt(i)) count++;
            }
            sb.append(count).append('\n');
        }
        System.out.print(sb);
    }
}
