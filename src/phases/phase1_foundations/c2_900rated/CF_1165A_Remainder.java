package phases.phase1_foundations.c2_900rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1165A — Remainder
 * Link   : https://codeforces.com/problemset/problem/1165/A
 * Rating : 900  |  Tags: constructive
 * Topic  : Phase 1: Foundations > Constructive
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given integers l, r, and m. Find an integer x in the range [l, r] such
 * that x mod m is as large as possible. If multiple x give the same maximum
 * remainder, output any one of them.
 *
 * EXAMPLE
 * -------
 * Input:  2 10 3
 * Output: 8   (8 mod 3 = 2, which is m-1, the maximum possible remainder)
 *
 * Input:  5 10 4
 * Output: 7   (7 mod 4 = 3 = m-1)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. The maximum possible remainder is m-1.
 * 2. To achieve remainder m-1, we need x = k*m + (m-1) for some k.
 * 3. Find the largest multiple of m that is ≤ r: last_mult = (r / m) * m.
 * 4. Candidate x = last_mult + (m-1). If x ≤ r and x ≥ l, that's our answer.
 * 5. If candidate < l (i.e., no complete cycle of m fits in [l,r]), then
 *    remainder = r mod m is maximized, so output r.
 *
 * COMPLEXITY
 * ----------
 * Time : O(1)
 * Space: O(1)
 * ============================================================
 */

public class CF_1165A_Remainder {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            long l = Long.parseLong(st.nextToken());
            long r = Long.parseLong(st.nextToken());
            long m = Long.parseLong(st.nextToken());
            // Find largest multiple of m not exceeding r, then add m-1
            long lastMult = (r / m) * m;
            long candidate = lastMult + (m - 1);
            if (candidate <= r && candidate >= l) {
                sb.append(candidate).append('\n');
            } else {
                // No full period fits; best is r itself
                sb.append(r).append('\n');
            }
        }
        System.out.print(sb);
    }
}
