package phases.phase4_advanced.segment_tree_bit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1409E — Two Platforms
 * Link   : https://codeforces.com/problemset/problem/1409/E
 * Rating : 1700  |  Tags: sorting, binary search, prefix sums
 * Topic  : Phase 4: Advanced > Segment Tree / BIT
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * n raindrops fall at x-coordinates x[i]. Two platforms of length k each.
 * Place them on the x-axis to maximize the total number of drops caught.
 * A drop is caught if it lands within the platform range [a, a+k].
 * The platforms must be placed independently (can overlap).
 *
 * EXAMPLE
 * -------
 * Input:  n=7, k=3, x=[1,2,3,4,5,6,7]
 * Output: 8  (platform 1 at [1,4] catches 4; platform 2 at [4,7] catches 4)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Sort drops. For each possible left endpoint of a platform [x[i], x[i]+k],
 *    count drops inside using binary search.
 * 2. Precompute: for each drop i (sorted), the max drops platform 1 can catch
 *    with RIGHT end at x[i]+k = prefix_right[i] = max drops in any window ending at or before x[i].
 * 3. For platform 2 starting at each i: drops = count in [x[i], x[i]+k].
 *    Combined = max drops using platform 1 on [0, x[i]-1] + drops from platform 2 at i.
 * 4. Precompute left[i] = max drops catchable by one platform using drops 0..i.
 *    Then for each starting position j of platform 2, find latest i where x[i] < x[j],
 *    combine left[i] + count_in_window(j).
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1409E_TwoPlatforms {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            long k = Long.parseLong(st.nextToken());
            long[] x = new long[n];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) x[i] = Long.parseLong(st.nextToken());
            Arrays.sort(x);

            // For each i (left edge of window at x[i]), find rightmost j where x[j] <= x[i]+k
            // count[i] = j - i + 1 = drops in window starting at x[i]
            int[] count = new int[n];
            for (int i = 0; i < n; i++) {
                int lo = i, hi = n;
                while (lo < hi) {
                    int mid = (lo + hi) / 2;
                    if (x[mid] <= x[i] + k) lo = mid + 1;
                    else hi = mid;
                }
                count[i] = lo - i; // drops in [x[i], x[i]+k]
            }

            // left[i] = max drops catchable using one platform among x[0..i]
            int[] left = new int[n];
            left[0] = count[0];
            for (int i = 1; i < n; i++) {
                left[i] = Math.max(left[i - 1], count[i]);
            }

            // For platform 2 starting at x[j], find best platform 1 before x[j]
            // i.e., platform 1 with window ending before x[j]: window right end = x[i]+k < x[j]
            // Need: x[i] + k < x[j] -> x[i] < x[j] - k
            // Find largest i where x[i] < x[j] - k -> binary search
            int ans = left[n - 1]; // only one platform
            for (int j = 0; j < n; j++) {
                // binary search for largest i where x[i] < x[j] - k (platform 1 doesn't overlap platform 2's area)
                // Actually, platforms can overlap, so just: platform 1 best on any position,
                // combined with platform 2 at j. But they can overlap.
                // Simpler: ans = max over all splits. For split at index i:
                // platform 1 covers 0..i (best window), platform 2 covers i+1..n-1 (best window).
                // Precompute right[i] = max drops for one platform in x[i..n-1].
                break;
            }

            // Recompute: right[i] = max drops using one platform among x[i..n-1]
            int[] right = new int[n + 1];
            right[n] = 0;
            // For i from n-1 down to 0: best window starting at or after x[i]
            // right[i] = max(count[i], right[i+1])
            for (int i = n - 1; i >= 0; i--) {
                right[i] = Math.max(count[i], right[i + 1]);
            }

            // Combine: for each split point i (platform 1 in 0..i, platform 2 in i+1..n-1)
            ans = right[0]; // only platform 2
            ans = Math.max(ans, left[n - 1]); // only platform 1
            for (int i = 0; i < n - 1; i++) {
                ans = Math.max(ans, left[i] + right[i + 1]);
            }

            sb.append(ans).append('\n');
        }

        System.out.print(sb);
    }
}
