package phases.phase1_foundations.sorting_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1829B — Blank Space
 * Link   : https://codeforces.com/problemset/problem/1829/B
 * Rating : 900  |  Tags: implementation, strings
 * Topic  : Phase 1: Foundations > Sorting Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given a binary string of '0's and '1's. Find the longest contiguous run of '0's
 * that is bounded by '1's on both sides (i.e., not at the start or end of the string).
 * Output the length of the longest such "inner" gap of zeros.
 *
 * EXAMPLE
 * -------
 * Input:  010010
 * Output: 2
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Scan through the string.
 * 2. Track gaps of zeros that are between two '1's.
 * 3. Start counting a gap when we see a '1' followed by '0's; end and record when
 *    we hit the next '1'.
 * 4. Track the maximum such gap.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1829B_BlankSpace {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            String s = br.readLine().trim();
            int n = s.length();
            int maxGap = 0;
            int gapStart = -1;
            for (int i = 0; i < n; i++) {
                if (s.charAt(i) == '1') {
                    if (gapStart != -1) {
                        // gap from gapStart to i-1
                        maxGap = Math.max(maxGap, i - gapStart);
                    }
                    gapStart = i + 1;
                }
            }
            sb.append(maxGap).append('\n');
        }
        System.out.print(sb);
    }
}
