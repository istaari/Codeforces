package phases.phase1_foundations.greedy_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1809B — Points on Plane
 * Link   : https://codeforces.com/problemset/problem/1809/B
 * Rating : 800  |  Tags: constructive algorithms, greedy, math
 * Topic  : Phase 1: Foundations > Greedy Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Place n points with integer coordinates (x[i], y[i]) such that:
 * - All points are distinct.
 * - 1 <= x[i] <= n, 1 <= y[i] <= n.
 * - sum(|x[i] - x[i+1]|) + sum(|y[i] - y[i+1]|) <= 4n (over consecutive points).
 * Output any valid placement.
 *
 * EXAMPLE
 * -------
 * Input:  3
 * Output: 1 1 / 2 3 / 3 2   (any valid placement)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Place points in a pattern that keeps total movement small.
 * 2. Strategy: sort by x coordinate (use each x from 1 to n once), and alternate
 *    y values (1, n, 1, n, ...) or use y = 1 for odd-indexed x, y = n for even.
 * 3. Simpler: use (i, 1) for all i from 1 to n. Sum of x-movements = n-1.
 *    Sum of y-movements = 0. Total = n-1 <= 4n. This works!
 *    But all y are 1 — they might need to be distinct? No, the problem says points distinct;
 *    (1,1),(2,1),(3,1) are distinct points.
 * 4. Even simpler valid answer: point i = (i, 1) for all i in 1..n.
 *    All points distinct. Sum movement in x: n-1. Sum movement in y: 0. Total = n-1 <= 4n.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(1)
 * ============================================================
 */

public class CF_1809B_PointsOnPlane {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            for (int i = 1; i <= n; i++) {
                sb.append(i).append(' ').append(1).append('\n');
            }
        }
        System.out.print(sb);
    }
}
