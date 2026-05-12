package phases.phase2_toolkit.two_pointers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1538C — Number of Pairs
 * Link   : https://codeforces.com/problemset/problem/1538/C
 * Rating : 1300  |  Tags: two pointers, sorting, math
 * Topic  : Phase 2: Toolkit > Two Pointers
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given array a of n integers and bounds l, r. Count the number of
 * pairs (i, j) where i < j and l <= a[i] + a[j] <= r.
 *
 * EXAMPLE
 * -------
 * Input:  4 5 8
 *         2 3 4 5
 * Output: 2
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Sort the array. Then pairs (i,j) with i<j and sum in [l,r] can
 *    be counted using two binary searches or two-pointer technique.
 * 2. For each i (from 0 to n-2), count j > i where a[i]+a[j] >= l AND
 *    a[i]+a[j] <= r.
 *    - Lower bound: smallest j > i where a[j] >= l - a[i]
 *    - Upper bound: largest j > i where a[j] <= r - a[i]
 *    Count = max(0, upperIdx - lowerIdx + 1) within valid range [i+1, n-1].
 * 3. Use two pointers: maintain one pointer for lower bound, one for upper
 *    bound, both moving only forward as i increases (sort ensures monotone).
 * 4. As i increases, a[i] increases, so l-a[i] decreases and r-a[i] decreases,
 *    meaning lower and upper pointers can only move left (but we process
 *    from i=0, so we track from the right side).
 *    Alternative: use two separate two-pointer passes for count(sum<=r) and
 *    count(sum<=l-1), subtract. Each pass: i from left, j from right.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1538C_NumberOfPairs {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        long l = Long.parseLong(st.nextToken());
        long r = Long.parseLong(st.nextToken());

        long[] a = new long[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) a[i] = Long.parseLong(st.nextToken());
        Arrays.sort(a);

        // Count pairs with sum <= x using two pointers
        // pairs(x) = number of (i,j) i<j with a[i]+a[j] <= x
        // Answer = pairs(r) - pairs(l-1)
        long ans = countAtMost(a, r) - countAtMost(a, l - 1);
        System.out.println(ans);
    }

    static long countAtMost(long[] a, long limit) {
        long count = 0;
        int left = 0, right = a.length - 1;
        while (left < right) {
            if (a[left] + a[right] <= limit) {
                // All pairs (left, left+1), (left, left+2), ..., (left, right) are valid
                count += (right - left);
                left++;
            } else {
                right--;
            }
        }
        return count;
    }
}
