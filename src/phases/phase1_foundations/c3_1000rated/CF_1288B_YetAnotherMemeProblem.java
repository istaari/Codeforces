package phases.phase1_foundations.c3_1000rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1288B — Yet Another Meme Problem
 * Link   : https://codeforces.com/problemset/problem/1288/B
 * Rating : 1000  |  Tags: math
 * Topic  : Phase 1: Foundations > Math
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Find the number of pairs (a, b) with 1 ≤ a ≤ A and 1 ≤ b ≤ B such that
 * a * b + a + b = concat(a, b), where concat(a, b) is the integer formed by
 * writing a and b side by side.
 *
 * EXAMPLE
 * -------
 * Input:  1 9
 * Output: 9   (pairs: (1,9),(2,9),...,(9,9) — all with b=9)
 *
 * Input:  2 109
 * Output: 11  (b can be 9 or 99: 2*9=18 pairs for b=9 gives a=1..2, b=99 gives a=1..2)
 *             Wait: for b=9: any a 1..A works; for b=99: any a 1..A works.
 *             So answer = A * (count of valid b values ≤ B).
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. concat(a,b) = a * 10^d + b where d = number of digits in b.
 * 2. Equation: a*b + a + b = a * 10^d + b → a*(b+1) = a*10^d → b+1 = 10^d.
 * 3. So b must equal 10^d - 1 (i.e., 9, 99, 999, 9999, ...).
 * 4. For each valid b (which is 9, 99, etc.) ≤ B, any a in [1, A] is valid.
 * 5. Answer = A * (count of valid b values ≤ B).
 *
 * COMPLEXITY
 * ----------
 * Time : O(log B) — at most 10 valid b values up to 10^10
 * Space: O(1)
 * ============================================================
 */

public class CF_1288B_YetAnotherMemeProblem {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            long A = Long.parseLong(st.nextToken());
            long B = Long.parseLong(st.nextToken());
            // Count valid b values: 9, 99, 999, ... <= B
            long validBCount = 0;
            long b = 9;
            while (b <= B) {
                validBCount++;
                b = b * 10 + 9;
                if (b < 0) break; // overflow guard
            }
            sb.append(A * validBCount).append('\n');
        }
        System.out.print(sb);
    }
}
