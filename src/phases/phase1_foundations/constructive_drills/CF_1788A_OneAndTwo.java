package phases.phase1_foundations.constructive_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1788A — One and Two
 * Link   : https://codeforces.com/problemset/problem/1788/A
 * Rating : 800  |  Tags: constructive algorithms, math
 * Topic  : Phase 1: Foundations > Constructive Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * You have an array of n integers, each either 1 or 2. Find an index k (1-indexed)
 * such that the count of 2s in the prefix a[1..k] equals the count of 2s in the
 * suffix a[k+1..n]. It is guaranteed that the total count of 2s is even, so such
 * a k always exists.
 *
 * EXAMPLE
 * -------
 * Input:  5 / 1 2 2 1 2 2
 * Output: 3
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Count total number of 2s (must be even by problem guarantee).
 * 2. Scan left to right, maintaining a running count of 2s seen so far.
 * 3. The first index where running count == total/2 is the answer.
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
