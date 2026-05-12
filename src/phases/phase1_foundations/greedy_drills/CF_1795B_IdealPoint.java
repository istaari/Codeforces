package phases.phase1_foundations.greedy_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1795B — Ideal Point
 * Link   : https://codeforces.com/problemset/problem/1795/B
 * Rating : 1000  |  Tags: greedy, implementation, data structures
 * Topic  : Phase 1: Foundations > Greedy Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * You have an array a[1..n] (initially all zeros) and m segments [l[i], r[i]].
 * A subset S of segments is chosen. For each chosen segment, a[l..r] is incremented
 * by 1. Position k is the "ideal point" if a[k] is strictly greater than all other
 * positions. Check if there exists a subset S such that k is the unique maximum.
 *
 * EXAMPLE
 * -------
 * Input:  5 3 2 / 1 3 / 2 4 / 3 5
 * Output: YES
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Key insight: we should include all segments that cover position k (to maximize a[k]).
 * 2. After including all segments covering k, check if a[k] is strictly greater than
 *    all other positions.
 * 3. If a[j] == a[k] for some j != k, we would need to exclude a segment that covers j
 *    but not k, but all segments covering k are already included. Segments not covering k
 *    would only increase other positions, making it worse.
 * 4. So: take exactly the segments that cover k. Compute the coverage array.
 *    Check if a[k] > a[j] for all j != k.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n + m)
 * Space: O(n)
 * ============================================================
 */

public class CF_1795B_IdealPoint {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());
            int[] diff = new int[n + 2]; // difference array
            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                int l = Integer.parseInt(st.nextToken());
                int r = Integer.parseInt(st.nextToken());
                // Only include segments that cover k
                if (l <= k && k <= r) {
                    diff[l]++;
                    diff[r + 1]--;
                }
            }
            // Build coverage array
            int[] cov = new int[n + 1];
            int running = 0;
            for (int i = 1; i <= n; i++) {
                running += diff[i];
                cov[i] = running;
            }
            // Check if k is the unique maximum
            boolean ok = true;
            for (int i = 1; i <= n; i++) {
                if (i != k && cov[i] >= cov[k]) {
                    ok = false;
                    break;
                }
            }
            sb.append(ok ? "YES" : "NO").append('\n');
        }
        System.out.print(sb);
    }
}
