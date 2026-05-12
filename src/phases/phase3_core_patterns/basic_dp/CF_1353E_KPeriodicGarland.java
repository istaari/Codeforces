package phases.phase3_core_patterns.basic_dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1353E — K-periodic Garland
 * Link   : https://codeforces.com/problemset/problem/1353/E
 * Rating : 1500  |  Tags: dp, prefix sums
 * Topic  : Phase 3: Core Patterns > Basic DP
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given array a of n integers and integer k. Find the maximum sum of a
 * subsequence where consecutive elements have indices differing by exactly k
 * (i.e., indices i1 < i2 < ... with i2-i1 = k, i3-i2 = k, etc.).
 * In other words, find the maximum sum of elements a[i], a[i+k], a[i+2k], ...
 * for some starting index i in [0, k-1], choosing which elements to include.
 *
 * EXAMPLE
 * -------
 * Input:  8 3
 *         -1 2 3 -2 3 -1 2 3
 * Output: 8
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. For each "thread" r = 0, 1, ..., k-1 (indices with same value mod k):
 *    thread r = a[r], a[r+k], a[r+2k], ...
 *    Find the maximum subarray sum (Kadane's algorithm) within this thread.
 * 2. But the problem says "turn on" some bulbs (no need for contiguous!).
 *    Actually: choose any subset of positions from the thread (indices spaced by k).
 *    Wait — re-read: "k-periodic garland" = bulbs at positions that form an
 *    arithmetic sequence with step k. We pick ONE such arithmetic sequence
 *    (choose starting position and which bulbs in it to turn on).
 *    The constraint: chosen indices form a subset of positions i, i+k, i+2k, ...
 *    for fixed i. We can choose any subset of this thread.
 *    To maximize sum: for each thread, the max sum = sum of all positive elements.
 *    Then take the max over all threads.
 * 3. For each thread, sum of positive elements = max achievable.
 *    But we need at least one element chosen (subsequence must be non-empty).
 *    Answer = max over all threads of (sum of positive elements in thread),
 *    but if all are negative, answer = max single element.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(1)
 * ============================================================
 */

public class CF_1353E_KPeriodicGarland {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());
            long[] a = new long[n];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) a[i] = Long.parseLong(st.nextToken());

            long ans = Long.MIN_VALUE;

            for (int r = 0; r < k; r++) {
                // Thread: indices r, r+k, r+2k, ...
                // Max subarray sum using Kadane's (contiguous within thread)
                // Wait — the problem says max sum subsequence where indices are
                // EXACTLY k apart. This means consecutive chosen must differ by k.
                // So it's a contiguous subarray OF THE THREAD (not all positives).
                // Actually rethinking: "subsequence with indices spaced by k" means
                // we pick some indices i_1 < i_2 < ... with i_{j+1} - i_j = k.
                // This is a CONTIGUOUS segment of the thread. So Kadane's applies.
                long maxSum = Long.MIN_VALUE;
                long curSum = 0;
                for (int i = r; i < n; i += k) {
                    curSum += a[i];
                    maxSum = Math.max(maxSum, curSum);
                    if (curSum < 0) curSum = 0;
                }
                ans = Math.max(ans, maxSum);
            }

            sb.append(ans).append('\n');
        }

        System.out.print(sb);
    }
}
