package phases.phase4_advanced.segment_tree_bit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 339D — Xenia and Bit Operations
 * Link   : https://codeforces.com/problemset/problem/339/D
 * Rating : 1700  |  Tags: segment tree
 * Topic  : Phase 4: Advanced > Segment Tree / BIT
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Array of n elements (n = 2^k). Build a segment tree where:
 * - Leaves are array values.
 * - At odd levels (from bottom), parent = XOR of children.
 * - At even levels (from bottom), parent = OR of children.
 * - Bottom (leaf) level = level 1 (XOR level).
 * Support point updates and root queries.
 *
 * EXAMPLE
 * -------
 * Input:  n=4, a=[1,6,3,5], update (2,4), query root
 * Output: 1, then 7
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Build segment tree. At each internal node, store the combined value.
 * 2. The level of a node determines whether to use XOR or OR.
 *    Root is at level log2(n)+1. Leaf level = 1.
 *    Level 1 (leaves) combines with XOR going to level 2 parent.
 *    Level 2 combines with OR going to level 3.
 * 3. For point update at position i with value v:
 *    Update leaf. Go up: if at XOR level, parent = XOR of children; if OR level, OR.
 * 4. Query: just return root value.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n) build, O(log n) update, O(1) query
 * Space: O(n)
 * ============================================================
 */

public class CF_339D_XeniaAndBitOperations {

    static int[] tree;
    static int n;

    static void build(int[] a) {
        // Copy leaves to tree
        for (int i = 0; i < n; i++) tree[n + i] = a[i];
        // Build from bottom up
        for (int i = n - 1; i >= 1; i--) {
            int level = Integer.numberOfTrailingZeros(Integer.highestOneBit(i));
            // i is at level = floor(log2(i)). Leaves at level log2(n).
            // Level from bottom: depth of i from bottom
            // At level 0 from bottom (leaves), combine with XOR.
            // Actually: compute depth from bottom.
            // Height of node i = log2(n) - floor(log2(i))
            // XOR at height 0 (leaf), OR at height 1, XOR at height 2, OR at height 3...
            // Height 0 = leaves (no combination needed there)
            // For internal node at height h (h >= 1):
            //   h odd -> XOR of children
            //   h even -> OR of children
            int height = Integer.numberOfTrailingZeros(Integer.highestOneBit(n)) - level;
            // Hmm: for n=4 (2^2), leaves are at positions 4..7 (level=2 in segment tree terms).
            // Node i=2,3 at tree level 1 (one above leaves). Height from leaf = 1.
            // Node i=1 at tree level 0 (root). Height from leaf = 2.
            // At height 1: XOR of children (leaves). At height 2: OR.
            if (height % 2 == 1) { // XOR
                tree[i] = tree[2 * i] ^ tree[2 * i + 1];
            } else { // OR
                tree[i] = tree[2 * i] | tree[2 * i + 1];
            }
        }
    }

    static void update(int pos, int val) {
        // pos is 1-indexed
        int i = n + pos - 1;
        tree[i] = val;
        i >>= 1;
        while (i >= 1) {
            int level = Integer.numberOfTrailingZeros(Integer.highestOneBit(i));
            int logn = Integer.numberOfTrailingZeros(Integer.highestOneBit(n));
            int height = logn - level;
            if (height % 2 == 1) {
                tree[i] = tree[2 * i] ^ tree[2 * i + 1];
            } else {
                tree[i] = tree[2 * i] | tree[2 * i + 1];
            }
            i >>= 1;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken()); // n is power of 2
        int m = Integer.parseInt(st.nextToken());

        int[] a = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) a[i] = Integer.parseInt(st.nextToken());

        tree = new int[2 * n + 1];
        build(a);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int type = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            if (type == 1) {
                update(x, y);
            } else {
                sb.append(tree[1]).append('\n');
            }
        }
        System.out.print(sb);
    }
}
