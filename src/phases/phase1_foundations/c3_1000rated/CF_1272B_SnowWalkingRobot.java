package phases.phase1_foundations.c3_1000rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1272B — Snow Walking Robot
 * Link   : https://codeforces.com/problemset/problem/1272/B
 * Rating : 1000  |  Tags: constructive, greedy
 * Topic  : Phase 1: Foundations > Constructive & Greedy
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * A robot starts at (0,0) and follows a sequence of moves (L, R, U, D).
 * We can remove any subset of moves. Find the lexicographically smallest
 * non-empty sequence (after removal) such that the robot returns to (0,0).
 * If original program already returns to origin, keep it as-is or trim.
 *
 * EXAMPLE
 * -------
 * Input:  LRLR
 * Output: LR   (keep L and R: net displacement = 0)
 *
 * Input:  LLUU
 * Output: LU...wait no, that doesn't return to origin. Answer: empty isn't allowed.
 *         Actually minimum non-empty path must be "LRLU..." or similar loop.
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Count L, R, U, D moves.
 * 2. Balance horizontal: minLR = min(L, R). Balance vertical: minUD = min(U, D).
 * 3. Keep minLR each of L and R, and minUD each of U and D.
 * 4. Edge case: if both horizontal and vertical are 0, the empty path results.
 *    In this case: if original had any moves, we must output exactly 1 L and 1 R
 *    (or 1 U and 1 D). Choose the lexicographically smaller option.
 *    But if original had no horizontal moves at all (only vertical or vice versa),
 *    we must ensure we have at least 1 pair.
 * 5. Reconstruct: print all 'L's, then 'R's, then 'U's, then 'D's (sorted order).
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(1)
 * ============================================================
 */

public class CF_1272B_SnowWalkingRobot {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            String s = br.readLine().trim();
            int L = 0, R = 0, U = 0, D = 0;
            for (char c : s.toCharArray()) {
                if (c == 'L') L++;
                else if (c == 'R') R++;
                else if (c == 'U') U++;
                else D++;
            }
            int lr = Math.min(L, R);
            int ud = Math.min(U, D);
            // If both are zero, we need at least one pair
            if (lr == 0 && ud == 0) {
                // Pick the smaller-starting pair lexicographically
                // 'L' < 'U', so prefer LR if available
                if (L > 0 && R > 0) { lr = 1; }
                else if (U > 0 && D > 0) { ud = 1; }
                // else impossible (input guarantees non-empty? use LR if present)
            }
            // Build: all L's then R's then U's then D's
            for (int i = 0; i < lr; i++) sb.append('L');
            for (int i = 0; i < ud; i++) sb.append('U');
            for (int i = 0; i < ud; i++) sb.append('D');
            for (int i = 0; i < lr; i++) sb.append('R');
            sb.append('\n');
        }
        System.out.print(sb);
    }
}
