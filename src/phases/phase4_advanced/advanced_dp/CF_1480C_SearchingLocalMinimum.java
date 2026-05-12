package phases.phase4_advanced.advanced_dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1480C — Searching Local Minimum
 * Link   : https://codeforces.com/problemset/problem/1480/C
 * Rating : 1700  |  Tags: dp, interactive, binary search
 * Topic  : Phase 4: Advanced > Advanced DP
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Interactive problem: there is a hidden array of n distinct integers.
 * You can query an index i and learn a[i]. Find any local minimum, i.e.,
 * an index i where a[i] < a[i-1] (if i>1) and a[i] < a[i+1] (if i<n).
 * You have at most 100 queries (n <= 10^5 so binary search works in ~17).
 * Array is 1-indexed.
 *
 * EXAMPLE
 * -------
 * Input:  n=5, hidden=[3,1,2,4,5]
 * Output: ? 3, ? 2, ? 1, ! 2
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Binary search on [lo, hi]. Query mid.
 * 2. If a[mid-1] < a[mid], local min must be in [lo, mid-1] (value decreasing left).
 * 3. If a[mid+1] < a[mid], local min must be in [mid+1, hi].
 * 4. Otherwise a[mid] is a local minimum.
 * 5. Cache all queried values to avoid redundant queries.
 * 6. Always flush after each query (System.out.flush()).
 *
 * COMPLEXITY
 * ----------
 * Time : O(log n) queries
 * Space: O(n) for cache
 * ============================================================
 */

public class CF_1480C_SearchingLocalMinimum {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static Map<Integer, Integer> cache = new HashMap<>();

    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());

        int lo = 1, hi = n;
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            int vMid = query(mid);
            int vLeft = (mid > 1) ? query(mid - 1) : Integer.MAX_VALUE;
            int vRight = (mid < n) ? query(mid + 1) : Integer.MAX_VALUE;

            if (vLeft < vMid) {
                hi = mid - 1;
            } else if (vRight < vMid) {
                lo = mid + 1;
            } else {
                lo = hi = mid;
            }
        }
        System.out.println("! " + lo);
        System.out.flush();
    }

    static int query(int idx) throws IOException {
        if (cache.containsKey(idx)) return cache.get(idx);
        System.out.println("? " + idx);
        System.out.flush();
        int val = Integer.parseInt(br.readLine().trim());
        cache.put(idx, val);
        return val;
    }
}
