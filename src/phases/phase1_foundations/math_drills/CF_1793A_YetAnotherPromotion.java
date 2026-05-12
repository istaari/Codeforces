package phases.phase1_foundations.math_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1793A — Yet Another Promotion
 * Link   : https://codeforces.com/problemset/problem/1793/A
 * Rating : 1100  |  Tags: greedy, math, sortings
 * Topic  : Phase 1: Foundations > Math Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * A shop sells n items. You want to buy all n. Promotion: buy any k items and get
 * the cheapest among them for free. You can apply this promotion multiple times.
 * Minimize total cost to acquire all n items.
 *
 * EXAMPLE
 * -------
 * Input:  4 2 / 1 2 3 4
 * Output: 6
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Sort prices in non-decreasing order.
 * 2. Work from right (most expensive): for every k items you buy, 1 is free.
 * 3. Free item = cheapest in the group (leftmost in sorted group from right).
 * 4. Greedy from right: buy k items, get the (k+1)-th cheapest free.
 *    Free positions from right (0-indexed): k, 2k+1, 3k+2, ... = j*(k+1)+k for j>=0.
 * 5. Sum all non-free item prices.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1793A_YetAnotherPromotion {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());
            long[] a = new long[n];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) a[i] = Long.parseLong(st.nextToken());
            Arrays.sort(a);
            long total = 0;
            for (int i = 0; i < n; i++) {
                // i is 0-indexed from right (a[n-1-i] is the item)
                boolean free = (i >= k) && ((i - k) % (k + 1) == 0);
                if (!free) total += a[n - 1 - i];
            }
            sb.append(total).append('\n');
        }
        System.out.print(sb);
    }
}
