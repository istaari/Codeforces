package phases.phase3_core_patterns.basic_dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1475D — Cleaning the Phone
 * Link   : https://codeforces.com/problemset/problem/1475/D
 * Rating : 1500  |  Tags: dp, sorting, two pointers
 * Topic  : Phase 3: Core Patterns > Basic DP
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * n apps, each with memory m[i] and importance c[i] (1 or 2). Remove some
 * apps to free at least k memory. Minimize the total "importance" lost
 * (sum of c[i] of removed apps). Apps with importance 1 cost 1 each to remove;
 * importance 2 cost 2 each to remove. Find minimum total cost to free >= k memory.
 *
 * EXAMPLE
 * -------
 * Input:  5 7
 *         1 2 1 2 1
 *         1 3 4 1 3
 * Output: 3
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Split apps into two groups: A (importance 1) and B (importance 2).
 *    Sort A and B by memory descending (remove largest first for efficiency).
 * 2. Precompute prefix sums for each group:
 *    sumA[j] = total memory of top-j apps from A (j apps with importance 1 removed).
 *    sumB[j] = total memory of top-j apps from B.
 * 3. For each number j of type-2 apps removed (0 to |B|):
 *    cost_B = 2*j, memory_freed_B = sumB[j].
 *    Need additional memory from type-1 apps: max(0, k - sumB[j]).
 *    Find minimum number of type-1 apps to remove to free that much memory.
 *    Binary search on sumA to find minimum i with sumA[i] >= needed.
 *    Cost = 2*j + i. Track minimum.
 * 4. Total complexity: O(n log n).
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1475D_CleaningThePhone {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            long k = Long.parseLong(st.nextToken());

            long[] m = new long[n];
            int[] c = new int[n];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) m[i] = Long.parseLong(st.nextToken());
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) c[i] = Integer.parseInt(st.nextToken());

            // Separate into two groups
            List<Long> A = new ArrayList<>(), B = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if (c[i] == 1) A.add(m[i]);
                else B.add(m[i]);
            }

            // Sort descending (remove largest first)
            A.sort((x, y) -> Long.compare(y, x));
            B.sort((x, y) -> Long.compare(y, x));

            // Prefix sums
            long[] sumA = new long[A.size() + 1];
            for (int i = 0; i < A.size(); i++) sumA[i + 1] = sumA[i] + A.get(i);
            long[] sumB = new long[B.size() + 1];
            for (int i = 0; i < B.size(); i++) sumB[i + 1] = sumB[i] + B.get(i);

            long ans = Long.MAX_VALUE;

            for (int j = 0; j <= B.size(); j++) {
                long memFromB = sumB[j];
                long needed = k - memFromB;
                if (needed <= 0) {
                    ans = Math.min(ans, 2L * j);
                    break;
                }
                // Binary search in sumA for minimum i with sumA[i] >= needed
                int lo = 0, hi = A.size();
                while (lo < hi) {
                    int mid = (lo + hi) / 2;
                    if (sumA[mid] >= needed) hi = mid;
                    else lo = mid + 1;
                }
                if (lo <= A.size() && sumA[lo] >= needed) {
                    ans = Math.min(ans, 2L * j + lo);
                }
            }

            sb.append(ans == Long.MAX_VALUE ? -1 : ans).append('\n');
        }

        System.out.print(sb);
    }
}
