package phases.phase3_core_patterns.basic_dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1525C — Robot Collisions
 * Link   : https://codeforces.com/problemset/problem/1525/C
 * Rating : 1600  |  Tags: greedy, stack, sorting
 * Topic  : Phase 3: Core Patterns > Basic DP
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * n robots on a number line, each at position x[i] moving left (L) or right (R).
 * Robots collide if an R-robot and L-robot meet. Also, robots can collide
 * with walls at position 0 (L robots bounce back as R effectively — actually
 * L robots go to 0 and stop? No: "L robot hits wall, bounces to become R robot
 * after reflecting off x=0"). So a robot at x moving L hits wall at time x,
 * then moves R; another robot at y moving R hits it at... find which robots
 * collide with each other (if any). Actually robots collide pairwise.
 * Output for each robot which robot it collides with (or -1 if none).
 *
 * EXAMPLE
 * -------
 * Input:  4
 *         3 5 2 4
 *         RLRL
 * Output: 2 1 -1 -1
 *         (Wait, check example carefully)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Sort robots by position. After sorting, for R-robots and L-robots,
 *    we need to match them.
 * 2. A R-robot at position x and L-robot at position y (x < y) collide
 *    at position (x+y)/2 at time (y-x)/2 if no wall bounce. They collide iff y >= x.
 * 3. A R-robot at x can also bounce off wall: it goes left, hits 0 at time x,
 *    then becomes an "effective R robot" at position 0 at time x, meaning
 *    it's equivalent to an R robot at position -x at time 0.
 *    Two L-robots at x and y (x < y): robot x bounces at time x and becomes R at 0.
 *    Robot y is still going L. They meet if robot y (going L) meets robot x (now going R):
 *    robot x is at 2x-t_x (going R after bounce), robot y is at y-t. Collision when equal:
 *    Wait: robot x bounces at time x (reaches 0), then starts moving R. At time t > x, robot x
 *    at position (t - x). Robot y at position (y - t). Meet when t-x = y-t → t = (x+y)/2.
 *    Need t > x (after bounce), so (x+y)/2 > x → y > x. Always true. So they always collide?
 *    But we must check if robot y hasn't already collided with something else before time (x+y)/2.
 * 4. Algorithm using a stack:
 *    Sort by position. Process L to R. Use a stack for R robots. When we see an L robot:
 *    - If stack non-empty (R robot at position r < current L position l): they collide (greedy match).
 *    - Else: push L robot onto a different stack (for wall-bounce matching).
 *    After processing all, match remaining L robots pairwise: sort by position, pair up
 *    adjacent (closest ones bounce off wall and collide).
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1525C_RobotCollisions {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            int[] x = new int[n];
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) x[i] = Integer.parseInt(st.nextToken());
            String dir = br.readLine().trim();

            int[] ans = new int[n];
            Arrays.fill(ans, -1);

            // Sort by position, keep original indices
            Integer[] idx = new Integer[n];
            for (int i = 0; i < n; i++) idx[i] = i;
            Arrays.sort(idx, (a, b) -> x[a] - x[b]);

            ArrayDeque<Integer> stackR = new ArrayDeque<>(); // indices of unmatched R robots (by position)
            ArrayDeque<Integer> stackL = new ArrayDeque<>(); // indices of unmatched L robots (for wall bounce)

            for (int i : idx) {
                if (dir.charAt(i) == 'R') {
                    stackR.push(i);
                } else { // 'L'
                    if (!stackR.isEmpty()) {
                        // Direct collision: R robot meets L robot
                        int rIdx = stackR.pop();
                        ans[i] = rIdx + 1;     // 1-indexed
                        ans[rIdx] = i + 1;
                    } else {
                        // No R robot to collide with directly; may bounce off wall
                        stackL.push(i);
                    }
                }
            }

            // Now match L robots pairwise via wall bounce
            // stackL has L robots in order they were pushed (decreasing position since we process L-R)
            // Wall bounce: L robot at x bounces at time x, becomes effective R at (x=0, time=x),
            // equivalent to R robot at -x at t=0. Two L robots at positions a < b bounce at times a, b.
            // They collide iff they're on the same parity and we can pair adjacent ones.
            // Sorted by position ascending: pairs (0,1), (2,3), ... in sorted L list.
            // But we need x[i] to have same parity for them to meet at an integer position.
            // Actually same-parity check: x[i] and x[j] (i < j) collide via wall iff x[i] + x[j] even? No.
            // They meet at position (x[i]+x[j])/2 - x[i] from wall after bounce?
            // Time of collision = (x[i] + x[j]) / 2. Valid collision iff x[i] + x[j] is even? No — they collide
            // regardless (position doesn't need to be integer). Just pair adjacent.
            int[] lList = new int[stackL.size()];
            int li = 0;
            while (!stackL.isEmpty()) lList[li++] = stackL.pop();
            // lList is in decreasing position order (stack was LIFO, pushed in increasing order)
            // Reverse to get increasing order
            for (int left = 0, right = li - 1; left < right; left++, right--) {
                int tmp = lList[left]; lList[left] = lList[right]; lList[right] = tmp;
            }
            // Pair adjacent with same parity of x+x (always collide: check x[i] + x[j] even? No constraint)
            // Actually: collision via wall: two L robots at a, b. Effective positions at t=0: -a, -b (going R).
            // They collide like two R robots going toward each other... no, both going R after bounce? That means
            // they never collide. Wait: a < b. Robot at a hits wall first (at time a), bounces R. Robot at b
            // is still going L. They meet: after time a, robot-a is going R (at position t-a), robot-b going L (at b-t).
            // Meet when t-a = b-t → t = (a+b)/2. Since (a+b)/2 > a always (b > a), valid.
            // So adjacent L robots DO collide (just pair them).
            for (int k = 0; k + 1 < li; k += 2) {
                ans[lList[k]] = lList[k + 1] + 1;
                ans[lList[k + 1]] = lList[k] + 1;
            }

            for (int i = 0; i < n; i++) {
                sb.append(ans[i]);
                if (i < n - 1) sb.append(' ');
            }
            sb.append('\n');
        }

        System.out.print(sb);
    }
}
