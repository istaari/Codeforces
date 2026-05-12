package phases.phase1_foundations.c2_900rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1197A — DIY Wooden Ladder
 * Link   : https://codeforces.com/problemset/problem/1197/A
 * Rating : 900  |  Tags: greedy, sorting
 * Topic  : Phase 1: Foundations > Greedy & Sorting
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * You have n planks. You want to build a ladder with k steps. The ladder
 * needs 2 vertical planks and k horizontal rungs. Each horizontal rung
 * must be strictly shorter than both vertical planks. Find if it's possible
 * to choose 2 vertical planks and k horizontal planks satisfying this.
 *
 * EXAMPLE
 * -------
 * Input:  5 2
 *         1 2 3 4 5
 * Output: YES   (horizontals: 1,2; verticals: 3,4 or similar)
 *
 * Input:  3 1
 *         1 1 1
 * Output: NO   (need horizontal strictly shorter than both verticals)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Sort the planks in ascending order.
 * 2. The optimal strategy: use the k smallest planks as horizontals
 *    (to maximize remaining plank lengths for verticals).
 * 3. The (k+1)-th plank (index k, 0-based) defines the minimum rung length
 *    that must be exceeded by both verticals.
 * 4. Count planks strictly greater than planks[k-1] (0-indexed).
 *    Actually: we need verticals strictly greater than max horizontal = planks[k-1].
 *    Count planks at index k, k+1, ... that are strictly > planks[k-1].
 *    If there are at least 2 such planks, output YES.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1197A_DIYWoodenLadder {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());
            int[] a = new int[n];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) {
                a[i] = Integer.parseInt(st.nextToken());
            }
            Arrays.sort(a);
            // Need at least k + 2 planks; horizontals are a[0..k-1], max horizontal = a[k-1]
            // Need 2 planks strictly greater than a[k-1] among a[k..n-1]
            if (n < k + 2) {
                sb.append("NO\n");
                continue;
            }
            int maxHorizontal = a[k - 1];
            int verticalCount = 0;
            for (int i = k; i < n; i++) {
                if (a[i] > maxHorizontal) verticalCount++;
            }
            sb.append(verticalCount >= 2 ? "YES" : "NO").append('\n');
        }
        System.out.print(sb);
    }
}
