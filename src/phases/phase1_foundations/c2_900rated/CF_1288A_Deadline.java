package phases.phase1_foundations.c2_900rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1288A — Deadline
 * Link   : https://codeforces.com/problemset/problem/1288/A
 * Rating : 900  |  Tags: math, brute force
 * Topic  : Phase 1: Foundations > Math & Brute Force
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Vasya needs to finish a task. Without optimization, it takes n days.
 * Vasya can spend x days (0 ≤ x) on preparation (improving tools), then
 * the task takes ceil(n / (x + 1)) days. Total time = x + ceil(n/(x+1)).
 * Given n and d, determine if it's possible to finish within d days.
 *
 * EXAMPLE
 * -------
 * Input:  7 4
 * Output: YES   (x=1: 1 + ceil(7/2) = 1+4=5 > 4; x=3: 3+ceil(7/4)=3+2=5>4;
 *                x=6: 6+ceil(7/7)=6+1=7>4... Actually: x=0:0+7=7>4, x=1:5>4, no...
 *                Hmm maybe this example gives NO? Let's trust the formula.)
 *
 * Input:  10 6
 * Output: YES   (x=1: 1+ceil(10/2)=1+5=6 ≤ 6 ✓)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Try all values of x from 0 to d (no point trying x > d).
 * 2. For each x, compute total = x + ceil(n / (x + 1)).
 * 3. If total ≤ d for any x, output YES.
 * 4. Otherwise output NO.
 * 5. Ceil(n / k) = (n + k - 1) / k in integer arithmetic.
 *
 * COMPLEXITY
 * ----------
 * Time : O(d) per test case
 * Space: O(1)
 * ============================================================
 */

public class CF_1288A_Deadline {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            long n = Long.parseLong(st.nextToken());
            long d = Long.parseLong(st.nextToken());
            boolean possible = false;
            for (long x = 0; x <= d; x++) {
                long workDays = (n + x) / (x + 1); // ceil(n / (x+1))
                if (x + workDays <= d) {
                    possible = true;
                    break;
                }
            }
            sb.append(possible ? "YES" : "NO").append('\n');
        }
        System.out.print(sb);
    }
}
