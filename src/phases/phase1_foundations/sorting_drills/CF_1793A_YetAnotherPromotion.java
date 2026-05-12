package phases.phase1_foundations.sorting_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1793A — Yet Another Promotion
 * Link   : https://codeforces.com/problemset/problem/1793/A
 * Rating : 1100  |  Tags: greedy, math, sortings
 * Topic  : Phase 1: Foundations > Sorting Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * A store has n items. You want to buy all n items. The store runs a promotion:
 * if you buy k items, every k-th item (by price order) is free (i.e., you can get
 * a free item whose cost <= the most expensive item in your current group of k).
 * You buy items in groups: for each group of k items you pay, you get 1 free item
 * (the cheapest in the group). Minimize total cost to get all n items.
 * More precisely: sort items by price. In each "round", buy k items (paying for the
 * most expensive k) and get 1 free (cheapest in next position). Group optimally.
 *
 * EXAMPLE
 * -------
 * Input:  4 2 / 1 2 3 4
 * Output: 6
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Sort prices in non-decreasing order.
 * 2. Greedy: process from most expensive to least. For every group of k paid items,
 *    the (k+1)-th cheapest item in that window can be free.
 * 3. Equivalently, work from right to left in sorted array. Every (k+1)-th item (0-indexed
 *    from right: positions k, 2k+1, 3k+2, ...) is free.
 * 4. Specifically: sort ascending. Free items are at indices (k, 2k+1, 3k+2, ...) from the end,
 *    i.e., from right: item at position k (0-indexed from right) is free, then next k items paid,
 *    then one free, etc.
 *    Indices from right: 0,1,...,k-1 (paid), k (free), k+1,...,2k (paid), 2k+1 (free), ...
 *    In sorted ascending array of size n, index from right = n-1-i.
 *    Free positions (0-indexed from right): k, 2k+1, 3k+2, ... = j*(k+1)+k for j=0,1,2,...
 *    In terms of ascending index i: i = n-1-(j*(k+1)+k) = n-1-j*(k+1)-k.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1793A_YetAnotherPromotion {

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
            Arrays.sort(a);
            // Work from right: every (k+1) group, the last item (cheapest) is free.
            // Free items from right: positions k, 2k+1, 3k+2, ...
            // In ascending sort, rightmost = most expensive.
            // Group from right: paid = a[n-1], a[n-2], ..., a[n-k]; free = a[n-k-1]
            // Next group: paid = a[n-k-2], ..., a[n-2k-2]; free = a[n-2k-3]; etc.
            long total = 0;
            // Mark which items are free (from right, every (k+1)-th)
            // Free indices (0-indexed from right): k, 2*(k+1)-1=2k+1, 3*(k+1)-1=3k+2,...
            // = j*(k+1)+k for j=0,1,2,...
            for (int i = 0; i < n; i++) {
                // i is index from right (0-based), a[n-1-i] is the item
                // Free if i == j*(k+1)+k for some j >= 0
                // i = j*k + j + k => (i-k) = j*(k+1) => j = (i-k)/(k+1)
                // So free if (i >= k) && ((i-k) % (k+1) == 0)
                boolean free = (i >= k) && ((i - k) % (k + 1) == 0);
                if (!free) total += a[n - 1 - i];
            }
            sb.append(total).append('\n');
        }
        System.out.print(sb);
    }
}
