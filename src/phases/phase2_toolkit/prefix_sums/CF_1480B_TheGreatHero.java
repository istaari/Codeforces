package phases.phase2_toolkit.prefix_sums;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1480B — The Great Hero
 * Link   : https://codeforces.com/problemset/problem/1480/B
 * Rating : 1200  |  Tags: prefix sums, greedy, math
 * Topic  : Phase 2: Toolkit > Prefix Sums
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * A hero with H HP fights n monsters in order. Monster i has damage d[i]
 * and bonus reward b[i]. The hero can kill monster i in one hit (deals b[i]
 * bonus HP) but takes d[i] damage first each round. More precisely:
 * each round the monster attacks (hero loses d[i] HP) then hero kills it
 * (hero gains b[i] HP — effectively hero HP after = HP - d[i] + b[i]).
 * Wait, re-read: hero attacks and monster attacks simultaneously, but
 * hero must survive. Actually: hero fights monsters one by one. Against
 * monster i, every round both deal damage. Hero wins when monster dies
 * (one shot). So hero takes exactly d[i] damage total from monster i,
 * but gains b[i] (the kill bonus). Hero must have HP > 0 after each hit.
 * Find if the hero can survive by choosing optimal fight order.
 *
 * EXAMPLE
 * -------
 * Input:  2 10
 *         6 4
 *         5 7
 * Output: YES
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. The hero gains b[i] - d[i] net HP per monster (except it must survive
 *    the d[i] damage hit). The last monster can kill you if HP > 0 before
 *    but HP <= 0 after, which means: HP > 0 before, HP - d[i] > 0 is
 *    needed to survive (actually we need HP - d[i] > 0, not >= 0).
 *    Wait: if hero HP = d[i], hero takes d[i] damage → HP = 0 → hero dies.
 *    So hero needs HP > d[i] to survive a monster with damage d[i].
 *    But wait — the hero kills the monster simultaneously or after taking damage?
 *    Standard interpretation: hero HP must remain > 0 after taking d[i] damage.
 * 2. For all monsters except the last: after each monster the hero's HP
 *    changes by b[i] - d[i]. To survive monster i: current_HP > d[i].
 *    After killing: HP = HP - d[i] + b[i].
 * 3. Order: fight all monsters except the last in any order. The constraint
 *    on the last monster is just HP_before_last > d[last].
 * 4. Sort by (b[i] - d[i]) ascending (most negative last in prefix, most
 *    positive last). Actually: fight monsters with b[i] >= d[i] first (in
 *    any order since they increase HP), then others. Among all non-last
 *    monsters, at each step we need HP > d[i]. The constraint is:
 *    - For non-last monsters: need to survive their damage; since hero must
 *      also have enough HP after all previous fights.
 * 5. Simplification: For any ordering, the hero's HP after all but the last
 *    monster = H + sum(b[i]-d[i]) for all non-last monsters. This is fixed
 *    regardless of order (as long as hero survives each step). So pick the
 *    monster with largest d[i] as the last one (hardest to survive at the end)
 *    but only if among all "risky" monsters it's last.
 * 6. Key insight: fight all monsters where b[i] >= d[i] in any order (they
 *    increase HP or are neutral). Then fight the rest. The last monster should
 *    be the one with max d[i] (since hero's HP is lowest at the end).
 *    Check: prefix_HP + sum(all b[i]-d[i] except last) > d[last].
 *    Also check at each prefix step HP > d[current]. Use prefix sums.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1480B_TheGreatHero {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            long H = Long.parseLong(st.nextToken());

            long[] d = new long[n], b = new long[n];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) d[i] = Long.parseLong(st.nextToken());
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) b[i] = Long.parseLong(st.nextToken());

            // Sort by (b[i] - d[i]) ascending: fight negative-net-HP monsters last
            // But the very last monster just needs HP > d[last] (no bonus needed after)
            // Among all orderings, pick the one that has best chance: put the hardest
            // "net negative" monster last. Actually: the optimal strategy is to fight
            // all monsters where b[i] >= d[i] first, then rest. The last monster in
            // our ordering should be the one with max d[i] among those we choose last.
            // Simplest correct approach: sort by (b[i] - d[i]) ascending.
            // Fight them in this order. Hero survives if at each step HP > d[i].

            Integer[] idx = new Integer[n];
            for (int i = 0; i < n; i++) idx[i] = i;
            Arrays.sort(idx, (x, y) -> Long.compare(b[x] - d[x], b[y] - d[y]));

            long hp = H;
            boolean alive = true;
            // Prefix check: for all monsters except last in sorted order
            for (int i = 0; i < n - 1; i++) {
                int j = idx[i];
                if (hp <= d[j]) { alive = false; break; }
                hp = hp - d[j] + b[j];
            }
            // Last monster: just need to survive the damage hit
            if (alive) {
                int j = idx[n - 1];
                if (hp <= d[j]) alive = false;
            }

            sb.append(alive ? "YES" : "NO").append('\n');
        }

        System.out.print(sb);
    }
}
