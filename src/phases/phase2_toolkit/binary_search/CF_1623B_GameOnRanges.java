package phases.phase2_toolkit.binary_search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


/*
 * ============================================================
 * CF 1623B — Game on Ranges
 * Link   : https://codeforces.com/problemset/problem/1623/B
 * Rating : 1100  |  Tags: binary search, greedy, sortings
 * Topic  : Phase 2: Toolkit > Binary Search
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given n ranges [l, r], each chosen number must be in the range and
 * not used by any other range. Ranges are sorted by size (r-l+1).
 * For each range (in any order), determine which number was chosen.
 * It is guaranteed a valid assignment exists.
 * Output the number chosen for each range in original input order.
 *
 * EXAMPLE
 * -------
 * Input:  4
 *         1 1
 *         2 3
 *         3 4
 *         1 4
 * Output: 1
 *         2
 *         4
 *         3
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Sort ranges by their size (r - l). Smallest ranges are most
 *    constrained, so process them first.
 * 2. For each range in sorted order, find the smallest number in [l,r]
 *    that hasn't been used yet. This greedy always works because the
 *    range is the most constrained (smallest) at this point.
 * 3. Use a TreeSet to efficiently find the next unused number >= l
 *    that is also <= r.
 * 4. Store the answer keyed by (l, r) to reconstruct original order.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1623B_GameOnRanges {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());

        int[][] ranges = new int[n][3]; // l, r, originalIndex
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            ranges[i][0] = Integer.parseInt(st.nextToken());
            ranges[i][1] = Integer.parseInt(st.nextToken());
            ranges[i][2] = i;
        }

        // Sort by size ascending (smallest range first = most constrained)
        int[][] sorted = ranges.clone();
        Arrays.sort(sorted, (a, b) -> (a[1] - a[0]) - (b[1] - b[0]));

        // TreeSet of all numbers from 1 to max_r
        int maxR = 0;
        for (int[] r : ranges) maxR = Math.max(maxR, r[1]);
        TreeSet<Integer> available = new TreeSet<>();
        for (int i = 1; i <= maxR; i++) available.add(i);

        int[] ans = new int[n];
        for (int[] range : sorted) {
            int l = range[0], r = range[1], idx = range[2];
            // Find smallest available number in [l, r]
            Integer chosen = available.ceiling(l);
            // chosen is guaranteed to be <= r since valid assignment exists
            ans[idx] = chosen;
            available.remove(chosen);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) sb.append(ans[i]).append('\n');
        System.out.print(sb);
    }
}
