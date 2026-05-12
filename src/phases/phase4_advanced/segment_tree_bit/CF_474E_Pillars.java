package phases.phase4_advanced.segment_tree_bit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 474E — Pillars
 * Link   : https://codeforces.com/problemset/problem/474/E
 * Rating : 1900  |  Tags: segment tree, dp, LIS variant
 * Topic  : Phase 4: Advanced > Segment Tree / BIT
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Array of n pillars with heights h[i]. Find the longest subsequence such that
 * consecutive elements in the subsequence differ by more than d (|h[i] - h[j]| > d
 * for consecutive pairs in subsequence). This is an LIS variant with gap constraint.
 *
 * EXAMPLE
 * -------
 * Input:  n=5, d=2, h=[1,3,6,2,9]
 * Output: 4  (1,6,2,9 all differ by >2? |1-6|=5>2, |6-2|=4>2, |2-9|=7>2. Yes!)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. dp[i] = longest valid subsequence ending at i.
 * 2. dp[i] = 1 + max(dp[j]) for all j < i where |h[j] - h[i]| > d.
 *    = 1 + max over j: h[j] < h[i]-d or h[j] > h[i]+d.
 * 3. Use segment tree on coordinate-compressed heights to query max dp value
 *    in range [1, h[i]-d-1] or [h[i]+d+1, maxH] in O(log n) per element.
 * 4. Two segment trees (or one with proper range query):
 *    Query max dp in [1, h[i]-d-1] and in [h[i]+d+1, MAX].
 *    Update dp[i] in segment tree at position h[i].
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log n)
 * Space: O(n)
 * ============================================================
 */

public class CF_474E_Pillars {

    static int[] seg;
    static int SZ;

    static void init(int n) {
        SZ = n;
        seg = new int[4 * n + 4];
    }

    static void update(int node, int lo, int hi, int pos, int val) {
        if (lo == hi) {
            seg[node] = Math.max(seg[node], val);
            return;
        }
        int mid = (lo + hi) / 2;
        if (pos <= mid) update(2 * node, lo, mid, pos, val);
        else update(2 * node + 1, mid + 1, hi, pos, val);
        seg[node] = Math.max(seg[2 * node], seg[2 * node + 1]);
    }

    static int query(int node, int lo, int hi, int l, int r) {
        if (r < lo || hi < l || l > r) return 0;
        if (l <= lo && hi <= r) return seg[node];
        int mid = (lo + hi) / 2;
        return Math.max(query(2 * node, lo, mid, l, r),
                        query(2 * node + 1, mid + 1, hi, l, r));
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        long d = Long.parseLong(st.nextToken());

        long[] h = new long[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) h[i] = Long.parseLong(st.nextToken());

        // Coordinate compress
        long[] sorted = h.clone();
        Arrays.sort(sorted);
        long[] unique = Arrays.stream(sorted).distinct().toArray();
        int m = unique.length;

        init(m);

        int ans = 0;
        for (int i = 0; i < n; i++) {
            int rank = Arrays.binarySearch(unique, h[i]) + 1; // 1-indexed

            // Find max dp in [1, rank of h[i]-d-1] and [rank of h[i]+d+1, m]
            // h[i] - d - 1: find largest rank with value <= h[i]-d-1
            // h[i] + d + 1: find smallest rank with value >= h[i]+d+1

            long lo1 = h[i] - d - 1;
            long lo2 = h[i] + d + 1;

            // Upper bound for lo1
            int rightBound = upperBound(unique, lo1);
            // Lower bound for lo2
            int leftBound = lowerBound(unique, lo2);

            int best = 0;
            if (rightBound >= 1) best = Math.max(best, query(1, 1, m, 1, rightBound));
            if (leftBound <= m) best = Math.max(best, query(1, 1, m, leftBound, m));

            int dp = best + 1;
            ans = Math.max(ans, dp);
            update(1, 1, m, rank, dp);
        }

        System.out.println(ans);
    }

    static int upperBound(long[] arr, long val) {
        int lo = 0, hi = arr.length;
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (arr[mid] <= val) lo = mid + 1;
            else hi = mid;
        }
        return lo; // number of elements <= val (1-indexed rank of last such)
    }

    static int lowerBound(long[] arr, long val) {
        int lo = 0, hi = arr.length;
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (arr[mid] < val) lo = mid + 1;
            else hi = mid;
        }
        return lo + 1; // 1-indexed rank of first element >= val
    }
}
