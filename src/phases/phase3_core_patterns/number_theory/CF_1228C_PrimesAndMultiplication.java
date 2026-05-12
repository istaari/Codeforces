package phases.phase3_core_patterns.number_theory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1228C — Powers and Multiplication
 * Link   : https://codeforces.com/problemset/problem/1228/C
 * Rating : 1500  |  Tags: number theory, euler totient, math
 * Topic  : Phase 3: Core Patterns > Number Theory
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given n and x. Compute the product of gcd(x*k, n) for k from 1 to n.
 * Output the result modulo 1e9+7.
 *
 * EXAMPLE
 * -------
 * Input:  2 6
 * Output: 6
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. We need product of gcd(x*k, n) for k = 1..n.
 *    gcd(x*k, n) = gcd(x,n) * gcd(k, n/gcd(x,n)) since gcd(x*k,n) = gcd(x,n)*gcd(k, n/gcd(x,n))
 *    when... actually this factorization isn't straightforward.
 * 2. Let d = gcd(x, n), n' = n/d. Then gcd(x*k, n) = d * gcd(x/d * k, n').
 *    Since gcd(x/d, n') = 1 (we divided out all common factors), gcd(x/d * k, n') = gcd(k, n').
 * 3. So product = d^n * product of gcd(k, n') for k = 1..n.
 *    But k ranges 1..n and n = d * n', so gcd(k, n') for k = 1..d*n' is periodic with period n'.
 *    product of gcd(k, n') for k = 1..d*n' = (product of gcd(k, n') for k = 1..n')^d.
 * 4. Now compute product of gcd(k, m) for k = 1..m (m = n').
 *    For each divisor f of m: count how many k in 1..m have gcd(k,m) = f.
 *    That count = phi(m/f) (Euler's totient of m/f).
 *    So product = product over divisors f of m: f^phi(m/f).
 *
 * COMPLEXITY
 * ----------
 * Time : O(sqrt(n) + d(n) * log(n))
 * Space: O(d(n))
 * ============================================================
 */

public class CF_1228C_PrimesAndMultiplication {

    static final long MOD = 1_000_000_007L;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            long x = Long.parseLong(st.nextToken());
            long n = Long.parseLong(st.nextToken());

            long d = gcd(x, n);
            long nPrime = n / d;

            // product of gcd(k, nPrime) for k=1..nPrime, raised to (n/nPrime = d) power
            // = (product_{f|nPrime} f^phi(nPrime/f))^d

            long innerProduct = computeProduct(nPrime);
            long ans = modPow(d % MOD, n % (MOD - 1), MOD) * modPow(innerProduct, d % (MOD - 1), MOD) % MOD;

            sb.append(ans).append('\n');
        }

        System.out.print(sb);
    }

    // Compute product of gcd(k, m) for k=1..m
    // = product over divisors f of m: f^phi(m/f)
    static long computeProduct(long m) {
        long result = 1;
        // Enumerate divisors of m
        for (long f = 1; f * f <= m; f++) {
            if (m % f == 0) {
                // Divisor f: contributes f^phi(m/f)
                long phi1 = eulerTotient(m / f);
                result = result * modPow(f % MOD, phi1 % (MOD - 1), MOD) % MOD;
                if (f != m / f) {
                    // Divisor m/f: contributes (m/f)^phi(f)
                    long phi2 = eulerTotient(f);
                    result = result * modPow((m / f) % MOD, phi2 % (MOD - 1), MOD) % MOD;
                }
            }
        }
        return result;
    }

    static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    static long eulerTotient(long n) {
        long result = n;
        long temp = n;
        for (long p = 2; p * p <= temp; p++) {
            if (temp % p == 0) {
                while (temp % p == 0) temp /= p;
                result -= result / p;
            }
        }
        if (temp > 1) result -= result / temp;
        return result;
    }

    static long modPow(long base, long exp, long mod) {
        long result = 1;
        base %= mod;
        if (base < 0) base += mod;
        while (exp > 0) {
            if ((exp & 1) == 1) result = result * base % mod;
            base = base * base % mod;
            exp >>= 1;
        }
        return result;
    }
}
