package phases.phase4_advanced.segment_tree_bit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1354D — Multiset
 * Link   : https://codeforces.com/problemset/problem/1354/D
 * Rating : 1700  |  Tags: binary search, BIT, coordinate compression
 * Topic  : Phase 4: Advanced > Segment Tree / BIT
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Maintain a multiset supporting:
 * 1. Add positive integer x
 * 2. Remove one occurrence of x (if present; else ignore)
 * 3. Find the k-th smallest element
 * Process q queries. Output k-th smallest or -1 if multiset has fewer than k elements.
 *
 * EXAMPLE
 * -------
 * Input:  q=6, queries: +1,+2,+1,-1,?1,?2
 * Output: 1, 2
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Coordinate compress all values that will be added (read all queries first).
 * 2. Use a BIT (Fenwick tree) over compressed coordinates.
 * 3. Add: BIT update +1 at position rank(x).
 * 4. Remove: if BIT prefix sum at rank(x) > 0, BIT update -1.
 * 5. K-th smallest: binary search on BIT prefix sums (or walk down BIT).
 *
 * COMPLEXITY
 * ----------
 * Time : O(q log q)
 * Space: O(q)
 * ============================================================
 */

public class CF_1354D_Multiset {

    static int[] bit;
    static int BIT_N;

    static void update(int i, int delta) {
        for (; i <= BIT_N; i += i & (-i)) bit[i] += delta;
    }

    static int query(int i) {
        int s = 0;
        for (; i > 0; i -= i & (-i)) s += bit[i];
        return s;
    }

    // Find k-th smallest (1-indexed)
    static int kth(int k) {
        int pos = 0;
        for (int pw = Integer.highestOneBit(BIT_N); pw > 0; pw >>= 1) {
            if (pos + pw <= BIT_N && bit[pos + pw] < k) {
                pos += pw;
                k -= bit[pos];
            }
        }
        return pos + 1;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int q = Integer.parseInt(br.readLine().trim());

        int[] type = new int[q];
        int[] val = new int[q];
        List<Integer> coords = new ArrayList<>();

        for (int i = 0; i < q; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String op = st.nextToken();
            val[i] = Integer.parseInt(st.nextToken());
            if (op.equals("+")) {
                type[i] = 1;
                coords.add(val[i]);
            } else if (op.equals("-")) {
                type[i] = -1;
                coords.add(val[i]);
            } else {
                type[i] = 0; // query k-th
            }
        }

        // Coordinate compression
        coords.sort(null);
        List<Integer> unique = new ArrayList<>();
        for (int x : coords) {
            if (unique.isEmpty() || unique.get(unique.size() - 1) != x) unique.add(x);
        }
        BIT_N = unique.size();
        bit = new int[BIT_N + 1];

        // Map value to rank
        int[] rankArr = new int[unique.size()];
        for (int i = 0; i < unique.size(); i++) rankArr[i] = unique.get(i);

        StringBuilder sb = new StringBuilder();
        int size = 0;

        for (int i = 0; i < q; i++) {
            if (type[i] == 1) {
                int rank = Arrays.binarySearch(rankArr, val[i]) + 1;
                update(rank, 1);
                size++;
            } else if (type[i] == -1) {
                int rank = Arrays.binarySearch(rankArr, val[i]) + 1;
                if (rank >= 1 && rank <= BIT_N && query(rank) - query(rank - 1) > 0) {
                    update(rank, -1);
                    size--;
                }
            } else {
                if (val[i] > size) {
                    sb.append(-1).append('\n');
                } else {
                    int rank = kth(val[i]);
                    sb.append(rankArr[rank - 1]).append('\n');
                }
            }
        }

        System.out.print(sb);
    }
}
