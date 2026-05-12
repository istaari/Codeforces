package phases.phase1_foundations.constructive_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1809A — Garland
 * Link   : https://codeforces.com/problemset/problem/1809/A
 * Rating : 800  |  Tags: constructive algorithms, math
 * Topic  : Phase 1: Foundations > Constructive Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * You have n lights in a row, each either on (1) or off (0).
 * You can choose a contiguous segment [l, r] and toggle all lights in it.
 * Determine if it is possible to turn all lights off with exactly one operation.
 * If the entire string is already all zeros, that is also valid (use a null op).
 * Actually: the operation must be applied exactly once; output YES if some
 * contiguous segment toggle results in all zeros (segment can be length 0 trivially
 * but re-read: the problem says find l<=r). Check if the 1s form a contiguous block.
 *
 * EXAMPLE
 * -------
 * Input:  3 / 010
 * Output: YES
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Find the first and last position that is '1'.
 * 2. If there are no '1's at all, answer is YES (toggle any single element twice or
 *    the problem allows any segment — but toggling a 0-segment means all must be 0).
 * 3. If all positions from first to last '1' are indeed '1', output YES; else NO.
 * 4. Edge case: all zeros → YES (toggle a segment of all-zero lights, they remain off).
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
                // all zeros
                sb.append("YES\n");
                continue;
            }
            // Check if all positions between first and last are '1'
            boolean ok = true;
            for (int i = first; i <= last; i++) {
                if (s.charAt(i) == '0') {
                    ok = false;
                    break;
                }
            }
            sb.append(ok ? "YES" : "NO").append('\n');
        }
        System.out.print(sb);
    }
}
