package phases.phase1_foundations.c1_800rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 148A — Insomnia Cure
 * Link   : https://codeforces.com/problemset/problem/148/A
 * Rating : 800  |  Tags: math
 * Topic  : Phase 1: Foundations > Math
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * A dragon has n dreams (numbered 1 to n). Four knights disturb the dragon:
 * the first on every k-th dream, the second on every d-th, third on every
 * p-th, fourth on every q-th. Count the total number of dreams during which
 * at least one knight disturbs the dragon (i.e., count integers in [1,n]
 * divisible by at least one of k, d, p, q).
 *
 * EXAMPLE
 * -------
 * Input:  10 2 3 5 7
 * Output: 7
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Use inclusion-exclusion principle for four sets.
 * 2. |A ∪ B ∪ C ∪ D| = |A|+|B|+|C|+|D| - |A∩B| - |A∩C| - ...
 *    + |A∩B∩C| + ... - |A∩B∩C∩D|
 * 3. |A| = floor(n / k), intersection uses LCM.
 * 4. Use GCD to compute LCM: lcm(a,b) = a/gcd(a,b)*b.
 *
 * COMPLEXITY
 * ----------
 * Time : O(log(max_val)) for GCD computations
 * Space: O(1)
 * ============================================================
 */

public class CF_148A_InsomniaCure {

    static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    static long lcm(long a, long b) {
        return a / gcd(a, b) * b;
    }

    static long count(long n, long d) {
        return n / d;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        long n = Long.parseLong(st.nextToken());
        long k = Long.parseLong(st.nextToken());
        long d = Long.parseLong(st.nextToken());
        long p = Long.parseLong(st.nextToken());
        long q = Long.parseLong(st.nextToken());

        long ans = count(n, k) + count(n, d) + count(n, p) + count(n, q)
                 - count(n, lcm(k, d)) - count(n, lcm(k, p)) - count(n, lcm(k, q))
                 - count(n, lcm(d, p)) - count(n, lcm(d, q)) - count(n, lcm(p, q))
                 + count(n, lcm(lcm(k, d), p)) + count(n, lcm(lcm(k, d), q))
                 + count(n, lcm(lcm(k, p), q)) + count(n, lcm(lcm(d, p), q))
                 - count(n, lcm(lcm(lcm(k, d), p), q));

        System.out.println(ans);
    }
}
