package phases.phase3_core_patterns.basic_dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 455A — Boredom
 * Link   : https://codeforces.com/problemset/problem/455/A
 * Rating : 1500  |  Tags: dp, implementation
 * Topic  : Phase 3: Core Patterns > Basic DP
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given array a of n integers. Select some elements to score points:
 * if you take element with value v, you score v points but must remove
 * ALL elements equal to v-1 and v+1 from consideration (cannot take those).
 * Maximize total points. Multiple elements of same value can all be taken.
 *
 * EXAMPLE
 * -------
 * Input:  2
 *         1 2
 * Output: 2
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. This is the "House Robber" variant. Count frequency of each value.
 *    Let cnt[v] = count of v in array. Score for taking all v's = v * cnt[v].
 * 2. DP on values 1..max_val:
 *    dp[v] = max points using values 1..v.
 *    dp[v] = max(dp[v-1], dp[v-2] + v*cnt[v])
 *    (either skip v, or take all v's and skip v-1).
 * 3. Base: dp[0] = 0, dp[1] = 1*cnt[1].
 *
 * COMPLEXITY
 * ----------
 * Time : O(n + max_val)
 * Space: O(max_val)
 * ============================================================
 */

public class CF_455A_Boredom {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        StringTokenizer st = new StringTokenizer(br.readLine());

        int MAX = 100001;
        long[] cnt = new long[MAX];
        for (int i = 0; i < n; i++) {
            int v = Integer.parseInt(st.nextToken());
            cnt[v]++;
        }

        long[] dp = new long[MAX];
        dp[1] = cnt[1];
        for (int v = 2; v < MAX; v++) {
            dp[v] = Math.max(dp[v - 1], dp[v - 2] + (long) v * cnt[v]);
        }

        System.out.println(dp[MAX - 1]);
    }
}
