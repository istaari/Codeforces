package phases.phase3_core_patterns.graph_fundamentals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1272D — Remove One Element
 * Link   : https://codeforces.com/problemset/problem/1272/D
 * Rating : 1500  |  Tags: dp, implementation
 * Topic  : Phase 3: Core Patterns > Graph Fundamentals
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given array a of n integers. Remove exactly one element to maximize
 * the length of the longest strictly increasing subarray (contiguous).
 *
 * EXAMPLE
 * -------
 * Input:  4
 *         3 1 2 3
 * Output: 3
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Precompute:
 *    - inc[i] = length of longest increasing subarray ending at i
 *      (inc[i] = inc[i-1]+1 if a[i]>a[i-1], else 1).
 *    - dec[i] = length of longest increasing subarray starting at i
 *      (dec[i] = dec[i+1]+1 if a[i]<a[i+1], else 1).
 * 2. For each element i (0-indexed), consider removing it:
 *    - Contribution = inc[i-1] + dec[i+1] if a[i-1] < a[i+1].
 *    - But capped at the total positions: length of merged subarray.
 *    Also consider dec[i+1] alone (remove i, take right part) and inc[i-1] alone.
 * 3. Also the answer can be just the maximum of inc[] or dec[] (remove some element
 *    not in the best subarray). But to be safe, take max over all i.
 * 4. Answer = max over all i of:
 *    - dec[i+1] (remove element i, use right increasing run).
 *    - inc[i-1] (remove element i, use left increasing run).
 *    - inc[i-1] + dec[i+1] if a[i-1] < a[i+1] (bridge left and right by removing i).
 *    Also answer >= 1 always.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1272D_RemoveOneElement {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        int[] a = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) a[i] = Integer.parseInt(st.nextToken());

        // inc[i] = length of increasing subarray ending at i
        int[] inc = new int[n];
        inc[0] = 1;
        for (int i = 1; i < n; i++) {
            inc[i] = (a[i] > a[i - 1]) ? inc[i - 1] + 1 : 1;
        }

        // dec[i] = length of increasing subarray starting at i
        int[] dec = new int[n];
        dec[n - 1] = 1;
        for (int i = n - 2; i >= 0; i--) {
            dec[i] = (a[i] < a[i + 1]) ? dec[i + 1] + 1 : 1;
        }

        int ans = 1;
        for (int i = 0; i < n; i++) {
            // Remove element i
            // Left part ends at i-1, right part starts at i+1
            int left = (i > 0) ? inc[i - 1] : 0;
            int right = (i < n - 1) ? dec[i + 1] : 0;
            int merged = left + right;
            // Can only bridge if a[i-1] < a[i+1]
            if (i > 0 && i < n - 1 && a[i - 1] >= a[i + 1]) {
                merged = Math.max(left, right);
            }
            ans = Math.max(ans, merged);
            ans = Math.max(ans, Math.max(left, right));
        }

        System.out.println(ans);
    }
}
