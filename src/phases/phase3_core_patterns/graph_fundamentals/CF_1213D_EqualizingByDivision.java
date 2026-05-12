package phases.phase3_core_patterns.graph_fundamentals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1213D — Equalizing by Division
 * Link   : https://codeforces.com/problemset/problem/1213/D
 * Rating : 1400  |  Tags: greedy, math, hashing
 * Topic  : Phase 3: Core Patterns > Graph Fundamentals
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given n integers. In one operation, choose any element and divide it by 2
 * (integer division). Find the maximum number of equal elements after at most
 * k total operations.
 *
 * EXAMPLE
 * -------
 * Input:  4 2
 *         1 2 2 4
 * Output: 3
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. For each starting value a[i], generate the sequence a[i], a[i]/2, a[i]/4, ...
 *    until it reaches 0. Track the cumulative cost (number of halvings) to reach each value.
 * 2. Use a HashMap<Long, List<Long>> mapping each reachable value v to the sorted list
 *    of costs needed to turn some a[i] into v.
 * 3. For each value v, greedily pick the cheapest subset of a[i]s that can reach v,
 *    using at most k total operations. Sort costs ascending and take as many as possible.
 * 4. Answer = max over all v of the count of a[i]s we can bring to v within k ops.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log(max_a) log(log(max_a))) amortized
 * Space: O(n log(max_a))
 * ============================================================
 */

public class CF_1213D_EqualizingByDivision {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        long k = Long.parseLong(st.nextToken());

        long[] a = new long[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) a[i] = Long.parseLong(st.nextToken());

        // Map: value → list of (cumulative_halvings to reach this value from some a[i])
        HashMap<Long, List<Long>> costMap = new HashMap<>();

        for (long val : a) {
            long cost = 0;
            long cur = val;
            while (cur > 0) {
                costMap.computeIfAbsent(cur, x -> new ArrayList<>()).add(cost);
                cur >>= 1;
                cost++;
            }
        }

        int ans = 1;
        for (var entry : costMap.entrySet()) {
            List<Long> costs = entry.getValue();
            Collections.sort(costs);
            long totalCost = 0;
            int count = 0;
            for (long c : costs) {
                if (totalCost + c <= k) {
                    totalCost += c;
                    count++;
                } else break;
            }
            ans = Math.max(ans, count);
        }

        System.out.println(ans);
    }
}
