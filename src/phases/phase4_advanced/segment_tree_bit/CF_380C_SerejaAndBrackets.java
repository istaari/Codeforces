package phases.phase4_advanced.segment_tree_bit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 380C — Sereja and Brackets
 * Link   : https://codeforces.com/problemset/problem/380/C
 * Rating : 1900  |  Tags: segment tree, bracket sequences
 * Topic  : Phase 4: Advanced > Segment Tree / BIT
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given a bracket sequence of length n. Process q queries (l, r):
 * find the maximum number of matched bracket pairs in substring [l, r].
 *
 * EXAMPLE
 * -------
 * Input:  s="()(())", queries: (1,6),(2,5)
 * Output: 3, 1
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Segment tree where each node stores: (matched, open, close) for the segment.
 *    - matched = number of matched pairs
 *    - open = unmatched '(' remaining
 *    - close = unmatched ')' remaining
 * 2. Merge two nodes (l, r):
 *    - m = min(l.open, r.close) (these cancel out)
 *    - merged.matched = l.matched + r.matched + m
 *    - merged.open = l.open - m + r.open
 *    - merged.close = l.close + r.close - m
 * 3. Leaf node for '(': (0, 1, 0); for ')': (0, 0, 1).
 * 4. Query returns the matched count of merged result.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n + q log n)
 * Space: O(n)
 * ============================================================
 */

public class CF_380C_SerejaAndBrackets {

    static int[] matched, open, close;
    static int n;

    static void build(String s, int node, int lo, int hi) {
        if (lo == hi) {
            if (s.charAt(lo - 1) == '(') {
                matched[node] = 0; open[node] = 1; close[node] = 0;
            } else {
                matched[node] = 0; open[node] = 0; close[node] = 1;
            }
            return;
        }
        int mid = (lo + hi) / 2;
        build(s, 2 * node, lo, mid);
        build(s, 2 * node + 1, mid + 1, hi);
        merge(node);
    }

    static void merge(int node) {
        int m = Math.min(open[2 * node], close[2 * node + 1]);
        matched[node] = matched[2 * node] + matched[2 * node + 1] + m;
        open[node] = open[2 * node] - m + open[2 * node + 1];
        close[node] = close[2 * node] + close[2 * node + 1] - m;
    }

    static int[] query(int node, int lo, int hi, int l, int r) {
        if (r < lo || hi < l) return new int[]{0, 0, 0}; // empty: no matched, no open, no close
        if (l <= lo && hi <= r) return new int[]{matched[node], open[node], close[node]};
        int mid = (lo + hi) / 2;
        int[] left = query(2 * node, lo, mid, l, r);
        int[] right = query(2 * node + 1, mid + 1, hi, l, r);
        int m = Math.min(left[1], right[2]);
        return new int[]{left[0] + right[0] + m, left[1] - m + right[1], left[2] + right[2] - m};
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine().trim());
        String s = br.readLine().trim();
        int q = Integer.parseInt(br.readLine().trim());

        matched = new int[4 * n + 4];
        open = new int[4 * n + 4];
        close = new int[4 * n + 4];

        build(s, 1, 1, n);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int l = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());
            int[] res = query(1, 1, n, l, r);
            sb.append(res[0]).append('\n');
        }
        System.out.print(sb);
    }
}
