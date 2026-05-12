package phases.phase1_foundations.c1_800rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 469A — I Wanna Be the Guy
 * Link   : https://codeforces.com/problemset/problem/469/A
 * Rating : 800  |  Tags: implementation, set
 * Topic  : Phase 1: Foundations > Implementation
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * There are n levels (numbered 1 to n). Little Guy has beaten some levels
 * and Big Guy has beaten some levels. A player can become "The Guy" if
 * together they have beaten all n levels (every level beaten by exactly one
 * of them, OR both). Actually: they can become The Guy if collectively
 * all levels 1..n have been beaten (each level by at least one of them).
 * If every level from 1 to n has been beaten by at least one of them, output YES.
 *
 * EXAMPLE
 * -------
 * Input:  4
 *         2
 *         1 3
 *         2
 *         2 4
 * Output: YES
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Read levels beaten by Little Guy into a set.
 * 2. Read levels beaten by Big Guy and add them to the same set.
 * 3. If the union set has size n, all levels are covered → YES, else NO.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_469A_IWannaBeTheGuy {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        HashSet<Integer> covered = new HashSet<>();

        for (int player = 0; player < 2; player++) {
            int m = Integer.parseInt(br.readLine().trim());
            if (m > 0) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int i = 0; i < m; i++) {
                    covered.add(Integer.parseInt(st.nextToken()));
                }
            }
        }

        System.out.println(covered.size() == n ? "I become the guy." : "Oh, my keyboard!");
    }
}
