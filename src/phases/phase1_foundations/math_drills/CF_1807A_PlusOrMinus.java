package phases.phase1_foundations.math_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1807A — Plus or Minus
 * Link   : https://codeforces.com/problemset/problem/1807/A
 * Rating : 800  |  Tags: math
 * Topic  : Phase 1: Foundations > Math Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given three integers a, b, c. Determine whether a + b = c or a - b = c.
 * It is guaranteed that exactly one condition holds. Output '+' or '-'.
 *
 * EXAMPLE
 * -------
 * Input:  1 2 3
 * Output: +
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. If a + b == c, output '+'.
 * 2. Otherwise output '-' (since a - b == c is guaranteed).
 *
 * COMPLEXITY
 * ----------
 * Time : O(1)
 * Space: O(1)
 * ============================================================
 */

public class CF_1807A_PlusOrMinus {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            sb.append(a + b == c ? '+' : '-').append('\n');
        }
        System.out.print(sb);
    }
}
