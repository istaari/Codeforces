package phases.phase1_foundations.greedy_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1807D — Odd Queries
 * Link   : https://codeforces.com/problemset/problem/1807/D
 * Rating : 1000  |  Tags: greedy, math, prefix sums
 * Topic  : Phase 1: Foundations > Greedy Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given an array a[1..n] and q queries. Each query (l, r, k): replace all elements
 * a[l..r] with k, then check if the sum of the entire array is odd. Answer YES/NO
 * for each query without actually modifying the array (queries are independent).
 *
 * EXAMPLE
 * -------
 * Input:  5 3 / 1 2 3 4 5 / 1 3 10 / 2 4 7 / 1 5 2
 * Output: YES / NO / NO
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. For each query (l, r, k): the new sum = (total_sum - sum[l..r]) + k*(r-l+1).
 * 2. Use prefix sums to compute sum[l..r] in O(1).
 * 3. new_sum is odd iff (total_sum - sum[l..r] + k*(r-l+1)) % 2 == 1.
 * 4. Parity: (total_sum - sum[l..r] + k*(r-l+1)) % 2
 *    = (total_sum + sum[l..r] + k*(r-l+1)) % 2 (subtraction = addition mod 2)
 *    Check if this is odd.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n + q)
 * Space: O(n)
 * ============================================================
 */

public class CF_1807D_OddQueries {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int q = Integer.parseInt(st.nextToken());
            long[] prefix = new long[n + 1];
            st = new StringTokenizer(br.readLine());
            for (int i = 1; i <= n; i++) {
                prefix[i] = prefix[i - 1] + Long.parseLong(st.nextToken());
            }
            long total = prefix[n];
            for (int i = 0; i < q; i++) {
                st = new StringTokenizer(br.readLine());
                int l = Integer.parseInt(st.nextToken());
                int r = Integer.parseInt(st.nextToken());
                long k = Long.parseLong(st.nextToken());
                long rangeSum = prefix[r] - prefix[l - 1];
                long newSum = total - rangeSum + k * (long)(r - l + 1);
                sb.append(newSum % 2 != 0 ? "YES" : "NO").append('\n');
            }
        }
        System.out.print(sb);
    }
}
