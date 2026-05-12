package phases.phase4_advanced.advanced_dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1426E — Rock, Paper, Scissors
 * Link   : https://codeforces.com/problemset/problem/1426/E
 * Rating : 1800  |  Tags: dp, math, combinatorics
 * Topic  : Phase 4: Advanced > Advanced DP
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * n players play round-robin rock-paper-scissors. There are r rock players,
 * s scissors players, and p paper players. Each pair plays once.
 * Rock beats scissors, scissors beats paper, paper beats rock.
 * Count the number of players who CANNOT possibly win all their games
 * (i.e., at least one loss is forced). A player CAN win all if there's
 * an assignment of outcomes where they beat everyone.
 *
 * EXAMPLE
 * -------
 * Input:  2 2 2  (r=2, s=2, p=2)
 * Output: 6
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. A rock player can potentially win all if s=0 (no scissors to lose to among rock)
 *    and p=0 (no paper players). But ties among same type: we must think about draws.
 *    Actually, ties are allowed to count as non-loss. Re-read: rock vs rock is a draw,
 *    which is NOT a win. So rock player loses to paper and draws rock. A rock player
 *    CAN win all iff: r=1 (no other rock to draw with... wait draws aren't wins either).
 *    Actually the problem asks for players that CAN'T win ALL games. A player wins all
 *    if they beat everyone. Rock player must beat all scissors and paper players but
 *    rock vs rock is a draw (not a win). So a rock player can only win all iff r=1 and p=0.
 * 2. Similarly: scissors wins all iff s=1 and r=0; paper wins all iff p=1 and s=0.
 * 3. Count players that CAN win all, subtract from n for answer. But: if multiple types
 *    can potentially win all simultaneously, count them all.
 * 4. A rock player CAN win all games iff: there are no paper players (p=0) and
 *    all other rock players are irrelevant (draws). Wait - draws mean they don't WIN
 *    that game. So to win ALL games, rock player needs: no paper players (p=0) and
 *    r==1 (no other rock to draw with). Similarly for others.
 * 5. Count canWin = (r>0 && p==0 && r==1? r:0) etc. Wait: all r rock players face same
 *    situation. If r>=2 they all draw each other so none can win all. If r==1 and p==0,
 *    that one rock player can win all (beats all s scissors, draws nobody else).
 *    Answer = n - canWin.
 *
 * COMPLEXITY
 * ----------
 * Time : O(1)
 * Space: O(1)
 * ============================================================
 */

public class CF_1426E_RockPaperScissors {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        long r = Long.parseLong(st.nextToken());
        long s = Long.parseLong(st.nextToken());
        long p = Long.parseLong(st.nextToken());
        long n = r + s + p;

        // A rock player can win all iff no paper players (p=0) and no other rocks (r=1)
        // A scissors player can win all iff no rock players (r=0) and no other scissors (s=1)
        // A paper player can win all iff no scissors players (s=0) and no other papers (p=1)
        // But multiple types can satisfy simultaneously (e.g. r=0,s=0,p=1 -> paper can win all)

        long canWin = 0;
        // rock players can win all their games: need p==0 and r==1
        if (p == 0 && r == 1) canWin += r;
        // scissors players can win all: need r==0 and s==1
        if (r == 0 && s == 1) canWin += s;
        // paper players can win all: need s==0 and p==1
        if (s == 0 && p == 1) canWin += p;

        // Edge: if all same type, e.g. r=5,s=0,p=0: all rock players draw each other
        // none can WIN all (need to beat everyone, draws don't count)
        // Actually wait: re-read problem carefully.
        // "player that can potentially beat all other players" means in some valid outcome
        // they win all matches. Same type match = draw, so they CANNOT win that match.
        // Hence: if r>=2, no rock player can win all (they must draw the other rocks).
        // The formula above already handles this correctly.

        System.out.println(n - canWin);
    }
}
