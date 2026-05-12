package phases.phase3_core_patterns.graph_fundamentals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


/*
 * ============================================================
 * CF 1249D — Too Many Segments
 * Link   : https://codeforces.com/problemset/problem/1249/D
 * Rating : 1600  |  Tags: greedy, segment tree, implementation
 * Topic  : Phase 3: Core Patterns > Graph Fundamentals
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given n segments [l[i], r[i]] on a number line where all l[i] and r[i]
 * are in [1, m]. For each point x from 1 to m, at most k segments can cover x.
 * Remove the minimum number of segments to satisfy this constraint.
 * When multiple segments cover x and we need to remove some, remove the one
 * with the largest right endpoint (greedy: remove segments that extend furthest).
 *
 * EXAMPLE
 * -------
 * Input:  5 3 2
 *         1 3
 *         1 3
 *         2 3
 *         2 4
 *         3 5
 * Output: 3
 *         1 2 4
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Sweep from left to right (x = 1 to m).
 * 2. At each x: add all segments starting at x to an active set (sorted by r desc).
 *    While active set size > k: remove the segment with the largest r (greedy: it
 *    causes violations furthest right, removing it resolves the most future points).
 * 3. Remove segments with r < x from active set (they no longer cover x).
 * 4. Use a TreeMap<Integer, List<Integer>> sorted by r to efficiently manage.
 *
 * COMPLEXITY
 * ----------
 * Time : O((n + m) log n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1249D_TooManySegments {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        int[] l = new int[n + 1], r = new int[n + 1];
        // Group segments by their left endpoint
        List<List<Integer>> startAt = new ArrayList<>();
        for (int i = 0; i <= m + 1; i++) startAt.add(new ArrayList<>());

        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            l[i] = Integer.parseInt(st.nextToken());
            r[i] = Integer.parseInt(st.nextToken());
            startAt.get(l[i]).add(i);
        }

        // Active segments: sorted by r (use TreeMap: r → list of segment indices)
        TreeMap<Integer, Deque<Integer>> active = new TreeMap<>();
        Set<Integer> removed = new HashSet<>();

        for (int x = 1; x <= m; x++) {
            // Add segments starting at x
            for (int idx : startAt.get(x)) {
                active.computeIfAbsent(r[idx], key -> new ArrayDeque<>()).add(idx);
            }

            // Remove segments that ended before x
            while (!active.isEmpty() && active.firstKey() < x) {
                active.pollFirstEntry();
            }

            // While too many active: remove segment with largest r
            while (countActive(active) > k) {
                Map.Entry<Integer, Deque<Integer>> last = active.lastEntry();
                int removedIdx = last.getValue().poll();
                if (last.getValue().isEmpty()) active.pollLastEntry();
                removed.add(removedIdx);
            }
        }

        List<Integer> result = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (!removed.contains(i)) result.add(i);
        }

        System.out.println(result.size());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < result.size(); i++) {
            if (i > 0) sb.append(' ');
            sb.append(result.get(i));
        }
        System.out.println(sb);
    }

    static int countActive(TreeMap<Integer, Deque<Integer>> active) {
        int count = 0;
        for (Deque<Integer> d : active.values()) count += d.size();
        return count;
    }
}
