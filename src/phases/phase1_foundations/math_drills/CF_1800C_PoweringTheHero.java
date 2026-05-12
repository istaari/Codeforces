package phases.phase1_foundations.math_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1800C — Powering the Hero
 * Link   : https://codeforces.com/problemset/problem/1800/C
 * Rating : 1000  |  Tags: greedy, data structures, math
 * Topic  : Phase 1: Foundations > Math Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * You have a deck of n cards. Each card is either a "hero card" (value 0) or a
 * "power card" (value > 0). You process cards in order. When you encounter a power
 * card, add its value to a max-heap. When you encounter a hero card, pop the maximum
 * value from the heap (if any) and add it to the total score. Maximize total score.
 *
 * EXAMPLE
 * -------
 * Input:  4 / 0 3 0 2
 * Output: 5
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Process cards left to right.
 * 2. When card value > 0: add to a max-heap (priority queue descending).
 * 3. When card value == 0 (hero card): if heap is non-empty, pop the maximum and add to score.
 * 4. Output total score.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1800C_PoweringTheHero {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            StringTokenizer st = new StringTokenizer(br.readLine());
            PriorityQueue<Long> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
            long score = 0;
            for (int i = 0; i < n; i++) {
                long x = Long.parseLong(st.nextToken());
                if (x > 0) {
                    maxHeap.offer(x);
                } else {
                    // hero card: take max power available
                    if (!maxHeap.isEmpty()) {
                        score += maxHeap.poll();
                    }
                }
            }
            sb.append(score).append('\n');
        }
        System.out.print(sb);
    }
}
