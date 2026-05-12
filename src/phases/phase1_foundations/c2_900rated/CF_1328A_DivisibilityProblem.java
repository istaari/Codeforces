package phases.phase1_foundations.c2_900rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1328A — Divisibility Problem
 * Link   : https://codeforces.com/problemset/problem/1328/A
 * Rating : 900  |  Tags: math
 * Topic  : Phase 1: Foundations > Math
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given two positive integers a and b. In one operation you can either
 * increment a by 1 or decrement a by 1. Find the minimum number of
 * operations to make a divisible by b.
 *
 * EXAMPLE
 * -------
 * Input:  10 4
 * Output: 2   (10 → 12, 12 % 4 == 0)
 *
 * Input:  13 9
 * Output: 5   (13 → 18, 18 % 9 == 0)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Compute r = a % b.
 * 2. We can either decrease a by r (r operations) or increase a by (b - r) operations.
 * 3. Answer = min(r, b - r).
 * 4. Special case: if r == 0, answer is 0 already.
 *
 * COMPLEXITY
 * ----------
 * Time : O(1)
 * Space: O(1)
 * ============================================================
 */

public class CF_1328A_DivisibilityProblem {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int r = a % b;
            sb.append(Math.min(r, b - r)).append('\n');
        }
        System.out.print(sb);
    }
}
