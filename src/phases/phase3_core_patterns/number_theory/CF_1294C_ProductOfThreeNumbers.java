package phases.phase3_core_patterns.number_theory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/*
 * ============================================================
 * CF 1294C — Product of Three Numbers
 * Link   : https://codeforces.com/problemset/problem/1294/C
 * Rating : 1300  |  Tags: number theory, math, divisors
 * Topic  : Phase 3: Core Patterns > Number Theory
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given n, find three distinct integers a, b, c > 1 such that a * b * c = n.
 * If impossible, output "NO".
 *
 * EXAMPLE
 * -------
 * Input:  12
 * Output: YES
 *         2 2 3
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Try all pairs (a, b) where 2 <= a <= b <= sqrt(n).
 *    For each a dividing n: try b dividing n/a where b >= a and b <= sqrt(n/a).
 *    Then c = n / (a*b). Check c > b and c != a and c != b.
 * 2. Optimization: find smallest prime factor a. Then find smallest prime factor b
 *    of n/a (with b >= a and b <= sqrt(n/a)). c = n/(a*b). Verify a*b*c = n and
 *    a,b,c all > 1.
 * 3. Iterate a from 2 to cbrt(n). For each a dividing n, iterate b from a to
 *    sqrt(n/a). If b divides n/a and c = n/(a*b) > b, we have a valid triple.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n^(2/3)) worst case, O(sqrt(n)) typical
 * Space: O(1)
 * ============================================================
 */

public class CF_1294C_ProductOfThreeNumbers {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            long n = Long.parseLong(br.readLine().trim());
            long a = -1, b = -1, c = -1;

            outer:
            for (long i = 2; i * i * i <= n; i++) {
                if (n % i == 0) {
                    long rem = n / i;
                    for (long j = i; j * j <= rem; j++) {
                        if (rem % j == 0) {
                            long k = rem / j;
                            if (k > j) { // k > j >= i → all distinct and > 1
                                a = i; b = j; c = k;
                                break outer;
                            }
                        }
                    }
                }
            }

            if (a == -1) {
                sb.append("NO\n");
            } else {
                sb.append("YES\n").append(a).append(' ').append(b).append(' ').append(c).append('\n');
            }
        }

        System.out.print(sb);
    }
}
