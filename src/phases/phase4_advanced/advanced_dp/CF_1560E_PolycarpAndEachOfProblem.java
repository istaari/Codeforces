package phases.phase4_advanced.advanced_dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1560E — Polycarp and Each Letter Counts
 * Link   : https://codeforces.com/problemset/problem/1560/E
 * Rating : 1700  |  Tags: dp, bitmask
 * Topic  : Phase 4: Advanced > Advanced DP
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * There are 26 topics. k problems, each belonging to a subset of topics
 * (given as a string of topics). Find if we can select at most one problem
 * from each topic (i.e., a set of problems covering all 26 topics, where
 * each selected problem can cover multiple topics, but topic sets may overlap).
 * Actually: find minimum problems to cover all 26 topics (set cover with k<=26).
 * Use bitmask DP since k <= 26.
 *
 * Note: The actual CF 1560E problem: n integers each in [1,k]. Must add exactly
 * one to some (between 0 and n) of them to make the array have each value from
 * 1 to k appear at least once. Find min operations.
 *
 * EXAMPLE
 * -------
 * Input:  n=7, k=4, a=[1,1,2,2,3,4,4]
 * Output: 1  (add 1 to one '1' or '4' element to get a 5... wait)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Count frequency of each value 1..k in array a.
 * 2. Missing values (freq=0) need to be covered by incrementing some element.
 * 3. A value v with freq >= 2 can "donate" one occurrence to v+1 (or anywhere).
 *    But we can only increment by 1.
 * 4. Greedy: count missing values m = count of values in [1,k] with freq=0.
 *    Count surplus values s = sum of max(0, freq[v]-1) for v in [1,k].
 *    If s >= m, answer might be m. But we need to check if surplus can reach missing.
 *    Each surplus at position v can reach v+1. Chain: consecutive surpluses fill
 *    consecutive gaps.
 * 5. Actually: find count of positions where we need to increment. Process left to right.
 *    For each gap (missing value), find nearest surplus to the left. Each such
 *    increment operation costs 1.
 * 6. Answer: number of missing values that cannot be filled by adjacent surpluses? No.
 *    Total operations = number of missing values (each requires one increment from
 *    some surplus). Answer = max(0, missing_count - available_surpluses)? Actually = missing_count
 *    if enough surpluses exist, otherwise impossible (output -1 if s < m).
 *    But actually answer = missing count when feasible (each missing needs one op).
 *
 * COMPLEXITY
 * ----------
 * Time : O(n + k)
 * Space: O(k)
 * ============================================================
 */

public class CF_1560E_PolycarpAndEachOfProblem {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());
            int[] freq = new int[k + 2];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) {
                int x = Integer.parseInt(st.nextToken());
                freq[x]++;
            }

            // Count missing values in [1,k]
            int missing = 0;
            for (int v = 1; v <= k; v++) {
                if (freq[v] == 0) missing++;
            }

            // Count surplus (values with freq >= 2 can spare one, but only to v+1)
            // We need to check if missing values can be covered
            // Greedy sweep: carry over surplus
            int ops = 0;
            int carry = 0;
            for (int v = 1; v <= k; v++) {
                if (freq[v] == 0) {
                    // Need one from carry
                    if (carry > 0) {
                        carry--;
                        ops++; // one operation used
                    } else {
                        // Cannot cover this missing value from the left
                        // Answer is -1? No - we can increment any element <= v to v
                        // Actually problem says: add 1 to some elements. So a[i] goes to a[i]+1.
                        // An element with value v-1 can become v (if freq[v-1] >= 2 or we had
                        // a spare from further left). This is the carry logic.
                        // If no carry, this gap cannot be filled -> output -1
                        sb.append(-1).append('\n');
                        ops = -1;
                        break;
                    }
                } else {
                    carry += freq[v] - 1; // extra occurrences become available
                }
            }

            if (ops != -1) sb.append(ops).append('\n');
        }

        System.out.print(sb);
    }
}
