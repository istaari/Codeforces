package phases.phase1_foundations.c3_1000rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1165B — Polycarp Training
 * Link   : https://codeforces.com/problemset/problem/1165/B
 * Rating : 1000  |  Tags: greedy, sorting
 * Topic  : Phase 1: Foundations > Greedy & Sorting
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Polycarp trains every day for n days. On day i (1-indexed), he can do at
 * most i exercises. He has m workouts each requiring exactly b[j] exercises.
 * He can do at most one workout per day. Find the maximum number of workouts
 * he can complete.
 *
 * EXAMPLE
 * -------
 * Input:  4 / 1 2 1 2
 * Output: 4   (sort: 1 1 2 2. Day1 ≥ 1 ✓, Day2 ≥ 1 ✓, Day3 ≥ 2 ✓, Day4 ≥ 2 ✓)
 *
 * Input:  3 / 4 1 1
 * Output: 2   (sort: 1 1 4. Day1≥1✓, Day2≥1✓, Day3 needs ≥4 but only 3→ fail → 2)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Sort the workouts by required exercises (ascending).
 * 2. Greedily assign: the i-th easiest workout (1-indexed) to day i.
 * 3. Workout i is completable on day i if i ≥ b[i] (sorted).
 * 4. Count how many consecutive workouts can be scheduled (stop at first failure,
 *    or count all valid ones — non-consecutive also works greedily since sorted).
 *
 * COMPLEXITY
 * ----------
 * Time : O(m log m)
 * Space: O(m)
 * ============================================================
 */

public class CF_1165B_PolycarpTraining {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int m = Integer.parseInt(br.readLine().trim());
            int[] b = new int[m];
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < m; i++) b[i] = Integer.parseInt(st.nextToken());
            Arrays.sort(b);
            int count = 0;
            int day = 1;
            for (int i = 0; i < m; i++) {
                if (day >= b[i]) {
                    count++;
                    day++;
                }
            }
            sb.append(count).append('\n');
        }
        System.out.print(sb);
    }
}
