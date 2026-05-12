package phases.phase1_foundations.c1_800rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 510A — Fox and Snake
 * Link   : https://codeforces.com/problemset/problem/510/A
 * Rating : 800  |  Tags: implementation
 * Topic  : Phase 1: Foundations > Implementation
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Fill an n×m grid with '#' and '.' in a snake pattern:
 * - Row 0: all '#'
 * - Row 1: only the rightmost cell (column m-1) is '#'
 * - Row 2: all '#'
 * - Row 3: only the leftmost cell (column 0) is '#'
 * - Row 4: all '#'  ... and so on (period 4).
 *
 * EXAMPLE
 * -------
 * Input:  4 3
 * Output: ###
 *         ..#
 *         ###
 *         #..
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. For each row i:
 *    - If i % 4 == 0: fill entire row with '#'.
 *    - If i % 4 == 2: fill entire row with '#'.
 *    - If i % 4 == 1: only column m-1 is '#', rest '.'.
 *    - If i % 4 == 3: only column 0 is '#', rest '.'.
 * 2. Use StringBuilder for efficient output.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n * m)
 * Space: O(m) per row
 * ============================================================
 */

public class CF_510A_FoxAndSnake {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            int mod = i % 4;
            if (mod == 0 || mod == 2) {
                // Full row of '#'
                for (int j = 0; j < m; j++) sb.append('#');
            } else if (mod == 1) {
                // Only rightmost '#'
                for (int j = 0; j < m - 1; j++) sb.append('.');
                sb.append('#');
            } else {
                // Only leftmost '#'
                sb.append('#');
                for (int j = 1; j < m; j++) sb.append('.');
            }
            sb.append('\n');
        }
        System.out.print(sb);
    }
}
