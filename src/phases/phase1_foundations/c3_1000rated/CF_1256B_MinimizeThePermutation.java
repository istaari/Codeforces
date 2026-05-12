package phases.phase1_foundations.c3_1000rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1256B — Minimize the Permutation
 * Link   : https://codeforces.com/problemset/problem/1256/B
 * Rating : 1000  |  Tags: greedy
 * Topic  : Phase 1: Foundations > Greedy
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given a permutation of 1..n. In operation i (1-indexed), you may swap
 * position i with position i-1 if a[i] < a[i-1] (bubble-sort-like). There
 * are exactly n-1 operations (i from 2 to n). Find the lexicographically
 * smallest permutation achievable.
 *
 * EXAMPLE
 * -------
 * Input:  5 / 5 3 1 4 2
 * Output: 1 3 4 2 5
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. For position i (1-indexed, operation i), we can move element at position i
 *    leftward as far as possible using the available swap at step i.
 * 2. Greedy: for each position from left to right, find the minimum element
 *    that can be moved to this position (within reach via remaining operations).
 * 3. Each element at index j can be moved to index i if j - i swaps are available
 *    (we have n - j remaining operations starting from op j+1 to n-1).
 * 4. Simulate: track available swaps per position and bubble elements.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n^2) in worst case
 * Space: O(n)
 * ============================================================
 */

public class CF_1256B_MinimizeThePermutation {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            int[] a = new int[n];
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) a[i] = Integer.parseInt(st.nextToken());

            // available[i] = number of times element at index i can be moved left
            // Element at index i can participate in operations i+1, i+2, ..., n-1
            // So max leftward moves for element originally at index i = i (it can be swapped left i times in total by ops 1..i)
            // Simulate greedily: for each position i from 0 to n-1,
            // find the minimum in a[i..min(i+swaps, n-1)] and bubble it to i.
            int[] swaps = new int[n];
            for (int i = 0; i < n; i++) swaps[i] = i; // element at index i has i potential left-swaps

            for (int i = 0; i < n; i++) {
                // Find minimum in a[i], using available swaps
                int minIdx = i;
                int stepsAvailable = swaps[i];
                for (int j = i + 1; j <= Math.min(i + stepsAvailable, n - 1); j++) {
                    if (a[j] < a[minIdx]) minIdx = j;
                    // element at j needs j-i swaps to reach i; check if it has enough
                    // actually swaps[j] tells how many left moves element at j can still do
                }
                // Re-check: element at minIdx needs (minIdx - i) moves leftward
                // Use proper check: find min element reachable at position i
                minIdx = i;
                for (int j = i + 1; j < n; j++) {
                    if (a[j] < a[minIdx] && swaps[j] >= j - i) {
                        minIdx = j;
                    }
                }
                // Bubble a[minIdx] to position i
                for (int j = minIdx; j > i; j--) {
                    int tmp = a[j]; a[j] = a[j-1]; a[j-1] = tmp;
                    swaps[j]--; // element that moved right lost a potential swap
                }
            }

            for (int i = 0; i < n; i++) {
                sb.append(a[i]);
                if (i < n - 1) sb.append(' ');
            }
            sb.append('\n');
        }
        System.out.print(sb);
    }
}
