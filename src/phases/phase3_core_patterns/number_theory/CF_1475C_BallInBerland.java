package phases.phase3_core_patterns.number_theory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1475C — Ball in Berland
 * Link   : https://codeforces.com/problemset/problem/1475/C
 * Rating : 1400  |  Tags: combinatorics, math, counting
 * Topic  : Phase 3: Core Patterns > Number Theory
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * A ball has boys (group A of size a) and girls (group B of size b).
 * Each boy i wants to dance with some set of girls, and each girl j wants
 * to dance with some set of boys. Count pairs (i, j) such that boy i and
 * girl j want to dance together, weighted by the number of ways to choose
 * one other pair from the remaining people. Formally: for each valid
 * (i, j) pair, the score is (a - 1 choose 0?) or actually: the contribution
 * of pair (i,j) = (number of other valid pairs not involving i or j).
 * Wait — actually: count the number of "dance sets" where exactly two pairs
 * (boy1,girl1) and (boy2,girl2) dance, with all four distinct and both pairs valid.
 *
 * EXAMPLE
 * -------
 * Input:  2 3 3
 *         1 2 3
 *         1 1
 *         2 3
 * Output: 4
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. We want to count ordered pairs of valid dance pairs ((b1,g1),(b2,g2)) where
 *    b1≠b2 and g1≠g2. Then divide by 2 (since order doesn't matter).
 * 2. Total = (sum over all valid (b,g) pairs of (total_valid_pairs - pairs_involving_b - pairs_involving_g + 1)) / 2
 *    = (T * (T - 1) - sum_b(deg_b*(deg_b-1)) - sum_g(deg_g*(deg_g-1))) / 2
 *    where T = total number of valid (b,g) pairs, deg_b = number of girls boy b wants, deg_g = boys.
 *    Wait: for pair (b,g), number of compatible pairs not sharing b or g =
 *    T - deg_b - deg_g + 1 (since pair (b,g) itself is counted in both deg_b and deg_g).
 * 3. Sum over all valid (b,g) of (T - deg_b - deg_g + 1) / 2:
 *    = T * (T - 1) / 2 - sum_{(b,g) valid} (deg_b + deg_g - 1) / 2... this gets messy.
 *    Cleaner: answer = (T*(T-1) - sum_b(deg_b*(deg_b-1)) - sum_g(deg_g*(deg_g-1))) / 2.
 *    Because: count all ordered pairs (p1,p2) of valid dance pairs = T*(T-1).
 *    Subtract pairs sharing a boy: for boy b, pairs sharing b = deg_b*(deg_b-1) (ordered).
 *    Subtract pairs sharing a girl: for girl g, pairs sharing g = deg_g*(deg_g-1) (ordered).
 *    Then divide by 2 for unordered pairs.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n + a + b) where n = total edges in bipartite graph
 * Space: O(a + b)
 * ============================================================
 */

public class CF_1475C_BallInBerland {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int q = Integer.parseInt(st.nextToken());

            int[] degA = new int[a + 1]; // degree of each boy
            int[] degB = new int[b + 1]; // degree of each girl

            for (int i = 0; i < q; i++) {
                st = new StringTokenizer(br.readLine());
                int bi = Integer.parseInt(st.nextToken());
                int gi = Integer.parseInt(st.nextToken());
                degA[bi]++;
                degB[gi]++;
            }

            // T = q (total valid pairs)
            long T = q;

            // Sum of degA[i]*(degA[i]-1) over boys
            long sumA = 0;
            for (int i = 1; i <= a; i++) sumA += (long) degA[i] * (degA[i] - 1);

            // Sum of degB[j]*(degB[j]-1) over girls
            long sumB = 0;
            for (int j = 1; j <= b; j++) sumB += (long) degB[j] * (degB[j] - 1);

            long ans = (T * (T - 1) - sumA - sumB) / 2;
            sb.append(ans).append('\n');
        }

        System.out.print(sb);
    }
}
