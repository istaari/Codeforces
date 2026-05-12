package phases.phase4_advanced.shortest_paths;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1567D — Expression Evaluation Error
 * Link   : https://codeforces.com/problemset/problem/1567/D
 * Rating : 1700  |  Tags: greedy, math, number theory
 * Topic  : Phase 4: Advanced > Shortest Paths
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Polycarp computes sum 1+2+...+n. Due to integer overflow in some
 * languages, when a partial sum exceeds k, it wraps to 0 (sum mod k).
 * Find the minimum n such that the "buggy" sum (with wrapping) is NOT
 * equal to the correct sum n*(n+1)/2. I.e., find minimum n where the
 * partial sum overflows at least once.
 *
 * EXAMPLE
 * -------
 * Input:  k=7
 * Output: 4  (1+2+3=6, +4=10>7, wraps to 3, != correct 10)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Simulate: maintain buggy sum s. Add i to s each step.
 *    If s + i >= k at any point, the buggy computation wraps -> n = i is answer.
 *    But "exceeds k" means s+i > k? Or s+i >= k?
 * 2. Re-read: the "bug" occurs when s + i > k (32-bit overflow equivalent, here mod k).
 *    When s + i > k, the actual stored value becomes (s + i) mod k (or 0?).
 *    Let's say: if s + i > k, the result is wrong. Output i as answer.
 * 3. Simple loop: s = 0, for i = 1, 2, ...: if s + i > k, output i. s += i if s+i<=k.
 *
 * COMPLEXITY
 * ----------
 * Time : O(sqrt(k)) (since sum 1..n ~ n^2/2, overflow at n ~ sqrt(2k))
 * Space: O(1)
 * ============================================================
 */

public class CF_1567D_ExpressionEvaluation {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            long k = Long.parseLong(br.readLine().trim());
            long s = 0;
            long ans = -1;
            for (long i = 1; ; i++) {
                if (s + i > k) {
                    ans = i;
                    break;
                }
                s += i;
            }
            sb.append(ans).append('\n');
        }

        System.out.print(sb);
    }
}
