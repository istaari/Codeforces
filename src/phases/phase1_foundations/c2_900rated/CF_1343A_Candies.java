package phases.phase1_foundations.c2_900rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1343A — Candies
 * Link   : https://codeforces.com/problemset/problem/1343/A
 * Rating : 900  |  Tags: math
 * Topic  : Phase 1: Foundations > Math
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Anton wants to give Anya x candies. Anya gives Kolya some candies y.
 * The relationship is: there exists a positive integer k such that
 * x + y = n (total candies) AND x = y * (2^k - 1) / ...
 * More precisely: find x such that x * 2^k = n + x for some k ≥ 1,
 * i.e., x * (2^k - 1) = n. Find any such positive integer x.
 *
 * EXAMPLE
 * -------
 * Input:  15
 * Output: 1   (k=4: 1*(16-1)=15 ✓ or x=3: 3*(4+1)? No. x*(2^k-1)=15 → x=1,k=4: 1*15=15✓)
 *
 * Input:  4
 * Output: 4   (k=1: 4*(2-1)=4✓)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Try all powers of 2 from k=1 to k=30.
 * 2. For each k, compute divisor = 2^k - 1.
 * 3. If n % divisor == 0, then x = n / divisor is a valid answer.
 * 4. Output the first valid x found (or any valid one).
 *
 * COMPLEXITY
 * ----------
 * Time : O(30) = O(1)
 * Space: O(1)
 * ============================================================
 */

public class CF_1343A_Candies {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            long n = Long.parseLong(br.readLine().trim());
            long answer = -1;
            for (int k = 1; k <= 30; k++) {
                long divisor = (1L << k) - 1; // 2^k - 1
                if (n % divisor == 0) {
                    answer = n / divisor;
                    break;
                }
            }
            sb.append(answer).append('\n');
        }
        System.out.print(sb);
    }
}
