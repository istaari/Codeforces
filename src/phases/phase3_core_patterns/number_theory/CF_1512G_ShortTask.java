package phases.phase3_core_patterns.number_theory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/*
 * ============================================================
 * CF 1512G — Short Task
 * Link   : https://codeforces.com/problemset/problem/1512/G
 * Rating : 1600  |  Tags: number theory, sieve, divisors
 * Topic  : Phase 3: Core Patterns > Number Theory
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * For each n from 1 to MAX, find the smallest m such that the sum of divisors
 * of m equals n. If no such m exists, output 0.
 *
 * EXAMPLE
 * -------
 * Input:  8
 * Output: (sigma(1)=1, sigma(2)=3, sigma(3)=4, ..., for n=1: m=1, for n=3: m=2, etc.)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Precompute sigma(m) = sum of all divisors of m for m in [1, MAX] using a sieve.
 * 2. For each m, compute sigma(m) and update ans[sigma(m)] = min(ans[sigma(m)], m).
 * 3. Sieve: for each d from 1 to MAX, add d to sigma[d], sigma[2d], sigma[3d], ...
 * 4. Answer queries: for each query n, output ans[n] (or 0 if not set).
 *
 * COMPLEXITY
 * ----------
 * Time : O(MAX * log(MAX)) for sieve
 * Space: O(MAX)
 * ============================================================
 */

public class CF_1512G_ShortTask {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        final int MAX = 1_000_001;
        int[] sigma = new int[MAX];
        int[] ans = new int[MAX];

        // Sieve of divisor sums
        for (int d = 1; d < MAX; d++) {
            for (int m = d; m < MAX; m += d) {
                sigma[m] += d;
            }
        }

        // For each m, update ans[sigma[m]]
        for (int m = 1; m < MAX; m++) {
            if (sigma[m] < MAX && ans[sigma[m]] == 0) {
                ans[sigma[m]] = m; // first (smallest) m with this sigma value
            }
        }

        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            sb.append(ans[n]).append('\n');
        }
        System.out.print(sb);
    }
}
