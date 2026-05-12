package phases.phase1_foundations.c2_900rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1313A — Fast Food Restaurant
 * Link   : https://codeforces.com/problemset/problem/1313/A
 * Rating : 900  |  Tags: greedy
 * Topic  : Phase 1: Foundations > Greedy
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * A fast food restaurant sells burgers with 1, 2, and 3 patties.
 * They have a burgers of type 1, b of type 2, and c of type 3.
 * n customers arrive, each wanting exactly 1, 2, or 3 patties.
 * Find the maximum number of customers that can be served (each
 * served customer gets exactly the burger type matching their request).
 *
 * EXAMPLE
 * -------
 * Input:  6 4 6 3
 *         1 1 1 2 2 3
 * Output: 6   (all served if stock sufficient)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Count how many customers want 1 patty (cnt1), 2 patties (cnt2), 3 (cnt3).
 * 2. Each type is served independently: min(cnt1, a) + min(cnt2, b) + min(cnt3, c).
 * 3. Sum these three minimums for the answer.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(1)
 * ============================================================
 */

public class CF_1313A_FastFoodRestaurant {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken()); // type 1 stock
            int b = Integer.parseInt(st.nextToken()); // type 2 stock
            int c = Integer.parseInt(st.nextToken()); // type 3 stock
            int cnt1 = 0, cnt2 = 0, cnt3 = 0;
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) {
                int order = Integer.parseInt(st.nextToken());
                if (order == 1) cnt1++;
                else if (order == 2) cnt2++;
                else cnt3++;
            }
            int served = Math.min(cnt1, a) + Math.min(cnt2, b) + Math.min(cnt3, c);
            sb.append(served).append('\n');
        }
        System.out.print(sb);
    }
}
