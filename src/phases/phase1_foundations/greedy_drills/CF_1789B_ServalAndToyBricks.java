package phases.phase1_foundations.greedy_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1789B — Serval and Toy Bricks
 * Link   : https://codeforces.com/problemset/problem/1789/B
 * Rating : 1100  |  Tags: greedy, constructive algorithms, implementation
 * Topic  : Phase 1: Foundations > Greedy Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Serval has a 3D structure made of unit bricks. You're given three projections:
 * - front view: f[j] = max height in column j (viewing from front, rows collapse)
 * - side view: s[i] = max height in row i (viewing from side, columns collapse)
 * - top view: t[i][j] = 1 if there's any brick at (i,j), 0 otherwise
 * Reconstruct a valid 3D structure (or determine impossible).
 * Output the height h[i][j] for each cell (i,j).
 *
 * EXAMPLE
 * -------
 * Input: 2 2 / front: 2 1 / side: 2 1 / top: 1 1 / 1 0
 * Output: 2 1 / 1 0
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. For each cell (i,j): h[i][j] = min(s[i], f[j]) if top[i][j] == 1, else 0.
 * 2. Verify: for each column j, max over i of h[i][j] should equal f[j].
 *    For each row i, max over j of h[i][j] should equal s[i].
 *    If top[i][j] == 0, then h[i][j] must be 0; but this might violate f[j] or s[i].
 * 3. If any row i has s[i] > 0 but all top[i][j] == 0: impossible.
 *    If any column j has f[j] > 0 but all top[i][j] == 0: impossible.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n*m)
 * Space: O(n*m)
 * ============================================================
 */

public class CF_1789B_ServalAndToyBricks {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            int[] f = new int[m]; // front view
            int[] s = new int[n]; // side view
            int[][] top = new int[n][m];

            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) f[j] = Integer.parseInt(st.nextToken());
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) s[i] = Integer.parseInt(st.nextToken());
            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < m; j++) top[i][j] = Integer.parseInt(st.nextToken());
            }

            int[][] h = new int[n][m];
            boolean possible = true;

            // Set heights
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (top[i][j] == 1) {
                        h[i][j] = Math.min(s[i], f[j]);
                    } else {
                        h[i][j] = 0;
                    }
                }
            }

            // Verify front and side projections
            for (int j = 0; j < m; j++) {
                int maxH = 0;
                for (int i = 0; i < n; i++) maxH = Math.max(maxH, h[i][j]);
                if (maxH != f[j]) { possible = false; break; }
            }
            if (possible) {
                for (int i = 0; i < n; i++) {
                    int maxH = 0;
                    for (int j = 0; j < m; j++) maxH = Math.max(maxH, h[i][j]);
                    if (maxH != s[i]) { possible = false; break; }
                }
            }

            if (!possible) {
                sb.append(-1).append('\n');
            } else {
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < m; j++) {
                        if (j > 0) sb.append(' ');
                        sb.append(h[i][j]);
                    }
                    sb.append('\n');
                }
            }
        }
        System.out.print(sb);
    }
}
