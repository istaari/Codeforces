package phases.phase4_advanced.union_find_dsu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1176F — Destroy It
 * Link   : https://codeforces.com/problemset/problem/1176/F
 * Rating : 1900  |  Tags: DSU, dp, intervals
 * Topic  : Phase 4: Advanced > Union Find / DSU
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Array of n cards. Some cards are "special" (a[i] = 1). You can destroy a
 * contiguous segment [l, r] if it contains at least one special card. Destroying
 * costs the number of non-special cards in the segment. Find maximum cards that
 * can be destroyed (i.e., maximize total destroyed = maximize segments selected
 * with minimum total cost, but we want MAXIMUM number of cards removed).
 *
 * Actually CF 1176F: n cards in a row. Some cards are "marked". You select segments
 * to destroy. A segment can be destroyed only if it contains at least one marked card.
 * You want to maximize the total number of destroyed cards. Cards can only be in
 * one segment. Find maximum cards removable.
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Key: we want to select non-overlapping segments, each with at least one marked card,
 *    maximizing total length.
 * 2. DP: dp[i] = maximum cards removed using only cards 1..i.
 * 3. dp[i] = max(dp[i-1], max over j <= i of (dp[j-1] + (i - j + 1)) if [j,i] has marked card)
 * 4. For each ending position i, the valid starting positions j are those where
 *    [j..i] contains a marked card: j <= last_marked[i] where last_marked[i] = last marked pos <= i.
 *    For j in [1, last_marked[i]] (but also need first_marked[j..i]).
 *    Simplification: segment [j..i] has marked card iff last_marked_pos <= i and first_marked_pos >= j.
 *    So j <= last_marked[i] and some marked card >= j and <= i.
 * 5. dp[i] = max(dp[i-1], i - j + 1 + dp[j-1]) for j in [prev_marked[i]+1 ... i] where prev_marked[i]
 *    is the last marked card at or before i.
 *    The maximum of (i-j+1+dp[j-1]) = i+1 + max(-j+dp[j-1]) for j in valid range.
 *    Use a running max of (dp[j-1]-j) for j in [1..last_marked[i]].
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1176F_DestroyIt {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        int[] a = new int[n + 1]; // 1-indexed
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) a[i] = Integer.parseInt(st.nextToken());

        // dp[i] = max cards removed from first i cards
        long[] dp = new long[n + 1];
        long NEG_INF = Long.MIN_VALUE / 2;
        // maxVal[i] = max(dp[j-1] - j) for j in [1..i] where a[j..j] can be start of valid segment
        // Valid start j: at least one marked card in [j..i], so j <= last_marked[i]
        // We track a running max of (dp[j-1] - j) for j up to current last marked position

        long runMax = NEG_INF;
        int lastMarked = 0; // last marked position processed in runMax

        for (int i = 1; i <= n; i++) {
            dp[i] = dp[i - 1]; // don't include card i in any removed segment

            // If card i is marked, all j in [lastMarked+1..i] can now be included in runMax
            // since [j..i] would contain the marked card at i.
            if (a[i] == 1) {
                // Update runMax with j from lastMarked+1 to i
                for (int j = lastMarked + 1; j <= i; j++) {
                    if (dp[j - 1] != NEG_INF || j == 1) {
                        runMax = Math.max(runMax, dp[j - 1] - j);
                    }
                }
                lastMarked = i;
            }

            // Can we end a segment at i? Need runMax to be valid (some j with marked in [j..i])
            if (runMax != NEG_INF) {
                // Best segment ending at i: dp[j-1] + (i - j + 1) = dp[j-1] - j + i + 1
                dp[i] = Math.max(dp[i], runMax + i + 1);
            }
        }

        System.out.println(dp[n]);
    }
}
