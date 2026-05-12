package phases.phase5_cm_push.advanced_graphs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1462F — The Treasure of The Segments
 * Link   : https://codeforces.com/problemset/problem/1462/F
 * Rating : 1800  |  Tags: binary search, sorting, two pointers
 * Topic  : Phase 5: CM Push > Advanced Graphs
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given n segments [l[i], r[i]] and q queries. For each query
 * [ql, qr], find the maximum number of the n segments that can
 * "intersect" with a single contiguous interval of length
 * (qr - ql), i.e., how many of the n segments intersect with
 * some placement of a window of that length [x, x+(qr-ql)].
 * Wait — re-reading: for each query [ql, qr], count how many
 * of the n segments [l[i], r[i]] have a non-empty intersection
 * with [ql, qr]. Segment [l[i], r[i]] intersects [ql, qr] iff
 * l[i] <= qr AND r[i] >= ql.
 *
 * EXAMPLE
 * -------
 * Input:  5 3
 *         1 4
 *         3 5
 *         2 7
 *         1 3
 *         5 6
 *         2 4
 *         1 3
 *         4 7
 * Output: 4
 *         3
 *         3
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. A segment [l[i], r[i]] intersects query [ql, qr] iff:
 *    l[i] <= qr AND r[i] >= ql.
 *    Equivalently: NOT (r[i] < ql OR l[i] > qr).
 * 2. Count = n - (segments entirely to the left of ql)
 *              - (segments entirely to the right of qr)
 *    = n - |{i: r[i] < ql}| - |{i: l[i] > qr}|
 * 3. Sort array of r[i] values and l[i] values separately.
 *    For each query (ql, qr):
 *    - Count of r[i] < ql: binary search in sorted r array.
 *    - Count of l[i] > qr: binary search in sorted l array.
 * 4. Answer = n - count_r_less_than_ql - count_l_greater_than_qr.
 *
 * COMPLEXITY
 * ----------
 * Time : O((n + q) log n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1462F_TheTreasureOfTheSegments {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        int[] l = new int[n], r = new int[n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            l[i] = Integer.parseInt(st.nextToken());
            r[i] = Integer.parseInt(st.nextToken());
        }

        // Sort l and r arrays for binary search
        int[] sortedL = Arrays.copyOf(l, n);
        int[] sortedR = Arrays.copyOf(r, n);
        Arrays.sort(sortedL);
        Arrays.sort(sortedR);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int ql = Integer.parseInt(st.nextToken());
            int qr = Integer.parseInt(st.nextToken());

            // Count segments with r[i] < ql (entirely to the left)
            // = lower_bound(sortedR, ql) = first index where sortedR[idx] >= ql
            int leftCount = lowerBound(sortedR, ql);

            // Count segments with l[i] > qr (entirely to the right)
            // = n - upper_bound(sortedL, qr) = n - (first index where sortedL[idx] > qr)
            int rightCount = n - upperBound(sortedL, qr);

            sb.append(n - leftCount - rightCount).append('\n');
        }

        System.out.print(sb);
    }

    // Returns index of first element >= target
    static int lowerBound(int[] arr, int target) {
        int lo = 0, hi = arr.length;
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (arr[mid] < target) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }

    // Returns index of first element > target
    static int upperBound(int[] arr, int target) {
        int lo = 0, hi = arr.length;
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (arr[mid] <= target) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }
}
