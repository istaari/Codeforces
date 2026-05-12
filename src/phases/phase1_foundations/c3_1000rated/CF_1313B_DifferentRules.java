package phases.phase1_foundations.c3_1000rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1313B — Different Rules
 * Link   : https://codeforces.com/problemset/problem/1313/B
 * Rating : 1000  |  Tags: math, constructive
 * Topic  : Phase 1: Foundations > Constructive & Math
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Construct any permutation p of 1..n such that:
 * - p[1] + p[2] = s (the first two elements sum to s).
 * Output any valid permutation or -1 if impossible.
 *
 * EXAMPLE
 * -------
 * Input:  5 3
 * Output: 1 2 3 4 5   (1+2=3✓)
 *
 * Input:  3 4
 * Output: 3 1 2   (3+1=4✓)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. We need two distinct values a and b (1 ≤ a,b ≤ n, a≠b) with a+b = s.
 * 2. Try a=1, b=s-1. Check if b is valid (1 < b ≤ n and b != 1... b=s-1 ≥ 2 iff s≥3).
 * 3. If valid: put 1 and s-1 first, then fill the rest in order.
 * 4. Fill remaining positions with values 2..n excluding the two chosen values.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1313B_DifferentRules {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            // Try to find a, b such that a+b=s, 1<=a<b<=n
            int a = -1, b = -1;
            for (int x = 1; x <= n; x++) {
                int y = s - x;
                if (y > 0 && y <= n && y != x) {
                    a = x;
                    b = y;
                    break;
                }
            }
            if (a == -1) {
                sb.append(-1).append('\n');
                continue;
            }
            sb.append(a).append(' ').append(b);
            for (int i = 1; i <= n; i++) {
                if (i != a && i != b) sb.append(' ').append(i);
            }
            sb.append('\n');
        }
        System.out.print(sb);
    }
}
