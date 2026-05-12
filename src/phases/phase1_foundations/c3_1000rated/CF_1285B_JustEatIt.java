package phases.phase1_foundations.c3_1000rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1285B — Just Eat It!
 * Link   : https://codeforces.com/problemset/problem/1285/B
 * Rating : 1000  |  Tags: greedy, dp
 * Topic  : Phase 1: Foundations > Greedy
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given array a of n integers. The array is "good" if no proper prefix and
 * no proper suffix has a sum strictly greater than the total sum of the array.
 * In other words: for all 1 ≤ i < n, prefix_sum[i] ≤ total AND
 * suffix_sum[i] ≤ total. Determine whether the array is good.
 *
 * EXAMPLE
 * -------
 * Input:  2 / 1 2
 * Output: YES   (prefix[1]=1 ≤ 3 ✓, suffix[1]=2 ≤ 3 ✓)
 *
 * Input:  2 / -1 2
 * Output: NO    (prefix[1]=-1 ≤ 1 ✓, but suffix[1]=2 > 1 ✗)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Compute total sum S.
 * 2. Compute running prefix sums. If any prefix sum > S, output NO.
 * 3. Compute running suffix sums. If any suffix sum > S, output NO.
 * 4. Equivalently: prefix[i] ≤ S for all i means all suffix sums ≥ 0
 *    (since suffix = S - prefix). And suffix[i] ≤ S means all prefix sums ≥ 0.
 * 5. So: array is good iff all prefix sums are ≥ 0 AND all suffix sums are ≥ 0.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(1)
 * ============================================================
 */

public class CF_1285B_JustEatIt {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            long[] a = new long[n];
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) a[i] = Long.parseLong(st.nextToken());

            // Check all prefix sums >= 0 (i.e., suffix <= total) and all suffix sums >= 0
            boolean good = true;
            long prefixSum = 0;
            for (int i = 0; i < n - 1; i++) {
                prefixSum += a[i];
                if (prefixSum < 0) { good = false; break; }
            }
            if (good) {
                long suffixSum = 0;
                for (int i = n - 1; i >= 1; i--) {
                    suffixSum += a[i];
                    if (suffixSum < 0) { good = false; break; }
                }
            }
            sb.append(good ? "YES" : "NO").append('\n');
        }
        System.out.print(sb);
    }
}
