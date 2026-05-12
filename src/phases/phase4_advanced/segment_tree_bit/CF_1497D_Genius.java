package phases.phase4_advanced.segment_tree_bit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1497D — Genius
 * Link   : https://codeforces.com/problemset/problem/1497/D
 * Rating : 1800  |  Tags: dp, bitmask, XOR
 * Topic  : Phase 4: Advanced > Segment Tree / BIT
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * n segments [l[i], r[i]]. Two segments i and j "conflict" if XOR(r[i]-l[i], r[j]-l[j])
 * is in a given set K. Find the maximum weight independent set where no two segments conflict.
 * Each segment has weight 1 (maximize count) or specific weight (maximize sum).
 * Actually: find maximum number of non-conflicting segments.
 *
 * EXAMPLE
 * -------
 * Input:  n=3, k=2, set={1,2}, segments with lengths [1,2,3]
 * Output: 2
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. n <= 12 (since bitmask DP with 2^n states). Precompute conflict matrix.
 * 2. Two segments i and j conflict if XOR(len[i], len[j]) is in set K.
 * 3. Build conflict bitmask: conflict[i] = bitmask of segments that conflict with i.
 * 4. DP on subsets: dp[mask] = 1 if subset "mask" is non-conflicting (independent set).
 *    dp[mask] = 1 iff for all i in mask and j in mask, i and j don't conflict.
 * 5. Find maximum popcount(mask) where dp[mask] = 1.
 * 6. Build dp incrementally: dp[0] = 1. For each mask, try adding one more bit.
 *    Or: check all pairs in each mask.
 * 7. Efficient: dp[mask] = dp[mask without highest bit] AND (highest bit doesn't conflict with others in mask).
 *
 * COMPLEXITY
 * ----------
 * Time : O(2^n * n)
 * Space: O(2^n)
 * ============================================================
 */

public class CF_1497D_Genius {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        int[] l = new int[n], r = new int[n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            l[i] = Integer.parseInt(st.nextToken());
            r[i] = Integer.parseInt(st.nextToken());
        }

        Set<Long> conflictSet = new HashSet<>();
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < k; i++) {
            conflictSet.add(Long.parseLong(st.nextToken()));
        }

        int[] len = new int[n];
        for (int i = 0; i < n; i++) len[i] = r[i] - l[i];

        // conflict[i] = bitmask of segments conflicting with i
        int[] conflict = new int[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j && conflictSet.contains((long) (len[i] ^ len[j]))) {
                    conflict[i] |= (1 << j);
                }
            }
        }

        // DP: dp[mask] = max size of independent set from subset mask
        // Actually: which masks are valid independent sets?
        boolean[] valid = new boolean[1 << n];
        valid[0] = true;
        int ans = 0;

        for (int mask = 1; mask < (1 << n); mask++) {
            int highest = Integer.numberOfTrailingZeros(Integer.highestOneBit(mask));
            int rest = mask ^ (1 << highest);
            // Check if rest is valid and highest doesn't conflict with rest
            if (valid[rest] && (conflict[highest] & rest) == 0) {
                valid[mask] = true;
                ans = Math.max(ans, Integer.bitCount(mask));
            }
        }

        System.out.println(ans);
    }
}
