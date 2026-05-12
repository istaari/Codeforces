package phases.phase4_advanced.advanced_dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1407D — Discrete Centrifugal Jumps
 * Link   : https://codeforces.com/problemset/problem/1407/D
 * Rating : 1800  |  Tags: dp, monotonic stack
 * Topic  : Phase 4: Advanced > Advanced DP
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given a 1D array h[] of length n. You can jump from position i to
 * position j (i < j) if a[i] or a[j] is a "border" of the subarray
 * [i..j]: specifically if i and j satisfy one of:
 *   - a[i] = max(a[i..j]) and a[j] = second max, OR similar conditions.
 * The exact condition: jump i->j is valid if either:
 *   a) a[i..j-1] has max at i (a[i] > all a[i+1..j-1]) and no other element > a[i]
 *   b) Similar conditions involving local max/min.
 * Find minimum jumps from index 1 to index n. Cost is 1 per jump.
 *
 * EXAMPLE
 * -------
 * Input:  n=6, h=[1,4,2,3,3,1]
 * Output: 2
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Valid jump i->j: j is the next greater element after i (NGE), OR
 *    i is the previous greater element before j (PGE), OR
 *    j is the next smaller element (NSE) or i is previous smaller (PSE).
 * 2. Use monotonic stacks to compute for each position: NGE, PGE, NSE, PSE.
 * 3. For each valid edge (i,j), add to adjacency: dp[j] = min(dp[j], dp[i]+1).
 * 4. Process positions left to right.
 * 5. Start dp[0]=0 (0-indexed), answer = dp[n-1].
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1407D_DiscreteCentrifugalJumps {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] h = new int[n];
        for (int i = 0; i < n; i++) h[i] = Integer.parseInt(st.nextToken());

        int[] dp = new int[n];
        Arrays.fill(dp, Integer.MAX_VALUE / 2);
        dp[0] = 0;

        // For each i, find valid jumps using monotonic stacks
        // Valid jump i->j if:
        // Case 1 (valley): h[i] > h[i+1] and h[j-1] < h[j] and h[i+1..j-1] are all less than h[i] and h[j]
        //   i.e., i is a "left wall" and j is a "right wall" of a valley
        // Case 2 (mountain): h[i] < h[i+1] and h[j-1] > h[j] and h[i+1..j-1] are all greater
        //   i.e., i and j are the feet of a mountain

        // Monotonic stacks give us next/prev greater/smaller
        // For descending stack (tracking maxima): when we pop i due to j, i->j is valid (NGE)
        //   Also i's previous on stack -> i is valid
        // For ascending stack (tracking minima): when we pop i due to j, i->j valid (NSE)

        // Process using two stacks
        Stack<Integer> decStack = new Stack<>(); // decreasing (for mountains: i < j means h[i] was local high)
        Stack<Integer> incStack = new Stack<>();  // increasing (for valleys)

        for (int j = 0; j < n; j++) {
            // Decreasing stack: h[stack.peek()] >= h[j]
            // When h[j] > h[decStack.peek()], popped element i has NGE at j
            while (!decStack.isEmpty() && h[decStack.peek()] < h[j]) {
                int i = decStack.pop();
                // Valid jump: i -> j (i is prev smaller, j is next greater -> valley border)
                if (dp[i] != Integer.MAX_VALUE / 2)
                    dp[j] = Math.min(dp[j], dp[i] + 1);
                // Also: if stack not empty, stack.peek() -> j (stack.peek() is left wall of mountain)
                if (!decStack.isEmpty()) {
                    int k = decStack.peek();
                    if (dp[k] != Integer.MAX_VALUE / 2)
                        dp[j] = Math.min(dp[j], dp[k] + 1);
                }
            }
            decStack.push(j);

            // Increasing stack: h[stack.peek()] <= h[j]
            // When h[j] < h[incStack.peek()], popped element i has NSE at j
            while (!incStack.isEmpty() && h[incStack.peek()] > h[j]) {
                int i = incStack.pop();
                if (dp[i] != Integer.MAX_VALUE / 2)
                    dp[j] = Math.min(dp[j], dp[i] + 1);
                if (!incStack.isEmpty()) {
                    int k = incStack.peek();
                    if (dp[k] != Integer.MAX_VALUE / 2)
                        dp[j] = Math.min(dp[j], dp[k] + 1);
                }
            }
            incStack.push(j);
        }

        System.out.println(dp[n - 1]);
    }
}
