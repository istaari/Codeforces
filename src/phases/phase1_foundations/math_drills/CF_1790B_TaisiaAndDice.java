package phases.phase1_foundations.math_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1790B — Taisia and Dice
 * Link   : https://codeforces.com/problemset/problem/1790/B
 * Rating : 1100  |  Tags: math, greedy, brute force
 * Topic  : Phase 1: Foundations > Math Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Taisia rolls 4 dice (each showing a value 1-6). The sum is s. She tells you s and
 * three of the four dice values (r1, r2, r3). Find the fourth die value r4.
 * r4 = s - r1 - r2 - r3. Output r4 (guaranteed to be in range [1,6]).
 *
 * EXAMPLE
 * -------
 * Input:  14 3 4 5
 * Output: 2
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. r4 = s - r1 - r2 - r3.
 * 2. The answer is guaranteed to be in [1,6] by problem constraints.
 *
 * COMPLEXITY
 * ----------
 * Time : O(1)
 * Space: O(1)
 * ============================================================
 */

public class CF_1790B_TaisiaAndDice {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int r1 = Integer.parseInt(st.nextToken());
            int r2 = Integer.parseInt(st.nextToken());
            int r3 = Integer.parseInt(st.nextToken());
            sb.append(s - r1 - r2 - r3).append('\n');
        }
        System.out.print(sb);
    }
}
