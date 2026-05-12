package phases.phase2_toolkit.prefix_sums;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1426D — Non-zero Segments
 * Link   : https://codeforces.com/problemset/problem/1426/D
 * Rating : 1300  |  Tags: prefix sums, greedy
 * Topic  : Phase 2: Toolkit > Prefix Sums
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given an array a of n integers. Insert the minimum number of positive
 * integers so that no contiguous subarray of the resulting array sums to 0.
 * (Actually: ensure no contiguous subarray has sum = 0.)
 *
 * EXAMPLE
 * -------
 * Input:  5
 *         1 -1 2 -2 3
 * Output: 2
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. A subarray sums to 0 iff prefix[r+1] == prefix[l] for some l <= r.
 * 2. Greedily scan using prefix sums. Maintain a set of seen prefix sums.
 *    Initially insert 0 (empty prefix). For each element, compute new
 *    prefix sum. If this prefix sum already exists in the set, a zero-sum
 *    subarray exists ending here.
 * 3. When a conflict is detected (prefix sum repeated): insert a new element
 *    with value = -current_prefix_sum + 1 (large enough to break the cycle),
 *    effectively reset: clear the seen set (new element creates a fresh start),
 *    set prefix sum = 1, insert {0, 1} as new base.
 *    Increment answer count.
 * 4. Continue until all elements processed.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log n) due to TreeSet, O(n) with HashSet
 * Space: O(n)
 * ============================================================
 */

public class CF_1426D_NonZeroSegments {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        StringTokenizer st = new StringTokenizer(br.readLine());

        HashSet<Long> seen = new HashSet<>();
        seen.add(0L);
        long prefix = 0;
        int ops = 0;

        for (int i = 0; i < n; i++) {
            long val = Long.parseLong(st.nextToken());
            prefix += val;
            if (seen.contains(prefix)) {
                // Zero-sum subarray detected; must insert an element before this one
                ops++;
                // Inserting a new element resets: start fresh after insertion
                seen.clear();
                seen.add(0L);
                prefix = val; // current element is now first after the inserted one
                seen.add(prefix);
            } else {
                seen.add(prefix);
            }
        }

        System.out.println(ops);
    }
}
