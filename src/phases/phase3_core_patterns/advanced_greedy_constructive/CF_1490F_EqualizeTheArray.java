package phases.phase3_core_patterns.advanced_greedy_constructive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1490F — Equalize the Array
 * Link   : https://codeforces.com/problemset/problem/1490/F
 * Rating : 1500  |  Tags: greedy, sorting, prefix sums
 * Topic  : Phase 3: Core Patterns > Advanced Greedy & Constructive
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given array a of n positive integers. In one operation, remove any one element.
 * Find the minimum number of operations to make all remaining elements equal.
 *
 * EXAMPLE
 * -------
 * Input:  4
 *         1 3 2 3
 * Output: 1
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Sort the array. After sorting, try each possible "target" value: the result must
 *    keep all elements equal to some value x that exists in the array.
 * 2. For target value x = sorted[i]: keep all elements equal to sorted[i], remove the rest.
 *    Number of elements equal to sorted[i] = (upper_bound - lower_bound of x in sorted array).
 *    Number of removals = n - count(x).
 * 3. The minimum is over all distinct values. But we also consider the case of removing ALL
 *    elements except one type. Answer = n - max_frequency(any value).
 * 4. Use frequency map: answer = n - max_frequency.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1490F_EqualizeTheArray {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            StringTokenizer st = new StringTokenizer(br.readLine());
            HashMap<Integer, Integer> freq = new HashMap<>();
            int maxFreq = 0;
            for (int i = 0; i < n; i++) {
                int v = Integer.parseInt(st.nextToken());
                int f = freq.merge(v, 1, Integer::sum);
                maxFreq = Math.max(maxFreq, f);
            }
            sb.append(n - maxFreq).append('\n');
        }

        System.out.print(sb);
    }
}
