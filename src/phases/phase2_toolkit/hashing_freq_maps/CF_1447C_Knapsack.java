package phases.phase2_toolkit.hashing_freq_maps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1447C — Knapsack
 * Link   : https://codeforces.com/problemset/problem/1447/C
 * Rating : 1300  |  Tags: hashing, greedy, math
 * Topic  : Phase 2: Toolkit > Hashing & Frequency Maps
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given n integers w[1..n] and bounds l, r (l <= r). Find a subset
 * whose sum S satisfies l <= S <= r, or report that none exists.
 *
 * EXAMPLE
 * -------
 * Input:  6 10 20
 *         1 2 3 4 5 6
 * Output: 3
 *         3 4 5
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Compute total = sum of all elements.
 *    - If total < l: impossible (no subset can reach l).
 *    - If total is in [l, r]: take all elements.
 *    - Otherwise total > r: we need to remove elements to bring sum into [l, r].
 * 2. To bring total into [l, r]: we need to remove elements with total sum
 *    = total - target, where target is in [l, r].
 *    Remove elements summing to (total - r) through (total - l).
 *    Find any single element x such that total - x is in [l, r]:
 *    total - x >= l → x <= total - l
 *    total - x <= r → x >= total - r
 *    So find any element in [total - r, total - l].
 * 3. If no single element works: try pairs? No — the key insight is that
 *    if no single element works, we can still try removing combinations.
 *    But actually: if total > r, we need to remove at least total - r.
 *    If no single element >= total - r exists (all elements < total - r),
 *    we can remove multiple small elements. Take elements greedily smallest
 *    first until removed sum is in [total-r, total-l].
 *    This always works if total >= l: accumulate small elements until
 *    removed sum first enters [total-r, total-l].
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1447C_Knapsack {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        long l = Long.parseLong(st.nextToken());
        long r = Long.parseLong(st.nextToken());

        long[] w = new long[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) w[i] = Long.parseLong(st.nextToken());

        long total = 0;
        for (long x : w) total += x;

        if (total < l) {
            System.out.println(0);
            return;
        }

        if (total <= r) {
            // Take all
            System.out.println(n);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) {
                if (i > 0) sb.append(' ');
                sb.append(i + 1);
            }
            System.out.println(sb);
            return;
        }

        // total > r: need to remove some elements summing to [total-r, total-l]
        // Sort by value, try to remove greedily
        Integer[] idx = new Integer[n];
        for (int i = 0; i < n; i++) idx[i] = i;
        Arrays.sort(idx, (a, b) -> Long.compare(w[a], w[b]));

        // Try removing smallest elements first
        long removed = 0;
        long target_lo = total - r;
        long target_hi = total - l;
        List<Integer> removedSet = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            removed += w[idx[i]];
            removedSet.add(idx[i]);
            if (removed >= target_lo && removed <= target_hi) {
                // Found valid removal set; remaining is our answer
                boolean[] remove = new boolean[n];
                for (int j : removedSet) remove[j] = true;
                StringBuilder sb = new StringBuilder();
                List<Integer> chosen = new ArrayList<>();
                for (int j = 0; j < n; j++) {
                    if (!remove[j]) chosen.add(j + 1);
                }
                System.out.println(chosen.size());
                for (int j = 0; j < chosen.size(); j++) {
                    if (j > 0) sb.append(' ');
                    sb.append(chosen.get(j));
                }
                System.out.println(sb);
                return;
            }
            if (removed > target_hi) break; // overshot
        }

        // Try removing largest elements first
        removed = 0;
        removedSet.clear();
        for (int i = n - 1; i >= 0; i--) {
            removed += w[idx[i]];
            removedSet.add(idx[i]);
            if (removed >= target_lo && removed <= target_hi) {
                boolean[] remove = new boolean[n];
                for (int j : removedSet) remove[j] = true;
                List<Integer> chosen = new ArrayList<>();
                for (int j = 0; j < n; j++) {
                    if (!remove[j]) chosen.add(j + 1);
                }
                System.out.println(chosen.size());
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < chosen.size(); j++) {
                    if (j > 0) sb.append(' ');
                    sb.append(chosen.get(j));
                }
                System.out.println(sb);
                return;
            }
        }

        System.out.println(0);
    }
}
