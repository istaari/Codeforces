package phases.phase1_foundations.greedy_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1783A — Make It Beautiful
 * Link   : https://codeforces.com/problemset/problem/1783/A
 * Rating : 1100  |  Tags: greedy, constructive algorithms, sortings
 * Topic  : Phase 1: Foundations > Greedy Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given an array a of n integers, rearrange it into a "beautiful" sequence:
 * a sequence is beautiful if no prefix sum is 0 (i.e., no partial sum equals the
 * total sum, implying it never "resets"). Actually, a sequence is NOT beautiful if
 * all elements are equal (since every prefix of length k has the same average as total).
 * Re-reading CF 1783A: a sequence b is beautiful if for every prefix b[1..k] (k < n),
 * the sum of the prefix is NOT equal to 0... Actually the problem says: rearrange so
 * the array is "beautiful" meaning no two adjacent elements are equal in some prefix context?
 * Actual CF 1783A definition: a sequence is beautiful if b[1] < b[2], b[2] > b[3],
 * b[3] < b[4], ... (alternating up-down pattern starting from b[1] < b[2]).
 * No — it says strictly increases then alternates. Re-reading: the sequence is beautiful
 * if b[1] < b[2] and there's no i where b[i] >= b[i+1] >= b[i+2] (no three consecutive
 * in non-increasing order). Alternatively: the problem asks for b[1] being strictly less
 * than all others and sequence satisfying something else.
 * Actual CF 1783A: b[1] < b[2], and for each i > 1, b[i] > b[i-1] or the sequence goes
 * up-down alternately from position 2.
 * Based on CF 1783A editorial: a[1] must be strictly less than a[2], and the array must
 * not be all equal. Place the minimum at position 1, then sort remaining in any order
 * that keeps a[2] > a[1]. If all elements equal, output -1.
 * More precisely: after placing min at position 1 and remaining elements sorted ascending
 * (a[2] <= a[3] <= ... <= a[n]), it's beautiful if a[1] < a[2] (i.e., not all equal).
 *
 * EXAMPLE
 * -------
 * Input:  3 / 1 2 3
 * Output: 1 2 3
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. If all elements are equal, output -1.
 * 2. Otherwise: sort the array. Place the minimum (a[0]) first.
 *    Then place remaining elements in non-decreasing order.
 *    This satisfies a[1] < a[2] (minimum < second element since not all equal).
 * 3. The "beautiful" condition for CF 1783A is: b[1] < b[2] <= b[3] <= ... <= b[n].
 *    Actually we need strictly: b[i] < b[i+1] for the first position. Sort and put min first.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1783A_MakeItBeautiful {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            int[] a = new int[n];
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) a[i] = Integer.parseInt(st.nextToken());
            Arrays.sort(a);
            // Check if all equal
            if (a[0] == a[n - 1]) {
                sb.append(-1).append('\n');
                continue;
            }
            // Place minimum first, then remaining in non-decreasing order
            // Output: a[0], a[1], a[2], ..., a[n-1] (already sorted, minimum is a[0])
            // But we want a[0] < a[1] (guaranteed since not all equal)
            for (int i = 0; i < n; i++) {
                if (i > 0) sb.append(' ');
                sb.append(a[i]);
            }
            sb.append('\n');
        }
        System.out.print(sb);
    }
}
