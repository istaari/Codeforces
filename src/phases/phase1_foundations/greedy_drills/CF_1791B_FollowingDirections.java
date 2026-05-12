package phases.phase1_foundations.greedy_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1791B — Following Directions
 * Link   : https://codeforces.com/problemset/problem/1791/B
 * Rating : 800  |  Tags: implementation, constructive algorithms
 * Topic  : Phase 1: Foundations > Greedy Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * You start at position (0,0) on a grid. You're given a sequence of moves:
 * 'L' (x-1), 'R' (x+1), 'U' (y+1), 'D' (y-1). Check if at any point during
 * the traversal (including the start and after each move), your position is (1,1).
 *
 * EXAMPLE
 * -------
 * Input:  LLURR
 * Output: YES
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Start at (0,0). Simulate each step.
 * 2. After each step, check if (x,y) == (1,1).
 * 3. If so, output YES.
 * 4. If after all steps never visited (1,1), output NO.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n) for the string
 * ============================================================
 */

public class CF_1791B_FollowingDirections {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            String moves = br.readLine().trim();
            int x = 0, y = 0;
            boolean found = false;
            for (char c : moves.toCharArray()) {
                if (c == 'L') x--;
                else if (c == 'R') x++;
                else if (c == 'U') y++;
                else if (c == 'D') y--;
                if (x == 1 && y == 1) {
                    found = true;
                    break;
                }
            }
            sb.append(found ? "YES" : "NO").append('\n');
        }
        System.out.print(sb);
    }
}
