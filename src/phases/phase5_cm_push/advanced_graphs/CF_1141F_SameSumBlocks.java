package phases.phase5_cm_push.advanced_graphs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1141F — Same Sum Blocks (Hard)
 * Link   : https://codeforces.com/problemset/problem/1141/F
 * Rating : 1800  |  Tags: graphs, greedy, hashing
 * Topic  : Phase 5: CM Push > Advanced Graphs
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given an array a of n integers and m segments [l[i], r[i]].
 * Group segments by their subarray sum a[l]+...+a[r].
 * For each group, greedily select the maximum number of
 * non-overlapping segments (sorted by right endpoint).
 * Print the selected segments in the order of their group
 * (groups ordered by their first occurrence, or by size desc
 * as given — actually output each group's selected segments).
 * Output the maximum total count across all groups, followed
 * by the actual segment indices chosen (1-indexed).
 *
 * EXAMPLE
 * -------
 * Input:  5
 *         1 2 3 -2 -3
 *         4
 *         1 3
 *         2 4
 *         1 1
 *         1 5
 * Output: 2
 *         3 1
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Compute prefix sums. Sum of [l,r] = prefix[r] - prefix[l-1].
 * 2. Use a HashMap from sum value → list of (r, l, original_index).
 * 3. For each group of equal sum, sort by right endpoint r.
 * 4. Greedy interval scheduling: pick segment if l > last_r chosen.
 * 5. Track the group that yields the maximum count of non-overlapping
 *    segments, and record which original indices were selected.
 * 6. Edge case: negative sums require long for prefix sums.
 *
 * COMPLEXITY
 * ----------
 * Time : O(m log m) for sorting groups
 * Space: O(n + m)
 * ============================================================
 */

public class CF_1141F_SameSumBlocks {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());

        long[] prefix = new long[n + 1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            prefix[i] = prefix[i - 1] + Long.parseLong(st.nextToken());
        }

        int m = Integer.parseInt(br.readLine().trim());

        // Map from sum -> list of [r, l, original_1indexed_pos]
        Map<Long, List<int[]>> groups = new HashMap<>();
        for (int i = 1; i <= m; i++) {
            st = new StringTokenizer(br.readLine());
            int l = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());
            long sum = prefix[r] - prefix[l - 1];
            groups.computeIfAbsent(sum, k -> new ArrayList<>()).add(new int[]{r, l, i});
        }

        // For each group, sort by r, then greedy interval scheduling
        int bestCount = 0;
        List<Integer> bestIndices = new ArrayList<>();

        for (List<int[]> group : groups.values()) {
            // Sort by right endpoint
            Collections.sort(group, (a, b) -> a[0] - b[0]);

            List<Integer> selected = new ArrayList<>();
            int lastR = -1;
            for (int[] seg : group) {
                int r = seg[0], l = seg[1], idx = seg[2];
                if (l > lastR) {
                    // Non-overlapping: l > lastR means starts after last chosen ends
                    selected.add(idx);
                    lastR = r;
                }
            }

            if (selected.size() > bestCount) {
                bestCount = selected.size();
                bestIndices = selected;
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append(bestCount).append('\n');
        for (int i = 0; i < bestIndices.size(); i++) {
            if (i > 0) sb.append(' ');
            sb.append(bestIndices.get(i));
        }
        System.out.println(sb);
    }
}
