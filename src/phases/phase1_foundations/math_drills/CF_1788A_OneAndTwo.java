package phases.phase1_foundations.math_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1788A — One and Two
 * Link   : https://codeforces.com/problemset/problem/1788/A
 * Rating : 800  |  Tags: math, constructive algorithms
 * Topic  : Phase 1: Foundations > Math Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given an array of n integers, each either 1 or 2. Find index k (1-indexed) such
 * that count(2s in a[1..k]) == count(2s in a[k+1..n]). Guaranteed that total count
 * of 2s is even.
 *
 * EXAMPLE
 * -------
 * Input:  6 / 1 2 2 1 2 2
 * Output: 3
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Count total 2s. Half = total / 2.
 * 2. Scan left to right; first index where running count of 2s reaches half is answer.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(1)
 * ============================================================
 */

public class CF_1788A_OneAndTwo {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            StringTokenizer st = new StringTokenizer(br.readLine());
            int[] a = new int[n];
            int total2 = 0;
            for (int i = 0; i < n; i++) {
                a[i] = Integer.parseInt(st.nextToken());
                if (a[i] == 2) total2++;
            }
            int half = total2 / 2;
            int running = 0;
            for (int i = 0; i < n; i++) {
                if (a[i] == 2) running++;
                if (running == half) {
                    sb.append(i + 1).append('\n');
                    break;
                }
            }
        }
        System.out.print(sb);
    }
}
