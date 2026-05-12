package phases.phase1_foundations.c3_1000rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1197B — Pillars
 * Link   : https://codeforces.com/problemset/problem/1197/B
 * Rating : 1000  |  Tags: greedy, implementation
 * Topic  : Phase 1: Foundations > Greedy & Implementation
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * There are n pillars in a row, each with a height h[i]. A robot starts at
 * the leftmost position (outside the pillars). The robot can climb a pillar
 * only if it is adjacent to the one it's currently on AND the height
 * difference is at most 1 (|h[next] - h[current]| ≤ 1). The ground counts
 * as height 0. Count the maximum number of pillars the robot can visit
 * starting from either the left or right end before it gets stuck.
 *
 * Note: actual problem asks for how many pillars can be visited in sequence
 * from left to right: count consecutive pillars accessible from ground.
 *
 * EXAMPLE
 * -------
 * Input:  5 / 1 2 1 2 1
 * Output: 5   (all reachable: ground(0)→1→2→1→2→1, each step ≤1 diff)
 *
 * Input:  4 / 1 5 1 1
 * Output: 1   (can go to pillar1 height 1, but pillar2 height 5 is too far)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. From the left: start with height 0. Walk right; at each step check if
 *    |h[i] - prev| ≤ 1. Count until stuck.
 * 2. From the right: similar. Start with height 0 from the right.
 * 3. Answer = max(left_count, right_count).
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1197B_Pillars {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        long[] h = new long[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) h[i] = Long.parseLong(st.nextToken());

        // From left
        int leftCount = 0;
        long prev = 0;
        for (int i = 0; i < n; i++) {
            if (Math.abs(h[i] - prev) <= 1) {
                leftCount++;
                prev = h[i];
            } else break;
        }

        // From right
        int rightCount = 0;
        prev = 0;
        for (int i = n - 1; i >= 0; i--) {
            if (Math.abs(h[i] - prev) <= 1) {
                rightCount++;
                prev = h[i];
            } else break;
        }

        System.out.println(Math.max(leftCount, rightCount));
    }
}
