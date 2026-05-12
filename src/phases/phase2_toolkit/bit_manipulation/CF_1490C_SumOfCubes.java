package phases.phase2_toolkit.bit_manipulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/*
 * ============================================================
 * CF 1490C — Sum of Cubes
 * Link   : https://codeforces.com/problemset/problem/1490/C
 * Rating : 1100  |  Tags: math, brute force
 * Topic  : Phase 2: Toolkit > Bit Manipulation
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given n, determine if n = a^3 + b^3 for some positive integers a, b.
 *
 * EXAMPLE
 * -------
 * Input:  2
 * Output: YES
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Iterate a from 1 to cbrt(n). For each a, compute b^3 = n - a^3.
 *    Check if b^3 is a perfect cube (b = round(cbrt(n - a^3)), verify b^3 == n - a^3).
 * 2. Use long arithmetic since n up to 10^12, so a up to 10^4.
 * 3. Be careful with floating point: compute b as (long) Math.round(Math.cbrt(rem)),
 *    then check (b-1)^3, b^3, (b+1)^3 to avoid floating point errors.
 * 4. Edge cases: a must be positive (>= 1) and b must be positive (>= 1).
 *
 * COMPLEXITY
 * ----------
 * Time : O(n^(1/3)) per test case
 * Space: O(1)
 * ============================================================
 */

public class CF_1490C_SumOfCubes {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            long n = Long.parseLong(br.readLine().trim());
            boolean found = false;

            for (long a = 1; a * a * a < n; a++) {
                long rem = n - a * a * a;
                if (rem <= 0) break;
                // Check if rem is a perfect cube
                long b = (long) Math.round(Math.cbrt(rem));
                for (long candidate = Math.max(1, b - 1); candidate <= b + 1; candidate++) {
                    if (candidate > 0 && candidate * candidate * candidate == rem) {
                        found = true;
                        break;
                    }
                }
                if (found) break;
            }

            sb.append(found ? "YES" : "NO").append('\n');
        }

        System.out.print(sb);
    }
}
