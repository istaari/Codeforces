package phases.phase5_cm_push.advanced_graphs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1324E — Sleeping Schedule
 * Link   : https://codeforces.com/problemset/problem/1324/E
 * Rating : 1700  |  Tags: dp, greedy
 * Topic  : Phase 5: CM Push > Advanced Graphs
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Vasya sleeps exactly h hours. Each day i he must fall asleep
 * at time t[i] where l[i] <= t[i] % h <= r[i] (all mod h).
 * He wakes up h hours after sleeping.
 * On day 1 he can sleep at any valid time. On day i+1 he sleeps
 * exactly h hours after waking (so sleep time on day i+1 =
 * sleep time on day i + h, mod h this is the same slot).
 * Wait — actually: if he sleeps at time s on day i, he wakes at
 * s+h. Next day he sleeps at s+h or s+h+1 (he can choose to
 * delay by 1 each day? No — the problem says he sleeps at
 * exactly time l[i] to r[i] within the h-hour window).
 * Re-reading: sleep time on day i is some s_i where
 * l[i] <= s_i % h <= r[i]. Between consecutive days,
 * s_{i+1} >= s_i + h (he wakes up at s_i + h and chooses when
 * to sleep next, but the "comfortable" range is [l[i+1], r[i+1]]
 * in terms of modulo h). Count the number of days where his
 * sleep time falls in [l[i], r[i]] (mod h) while choosing the
 * minimum wakeup-to-sleep gap (0 or 1 extra hours).
 * Actual problem: Each day he wakes, and can go to sleep at
 * current_time or current_time+1 (add 0 or 1 to wake-up time).
 * Maximize comfortable days. DP on current time mod h.
 *
 * EXAMPLE
 * -------
 * Input:  3 10
 *         1 3
 *         6 9
 *         4 7
 * Output: 2
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Let dp[t] = max comfortable days achievable when current
 *    sleep time mod h equals t (at start of processing).
 * 2. For each day i with interval [l[i], r[i]]:
 *    From state t, Vasya wakes at t+h ≡ t (mod h).
 *    He can sleep at t (no delay) or t+1 mod h (1 hour delay).
 *    - If t is in [l[i], r[i]]: +1 comfortable if he sleeps at t.
 *    - If (t+1)%h is in [l[i], r[i]]: +1 comfortable for +1 delay.
 * 3. Transition: new_t = t or (t+1)%h.
 * 4. dp[new_t] = max(dp[new_t], dp[t] + isComfortable).
 * 5. Answer = max over all t of dp[t] after n days.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n * h) — n days, h states
 * Space: O(h)
 * ============================================================
 */

public class CF_1324E_SleepingSchedule {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int h = Integer.parseInt(st.nextToken());

        int[] l = new int[n], r = new int[n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            l[i] = Integer.parseInt(st.nextToken());
            r[i] = Integer.parseInt(st.nextToken());
        }

        // dp[t] = max comfortable days when sleep time mod h = t
        int[] dp = new int[h];
        Arrays.fill(dp, -1);
        // Initially, before any day, he can be at any mod (let's say time 0 before day 1)
        // Actually: on day 1 he first goes to sleep. He starts at time 0 (wake up at 0).
        // So current time mod h = 0, and he can sleep at 0 or 1.
        // Initialize dp with all zeros at all states (he hasn't slept yet)
        Arrays.fill(dp, 0); // At time t mod h = t, 0 comfortable days so far

        for (int i = 0; i < n; i++) {
            int[] ndp = new int[h];
            Arrays.fill(ndp, -1);

            for (int t = 0; t < h; t++) {
                if (dp[t] < 0) continue;

                // Option 1: sleep at time with mod t (no extra delay)
                int comfortable0 = (t >= l[i] && t <= r[i]) ? 1 : 0;
                int nt0 = t; // new sleep mod = t
                ndp[nt0] = Math.max(ndp[nt0], dp[t] + comfortable0);

                // Option 2: sleep at time with mod (t+1)%h (1 hour extra delay)
                int t1 = (t + 1) % h;
                int comfortable1 = (t1 >= l[i] && t1 <= r[i]) ? 1 : 0;
                ndp[t1] = Math.max(ndp[t1], dp[t] + comfortable1);
            }

            dp = ndp;
        }

        int ans = 0;
        for (int t = 0; t < h; t++) {
            ans = Math.max(ans, dp[t]);
        }
        System.out.println(ans);
    }
}
