package phases.phase1_foundations.c1_800rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 231A — Team
 * Link   : https://codeforces.com/problemset/problem/231/A
 * Rating : 800  |  Tags: greedy, implementation
 * Topic  : Phase 1: Foundations > Sheet C1 (800-rated)
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * A team of three people (Petya, Vasya, Tonya) tries to solve problems.
 * For each of n problems, each person either knows (1) or doesn't know (0)
 * the solution. A problem is solved if at least 2 of the 3 know the answer.
 * Count the number of problems that will be solved.
 *
 * EXAMPLE
 * -------
 * Input:  3 / 1 1 0 / 1 1 1 / 1 0 0
 * Output: 2
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. For each row (problem), sum the three values.
 * 2. If sum >= 2, the problem gets solved → increment counter.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(1)
 * ============================================================
 */

public class CF_231A_Team {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        int count = 0;
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int sum = 0;
            for (int j = 0; j < 3; j++) {
                sum += Integer.parseInt(st.nextToken());
            }
            if (sum >= 2) count++;
        }
        System.out.println(count);
    }
}
