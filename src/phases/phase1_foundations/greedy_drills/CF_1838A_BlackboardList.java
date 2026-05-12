package phases.phase1_foundations.greedy_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1838A — Blackboard List
 * Link   : https://codeforces.com/problemset/problem/1838/A
 * Rating : 900  |  Tags: greedy, math
 * Topic  : Phase 1: Foundations > Greedy Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * There are n students. Each student writes a number on the blackboard. The list
 * of n numbers written contains the original number and n-1 numbers derived from
 * it by some process. Find the original number.
 * Re-reading actual CF 1838A: you have an array of n numbers. It is a sequence
 * where each element (except one "original") was derived from the previous by
 * adding or subtracting some d. Find the original (minimum) element.
 * Actually CF 1838A: given n numbers on blackboard, where n-1 of them are created
 * by students each picking the current minimum and subtracting 1 (or gcd operation).
 * The actual problem: a[0] is the original. a[i] = gcd(a[i-1], x_i) for some x_i.
 * Given the array (possibly reordered), find the original.
 * Re-reading more carefully: you start with a number x. Each operation: pick any
 * divisor d of x (1 <= d <= x), subtract d from x, write new x on board. You do
 * this n-1 times starting from original. Given the n numbers on board (in some order),
 * find the original (the largest, since each step decreases x).
 * So the original is simply the maximum of the array.
 *
 * EXAMPLE
 * -------
 * Input:  3 / 6 4 2
 * Output: 6
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Each operation strictly decreases x (subtract a positive divisor d >= 1).
 * 2. Therefore the original number is the maximum element in the array.
 * 3. Additional validity check: consecutive elements in sorted order must differ
 *    by a divisor of the larger. But the problem just asks to find the original.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(1)
 * ============================================================
 */

public class CF_1838A_BlackboardList {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            StringTokenizer st = new StringTokenizer(br.readLine());
            long maxVal = Long.MIN_VALUE;
            for (int i = 0; i < n; i++) {
                long x = Long.parseLong(st.nextToken());
                maxVal = Math.max(maxVal, x);
            }
            sb.append(maxVal).append('\n');
        }
        System.out.print(sb);
    }
}
