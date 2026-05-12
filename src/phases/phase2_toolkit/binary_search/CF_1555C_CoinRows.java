package phases.phase2_toolkit.binary_search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1555C — Coin Rows
 * Link   : https://codeforces.com/problemset/problem/1555/C
 * Rating : 1300  |  Tags: binary search, dp, greedy
 * Topic  : Phase 2: Toolkit > Binary Search
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * A 2-row grid of width n. Alice picks a path from (1,1) to (2,n)
 * (only right or down moves). Bob also picks a path from (1,1) to (2,n).
 * Bob wants to maximize coins collected only on his exclusive cells.
 * Alice moves first and wants to minimize Bob's maximum exclusive collection.
 * Find the optimal value (Alice minimizes, Bob maximizes).
 *
 * EXAMPLE
 * -------
 * Input:  5
 *         1 3 4 5 2
 *         2 1 3 4 6
 * Output: 7
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Alice's path transitions from row1 to row2 at some column c
 *    (meaning she goes right along row1[1..c] then down to row2, then
 *    right to row2[c..n]).
 * 2. Bob will take the complementary L-shape: row2[1..c-1] or row1[c+1..n]
 *    plus the exclusive part. Specifically Bob will choose either:
 *    - Top path exclusive: suffix of row1 from c+1..n
 *    - Bottom path exclusive: prefix of row2 from 1..c-1
 *    Bob picks the max of these two.
 * 3. Alice picks the transition column c to minimize max(suffixRow1[c+1],
 *    prefixRow2[c]).
 * 4. Precompute suffix sums for row1 and prefix sums for row2.
 *    Try all c from 1..n, answer = min over c of max(suffix1[c+1], prefix2[c]).
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1555C_CoinRows {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());

        long[] row1 = new long[n + 1];
        long[] row2 = new long[n + 1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) row1[i] = Long.parseLong(st.nextToken());
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) row2[i] = Long.parseLong(st.nextToken());

        // suffix sum of row1: suffR1[i] = row1[i] + row1[i+1] + ... + row1[n]
        long[] suffR1 = new long[n + 2];
        for (int i = n; i >= 1; i--) suffR1[i] = suffR1[i + 1] + row1[i];

        // prefix sum of row2: prefR2[i] = row2[1] + ... + row2[i]
        long[] prefR2 = new long[n + 1];
        for (int i = 1; i <= n; i++) prefR2[i] = prefR2[i - 1] + row2[i];

        // Alice transitions at column c (1-indexed): she walks row1[1..c] then row2[c..n]
        // Bob's exclusive choices:
        //   suffix of row1 from c+1..n = suffR1[c+1]
        //   prefix of row2 from 1..c-1 = prefR2[c-1]
        // Alice minimizes max of these two
        long ans = Long.MAX_VALUE;
        for (int c = 1; c <= n; c++) {
            long bobChoice = Math.max(suffR1[c + 1], prefR2[c - 1]);
            ans = Math.min(ans, bobChoice);
        }

        System.out.println(ans);
    }
}
