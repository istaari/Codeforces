package phases.phase1_foundations.constructive_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1793B — Fedya and Array
 * Link   : https://codeforces.com/problemset/problem/1793/B
 * Rating : 1100  |  Tags: constructive algorithms
 * Topic  : Phase 1: Foundations > Constructive Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Construct an array of exactly n integers such that:
 * - The minimum element equals mn.
 * - The maximum element equals mx.
 * - For every integer d with 1 <= d <= mx - mn, there exist two elements
 *   a[i] and a[j] in the array with a[i] - a[j] = d (all differences appear).
 * The array length is exactly n. Values need not be distinct.
 *
 * EXAMPLE
 * -------
 * Input:  4 1 5
 * Output: 1 2 4 5
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. We need all differences 1...(mx-mn) to appear.
 * 2. Use the sequence: mn, mn+1, mn+2, ..., mx (ascending from mn to mx).
 *    This has (mx - mn + 1) elements and covers all differences.
 * 3. If n > (mx - mn + 1), we have extra slots: fill them with mn or mx (any value).
 * 4. The required length (mx - mn + 1) <= n is guaranteed by the problem constraints.
 * 5. Output: mn, mn+1, ..., mx, then fill remaining (n - (mx-mn+1)) positions with mn.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1793B_FedyaAndArray {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int mn = Integer.parseInt(st.nextToken());
            int mx = Integer.parseInt(st.nextToken());
            // Fill: mn, mn+1, ..., mx, then remaining with mn
            int range = mx - mn + 1;
            for (int v = mn; v <= mx; v++) {
                sb.append(v);
                sb.append(' ');
            }
            for (int i = 0; i < n - range; i++) {
                sb.append(mn);
                sb.append(' ');
            }
            sb.append('\n');
        }
        System.out.print(sb);
    }
}
