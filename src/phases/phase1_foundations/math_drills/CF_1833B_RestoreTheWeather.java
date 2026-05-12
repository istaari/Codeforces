package phases.phase1_foundations.math_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1833B — Restore the Weather
 * Link   : https://codeforces.com/problemset/problem/1833/B
 * Rating : 900  |  Tags: greedy, sortings, math
 * Topic  : Phase 1: Foundations > Math Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given two arrays: minimum temperatures a[1..n] and maximum temperatures b[1..n]
 * (both already sorted). Each day i has a pair (min_i, max_i) with min_i <= max_i.
 * The arrays were sorted and separated; reconstruct valid pairs. Output pairs where
 * a[i] <= b[i] for all i.
 *
 * EXAMPLE
 * -------
 * Input:  3 / 1 2 3 / 3 4 5
 * Output: 1 3 / 2 4 / 3 5
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Sort both arrays (they're already sorted by problem).
 * 2. Pair the i-th smallest min with the i-th smallest max.
 * 3. Since all valid pairs have min <= max, and sorting preserves this rank ordering,
 *    this greedy pairing always works.
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
