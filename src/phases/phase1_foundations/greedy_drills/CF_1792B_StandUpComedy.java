package phases.phase1_foundations.greedy_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1792B — Stand-up Comedy
 * Link   : https://codeforces.com/problemset/problem/1792/B
 * Rating : 1100  |  Tags: greedy, constructive algorithms
 * Topic  : Phase 1: Foundations > Greedy Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * A comedian performs n acts. Each act belongs to group 0 or group 1. The show
 * is "good" if no two consecutive acts belong to the same group. Given the groups
 * of all n acts (in fixed order), check if you can select a subsequence of acts
 * (keeping relative order) such that the subsequence forms a good show (alternating
 * groups) and is as long as possible. Output the length and which acts to include.
 * Re-reading: you need to select a subsequence where groups alternate. Greedy:
 * scan left to right and greedily pick each act if it differs from the last picked.
 *
 * EXAMPLE
 * -------
 * Input:  5 / 0 0 1 0 1
 * Output: 4 / 1 3 4 5
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Initialize last_group = -1 (none picked yet).
 * 2. Scan left to right: if current group != last_group, include this act.
 * 3. This greedy gives the maximum alternating subsequence.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1792B_StandUpComedy {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            StringTokenizer st = new StringTokenizer(br.readLine());
            List<Integer> chosen = new ArrayList<>();
            int lastGroup = -1;
            for (int i = 1; i <= n; i++) {
                int g = Integer.parseInt(st.nextToken());
                if (g != lastGroup) {
                    chosen.add(i);
                    lastGroup = g;
                }
            }
            sb.append(chosen.size()).append('\n');
            for (int i = 0; i < chosen.size(); i++) {
                if (i > 0) sb.append(' ');
                sb.append(chosen.get(i));
            }
            sb.append('\n');
        }
        System.out.print(sb);
    }
}
