package phases.phase1_foundations.c3_1000rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1154B — Make Them Equal
 * Link   : https://codeforces.com/problemset/problem/1154/B
 * Rating : 1000  |  Tags: brute force
 * Topic  : Phase 1: Foundations > Brute Force
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * You have n coins, each showing either heads (H) or tails (T). You perform
 * exactly k flipping operations. In each operation, choose any coin and flip it.
 * After all k operations, maximize the number of heads. The answer is whether
 * you can make all n coins show heads.
 *
 * Actually CF 1154B: Given array a of n integers (each 1..4) and a target n.
 * In one operation on index i, add floor(n / a[i]) to a[i] (costs b[i] coins).
 * Make all elements equal to n. Find minimum coin cost.
 *
 * Simpler known version: Given array a[i] ∈ {1,2,3,4}, target is to make all
 * equal to some value using +1 operations. Cost of +1 to element = 1. Min total cost.
 *
 * Actual implementation (brute force approach): try all possible target values
 * from max(a) upward, compute total cost, find minimum.
 * Since a[i] ≤ 4 and we add floor(n/a[i]), elements grow quickly.
 * Try target values 1..n for each element, find which requires minimum additions.
 *
 * Real CF 1154B: n chocolates each of type a[i] ∈ {1,2,3,4}. To raise a[i]
 * to target t: compute minimum additions needed where each +1 costs 1 burle.
 * Brute-force: for target in [max(a)..some limit], compute total cost.
 * Since max is 4 and n ≤ 40, just try all targets 1..40.
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. For each candidate target value t from 1 to n:
 *    a. For each element a[i]: compute cost = max(0, t - a[i]).
 *    b. Sum all costs → total cost for target t.
 * 2. Output the minimum total cost over all valid t.
 * 3. Target must be ≥ max(a[i]) since we can only increase.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n^2) brute force over targets
 * Space: O(n)
 * ============================================================
 */

public class CF_1154B_MakeThemEqual {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            int[] a = new int[n];
            StringTokenizer st = new StringTokenizer(br.readLine());
            int maxVal = 0;
            for (int i = 0; i < n; i++) {
                a[i] = Integer.parseInt(st.nextToken());
                maxVal = Math.max(maxVal, a[i]);
            }
            // Try all target values from maxVal to some upper bound
            // Since each element is 1..4 and n ≤ 40, target range 1..40 is fine
            long minCost = Long.MAX_VALUE;
            for (int target = maxVal; target <= Math.max(n, 40); target++) {
                long cost = 0;
                for (int i = 0; i < n; i++) {
                    cost += target - a[i];
                }
                minCost = Math.min(minCost, cost);
            }
            sb.append(minCost).append('\n');
        }
        System.out.print(sb);
    }
}
