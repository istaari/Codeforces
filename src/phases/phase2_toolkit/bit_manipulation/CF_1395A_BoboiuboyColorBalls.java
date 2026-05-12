package phases.phase2_toolkit.bit_manipulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1395A — Boboiuboy and Color Balls
 * Link   : https://codeforces.com/problemset/problem/1395/A
 * Rating : 1200  |  Tags: bitmasks, math, combinatorics
 * Topic  : Phase 2: Toolkit > Bit Manipulation
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * There are n red and m blue balls. We want to split them into groups
 * such that each group has either all red or all blue balls (monochromatic)
 * or one group has both colors (bicolor). Count total moves: each move
 * merges two adjacent groups or splits. Actually: count the minimum number
 * of moves to form valid groups where each group has equal number of each
 * color, or valid mixed groups. Re-reading...
 *
 * ACTUAL CF 1395A: n red and m blue candies. Group them such that each
 * group has the same number of each color. Find the number of valid group
 * sizes k (k divides both n and m, and n/k red + m/k blue = k? No...).
 * Find number of k such that we can split n+m candies into groups of k
 * where each group has equal candies of each color. So k | n, k | m,
 * and n/k = m/k... no that means n=m. Let me look at actual problem.
 *
 * CF 1395A: n and m given. Count the number of values x such that
 * you can divide n red balls into groups of x and m blue balls into groups of x.
 * Answer: number of common divisors of n and m = number of divisors of gcd(n,m).
 *
 * EXAMPLE
 * -------
 * Input:  12 8
 * Output: 4
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. We want x such that x | n AND x | m. So x | gcd(n, m).
 * 2. Count the number of divisors of gcd(n, m).
 * 3. Enumerate divisors from 1 to sqrt(gcd(n,m)).
 *
 * COMPLEXITY
 * ----------
 * Time : O(sqrt(gcd(n,m)))
 * Space: O(1)
 * ============================================================
 */

public class CF_1395A_BoboiuboyColorBalls {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            long n = Long.parseLong(st.nextToken());
            long m = Long.parseLong(st.nextToken());
            long g = gcd(n, m);

            // Count divisors of g
            long count = 0;
            for (long i = 1; i * i <= g; i++) {
                if (g % i == 0) {
                    count++;
                    if (i != g / i) count++;
                }
            }

            sb.append(count).append('\n');
        }

        System.out.print(sb);
    }

    static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
