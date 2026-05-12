package phases.phase1_foundations.c3_1000rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1296B — Food Buying
 * Link   : https://codeforces.com/problemset/problem/1296/B
 * Rating : 1000  |  Tags: math, greedy
 * Topic  : Phase 1: Foundations > Math & Greedy
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * You have n burles. Burgers cost 1 burle each. For every 10 burgers you buy,
 * you receive a special coupon worth 1 burle. Coupons can also be used to buy
 * burgers (1 coupon = 1 burle). Find the maximum total number of burgers you
 * can get.
 *
 * EXAMPLE
 * -------
 * Input:  14
 * Output: 15   (buy 14 burgers → 1 coupon → buy 1 more = 15 total)
 *
 * Input:  100
 * Output: 111  (buy 100 → 10 coupons → buy 10 → 1 coupon → buy 1 = 111)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. With n coins: buy n burgers, earn floor(n/10) coupons.
 * 2. Use the coupons as new coins, buy more burgers, earn more coupons.
 * 3. Repeat until no more coupons (coins = 0).
 * 4. Simulate: total += coins, coins = coins / 10, repeat.
 *
 * COMPLEXITY
 * ----------
 * Time : O(log n) — coins reduce by factor of 10 each iteration
 * Space: O(1)
 * ============================================================
 */

public class CF_1296B_FoodBuying {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            long n = Long.parseLong(br.readLine().trim());
            long total = 0;
            long coins = n;
            while (coins > 0) {
                total += coins;
                coins = coins / 10; // coupons earned
            }
            sb.append(total).append('\n');
        }
        System.out.print(sb);
    }
}
