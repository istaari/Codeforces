package phases.phase1_foundations.math_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1809A — Garland
 * Link   : https://codeforces.com/problemset/problem/1809/A
 * Rating : 800  |  Tags: math, constructive algorithms
 * Topic  : Phase 1: Foundations > Math Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * You have n lights in a row, each either on (1) or off (0). You apply exactly one
 * operation: choose a contiguous segment [l, r] and toggle all lights in it.
 * Determine if you can turn ALL lights off with exactly one such operation.
 *
 * EXAMPLE
 * -------
 * Input:  3 / 010
 * Output: YES
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Find first and last position of '1'.
 * 2. If no '1' exists, all lights are already off — YES (toggle any trivial range).
 * 3. The segment to toggle must be exactly [first_1, last_1].
 * 4. Check if all positions between first_1 and last_1 are also '1'.
 * 5. If yes: toggling [first_1, last_1] turns all '1's to '0', YES. Else NO.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1809A_Garland {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            String s = br.readLine().trim();
            int first = -1, last = -1;
            for (int i = 0; i < n; i++) {
                if (s.charAt(i) == '1') {
                    if (first == -1) first = i;
                    last = i;
                }
            }
            if (first == -1) {
                sb.append("YES\n");
                continue;
            }
            boolean ok = true;
            for (int i = first; i <= last; i++) {
                if (s.charAt(i) == '0') { ok = false; break; }
            }
            sb.append(ok ? "YES" : "NO").append('\n');
        }
        System.out.print(sb);
    }
}
