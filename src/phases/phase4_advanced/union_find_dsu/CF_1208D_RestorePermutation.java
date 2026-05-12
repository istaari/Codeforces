package phases.phase4_advanced.union_find_dsu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1208D — Restore Permutation
 * Link   : https://codeforces.com/problemset/problem/1208/D
 * Rating : 1700  |  Tags: DSU, BIT, k-th smallest
 * Topic  : Phase 4: Advanced > Union Find / DSU
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * An array p[] is a permutation of 1..n. Given sequence s[] where
 * s[i] = sum of all p[j] for j < i where p[j] < p[i]. Restore p[].
 *
 * EXAMPLE
 * -------
 * Input:  n=3, s=[0,0,1]
 * Output: 2 1 3  (p[3]=3: all smaller elements {1,2} sum to 3, but s[3]=1. Hmm.)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Process from right to left. s[i] = sum of elements smaller than p[i] that come before i.
 *    But for the rightmost element n, s[n] = sum of ALL elements smaller than p[n].
 *    So the sum of the smallest k elements = s[n], meaning p[n] is the (k+1)-th smallest available.
 * 2. Process i from n down to 1. At each step, find the element x such that
 *    sum of all available elements less than x = s[i]. Use BIT on available elements.
 * 3. BIT stores prefix sums of "available" elements. Binary search for x:
 *    find smallest x where prefix_sum(x) = s[i] + x (i.e., s[i] = sum of elements < x).
 * 4. Remove x from available set after assigning it.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log^2 n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1208D_RestorePermutation {

    static long[] bit;
    static int N;

    static void update(int i, long delta) {
        for (; i <= N; i += i & (-i)) bit[i] += delta;
    }

    static long query(int i) {
        long s = 0;
        for (; i > 0; i -= i & (-i)) s += bit[i];
        return s;
    }

    // Find smallest x in [1..N] such that prefix_sum(x-1) >= target (sum of elements < x >= target)
    // i.e., find x such that sum of available elements strictly less than x equals target
    // sum of available elements in [1..x-1] = target
    static int findX(long target) {
        int pos = 0;
        long rem = target;
        // We want: sum(1..pos) = target, then pos+1 is the answer
        // Binary search on BIT
        for (int pw = Integer.highestOneBit(N); pw > 0; pw >>= 1) {
            if (pos + pw <= N && bit[pos + pw] <= rem) {
                pos += pw;
                rem -= bit[pos];
            }
        }
        // pos = largest prefix with sum <= target
        // But we want sum = target exactly, and then the NEXT element after that
        // The element at pos+1 (if it exists and is available) has value pos+1
        return pos + 1;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine().trim());
        long[] s = new long[N + 1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) s[i] = Long.parseLong(st.nextToken());

        bit = new long[N + 1];
        // Initialize BIT with all elements 1..N
        for (int i = 1; i <= N; i++) update(i, i); // store value i at position i

        int[] p = new int[N + 1];
        for (int i = N; i >= 1; i--) {
            // Find x such that sum of available elements in [1..x-1] = s[i]
            int x = findX(s[i]);
            p[i] = x;
            update(x, -x); // remove x from available
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= N; i++) {
            sb.append(p[i]);
            if (i < N) sb.append(' ');
        }
        System.out.println(sb);
    }
}
