package phases.phase1_foundations.c2_900rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1263A — Sweet Problem
 * Link   : https://codeforces.com/problemset/problem/1263/A
 * Rating : 900  |  Tags: math, greedy
 * Topic  : Phase 1: Foundations > Math & Greedy
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * You have three piles of candies: a (red), b (green), c (blue). Each day
 * you eat exactly two candies of different colors: either (red+green),
 * (green+blue), or (red+blue). Maximize the number of days you can eat.
 *
 * EXAMPLE
 * -------
 * Input:  1 1 1
 * Output: 1
 *
 * Input:  2 2 2
 * Output: 3
 *
 * Input:  3 2 0
 * Output: 2   (eat red+green twice, but also limited by: total/2 = 5/2 = 2,
 *               and total - max(3,2,0) = 5-3=2)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Each day removes 2 candies total.
 * 2. Upper bound 1: floor((a+b+c) / 2) — can't eat more than total/2 days.
 * 3. Upper bound 2: total - max(a,b,c) — the largest pile can't dominate
 *    (each day uses at most 1 from the largest pile, so days ≤ sum of others).
 * 4. Answer = min(floor(total/2), total - max(a,b,c)).
 *
 * COMPLEXITY
 * ----------
 * Time : O(1)
 * Space: O(1)
 * ============================================================
 */

public class CF_1263A_SweetProblem {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            long a = Long.parseLong(st.nextToken());
            long b = Long.parseLong(st.nextToken());
            long c = Long.parseLong(st.nextToken());
            long total = a + b + c;
            long maxPile = Math.max(a, Math.max(b, c));
            long ans = Math.min(total / 2, total - maxPile);
            sb.append(ans).append('\n');
        }
        System.out.print(sb);
    }
}
