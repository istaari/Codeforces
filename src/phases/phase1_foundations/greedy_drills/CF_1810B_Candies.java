package phases.phase1_foundations.greedy_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1810B — Candies
 * Link   : https://codeforces.com/problemset/problem/1810/B
 * Rating : 1000  |  Tags: greedy, math, binary search
 * Topic  : Phase 1: Foundations > Greedy Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * You have n children. You want to give them candies such that:
 * - Child 1 gets an odd number of candies.
 * - Children 2..n get either 1 or 2 candies each.
 * - Total candies = k.
 * Find if it is possible and output any valid distribution.
 *
 * EXAMPLE
 * -------
 * Input:  3 4 / 3 6
 * Output: 2 1 1 / 4 1 1
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Children 2..n each get at least 1 candy, at most 2 candies.
 * 2. So children 2..n together need between n-1 and 2*(n-1) candies.
 * 3. Child 1 gets the remainder: c1 = k - sum(others).
 * 4. Minimize sum(others) = n-1, maximize = 2*(n-1). Child 1 needs to be positive and odd.
 * 5. Start with all children 2..n getting 1 candy. Remaining for child 1 = k-(n-1).
 *    If k-(n-1) > 0 and k-(n-1) is odd, done.
 *    If k-(n-1) is even and >= 2, we can increment one child 2..n by 1 (making it 2),
 *    which decreases child 1's share by 1 (making it odd). But must still be >= 1.
 * 6. So: give each of 2..n exactly 1 candy initially. If k-(n-1) is odd and >= 1, valid.
 *    If k-(n-1) is even, try making one other child get 2: child1 = k-(n-1)-1 = k-n.
 *    Need k-n >= 1 and k-n odd.
 * 7. If n == 1: child 1 must get k candies, which must be odd. Check k%2==1.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1810B_Candies {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            long k = Long.parseLong(st.nextToken());
            if (n == 1) {
                // child 1 must get k; must be odd
                if (k % 2 == 1) {
                    sb.append(k).append('\n');
                } else {
                    sb.append(-1).append('\n');
                }
                continue;
            }
            // children 2..n each get 1 or 2
            // Give all 1 first: extra = k - (n-1), child1 = extra
            long others = n - 1;
            long c1 = k - others;
            int bonusIdx = -1; // which child (2-indexed) gets 2
            if (c1 < 1) {
                sb.append(-1).append('\n');
                continue;
            }
            if (c1 % 2 == 0) {
                // increment one other child by 1 (it gets 2), child1 decreases by 1
                c1--;
                bonusIdx = 2; // child index 2 gets 2
                if (c1 < 1) {
                    sb.append(-1).append('\n');
                    continue;
                }
            }
            // verify c1 is odd
            if (c1 % 2 == 0) {
                sb.append(-1).append('\n');
                continue;
            }
            // Also check c1 <= ...? No constraint on max for child 1.
            sb.append(c1);
            for (int i = 2; i <= n; i++) {
                if (i == bonusIdx) sb.append(' ').append(2);
                else sb.append(' ').append(1);
            }
            sb.append('\n');
        }
        System.out.print(sb);
    }
}
