package phases.phase5_cm_push.advanced_graphs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1491D — Zookeeper and The Infinite Zoo
 * Link   : https://codeforces.com/problemset/problem/1491/D
 * Rating : 1800  |  Tags: bitmasks, greedy, math
 * Topic  : Phase 5: CM Push > Advanced Graphs
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given two arrays a and b of n non-negative integers.
 * In one operation: choose indices i < j such that a[i] = 2*a[j]
 * or a[j] = 2*a[i]; swap a[i] and a[j] then divide the larger
 * by 2 (equivalently: pick adjacent-in-value pair, transfer a
 * factor of 2). Actually: the operation is: pick i < j, one must
 * equal twice the other; set a[i] = a[i]/2 + a[j]/2 (average if
 * equal halves) — actually: if a[i] = 2*a[j], set a[i] -= a[j]
 * and a[j] += a[j] (i.e., move a factor of 2 from one to other).
 * Check if array a can be transformed into array b.
 *
 * EXAMPLE
 * -------
 * Input:  4
 *         4 8 2 2
 *         2 4 4 4
 * Output: Yes
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Necessary condition 1: sum(a) == sum(b), since operations
 *    preserve the sum (they redistribute, don't change total).
 * 2. Necessary condition 2: for each bit position k, the number
 *    of elements in a that have bit k set (counting with multiplicity
 *    of 2^k in their binary representation) must equal that in b.
 *    More precisely: for each bit k, define cnt_a[k] = count of
 *    i where (a[i] >> k) is odd (bit k is set in a[i]).
 *    Then prefix sums of these counts must be non-decreasing and
 *    match between a and b.
 * 3. Key observation: define for each bit k:
 *    f_a[k] = number of elements in a where a[i] has bit k set
 *             (i.e., floor(a[i] / 2^k) is odd).
 *    Operations can "carry" bits upward but cannot create new bits.
 *    The correct invariant: for every bit k,
 *    sum_{i} floor(a[i] / 2^k) == sum_{i} floor(b[i] / 2^k).
 *    This means: the count of numbers with bit k set after dividing
 *    all by 2^k is the same for a and b.
 * 4. So: for each k from 0 to 30, check that
 *    sum_i (a[i] >> k) == sum_i (b[i] >> k).
 *    Also check sum(a) == sum(b).
 *    If all hold → "Yes", otherwise "No".
 *
 * COMPLEXITY
 * ----------
 * Time : O(n * 30)
 * Space: O(1)
 * ============================================================
 */

public class CF_1491D_ZookeeperAndTheInfiniteZoo {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());

        int[] a = new int[n], b = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) a[i] = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) b[i] = Integer.parseInt(st.nextToken());

        // For each bit level k, sum of floor(a[i] / 2^k) must equal sum of floor(b[i] / 2^k)
        boolean ok = true;
        for (int k = 0; k <= 30 && ok; k++) {
            long sumA = 0, sumB = 0;
            for (int i = 0; i < n; i++) {
                sumA += (a[i] >> k);
                sumB += (b[i] >> k);
            }
            if (sumA != sumB) ok = false;
        }

        System.out.println(ok ? "Yes" : "No");
    }
}
