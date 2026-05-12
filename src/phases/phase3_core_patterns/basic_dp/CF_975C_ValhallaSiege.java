package phases.phase3_core_patterns.basic_dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 975C — Valhalla Siege
 * Link   : https://codeforces.com/problemset/problem/975/C
 * Rating : 1400  |  Tags: dp, prefix sums, binary search
 * Topic  : Phase 3: Core Patterns > Basic DP
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * n waves of enemies arrive. You have a health pool h. Wave i has
 * enemies with total power p[i]. Enemies deal p[i] damage to the pool.
 * After each wave, if pool <= 0, defense fails. There are q queries:
 * each query increases the max health pool by some amount or asks if
 * the defense can withstand to wave i. Actually:
 *
 * n waves, pool = h initially. Each wave deals d[i] damage.
 * But you can repair: between waves you can heal. Find the first wave
 * where cumulative damage exceeds current pool.
 *
 * ACTUAL CF 975C: n is the total number of enemies. Enemies arrive in
 * groups. After each group, check if cumulative damage > health. Find
 * first group that causes health to drop to 0.
 *
 * RE-READ: n enemies arrive over time in groups. Group sizes given.
 * Health is h. Each enemy deals 1 damage. Find first group after which
 * cumulative damage >= h. Binary search on prefix sums.
 *
 * EXAMPLE
 * -------
 * Input:  5 5
 *         2 3
 * Output: Wave 2
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Compute prefix sums of wave damages. Defense fails at first wave w
 *    where prefix_sum[w] >= h (or > 0 if started at h).
 * 2. Binary search on prefix sums array to find the first wave where
 *    cumulative damage >= h. Since prefix sums are non-decreasing, binary
 *    search works in O(log n).
 * 3. For online queries (each query updates h or asks for current failing wave):
 *    maintain sorted list or use binary search on prefix sum array.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n + q log n)
 * Space: O(n)
 * ============================================================
 */

public class CF_975C_ValhallaSiege {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        long[] prefix = new long[n + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            prefix[i] = prefix[i - 1] + Long.parseLong(st.nextToken());
        }

        long currentH = 0;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int type = Integer.parseInt(st.nextToken());
            if (type == 1) {
                // Add to health pool
                currentH += Long.parseLong(st.nextToken());
            } else {
                // Query: find first wave where cumulative damage >= currentH
                // Binary search in prefix[1..n] for first index where prefix[idx] >= currentH
                int lo = 1, hi = n, ans = -1;
                while (lo <= hi) {
                    int mid = (lo + hi) / 2;
                    if (prefix[mid] >= currentH) {
                        ans = mid;
                        hi = mid - 1;
                    } else {
                        lo = mid + 1;
                    }
                }
                sb.append(ans).append('\n');
            }
        }

        System.out.print(sb);
    }
}
