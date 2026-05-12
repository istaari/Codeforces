package phases.phase1_foundations.c3_1000rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1220B — Multiplication Table
 * Link   : https://codeforces.com/problemset/problem/1220/B
 * Rating : 1000  |  Tags: math, binary search
 * Topic  : Phase 1: Foundations > Math & Binary Search
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given an n×m multiplication table where entry (i,j) = i*j (1 ≤ i ≤ n,
 * 1 ≤ j ≤ m). Find the k-th smallest element in this table.
 *
 * EXAMPLE
 * -------
 * Input:  2 3 4
 * Output: 3   (table: 1,2,3,2,4,6 sorted: 1,2,2,3,4,6 → 4th = 3)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Binary search on the answer x.
 * 2. Count function: how many entries in the table are ≤ x?
 *    For each row i (1..n): entries in that row are i, 2i, ..., m*i.
 *    Count of entries ≤ x in row i = min(m, floor(x / i)).
 *    Total = sum over i=1..n of min(m, x/i).
 * 3. Binary search for the smallest x such that count(x) ≥ k.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n * log(n*m))
 * Space: O(1)
 * ============================================================
 */

public class CF_1220B_MultiplicationTable {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        long n = Long.parseLong(st.nextToken());
        long m = Long.parseLong(st.nextToken());
        long k = Long.parseLong(st.nextToken());

        long lo = 1, hi = n * m, ans = hi;
        while (lo <= hi) {
            long mid = (lo + hi) / 2;
            long count = 0;
            for (long i = 1; i <= n; i++) {
                count += Math.min(m, mid / i);
            }
            if (count >= k) {
                ans = mid;
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }
        System.out.println(ans);
    }
}
