package phases.phase4_advanced.segment_tree_bit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1080F — Katya and Segments Sets
 * Link   : https://codeforces.com/problemset/problem/1080/F
 * Rating : 1900  |  Tags: segment tree, intervals, offline
 * Topic  : Phase 4: Advanced > Segment Tree / BIT
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * You have n segments grouped into k groups. Each group covers a contiguous
 * range of the number line (union of its segments is an interval).
 * For each query (l, r, g): check if segment [l, r] is fully covered by group g.
 *
 * EXAMPLE
 * -------
 * Input:  n=6, k=2, groups: 1=[1,3],[5,7], 2=[2,8]
 * Output: YES/NO for each query
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. For each group g, merge its segments into a sorted list of non-overlapping intervals.
 * 2. For a query (l, r, g): check if [l, r] is contained within any single merged interval
 *    of group g. Binary search for the interval containing l, check if r is also contained.
 * 3. This handles each query in O(log n) after O(n log n) preprocessing.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log n + q log n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1080F_KatyaAndSegmentsSets {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        @SuppressWarnings("unchecked")
        List<int[]>[] groupSegs = new List[k + 1];
        for (int i = 1; i <= k; i++) groupSegs[i] = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int l = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());
            int g = Integer.parseInt(st.nextToken());
            groupSegs[g].add(new int[]{l, r});
        }

        // Merge segments in each group
        @SuppressWarnings("unchecked")
        List<int[]>[] merged = new List[k + 1];
        for (int g = 1; g <= k; g++) {
            List<int[]> segs = groupSegs[g];
            Collections.sort(segs, (a, b) -> a[0] - b[0]);
            List<int[]> m = new ArrayList<>();
            for (int[] seg : segs) {
                if (m.isEmpty() || m.get(m.size() - 1)[1] < seg[0] - 1) {
                    m.add(new int[]{seg[0], seg[1]});
                } else {
                    m.get(m.size() - 1)[1] = Math.max(m.get(m.size() - 1)[1], seg[1]);
                }
            }
            merged[g] = m;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int l = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());
            int g = Integer.parseInt(st.nextToken());

            List<int[]> m = merged[g];
            // Binary search for interval with start <= l
            int lo = 0, hi = m.size() - 1, idx = -1;
            while (lo <= hi) {
                int mid = (lo + hi) / 2;
                if (m.get(mid)[0] <= l) { idx = mid; lo = mid + 1; }
                else hi = mid - 1;
            }
            if (idx != -1 && m.get(idx)[1] >= r) {
                sb.append("Yes\n");
            } else {
                sb.append("No\n");
            }
        }

        System.out.print(sb);
    }
}
