package phases.phase1_foundations.c2_900rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1154A — Restoring Three Numbers
 * Link   : https://codeforces.com/problemset/problem/1154/A
 * Rating : 900  |  Tags: math
 * Topic  : Phase 1: Foundations > Math
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Three positive integers a, b, c were written down. Then their three
 * pairwise sums were computed: a+b, b+c, a+c (in some arbitrary order).
 * You are given these three pairwise sums (in some order). Restore the
 * original three numbers a, b, c and print them in any order.
 *
 * EXAMPLE
 * -------
 * Input:  3 5 6
 * Output: 2 1 4   (a+b=3, b+c=5, a+c=6 → total=7, a=7-5=2, b=7-6=1, c=7-3=4)
 *
 * Input:  40 50 90
 * Output: 40 50 0   (sum=180, half=90; 90-90=0, 90-50=40, 90-40=50)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Let x = a+b, y = b+c, z = a+c (the three given sums in any order).
 * 2. Total = x + y + z = 2*(a+b+c), so a+b+c = total / 2.
 * 3. Then: a = total/2 - (b+c) = total/2 - y,
 *           b = total/2 - (a+c) = total/2 - z,
 *           c = total/2 - (a+b) = total/2 - x.
 * 4. Since we don't know the order, compute total/2 and subtract each of x, y, z.
 *
 * COMPLEXITY
 * ----------
 * Time : O(1)
 * Space: O(1)
 * ============================================================
 */

public class CF_1154A_RestoringThreeNumbers {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        long x = Long.parseLong(st.nextToken());
        long y = Long.parseLong(st.nextToken());
        long z = Long.parseLong(st.nextToken());
        long half = (x + y + z) / 2;
        System.out.println((half - x) + " " + (half - y) + " " + (half - z));
    }
}
