package phases.phase3_core_patterns.basic_dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1301C — Ayoub's Function
 * Link   : https://codeforces.com/problemset/problem/1301/C
 * Rating : 1500  |  Tags: dp, math, number theory
 * Topic  : Phase 3: Core Patterns > Basic DP
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Count arrays of length m with elements in [1, n] such that no two
 * adjacent elements are equal. Output the count modulo 1e9+7.
 *
 * EXAMPLE
 * -------
 * Input:  2 3
 * Output: 6
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. The first element has n choices. Each subsequent element has (n-1)
 *    choices (anything but the previous).
 * 2. Count = n * (n-1)^(m-1).
 * 3. Compute using fast modular exponentiation.
 *
 * COMPLEXITY
 * ----------
 * Time : O(log m)
 * Space: O(1)
 * ============================================================
 */

public class CF_1301C_AyoubsFunction {

    static final long MOD = 1_000_000_007L;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            long n = Long.parseLong(st.nextToken());
            long m = Long.parseLong(st.nextToken());

            // n * (n-1)^(m-1) mod MOD
            long ans = n % MOD * modPow(n - 1, m - 1, MOD) % MOD;
            sb.append(ans).append('\n');
        }

        System.out.print(sb);
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
