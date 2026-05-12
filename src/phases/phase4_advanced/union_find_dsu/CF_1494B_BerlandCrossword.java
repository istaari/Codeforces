package phases.phase4_advanced.union_find_dsu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1494B — Berland Crossword
 * Link   : https://codeforces.com/problemset/problem/1494/B
 * Rating : 1700  |  Tags: bitmask, implementation
 * Topic  : Phase 4: Advanced > Union Find / DSU
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * n×n crossword. Border cells can be black or white. Given constraints:
 * each border row/column has a required number of black cells. The 4 corners
 * must each be black or white. Find minimum total black cells on the border,
 * or determine if impossible.
 *
 * EXAMPLE
 * -------
 * Input:  n=3, top=1, bottom=1, left=1, right=1
 * Output: 4
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. The 4 corners appear in both row and column constraints. Enumerate all 16
 *    possibilities (2^4) for which corners are black.
 * 2. For each corner assignment:
 *    - Top row: top_req - corners_used_in_top black cells remaining in non-corner positions.
 *    - Similarly for bottom, left, right.
 *    - If any required count goes negative or exceeds n-2 (available non-corner positions): invalid.
 * 3. For each valid corner assignment, compute total = black corners + remaining black in each side.
 * 4. Minimize total.
 *
 * COMPLEXITY
 * ----------
 * Time : O(1) (16 configurations, each O(1) check)
 * Space: O(1)
 * ============================================================
 */

public class CF_1494B_BerlandCrossword {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            StringTokenizer st = new StringTokenizer(br.readLine());
            // top, bottom, left, right required black cells on border (including corners)
            long top = Long.parseLong(st.nextToken());
            long bottom = Long.parseLong(st.nextToken());
            long left = Long.parseLong(st.nextToken());
            long right = Long.parseLong(st.nextToken());

            long ans = Long.MAX_VALUE;

            // Enumerate corners: bit 0=top-left, bit 1=top-right, bit 2=bottom-left, bit 3=bottom-right
            for (int mask = 0; mask < 16; mask++) {
                int tl = (mask >> 0) & 1; // top-left
                int tr = (mask >> 1) & 1; // top-right
                int bl = (mask >> 2) & 1; // bottom-left
                int br2 = (mask >> 3) & 1; // bottom-right

                // Remaining required non-corner black cells:
                long remTop    = top    - tl - tr;
                long remBottom = bottom - bl - br2;
                long remLeft   = left   - tl - bl;
                long remRight  = right  - tr - br2;

                long avail = n - 2; // non-corner cells per side

                if (remTop < 0 || remTop > avail ||
                    remBottom < 0 || remBottom > avail ||
                    remLeft < 0 || remLeft > avail ||
                    remRight < 0 || remRight > avail) continue;

                long total = tl + tr + bl + br2 + remTop + remBottom + remLeft + remRight;
                ans = Math.min(ans, total);
            }

            sb.append(ans == Long.MAX_VALUE ? -1 : ans).append('\n');
        }

        System.out.print(sb);
    }
}
