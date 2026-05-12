package phases.phase4_advanced.segment_tree_bit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1549D — Integers Have Friends
 * Link   : https://codeforces.com/problemset/problem/1549/D
 * Rating : 1800  |  Tags: segment tree, sparse table, binary search, GCD
 * Topic  : Phase 4: Advanced > Segment Tree / BIT
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Array a[] of n integers. Find the longest subarray [l, r] such that there
 * exists a value g >= 2 where g divides (a[i] - a[j]) for all i, j in [l, r].
 * Equivalently, all elements in [l, r] are congruent modulo g for some g >= 2.
 * This means GCD of all pairwise differences in [l, r] is >= 2.
 * GCD of differences = GCD(a[l], a[l+1]-a[l], a[l+2]-a[l], ...) = GCD of consecutive differences.
 *
 * EXAMPLE
 * -------
 * Input:  n=6, a=[1,2,3,4,5,6]
 * Output: 2  (any subarray of length 2 has GCD of diff = 1? Wait: [2,4] diff=2, GCD=2.)
 *            Actually [1,3,5]: GCD(2,2)=2. Length 3.
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Let b[i] = a[i+1] - a[i] (differences). The subarray [l, r] satisfies condition
 *    iff GCD(b[l], b[l+1], ..., b[r-1]) >= 2.
 * 2. Find longest subarray of b[] with GCD >= 2.
 * 3. Use sparse table for O(1) range GCD queries.
 * 4. Binary search: for each left endpoint l, find maximum r such that GCD(b[l..r-1]) >= 2.
 *    GCD is non-increasing as we extend, so binary search on r.
 * 5. Answer = max(r - l + 1) + 1 (convert back to original array, +1 because b has n-1 elements).
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log^2 n)
 * Space: O(n log n)
 * ============================================================
 */

public class CF_1549D_IntegersHaveFriends {

    static long[][] sparse;
    static int[] log2;
    static int n;

    static long gcd(long a, long b) {
        while (b != 0) { long t = b; b = a % b; a = t; }
        return a;
    }

    static void buildSparse(long[] b) {
        int m = b.length;
        int LOG = 1;
        while ((1 << LOG) <= m) LOG++;
        sparse = new long[LOG][m];
        log2 = new int[m + 1];
        log2[1] = 0;
        for (int i = 2; i <= m; i++) log2[i] = log2[i / 2] + 1;
        for (int i = 0; i < m; i++) sparse[0][i] = b[i];
        for (int j = 1; j < LOG; j++) {
            for (int i = 0; i + (1 << j) <= m; i++) {
                sparse[j][i] = gcd(sparse[j-1][i], sparse[j-1][i + (1 << (j-1))]);
            }
        }
    }

    static long queryGcd(int l, int r) { // inclusive [l, r]
        if (l > r) return 0;
        int k = log2[r - l + 1];
        return gcd(sparse[k][l], sparse[k][r - (1 << k) + 1]);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            n = Integer.parseInt(br.readLine().trim());
            long[] a = new long[n];
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) a[i] = Long.parseLong(st.nextToken());

            if (n == 1) {
                sb.append(1).append('\n');
                continue;
            }

            long[] b = new long[n - 1];
            for (int i = 0; i < n - 1; i++) b[i] = Math.abs(a[i + 1] - a[i]);

            buildSparse(b);

            int ans = 1;
            for (int l = 0; l < n - 1; l++) {
                if (b[l] < 2) continue; // GCD must be >=2, so b[l] must be >=2
                // Binary search for maximum r where GCD(b[l..r]) >= 2
                int lo = l, hi = n - 2, best = l;
                while (lo <= hi) {
                    int mid = (lo + hi) / 2;
                    if (queryGcd(l, mid) >= 2) { best = mid; lo = mid + 1; }
                    else hi = mid - 1;
                }
                ans = Math.max(ans, best - l + 2); // +2: b indices to a indices
            }

            sb.append(ans).append('\n');
        }

        System.out.print(sb);
    }
}
