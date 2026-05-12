package phases.phase3_core_patterns.number_theory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1114C — Trailing Loves (or L'oeufs?)
 * Link   : https://codeforces.com/problemset/problem/1114/C
 * Rating : 1600  |  Tags: number theory, prime factorization, math
 * Topic  : Phase 3: Core Patterns > Number Theory
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given n and b. Find the number of trailing zeros in n! when written in base b.
 * (The number of times b divides n!.)
 *
 * EXAMPLE
 * -------
 * Input:  6 9
 * Output: 1
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. The number of trailing zeros in n! in base b = floor(v_b(n!)) where v_b is the
 *    b-adic valuation (how many times b divides n!).
 * 2. Factorize b = p1^e1 * p2^e2 * ... For each prime pi, compute v_pi(n!) = Legendre's formula:
 *    v_pi(n!) = floor(n/pi) + floor(n/pi^2) + ...
 * 3. The answer = min over all prime factors pi of b: floor(v_pi(n!) / ei).
 *
 * COMPLEXITY
 * ----------
 * Time : O(sqrt(b) + log(n) * omega(b)) where omega(b) = number of distinct prime factors
 * Space: O(1)
 * ============================================================
 */

public class CF_1114C_TrailingLoves {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        long n = Long.parseLong(st.nextToken());
        long b = Long.parseLong(st.nextToken());

        // Factorize b
        long ans = Long.MAX_VALUE;
        long temp = b;
        for (long p = 2; p * p <= temp; p++) {
            if (temp % p == 0) {
                int e = 0;
                while (temp % p == 0) { e++; temp /= p; }
                // Compute v_p(n!) using Legendre's formula
                long vp = legendreFactorial(n, p);
                ans = Math.min(ans, vp / e);
            }
        }
        if (temp > 1) {
            // temp is a prime factor with exponent 1
            long vp = legendreFactorial(n, temp);
            ans = Math.min(ans, vp);
        }

        System.out.println(ans == Long.MAX_VALUE ? 0 : ans);
    }

    static long legendreFactorial(long n, long p) {
        long count = 0;
        long pk = p;
        while (pk <= n) {
            count += n / pk;
            if (pk > n / p) break; // prevent overflow
            pk *= p;
        }
        return count;
    }
}
