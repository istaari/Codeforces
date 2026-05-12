package phases.phase1_foundations.math_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1795A — Two Towers
 * Link   : https://codeforces.com/problemset/problem/1795/A
 * Rating : 1000  |  Tags: math, greedy, implementation
 * Topic  : Phase 1: Foundations > Math Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Two towers of discs: tower 1 with n discs and tower 2 with m discs, each disc
 * colored 'W' (white) or 'B' (black) from bottom to top. You can move the top disc
 * of tower 1 to the top of tower 2 (or vice versa). Goal: merge them into one tower
 * with no two adjacent discs of the same color (alternating). Find if possible and
 * min moves. Actually: the combined tower (tower1 + reversed tower2 stacked) should
 * have no two adjacent same-color. Count "bad adjacencies" (same color pairs) in the
 * concatenated string (tower1 bottom-to-top + tower2 top-to-bottom). Each bad adjacency
 * requires one move to fix (move the dividing point). The answer is the number of
 * same-color adjacent pairs at the junction and within each tower at the merging point.
 * More precisely: concatenate tower1 (bottom-to-top) with reversed tower2. Count
 * same-color adjacent pairs in this combined string. That count equals the min moves.
 *
 * EXAMPLE
 * -------
 * Input:  2 2 / BW / WB
 * Output: 0
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Concatenate s = tower1 + reverse(tower2).
 * 2. Count positions i where s[i] == s[i+1].
 * 3. That count is the answer (minimum moves).
 *
 * COMPLEXITY
 * ----------
 * Time : O(n + m)
 * Space: O(n + m)
 * ============================================================
 */

public class CF_1795A_TwoTowers {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            String t1 = br.readLine().trim(); // tower1 bottom-to-top
            String t2 = br.readLine().trim(); // tower2 bottom-to-top
            // Combined: t1 + reversed(t2)
            String combined = t1 + new StringBuilder(t2).reverse().toString();
            int badPairs = 0;
            for (int i = 0; i + 1 < combined.length(); i++) {
                if (combined.charAt(i) == combined.charAt(i + 1)) badPairs++;
            }
            sb.append(badPairs).append('\n');
        }
        System.out.print(sb);
    }
}
