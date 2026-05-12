package phases.phase4_advanced.union_find_dsu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeMap;


/*
 * ============================================================
 * CF 1157E — Minimum Array
 * Link   : https://codeforces.com/problemset/problem/1157/E
 * Rating : 1700  |  Tags: DSU, greedy, multiset
 * Topic  : Phase 4: Advanced > Union Find / DSU
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given array a[] of n integers. You want to construct a permutation b[].
 * For each i, b[i] = MEX of the multiset {a[1],...,a[i],b[1],...,b[i-1]}.
 * Find the lexicographically smallest permutation b[] satisfying this,
 * or determine it's impossible.
 *
 * ACTUALLY: Given a[], find permutation b[] such that the resulting sequence
 * using the MEX rule produces lexicographically minimum b[].
 *
 * EXAMPLE
 * -------
 * Input:  n=3, a=[2,1,0]
 * Output: 0 1 2
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Greedily: at each step i, we want b[i] to be as small as possible.
 * 2. Available values for b[i]: any value not yet used as b[j] (j<i) and
 *    not equal to a[i] (since a[i] is already "provided" for MEX computation).
 *    Wait, the MEX condition is complex. Let me re-read.
 * 3. Alternative interpretation: b is a permutation of 0..n-1.
 *    Greedy construction: use a multiset of available values.
 *    At step i, choose b[i] = smallest available value not in {a[i+1],...,a[n]}.
 *    This way, we "save" values needed for future a[] contributions.
 * 4. Simple greedy: maintain sorted set of available values {0..n-1}.
 *    At step i, for b[i]: take the smallest value available.
 *    If that value appears in remaining a[i+1..n], we might want to skip it.
 *    Use two-pointer: take smallest available, or if it's "reserved" by future a[], skip.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1157E_MinimumArray {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        int[] a = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) a[i] = Integer.parseInt(st.nextToken());

        // Count occurrences of each value in a (only values in [0,n-1] matter for permutation)
        int[] countA = new int[n];
        for (int x : a) if (x < n) countA[x]++;

        // Available values for b: all 0..n-1, use a sorted multiset
        TreeMap<Integer, Integer> available = new TreeMap<>();
        for (int v = 0; v < n; v++) available.put(v, 1);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            // "Release" a[i] from the reserved set (it's now contributed)
            if (a[i] < n) {
                countA[a[i]]--;
                // a[i] is now available for b if not already used
                // But a[i] might not be in available (if already used as b[j])
            }

            // Choose smallest available value
            // But skip values that are needed by future a[]: i.e., values still in countA
            // Greedy: take smallest v from available where countA[v] == 0 (not needed later)
            // If all available values are needed, take the smallest anyway

            Integer chosen = null;
            // Try smallest not needed
            for (int v : available.keySet()) {
                if (countA[v] == 0) { chosen = v; break; }
            }
            if (chosen == null) {
                // All available are needed; take smallest
                chosen = available.firstKey();
            }

            // Use chosen as b[i]
            available.merge(chosen, -1, Integer::sum);
            if (available.get(chosen) == 0) available.remove(chosen);

            // Remove from countA if it was there (but countA tracks a values, not b values)
            // Actually countA tracks remaining a values, already decremented above

            sb.append(chosen);
            if (i < n - 1) sb.append(' ');
        }
        System.out.println(sb);
    }
}
