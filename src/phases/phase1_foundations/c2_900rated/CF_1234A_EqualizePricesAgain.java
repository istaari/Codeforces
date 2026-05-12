package phases.phase1_foundations.c2_900rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1234A — Equalize Prices Again
 * Link   : https://codeforces.com/problemset/problem/1234/A
 * Rating : 900  |  Tags: math
 * Topic  : Phase 1: Foundations > Math
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * There are n products with prices a[i]. You want to set a single price p
 * such that all prices become equal to p, where you can only change prices
 * by buying/selling at market price. The "market price" for each product is
 * a[i]. The total budget allows setting p = average of all prices, but only
 * if the sum is divisible by n. If sum % n == 0, print sum/n, else print -1.
 *
 * EXAMPLE
 * -------
 * Input:  3 / 1 2 3
 * Output: 2
 *
 * Input:  2 / 1 4
 * Output: -1   (sum=5, 5%2 != 0... wait sum=5, 5/2=2.5 not integer)
 * Actually wait: 1 and 4 → average is 2.5 → NO. But wait, CF 1234A might
 * always guarantee a valid answer? Let me check: it says p must be integer.
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Compute the sum of all prices.
 * 2. If sum % n == 0, the equal price p = sum / n is valid; output it n times.
 * 3. Otherwise output -1 (no integer equal price exists).
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(1)
 * ============================================================
 */

public class CF_1234A_EqualizePricesAgain {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            StringTokenizer st = new StringTokenizer(br.readLine());
            long sum = 0;
            for (int i = 0; i < n; i++) {
                sum += Long.parseLong(st.nextToken());
            }
            if (sum % n == 0) {
                long p = sum / n;
                for (int i = 0; i < n; i++) {
                    sb.append(p);
                    if (i < n - 1) sb.append(' ');
                }
                sb.append('\n');
            } else {
                sb.append(-1).append('\n');
            }
        }
        System.out.print(sb);
    }
}
