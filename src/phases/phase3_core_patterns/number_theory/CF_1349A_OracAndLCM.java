package phases.phase3_core_patterns.number_theory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1349A — Orac and LCM
 * Link   : https://codeforces.com/problemset/problem/1349/A
 * Rating : 1600  |  Tags: number theory, gcd, lcm, prefix sums
 * Topic  : Phase 3: Core Patterns > Number Theory
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given array a of n positive integers. Compute the LCM of all prefix minimum
 * values, i.e., LCM(min(a[0..0]), min(a[0..1]), ..., min(a[0..n-1])).
 * Actually: for each prefix ending at i, add GCD(a[0], a[1], ..., a[i]) to a
 * "b" array. Find LCM of b.
 * More precisely: b[i] = GCD(a[i], a[i+1], ..., a[n-1]) (suffix GCDs).
 * Then compute LCM(b[0], b[1], ..., b[n-2], a[n-1]).
 * Wait — actual problem: compute LCM of all "a[l..r] GCD" for all pairs l <= r?
 * No. Actual CF 1349A: find a permutation b such that LCM(b[i], b[i+1]) is the same
 * for all i? No.
 *
 * ACTUAL CF 1349A: Given array a. Let b[i] = GCD(a[i], a[i+1], ..., a[n-1]) (suffix GCDs).
 * Output LCM(b[0], b[1], ..., b[n-1]).
 * Equivalently: answer = LCM of all suffix GCDs.
 *
 * EXAMPLE
 * -------
 * Input:  4
 *         2 3 6 12
 * Output: 12
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Compute suffix GCDs: suffGcd[i] = gcd(a[i], a[i+1], ..., a[n-1]).
 *    suffGcd[n-1] = a[n-1], suffGcd[i] = gcd(a[i], suffGcd[i+1]).
 * 2. Answer = LCM(suffGcd[0], suffGcd[1], ..., suffGcd[n-1]).
 * 3. LCM is computed iteratively: running lcm = lcm(prev, suffGcd[i]).
 *    lcm(a, b) = a / gcd(a,b) * b. Handle overflow with long.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log(max_a))
 * Space: O(n)
 * ============================================================
 */

public class CF_1349A_OracAndLCM {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            long[] a = new long[n];
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) a[i] = Long.parseLong(st.nextToken());

            // Compute suffix GCDs
            long[] suffGcd = new long[n];
            suffGcd[n - 1] = a[n - 1];
            for (int i = n - 2; i >= 0; i--) {
                suffGcd[i] = gcd(a[i], suffGcd[i + 1]);
            }

            // Compute LCM of all suffix GCDs
            long ans = suffGcd[0];
            for (int i = 1; i < n; i++) {
                ans = lcm(ans, suffGcd[i]);
            }

            sb.append(ans).append('\n');
        }

        System.out.print(sb);
    }

    static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    static long lcm(long a, long b) {
        return a / gcd(a, b) * b;
    }
}
