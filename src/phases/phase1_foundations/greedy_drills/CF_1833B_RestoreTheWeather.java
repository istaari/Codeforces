package phases.phase1_foundations.greedy_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1833B — Restore the Weather
 * Link   : https://codeforces.com/problemset/problem/1833/B
 * Rating : 900  |  Tags: greedy, sortings
 * Topic  : Phase 1: Foundations > Greedy Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * You are given two sorted arrays: a[] of n minimum temperatures and b[] of n
 * maximum temperatures (both already sorted in non-decreasing order). These were
 * originally paired as (a[i], b[i]) for each day i, but got mixed up. Reconstruct
 * any valid pairing where a[i] <= b[i] for each pair. Output the pairs in sorted
 * order by minimum temperature.
 *
 * EXAMPLE
 * -------
 * Input:  3 / 1 2 3 / 3 4 5
 * Output: 1 3 / 2 4 / 3 5
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Sort a[] (minimums) and b[] (maximums) independently.
 * 2. The greedy pairing: pair the i-th smallest minimum with the i-th smallest
 *    maximum. This works because if a[i] <= b[i] after sorting, all pairs are valid.
 * 3. Since all minimums <= corresponding maximums (by problem guarantee), and both
 *    are sorted, matching by rank (smallest with smallest) always satisfies a[i] <= b[i].
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1833B_RestoreTheWeather {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            int[] a = new int[n], b = new int[n];
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) a[i] = Integer.parseInt(st.nextToken());
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) b[i] = Integer.parseInt(st.nextToken());
            Arrays.sort(a);
            Arrays.sort(b);
            for (int i = 0; i < n; i++) {
                sb.append(a[i]).append(' ').append(b[i]).append('\n');
            }
        }
        System.out.print(sb);
    }
}
