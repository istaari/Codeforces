package phases.phase3_core_patterns.number_theory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1225C — p-binary
 * Link   : https://codeforces.com/problemset/problem/1225/C
 * Rating : 1400  |  Tags: math, bitmasks, number theory
 * Topic  : Phase 3: Core Patterns > Number Theory
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given n and p. Find the minimum k such that n can be represented as a
 * sum of exactly k terms, where each term is 2^x + p for some non-negative x.
 * In other words: n = sum_{i=1..k} (2^{x_i} + p) = p*k + sum_{i=1..k} 2^{x_i}.
 * So n - p*k must be representable as a sum of exactly k distinct powers of 2
 * (powers can repeat? no — we need 2^{x_i} to be valid powers, can they repeat?
 * Actually the x_i can be the same or different).
 * Wait: actually exponents x_i can repeat. But 2^{x_i} are just powers of 2 (can repeat).
 * n - p*k = sum of k powers of 2 (with repetition).
 * A number m can be written as sum of exactly k powers of 2 (with repetition) iff:
 * popcount(m) <= k <= m (since we can always split 2^x into 2^(x-1) + 2^(x-1)).
 *
 * EXAMPLE
 * -------
 * Input:  24 0
 * Output: 2
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. For each k from 1 upward: compute m = n - p*k.
 *    Check: m >= k (enough room for k powers) and popcount(m) <= k (at least k bits).
 *    Also m > 0.
 * 2. If m <= 0 or m < k: impossible for this k (no valid representation).
 *    First valid k = minimum k with popcount(n - p*k) <= k and n - p*k >= k.
 * 3. Since p >= 1, as k increases, m decreases. The condition m >= k means n - p*k >= k,
 *    i.e., k <= n/(p+1). So k ranges from 1 to n/(p+1) (or n if p=0).
 *    Also k <= 40 or so since sum of k powers of 2 with popcount <= 40 covers large numbers.
 *    Just iterate k from 1 to ~40 (since n <= 1e9, at most 30 bits needed).
 *
 * COMPLEXITY
 * ----------
 * Time : O(log n)
 * Space: O(1)
 * ============================================================
 */

public class CF_1225C_PBinary {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        long n = Long.parseLong(st.nextToken());
        long p = Long.parseLong(st.nextToken());

        for (long k = 1; k <= 40; k++) {
            long m = n - p * k;
            if (m <= 0) {
                System.out.println(-1);
                return;
            }
            if (m < k) {
                // m < k means we can't have k terms (need at least k, but m < k is impossible
                // since smallest sum of k powers of 2 is k*1 = k)
                System.out.println(-1);
                return;
            }
            int bits = Long.bitCount(m);
            if (bits <= k) {
                System.out.println(k);
                return;
            }
        }

        System.out.println(-1);
    }
}
