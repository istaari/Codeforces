package phases.phase3_core_patterns.number_theory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1462E2 — Close Tuples (Hard version)
 * Link   : https://codeforces.com/problemset/problem/1462/E2
 * Rating : 1500  |  Tags: combinatorics, sorting, math
 * Topic  : Phase 3: Core Patterns > Number Theory
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given array a of n integers and integers m, k. Count the number of tuples
 * of m indices (i1, i2, ..., im) where i1 < i2 < ... < im and
 * a[im] - a[i1] <= k (after sorting the tuple values: max - min <= k).
 * Output modulo 1e9+7.
 *
 * EXAMPLE
 * -------
 * Input:  4 2 3
 *         3 1 4 2
 * Output: 4
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Sort array a. Then for a sorted tuple, condition is a[im]-a[i1] <= k.
 * 2. For each right endpoint j (after sorting), find the leftmost l such that
 *    a[j] - a[l] <= k. The window [l, j] has size w = j - l + 1.
 *    We need to choose m-1 more elements from [l, j-1] to form the tuple with j.
 *    Count = C(j-l, m-1) for each j.
 * 3. As j increases, l is non-decreasing (two pointer). Sliding window.
 *    Precompute factorials and inverse factorials for combination.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log n + n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1462E2_CloseTuplesHard {

    static final long MOD = 1_000_000_007L;
    static long[] fact, invFact;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        // Precompute factorials up to 2e5
        int MAXN = 200001;
        fact = new long[MAXN];
        invFact = new long[MAXN];
        fact[0] = 1;
        for (int i = 1; i < MAXN; i++) fact[i] = fact[i - 1] * i % MOD;
        invFact[MAXN - 1] = modPow(fact[MAXN - 1], MOD - 2, MOD);
        for (int i = MAXN - 2; i >= 0; i--) invFact[i] = invFact[i + 1] * (i + 1) % MOD;

        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            long k = Long.parseLong(st.nextToken());

            long[] a = new long[n];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) a[i] = Long.parseLong(st.nextToken());
            Arrays.sort(a);

            long ans = 0;
            int l = 0;
            for (int j = 0; j < n; j++) {
                while (a[j] - a[l] > k) l++;
                // Window [l, j], size = j - l + 1.
                // Choose m-1 from [l, j-1] = j - l elements.
                int window = j - l;
                if (window >= m - 1) {
                    ans = (ans + comb(window, m - 1)) % MOD;
                }
            }

            sb.append(ans).append('\n');
        }

        System.out.print(sb);
    }

    static long comb(int n, int r) {
        if (r < 0 || r > n) return 0;
        return fact[n] % MOD * invFact[r] % MOD * invFact[n - r] % MOD;
    }

    static long modPow(long base, long exp, long mod) {
        long result = 1;
        base %= mod;
        while (exp > 0) {
            if ((exp & 1) == 1) result = result * base % mod;
            base = base * base % mod;
            exp >>= 1;
        }
        return result;
    }
}
