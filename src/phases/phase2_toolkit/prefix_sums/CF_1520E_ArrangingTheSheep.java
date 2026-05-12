package phases.phase2_toolkit.prefix_sums;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/*
 * ============================================================
 * CF 1520E — Arranging The Sheep
 * Link   : https://codeforces.com/problemset/problem/1520/E
 * Rating : 1400  |  Tags: prefix sums, greedy, strings
 * Topic  : Phase 2: Toolkit > Prefix Sums
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * A line of cells, each either 'S' (sheep) or '.' (empty). You can move
 * sheep one cell at a time (left or right). Find the minimum total moves
 * to group all sheep into one contiguous block.
 *
 * EXAMPLE
 * -------
 * Input:  1
 *         .S..S
 * Output: 2
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Optimal: all sheep gather at the median sheep's position. Since
 *    we minimize sum of absolute deviations, the optimal meeting point
 *    is the median.
 * 2. Number the sheep by their original indices (positions in string).
 *    If sheep are at positions p[0], p[1], ..., p[k-1] (sorted), the
 *    optimal block starts such that sheep i occupies position p[mid] + (i - mid).
 *    i.e., remap each sheep to position p[mid] - mid + i.
 * 3. Total moves = sum |p[i] - (p[mid] - mid + i)| for all i.
 *    = sum |p[i] - p[mid] - (i - mid)| = sum |(p[i] - i) - (p[mid] - mid)|.
 *    Let q[i] = p[i] - i. Then total moves = sum |q[i] - q[mid]|.
 *    This is sum of absolute deviations from median of q[].
 * 4. Compute q[i] = p[i] - i for all sheep (0-indexed).
 *    q is already sorted (since p[i] is strictly increasing, q[i] = p[i]-i is non-decreasing).
 *    Use prefix sums of q to compute sum |q[i] - median| efficiently.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(k) where k = number of sheep
 * ============================================================
 */

public class CF_1520E_ArrangingTheSheep {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            String s = br.readLine().trim();
            List<Long> q = new ArrayList<>();
            int sheepIdx = 0;
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == 'S') {
                    q.add((long) i - sheepIdx); // p[sheepIdx] - sheepIdx
                    sheepIdx++;
                }
            }

            int k = q.size();
            if (k <= 1) {
                sb.append(0).append('\n');
                continue;
            }

            // Prefix sums of q
            long[] pre = new long[k + 1];
            for (int i = 0; i < k; i++) pre[i + 1] = pre[i] + q.get(i);

            // Median of q is q[k/2]
            long median = q.get(k / 2);

            // Sum |q[i] - median|
            long ans = 0;
            for (int i = 0; i < k; i++) {
                ans += Math.abs(q.get(i) - median);
            }

            sb.append(ans).append('\n');
        }

        System.out.print(sb);
    }
}
