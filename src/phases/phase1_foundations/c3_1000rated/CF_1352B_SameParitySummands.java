package phases.phase1_foundations.c3_1000rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1352B — Same Parity Summands
 * Link   : https://codeforces.com/problemset/problem/1352/B
 * Rating : 1000  |  Tags: constructive, math
 * Topic  : Phase 1: Foundations > Constructive & Math
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Write n as a sum of exactly k positive integers where all summands have
 * the same parity (all even or all odd). If possible, output "YES" and the
 * k integers; otherwise output "NO".
 *
 * EXAMPLE
 * -------
 * Input:  10 3
 * Output: YES
 *         2 2 6
 *
 * Input:  6 4
 * Output: NO   (need 4 even ≥1: min sum = 2*4=8 > 6; or 4 odd ≥1: min sum = 1*4=4, need 6-4=2 left to distribute keeping odd... 1 1 1 3 works! sum=6✓, all odd✓ → YES)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Try all-odd: use (k-1) ones and (n - (k-1)) as last element.
 *    Condition: (n - (k-1)) >= 1 and (n - (k-1)) is odd (same parity as 1).
 *    n-(k-1) is odd iff n and k-1 have different parities iff n and k have same parity.
 * 2. Try all-even: use (k-1) twos and (n - 2*(k-1)) as last element.
 *    Condition: (n - 2*(k-1)) >= 2 and even.
 *    n - 2*(k-1) is always same parity as n. Need n - 2*(k-1) >= 2.
 * 3. If neither works, output NO.
 *
 * COMPLEXITY
 * ----------
 * Time : O(k) for output
 * Space: O(k)
 * ============================================================
 */

public class CF_1352B_SameParitySummands {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            long n = Long.parseLong(st.nextToken());
            long k = Long.parseLong(st.nextToken());
            // Try all-odd: k-1 ones and last = n-(k-1)
            long lastOdd = n - (k - 1);
            if (lastOdd >= 1 && lastOdd % 2 == 1) {
                sb.append("YES\n");
                for (int i = 0; i < k - 1; i++) sb.append("1 ");
                sb.append(lastOdd).append('\n');
                continue;
            }
            // Try all-even: k-1 twos and last = n-2*(k-1)
            long lastEven = n - 2 * (k - 1);
            if (lastEven >= 2 && lastEven % 2 == 0) {
                sb.append("YES\n");
                for (int i = 0; i < k - 1; i++) sb.append("2 ");
                sb.append(lastEven).append('\n');
                continue;
            }
            sb.append("NO\n");
        }
        System.out.print(sb);
    }
}
