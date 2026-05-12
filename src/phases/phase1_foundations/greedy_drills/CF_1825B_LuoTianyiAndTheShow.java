package phases.phase1_foundations.greedy_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1825B — LuoTianyi and the Show
 * Link   : https://codeforces.com/problemset/problem/1825/B
 * Rating : 800  |  Tags: greedy, implementation
 * Topic  : Phase 1: Foundations > Greedy Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * There are n audience members and m song slots in a show. Audience member i
 * has a favorite song f[i]. The show plays songs in a sequence. For each song
 * played, all audience members whose favorite song matches are "happy" (+1 score
 * each). Once an audience member's favorite song is played, they leave. You choose
 * which songs to play (a sequence of length m). Maximize total happiness.
 * The trick: each song you play, all remaining audience members who like that song
 * become happy. You can play the same song multiple times but it only satisfies
 * members who haven't left yet.
 *
 * EXAMPLE
 * -------
 * Input:  3 2 / 1 2 1
 * Output: 3
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. For each song s, if we choose to play it k times, the audience members who
 *    like song s and are present get +1 each time (they leave after the first time
 *    their favorite is played). So playing s multiple times only helps the first time.
 * 2. Optimal strategy: pick the most popular song to play first (all its fans leave
 *    happy), then pick the next most popular among remaining audience.
 * 3. This is equivalent to: sort songs by frequency descending. Pick top songs until
 *    we've used m slots. But repeated plays of the same song waste slots.
 * 4. Best: play up to m distinct songs (the top m most frequent). Each fan of each
 *    chosen song becomes happy exactly once. Total = sum of frequencies of top min(m, distinct) songs.
 *    But if distinct songs < m, all fans are happy (play each song once, rest are wasted).
 * 5. Actually since each person can only be happy once: answer = sum of count[s] for top
 *    min(m, number_of_distinct_songs) songs by frequency.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1825B_LuoTianyiAndTheShow {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            st = new StringTokenizer(br.readLine());
            Map<Integer, Integer> freq = new HashMap<>();
            for (int i = 0; i < n; i++) {
                int f = Integer.parseInt(st.nextToken());
                freq.merge(f, 1, Integer::sum);
            }
            // Sort frequencies descending
            int[] freqs = freq.values().stream().mapToInt(Integer::intValue).toArray();
            Arrays.sort(freqs);
            long total = 0;
            int slots = m;
            for (int i = freqs.length - 1; i >= 0 && slots > 0; i--, slots--) {
                total += freqs[i];
            }
            sb.append(total).append('\n');
        }
        System.out.print(sb);
    }
}
