package phases.phase4_advanced.advanced_dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1547E — Air Conditioners
 * Link   : https://codeforces.com/problemset/problem/1547/E
 * Rating : 1700  |  Tags: dp, multi-source BFS
 * Topic  : Phase 4: Advanced > Advanced DP
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * There are n rooms in a row. Some rooms have air conditioners with
 * temperature setting t[i]. Heat propagates: if room j is at temperature
 * T, then room j+1 and j-1 are at temperature at most T+1. Find the
 * minimum achievable temperature for each room.
 *
 * EXAMPLE
 * -------
 * Input:  n=5, ACs at room 2 (t=20) and room 4 (t=10)
 * Output: 21 20 11 10 11
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Initialize dp[i] = INF for all rooms, dp[i] = t[i] for AC rooms.
 * 2. Left-to-right pass: dp[i] = min(dp[i], dp[i-1] + 1).
 * 3. Right-to-left pass: dp[i] = min(dp[i], dp[i+1] + 1).
 * 4. This is equivalent to multi-source BFS/DP where each AC room is a source.
 * 5. Two linear passes suffice because heat spreads by +1 per step.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1547E_AirConditioners {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());

            int[] dp = new int[n + 1];
            Arrays.fill(dp, Integer.MAX_VALUE / 2);

            for (int i = 0; i < k; i++) {
                st = new StringTokenizer(br.readLine());
                int room = Integer.parseInt(st.nextToken());
                int temp = Integer.parseInt(st.nextToken());
                dp[room] = Math.min(dp[room], temp);
            }

            // Left to right
            for (int i = 2; i <= n; i++) {
                if (dp[i - 1] + 1 < dp[i]) dp[i] = dp[i - 1] + 1;
            }

            // Right to left
            for (int i = n - 1; i >= 1; i--) {
                if (dp[i + 1] + 1 < dp[i]) dp[i] = dp[i + 1] + 1;
            }

            for (int i = 1; i <= n; i++) {
                sb.append(dp[i]);
                if (i < n) sb.append(' ');
            }
            sb.append('\n');
        }

        System.out.print(sb);
    }
}
