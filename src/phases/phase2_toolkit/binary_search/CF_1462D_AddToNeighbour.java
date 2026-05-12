package phases.phase2_toolkit.binary_search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1462D — Add to Neighbour and Remove
 * Link   : https://codeforces.com/problemset/problem/1462/D
 * Rating : 1400  |  Tags: binary search, greedy
 * Topic  : Phase 2: Toolkit > Binary Search
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given array a of n positive integers. One operation: choose an element,
 * add its value to a neighbor (left or right), then remove it. Find the
 * minimum number of operations to make the array non-decreasing.
 *
 * EXAMPLE
 * -------
 * Input:  5
 *         1 2 3 1 2
 * Output: 2
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Binary search on the answer k (number of operations = removals).
 *    After k removals, remaining elements are contiguous segment sums.
 * 2. Feasibility check feasible(k): greedily partition array left to right
 *    into groups. Each group's sum must be >= previous group's sum.
 *    We accumulate elements into a group until the sum is >= previous sum.
 *    Count total extra elements merged (= total removals used).
 *    If total removals > k, return false.
 * 3. Binary search: lo=0, hi=n-1. Find minimum k where feasible(k)=true.
 * 4. Edge case: all elements already non-decreasing → answer is 0.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log n)
 * Space: O(1)
 * ============================================================
 */

public class CF_1462D_AddToNeighbour {

    static int n;
    static long[] a;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine().trim());
        a = new long[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) a[i] = Long.parseLong(st.nextToken());

        // Binary search on the number of operations (removals)
        int lo = 0, hi = n - 1;
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (feasible(mid)) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }

        System.out.println(lo);
    }

    // Can we achieve non-decreasing array using at most k operations?
    static boolean feasible(int k) {
        long prevSum = 0;
        int idx = 0;
        int totalRemoved = 0;
        while (idx < n) {
            int groupStart = idx;
            long curSum = 0;
            // Accumulate elements until group sum >= previous group sum
            while (idx < n && curSum < prevSum) {
                curSum += a[idx++];
            }
            if (curSum < prevSum) return false; // couldn't reach prevSum
            // Elements in this group: a[groupStart..idx-1]
            // Removals for this group = (group size - 1) = idx - groupStart - 1
            totalRemoved += (idx - groupStart - 1);
            if (totalRemoved > k) return false;
            prevSum = curSum;
        }
        return true;
    }
}
