package phases.phase1_foundations.c2_900rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1285A — Mezo Playing Zoma
 * Link   : https://codeforces.com/problemset/problem/1285/A
 * Rating : 900  |  Tags: greedy
 * Topic  : Phase 1: Foundations > Greedy
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Mezo starts at position 0 on a number line. He has a string of n moves,
 * each either 'L' (go left by 1) or 'R' (go right by 1). Count the number
 * of distinct positions he can be at after all n moves (he can freely choose
 * the order/interpretation — actually the string is fixed but he can choose
 * WHICH moves to apply and which to skip? No: he applies ALL moves in any order).
 *
 * Actually: Mezo can choose to apply each move or not, OR the string defines
 * a set of possible moves at each step, so the result ranges from -countL to +countR.
 * The distinct positions reachable = countL + countR + 1.
 *
 * EXAMPLE
 * -------
 * Input:  3
 *         LRL
 * Output: 3   (can end at -2, -1, 0, ...; distinct = 2+1+1 = ? Let countL=2,countR=1 → 4?)
 *
 * Input:  1
 *         R
 * Output: 2   (positions 0 or 1)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Count the number of 'L' moves (cntL) and 'R' moves (cntR).
 * 2. Mezo can end up at any position from -cntL to +cntR by selectively
 *    applying moves (taking any subset of L moves and any subset of R moves).
 * 3. Total distinct positions = cntL + cntR + 1.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(1)
 * ============================================================
 */

public class CF_1285A_MezoPlayingZoma {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            String s = br.readLine().trim();
            long cntL = 0, cntR = 0;
            for (char c : s.toCharArray()) {
                if (c == 'L') cntL++;
                else cntR++;
            }
            sb.append(cntL + cntR + 1).append('\n');
        }
        System.out.print(sb);
    }
}
