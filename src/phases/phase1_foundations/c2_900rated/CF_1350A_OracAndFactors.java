package phases.phase1_foundations.c2_900rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1350A — Orac and Factors
 * Link   : https://codeforces.com/problemset/problem/1350/A
 * Rating : 900  |  Tags: math
 * Topic  : Phase 1: Foundations > Math
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given two integers n and k (k ≥ 2), find any k positive integers whose
 * product equals n. Print these k integers (each ≥ 2 is not required; they
 * just must be positive and multiply to n). It is guaranteed a solution exists.
 *
 * EXAMPLE
 * -------
 * Input:  12 3
 * Output: 2 2 3
 *
 * Input:  6 6
 * Output: 1 1 1 1 1 6  (can use 1s)
 * Actually: 6 6 → 1 1 1 1 2 3 would be 6 numbers multiplying to 6.
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. For the first k-1 factors: find smallest prime factor of current n,
 *    add it to result, divide n by it.
 * 2. If we still need more factors (k-1 haven't been found or n=1 early),
 *    fill with 1s to make up to k-1 factors.
 * 3. The k-th factor is the remaining n.
 * 4. Use smallest factor finding: check 2, then odd numbers up to sqrt(n).
 *
 * COMPLEXITY
 * ----------
 * Time : O(k * sqrt(n))
 * Space: O(k)
 * ============================================================
 */

public class CF_1350A_OracAndFactors {

    static long smallestFactor(long n) {
        if (n % 2 == 0) return 2;
        for (long i = 3; i * i <= n; i += 2) {
            if (n % i == 0) return i;
        }
        return n; // n is prime
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            long n = Long.parseLong(st.nextToken());
            int k = Integer.parseInt(st.nextToken());
            List<Long> factors = new ArrayList<>();
            long remaining = n;
            for (int i = 0; i < k - 1; i++) {
                if (remaining > 1) {
                    long f = smallestFactor(remaining);
                    factors.add(f);
                    remaining /= f;
                } else {
                    factors.add(1L);
                }
            }
            factors.add(remaining);
            for (int i = 0; i < factors.size(); i++) {
                sb.append(factors.get(i));
                if (i < factors.size() - 1) sb.append(' ');
            }
            sb.append('\n');
        }
        System.out.print(sb);
    }
}
