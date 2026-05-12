package phases.phase4_advanced.segment_tree_bit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1555E — Boring Segments
 * Link   : https://codeforces.com/problemset/problem/1555/E
 * Rating : 1900  |  Tags: segment tree, two pointers, intervals
 * Topic  : Phase 4: Advanced > Segment Tree / BIT
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * n segments [l,r] each with weight w. Select a subset covering [1, m]
 * (every integer in [1,m] covered by at least one segment) minimizing
 * max_weight - min_weight of selected subset.
 *
 * EXAMPLE
 * -------
 * Input:  m=5, segments: [1,3,3],[2,5,1],[3,5,2]
 * Output: 1
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Sort segments by weight. Two pointers [lo, hi] on weight-sorted segments.
 * 2. Expand hi to add segments until [1,m] is covered. Record answer = w[hi] - w[lo].
 * 3. Then try removing lo (smallest weight) while coverage maintained.
 * 4. Need efficient check if [1,m] is covered: use segment tree with lazy propagation
 *    tracking min coverage count. When min > 0, all of [1,m] is covered.
 * 5. Segment tree: range add on [l,r] when adding/removing segment i.
 *    Query: minimum value in [1,m-1] (covering gaps between 1..m means covering [1,m-1] intervals).
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log(n+m))
 * Space: O(n+m)
 * ============================================================
 */

public class CF_1555E_BoringSegments {

    static int[] tree, lazy;
    static int SZ;

    static void build(int node, int lo, int hi) {
        tree[node] = 0; lazy[node] = 0;
        if (lo == hi) return;
        int mid = (lo + hi) / 2;
        build(2 * node, lo, mid);
        build(2 * node + 1, mid + 1, hi);
    }

    static void push(int node) {
        if (lazy[node] != 0) {
            tree[2 * node] += lazy[node];
            lazy[2 * node] += lazy[node];
            tree[2 * node + 1] += lazy[node];
            lazy[2 * node + 1] += lazy[node];
            lazy[node] = 0;
        }
    }

    static void update(int node, int lo, int hi, int l, int r, int val) {
        if (r < lo || hi < l) return;
        if (l <= lo && hi <= r) {
            tree[node] += val;
            lazy[node] += val;
            return;
        }
        push(node);
        int mid = (lo + hi) / 2;
        update(2 * node, lo, mid, l, r, val);
        update(2 * node + 1, mid + 1, hi, l, r, val);
        tree[node] = Math.min(tree[2 * node], tree[2 * node + 1]);
    }

    static int query(int node, int lo, int hi, int l, int r) {
        if (r < lo || hi < l) return Integer.MAX_VALUE;
        if (l <= lo && hi <= r) return tree[node];
        push(node);
        int mid = (lo + hi) / 2;
        return Math.min(query(2 * node, lo, mid, l, r),
                        query(2 * node + 1, mid + 1, hi, l, r));
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[][] segs = new int[n][3]; // {l, r, w}
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            segs[i][0] = Integer.parseInt(st.nextToken());
            segs[i][1] = Integer.parseInt(st.nextToken());
            segs[i][2] = Integer.parseInt(st.nextToken());
        }

        // Sort by weight
        Arrays.sort(segs, (a, b) -> a[2] - b[2]);

        // Segment tree on [1, m-1] (gaps)
        SZ = m;
        tree = new int[4 * (m + 1)];
        lazy = new int[4 * (m + 1)];
        if (m > 1) build(1, 1, m - 1);

        int ans = Integer.MAX_VALUE;
        int lo = 0;

        for (int hi = 0; hi < n; hi++) {
            // Add segment hi
            int l = segs[hi][0], r = segs[hi][1];
            if (l < r && m > 1) update(1, 1, m - 1, l, r - 1, 1);

            // Try to shrink lo
            while (lo <= hi) {
                int minCov = (m > 1) ? query(1, 1, m - 1, 1, m - 1) : 1;
                if (minCov <= 0) break; // not fully covered if we remove lo

                // Currently covered and lo can potentially be removed
                // Check if removing lo still covers
                int ll = segs[lo][0], lr = segs[lo][1];
                if (ll < lr && m > 1) update(1, 1, m - 1, ll, lr - 1, -1);
                int minAfter = (m > 1) ? query(1, 1, m - 1, 1, m - 1) : 1;
                if (minAfter > 0) {
                    // Can remove lo
                    lo++;
                } else {
                    // Cannot remove; restore and record
                    if (ll < lr && m > 1) update(1, 1, m - 1, ll, lr - 1, 1);
                    ans = Math.min(ans, segs[hi][2] - segs[lo][2]);
                    break;
                }
            }

            // Record if fully covered
            int minCov = (m > 1) ? query(1, 1, m - 1, 1, m - 1) : 1;
            if (minCov > 0 && lo <= hi) {
                ans = Math.min(ans, segs[hi][2] - segs[lo][2]);
            }
        }

        System.out.println(ans == Integer.MAX_VALUE ? -1 : ans);
    }
}
