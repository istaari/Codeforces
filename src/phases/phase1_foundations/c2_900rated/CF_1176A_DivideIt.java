package phases.phase1_foundations.c2_900rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1176A — Divide It!
 * Link   : https://codeforces.com/problemset/problem/1176/A
 * Rating : 900  |  Tags: implementation
 * Topic  : Phase 1: Foundations > Implementation
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given an integer n. In one operation you can either divide n by 2 or
 * divide n by 3 (only when n is exactly divisible). Find the minimum
 * number of operations to reduce n to 1, or output -1 if impossible.
 *
 * EXAMPLE
 * -------
 * Input:  6
 * Output: 2   (6/2=3, 3/3=1)
 *
 * Input:  4
 * Output: 2   (4/2=2, 2/2=1)
 *
 * Input:  1
 * Output: 0
 *
 * Input:  7
 * Output: -1
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Repeatedly try to divide by 2 or 3 (greedily, either works since
 *    both are prime factors — we need to exhaust them).
 * 2. While n != 1: try dividing by 2, then by 3.
 * 3. If at any point n is not divisible by 2 or 3 and n != 1, output -1.
 *
 * COMPLEXITY
 * ----------
 * Time : O(log n)
 * Space: O(1)
 * ============================================================
 */

public class CF_1176A_DivideIt {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            long n = Long.parseLong(br.readLine().trim());
            int ops = 0;
            boolean possible = true;
            while (n != 1) {
                if (n % 2 == 0) {
                    n /= 2;
                } else if (n % 3 == 0) {
                    n /= 3;
                } else {
                    possible = false;
                    break;
                }
                ops++;
            }
            sb.append(possible ? ops : -1).append('\n');
        }
        System.out.print(sb);
    }
}
