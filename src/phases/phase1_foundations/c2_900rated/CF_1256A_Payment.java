package phases.phase1_foundations.c2_900rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1256A — Payment Without Change
 * Link   : https://codeforces.com/problemset/problem/1256/A
 * Rating : 900  |  Tags: math
 * Topic  : Phase 1: Foundations > Math
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * You have n coins of value 1 and m coins of value 10. You need to pay
 * exactly s units. Is it possible? You must use at most n coins of value 1
 * and at most m coins of value 10. Output YES or NO.
 *
 * EXAMPLE
 * -------
 * Input:  1000000000 1000000000 1000000000
 * Output: YES
 *
 * Input:  1 1 1
 * Output: YES
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Use as many 10-value coins as possible: use min(m, s/10) coins of value 10.
 * 2. Remaining amount = s - used10 * 10.
 * 3. If remaining ≤ n (enough 1-value coins), output YES, else NO.
 *
 * COMPLEXITY
 * ----------
 * Time : O(1)
 * Space: O(1)
 * ============================================================
 */

public class CF_1256A_Payment {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            long n = Long.parseLong(st.nextToken()); // 1-value coins
            long m = Long.parseLong(st.nextToken()); // 10-value coins
            long s = Long.parseLong(st.nextToken()); // target sum
            long used10 = Math.min(m, s / 10);
            long remaining = s - used10 * 10;
            sb.append(remaining <= n ? "YES" : "NO").append('\n');
        }
        System.out.print(sb);
    }
}
