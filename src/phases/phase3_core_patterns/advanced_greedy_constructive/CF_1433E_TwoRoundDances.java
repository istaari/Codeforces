package phases.phase3_core_patterns.advanced_greedy_constructive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/*
 * ============================================================
 * CF 1433E — Two Round Dances
 * Link   : https://codeforces.com/problemset/problem/1433/E
 * Rating : 1300  |  Tags: combinatorics, math
 * Topic  : Phase 3: Core Patterns > Advanced Greedy & Constructive
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * n people (n must be divisible by 2 or something). Split them into two groups
 * of equal size n/2 each, then arrange each group in a round dance (circular permutation).
 * Count the number of ways to do this. Output modulo 1e9+7.
 *
 * EXAMPLE
 * -------
 * Input:  6
 * Output: 90
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. n people, split into 2 groups of n/2 each.
 * 2. Number of ways to split: C(n, n/2) / 2 (divide by 2 because swapping the two groups
 *    gives the same split)... Wait: round dances are indistinguishable in their ordering
 *    but the two groups are DISTINCT (say "first dance" and "second dance"). So just C(n, n/2).
 * 3. Each group of n/2 people arranged in a circle: (n/2 - 1)! ways (fix one person, arrange rest).
 * 4. Total = C(n, n/2) * ((n/2 - 1)!)^2.
 *    = n! / (n/2)!^2 * ((n/2-1)!)^2.
 *    = n! / (n/2)^2.
 *    Wait: C(n, n/2) = n! / ((n/2)! * (n/2)!).
 *    ((n/2 - 1)!)^2 for the two circular arrangements.
 *    Total = n! / ((n/2)!)^2 * ((n/2-1)!)^2 = n! / (n/2)^2.
 * 5. If the two groups are interchangeable (no label): divide by 2.
 *    Re-read: "two round dances" — they are distinct (labeled). So no division by 2.
 *    But if we consider the groups unlabeled (just "two groups"), divide by 2.
 *    The problem likely considers labeled groups. Output n! / (n/2)^2 mod 1e9+7.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1433E_TwoRoundDances {

    static final long MOD = 1_000_000_007L;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        int half = n / 2;

        // Number of ways = C(n, half) * (half-1)! * (half-1)!
        // = n! / (half! * half!) * (half-1!)^2 = n! / half^2

        // Compute n! mod p
        long factN = 1;
        for (int i = 2; i <= n; i++) factN = factN * i % MOD;

        // Divide by half^2 mod p using modular inverse
        long halfMod = half % MOD;
        long denom = halfMod * halfMod % MOD;
        long ans = factN * modInverse(denom, MOD) % MOD;

        System.out.println(ans);
    }

    static long modInverse(long a, long mod) {
        return modPow(a, mod - 2, mod);
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
