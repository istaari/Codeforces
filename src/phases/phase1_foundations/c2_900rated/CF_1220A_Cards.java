package phases.phase1_foundations.c2_900rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1220A — Cards
 * Link   : https://codeforces.com/problemset/problem/1220/A
 * Rating : 900  |  Tags: greedy, sorting
 * Topic  : Phase 1: Foundations > Greedy
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Monocarp has a sequence of n cards, alternating between black (0) and white (1).
 * He can swap adjacent cards. Find the minimum number of swaps needed to sort
 * all white cards to one side and black cards to the other, OR determine if
 * an alternating arrangement is possible.
 *
 * Actually CF 1220A: Given a string of 0s and 1s (n cards). Check if it can be
 * rearranged into a "good" sequence where no two adjacent cards are the same.
 * A good sequence alternates: 0101... or 1010...
 * Condition: |count(0) - count(1)| <= 1.
 * If good, output the sequence; otherwise output -1.
 *
 * EXAMPLE
 * -------
 * Input:  5 / 0 1 0 1 0
 * Output: 0 1 0 1 0   (already alternating)
 *
 * Input:  4 / 0 0 0 0
 * Output: -1
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Count zeros (cnt0) and ones (cnt1).
 * 2. If |cnt0 - cnt1| > 1, it's impossible — output -1.
 * 3. Otherwise, construct the alternating sequence starting with the majority digit.
 * 4. If cnt0 == cnt1, start with either (start with 0 conventionally).
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1220A_Cards {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            StringTokenizer st = new StringTokenizer(br.readLine());
            int cnt0 = 0, cnt1 = 0;
            for (int i = 0; i < n; i++) {
                int x = Integer.parseInt(st.nextToken());
                if (x == 0) cnt0++;
                else cnt1++;
            }
            if (Math.abs(cnt0 - cnt1) > 1) {
                sb.append(-1).append('\n');
            } else {
                // Determine starting digit
                int start = (cnt0 >= cnt1) ? 0 : 1;
                for (int i = 0; i < n; i++) {
                    sb.append((start + i) % 2);
                    if (i < n - 1) sb.append(' ');
                }
                sb.append('\n');
            }
        }
        System.out.print(sb);
    }
}
