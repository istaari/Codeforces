package phases.phase4_advanced.advanced_dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;


/*
 * ============================================================
 * CF 1272F — Two Bracket Sequences
 * Link   : https://codeforces.com/problemset/problem/1272/F
 * Rating : 1900  |  Tags: dp, bfs, shortest common supersequence
 * Topic  : Phase 4: Advanced > Advanced DP
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given two bracket sequences s and t. Find the shortest common supersequence
 * of s and t that is also a valid balanced bracket sequence. Output the
 * lexicographically smallest such sequence if multiple exist.
 *
 * EXAMPLE
 * -------
 * Input:  s="()", t="()"
 * Output: "()"
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. BFS on state (i, j, depth) where i = chars consumed from s,
 *    j = chars consumed from t, depth = current bracket depth.
 * 2. From state (i,j,d), try appending '(' or ')':
 *    - Append '(': new state (i', j', d+1) where i'=i+1 if s[i]='(' else i,
 *      j'=j+1 if t[j]='(' else j.
 *    - Append ')': only if d>0; similarly advance i,j if they match ')'.
 * 3. BFS finds shortest path (minimum length supersequence).
 *    Use visited array to avoid revisiting states.
 * 4. State space: (|s|+1) * (|t|+1) * (|s|+|t|+1) for depths.
 * 5. Terminal state: i=|s|, j=|t|, d=0.
 * 6. To reconstruct path, store parent state for each visited state.
 *
 * COMPLEXITY
 * ----------
 * Time : O(|s| * |t| * (|s|+|t|))
 * Space: O(|s| * |t| * (|s|+|t|))
 * ============================================================
 */

public class CF_1272F_TwoBracketSequences {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine().trim();
        String t = br.readLine().trim();

        int sn = s.length(), tn = t.length();
        int maxDepth = sn + tn;

        // State: (i, j, depth) - encoded
        // i in [0..sn], j in [0..tn], depth in [0..maxDepth]
        int SI = sn + 1, TI = tn + 1, DI = maxDepth + 1;
        int[][][] prev = new int[SI][TI][DI]; // encoded previous state + char used
        // prev[i][j][d] = encoded (pi, pj, pd, ch) where ch=0 means '(', ch=1 means ')'
        // Use -1 for unvisited, -2 for start

        int UNVISITED = -1;
        // Store as: encode prev state and char
        // Use separate arrays
        int[][][] prevI = new int[SI][TI][DI];
        int[][][] prevJ = new int[SI][TI][DI];
        int[][][] prevD = new int[SI][TI][DI];
        byte[][][] prevCh = new byte[SI][TI][DI]; // 0='(', 1=')'
        boolean[][][] visited = new boolean[SI][TI][DI];

        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{0, 0, 0});
        visited[0][0][0] = true;

        boolean found = false;
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int ci = cur[0], cj = cur[1], cd = cur[2];

            // Check if terminal
            if (ci == sn && cj == tn && cd == 0) {
                found = true;
                break; // BFS guarantees shortest path
            }

            // Try appending '(' if depth < maxDepth
            if (cd < maxDepth) {
                int ni = ci + (ci < sn && s.charAt(ci) == '(' ? 1 : 0);
                int nj = cj + (cj < tn && t.charAt(cj) == '(' ? 1 : 0);
                int nd = cd + 1;
                if (!visited[ni][nj][nd]) {
                    visited[ni][nj][nd] = true;
                    prevI[ni][nj][nd] = ci;
                    prevJ[ni][nj][nd] = cj;
                    prevD[ni][nj][nd] = cd;
                    prevCh[ni][nj][nd] = 0;
                    queue.offer(new int[]{ni, nj, nd});
                }
            }

            // Try appending ')' if depth > 0
            if (cd > 0) {
                int ni = ci + (ci < sn && s.charAt(ci) == ')' ? 1 : 0);
                int nj = cj + (cj < tn && t.charAt(cj) == ')' ? 1 : 0);
                int nd = cd - 1;
                if (!visited[ni][nj][nd]) {
                    visited[ni][nj][nd] = true;
                    prevI[ni][nj][nd] = ci;
                    prevJ[ni][nj][nd] = cj;
                    prevD[ni][nj][nd] = cd;
                    prevCh[ni][nj][nd] = 1;
                    queue.offer(new int[]{ni, nj, nd});
                }
            }
        }

        if (!found) {
            System.out.println(0);
            return;
        }

        // Reconstruct path from (sn, tn, 0) back to (0, 0, 0)
        StringBuilder path = new StringBuilder();
        int ci = sn, cj = tn, cd = 0;
        while (ci != 0 || cj != 0 || cd != 0) {
            path.append(prevCh[ci][cj][cd] == 0 ? '(' : ')');
            int pi = prevI[ci][cj][cd];
            int pj = prevJ[ci][cj][cd];
            int pd = prevD[ci][cj][cd];
            ci = pi; cj = pj; cd = pd;
        }
        System.out.println(path.reverse().toString());
    }
}
