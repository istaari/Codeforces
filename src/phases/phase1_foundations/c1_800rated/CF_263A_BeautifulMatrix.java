package phases.phase1_foundations.c1_800rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 263A — Beautiful Matrix
 * Link   : https://codeforces.com/problemset/problem/263/A
 * Rating : 800  |  Tags: implementation
 * Topic  : Phase 1: Foundations > Implementation
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given a 5×5 matrix, find the position of the element 5. Count the
 * minimum number of moves (up, down, left, right — each move shifts
 * the element by one cell) needed to bring the element 5 to the center
 * of the matrix, which is position (2, 2) (0-indexed).
 *
 * EXAMPLE
 * -------
 * Input:  1 2 3 4 5
 *         6 7 8 9 10
 *         11 12 13 14 15
 *         16 17 18 19 20
 *         21 22 23 24 25
 * Output: 6   (5 is at (0,4), center is (2,2), |0-2|+|4-2|=4)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Scan the 5×5 matrix to find the row r and column c where value is 5.
 * 2. The center is at (2, 2).
 * 3. The answer is the Manhattan distance: |r - 2| + |c - 2|.
 *
 * COMPLEXITY
 * ----------
 * Time : O(25) = O(1)
 * Space: O(1)
 * ============================================================
 */

public class CF_263A_BeautifulMatrix {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int row = -1, col = -1;
        for (int i = 0; i < 5; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 5; j++) {
                int val = Integer.parseInt(st.nextToken());
                if (val == 5) {
                    row = i;
                    col = j;
                }
            }
        }
        System.out.println(Math.abs(row - 2) + Math.abs(col - 2));
    }
}
