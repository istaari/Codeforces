package phases.phase3_core_patterns.basic_dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1509C — The Sports Festival
 * Link   : https://codeforces.com/problemset/problem/1509/C
 * Rating : 1600  |  Tags: dp, sorting, greedy
 * Topic  : Phase 3: Core Patterns > Basic DP
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * n athletes, each can participate in events within interval [l[i], r[i]].
 * Choose exactly one event per athlete (a value in [l[i], r[i]]).
 * Minimize the difference between the maximum and minimum chosen values.
 *
 * EXAMPLE
 * -------
 * Input:  3
 *         3 5
 *         1 4
 *         2 8
 * Output: 2
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Sort intervals by their right endpoint. After sorting, consider
 *    the minimum chosen value. For a fixed minimum value x, all intervals
 *    containing x can choose x; others must choose the smallest value
 *    in their interval that is >= x. This makes the maximum as small as
 *    possible.
 * 2. DP approach: sort by r[i]. For each i as the "last" interval (rightmost r),
 *    find the minimum possible maximum of chosen values when we must include
 *    all intervals sorted up to i.
 * 3. dp[i] = minimum possible maximum value when intervals 0..i are all assigned
 *    and interval i determines the current maximum (i.e., interval i's chosen
 *    value is the maximum).
 * 4. After sorting by r: dp[i] = min over j < i of (max(dp[j], l[i])) where
 *    interval i must pick a value >= some value determined by previous choices.
 *    Actually: dp[i] = min possible "maximum" value in an assignment covering
 *    intervals 0..i, with i being the one with the largest right endpoint.
 *    dp[i] = max(dp[i-1], l[i]) (if we want minimum max, set current to max of
 *    previous max and l[i] — meaning the maximum is determined by whoever has
 *    the largest minimum in the required range).
 * 5. Then the answer = min over all i of (r[i] - dp[i]) where dp[i] <= r[i].
 *    Wait: the range is [dp[i], r[i]] — all chosen values in this range, so
 *    spread = r[i] - dp[i]. We want all n athletes, so must cover all intervals.
 *    The i-th athlete (sorted by r) has assigned value in [l[i], r[i]].
 *    If we fix the minimum as v, then all assigned values are >= v. The athlete
 *    with smallest r[i] = r[0] constrains the max to <= r[0]. So we enumerate
 *    which athlete has the binding upper constraint.
 * 6. Sort by r. For each "right boundary" i (fixing max <= r[i]):
 *    All intervals j <= i must pick values in [l[j], r[i]]. If l[j] > r[i],
 *    impossible. Minimum possible minimum = max(l[0], l[1], ..., l[i]).
 *    Answer = r[i] - max_l[0..i].
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log n)
 * Space: O(1)
 * ============================================================
 */

public class CF_1509C_TheSportsFestival {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            long[][] intervals = new long[n][2];
            for (int i = 0; i < n; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                intervals[i][0] = Long.parseLong(st.nextToken()); // l
                intervals[i][1] = Long.parseLong(st.nextToken()); // r
            }

            // Sort by right endpoint
            Arrays.sort(intervals, (a, b) -> Long.compare(a[1], b[1]));

            long ans = Long.MAX_VALUE;
            long maxL = Long.MIN_VALUE;

            for (int i = 0; i < n; i++) {
                maxL = Math.max(maxL, intervals[i][0]);
                // Right boundary = intervals[i][1], minimum = maxL
                // All intervals 0..i must pick from [maxL, intervals[i][1]]
                // Check all l[j] <= intervals[i][1] (guaranteed by sort? No, l[j] could > r[i])
                // If maxL > intervals[i][1]: impossible for this i (some interval has l > this r)
                if (maxL <= intervals[i][1]) {
                    ans = Math.min(ans, intervals[i][1] - maxL);
                }
            }

            sb.append(ans).append('\n');
        }

        System.out.print(sb);
    }
}
