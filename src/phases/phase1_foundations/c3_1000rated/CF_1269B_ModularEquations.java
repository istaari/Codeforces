package phases.phase1_foundations.c3_1000rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1269B — Modular Equations
 * Link   : https://codeforces.com/problemset/problem/1269/B
 * Rating : 1000  |  Tags: math
 * Topic  : Phase 1: Foundations > Math
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given integers a and b with a > b. Count the number of positive integers n
 * such that a mod n = b.
 *
 * EXAMPLE
 * -------
 * Input:  21 5
 * Output: 2   (n=8: 21%8=5✓, n=16: 21%16=5✓)
 *
 * Input:  9435152 272
 * Output: 282
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. a mod n = b means a = k*n + b for some integer k ≥ 1, so n | (a - b).
 * 2. Also, for a mod n = b, we need n > b (otherwise remainder can't be b).
 * 3. Count all divisors of (a - b) that are strictly greater than b.
 *
 * COMPLEXITY
 * ----------
 * Time : O(sqrt(a - b))
 * Space: O(1)
 * ============================================================
 */

public class CF_1269B_ModularEquations {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            long a = Long.parseLong(st.nextToken());
            long b = Long.parseLong(st.nextToken());
            long diff = a - b;
            int count = 0;
            for (long i = 1; i * i <= diff; i++) {
                if (diff % i == 0) {
                    if (i > b) count++;
                    long other = diff / i;
                    if (other != i && other > b) count++;
                }
            }
            sb.append(count).append('\n');
        }
        System.out.print(sb);
    }
}
