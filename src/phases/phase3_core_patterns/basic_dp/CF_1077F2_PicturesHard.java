package phases.phase3_core_patterns.basic_dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1077F2 — Pictures with Kittens (Hard version)
 * Link   : https://codeforces.com/problemset/problem/1077/F2
 * Rating : 1500  |  Tags: dp, deque, segment tree
 * Topic  : Phase 3: Core Patterns > Basic DP
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * n pictures lined up, each with beauty value a[i]. Choose exactly k
 * pictures such that consecutive chosen pictures are at most x positions
 * apart (i.e., chosen[j+1] - chosen[j] <= x). Also must choose picture 1
 * and picture n. Maximize total beauty.
 *
 * EXAMPLE
 * -------
 * Input:  6 2 2
 *         1 2 3 4 5 6
 * Output: 15
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. dp[j][i] = max beauty of choosing exactly j pictures, with the j-th
 *    picture being picture i. Answer = dp[k][n].
 * 2. Transition: dp[j][i] = max(dp[j-1][t]) + a[i] for t in [i-x, i-1].
 *    This is a sliding window maximum over a range of the previous DP layer.
 * 3. Use a monotonic deque (deque of indices) to maintain the maximum of
 *    dp[j-1][t] for t in [i-x, i-1] as i increases.
 * 4. For each layer j from 2 to k, scan i from j to n-(k-j):
 *    - Maintain deque with dp[j-1] values for valid window [i-x, i-1].
 *    - dp[j][i] = deque_max + a[i].
 * 5. Must pick first and last: force dp[1][1]=a[1], rest=-inf; force
 *    final answer dp[k][n].
 *
 * COMPLEXITY
 * ----------
 * Time : O(n * k)
 * Space: O(n * k) or O(n) with rolling array
 * ============================================================
 */

public class CF_1077F2_PicturesHard {

    static final long NEG_INF = Long.MIN_VALUE / 2;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());

        long[] a = new long[n + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) a[i] = Long.parseLong(st.nextToken());

        // dp[i] = max beauty choosing j pictures with last at position i
        long[] dp = new long[n + 1];
        long[] ndp = new long[n + 1];
        Arrays.fill(dp, NEG_INF);
        dp[1] = a[1]; // must start at picture 1

        for (int j = 2; j <= k; j++) {
            Arrays.fill(ndp, NEG_INF);
            ArrayDeque<Integer> deque = new ArrayDeque<>(); // stores indices, max dp[] at front

            for (int i = j; i <= n - (k - j); i++) {
                // Add dp[i-1] to window if i-1 >= j-1 and i-1 >= 1
                int newIdx = i - 1;
                if (newIdx >= j - 1 && dp[newIdx] != NEG_INF) {
                    while (!deque.isEmpty() && dp[deque.peekLast()] <= dp[newIdx]) {
                        deque.pollLast();
                    }
                    deque.addLast(newIdx);
                }
                // Remove elements out of window (too far left)
                while (!deque.isEmpty() && deque.peekFirst() < i - x) {
                    deque.pollFirst();
                }
                if (!deque.isEmpty() && dp[deque.peekFirst()] != NEG_INF) {
                    ndp[i] = dp[deque.peekFirst()] + a[i];
                }
            }

            long[] tmp = dp;
            dp = ndp;
            ndp = tmp;
        }

        System.out.println(dp[n]);
    }
}
