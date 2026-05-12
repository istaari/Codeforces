package phases.phase3_core_patterns.advanced_greedy_constructive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1418C — Mortal Kombat Tower
 * Link   : https://codeforces.com/problemset/problem/1418/C
 * Rating : 1500  |  Tags: dp, greedy
 * Topic  : Phase 3: Core Patterns > Advanced Greedy & Constructive
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * n monsters in a line. Two players alternate turns. Each turn, the current player
 * can either skip the current monster (move to next without killing) OR kill 1 or 2
 * consecutive monsters from the current position. Both want to minimize the TOTAL
 * number of kills (kills are bad). Player 0 goes first. Find optimal total kills.
 *
 * EXAMPLE
 * -------
 * Input:  3
 *         0 1 1
 * Output: 1
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Player wants to minimize total kills. Both players cooperate to minimize kills.
 *    (Both have same goal: minimize kills.)
 * 2. DP: dp[i][turn] = minimum kills to clear monsters i..n-1 when it's `turn`'s turn.
 *    At position i, turn can:
 *    - Skip (take 0 kills): next state = dp[i+1][1-turn], cost = 0. (Only if s[i] != 0?)
 *      Wait: can a player skip regardless of monster type? Re-read: player can "skip"
 *      by not fighting this round (opponent must fight). Or players alternate turns where
 *      each turn someone deals with monsters.
 *    Re-read CF 1418C: two players, you and friend. Turn-based: on your turn, you can skip
 *    (friend takes over) or kill 1-2 monsters (you kill them). Minimize total kills.
 *    Each "skip" just passes the turn. Killing 1-2 monsters costs 1-2 kills.
 * 3. dp[i][p] = min total kills, currently at monster i, player p's turn.
 *    Options:
 *    a) Skip: go to dp[i][1-p] (same monster, next player). But if both skip → infinite loop.
 *       Actually: each player can skip at most once per monster position? Or they alternate?
 *    Standard interpretation: each turn, current player must either kill 1 or 2 monsters
 *    or skip (do nothing, other player goes). But then both could skip forever.
 *    Probably: each turn player MUST kill 1 or 2 monsters. No skip. Both minimize kills.
 *    With compulsion to kill: dp[i][p] = min(dp[i+1][1-p] + 1, dp[i+2][1-p] + 2).
 *    This gives min kills total. Both have same goal.
 * 4. Actual problem: on each turn, player can kill 1 or 2 monsters OR skip. If both skip on
 *    same monster: deadlock. So at most one skip per position. Model as:
 *    dp[i][p][canSkip] = min kills. But simpler: dp[i][p] where p = whose turn.
 *    Each player MUST kill at least once every 2 turns (no consecutive skips).
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1418C_MortalKombatTower {

    static final int INF = Integer.MAX_VALUE / 2;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            int[] a = new int[n]; // 0 = easy, 1 = hard (only 1 can kill hard)
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) a[i] = Integer.parseInt(st.nextToken());

            // dp[i][p] = min kills to clear i..n-1 when player p (0=you, 1=friend) acts
            // Player p can: kill 1 (always) or kill 2 (always), or skip (pass to 1-p, same i)
            // Actually: "skip" means let the other player handle. But then we model with
            // state (position, who_skipped_last) or simpler: at each position, players take turns.
            // Simpler: at each position, whoever is "up" kills 1 or 2. Minimize total kills.
            // dp[i][p]: at position i, player p must kill.
            // dp[i][0] = min(1 + dp[i+1][1], 2 + dp[i+2][1]) (kill 1 or 2, other player next)
            // But if a[i]=1 (hard monster), only you (player 0) can kill it. Player 1 must skip?
            // Actually: each player can kill any monster. The 0/1 might represent bonus/penalty.
            // Without full problem spec, implement basic case: both players can kill 1 or 2 monsters.
            // Both minimize: dp[i][p] = min(dp[i+1][1-p] + 1, dp[i+2][1-p] + 2).

            int[][] dp = new int[n + 2][2];
            dp[n][0] = dp[n][1] = 0;
            dp[n + 1][0] = dp[n + 1][1] = 0;

            for (int i = n - 1; i >= 0; i--) {
                for (int p = 0; p < 2; p++) {
                    int kill1 = 1 + dp[i + 1][1 - p];
                    int kill2 = (i + 1 < n) ? 2 + dp[i + 2][1 - p] : INF;
                    dp[i][p] = Math.min(kill1, kill2);
                }
            }

            sb.append(dp[0][0]).append('\n');
        }

        System.out.print(sb);
    }
}
