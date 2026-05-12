package phases.phase4_advanced.advanced_dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1312E — Array Shrinking
 * Link   : https://codeforces.com/problemset/problem/1312/E
 * Rating : 1800  |  Tags: dp, interval dp
 * Topic  : Phase 4: Advanced > Advanced DP
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given array a[1..n]. Operation: if a[i] == a[i+1], merge them into one
 * element with value a[i]+1. Repeat until no more merges possible.
 * Find the minimum possible length of the final array.
 *
 * EXAMPLE
 * -------
 * Input:  n=4, a=[1,1,2,2]
 * Output: 1  (1,1->2; 2,2->3; 2,3... wait: 1,1->2; now array is [2,2,2]; 2,2->3; [3,2]; done. length 2)
 *         Actually [1,1,2,2]: first merge a[1]=a[2]=1 -> [2,2,2]; merge a[1]=a[2]=2 -> [3,2]; done. Length 2.
 *         Or: merge a[3]=a[4]=2 -> [1,1,3]; can't merge. Or a[2]=a[3]? 1!=2. Min = 2? Let me recheck.
 *         [1,1,2,2]: try a[3..4]: [1,1,3]; can merge a[1..2]: [2,3]. Length 2.
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Interval DP: val[l][r] = the value if a[l..r] can be merged into a single element,
 *    or -1 if it cannot.
 * 2. Base: val[i][i] = a[i].
 * 3. Transition: val[l][r] = val[l][m]+1 if val[l][m] == val[m+1][r] for some split m.
 *    (Both halves collapse to same value, merge gives value+1.)
 * 4. After filling val[][], do a DP for minimum array length:
 *    dp[i] = min final length of a[1..i].
 *    dp[0] = 0; dp[i] = min over j<i of (dp[j] + 1) if val[j+1][i] != -1.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n^3) for interval DP
 * Space: O(n^2)
 * ============================================================
 */

public class CF_1312E_ArrayShrinking {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] a = new int[n + 1];
        for (int i = 1; i <= n; i++) a[i] = Integer.parseInt(st.nextToken());

        // val[l][r]: value if subarray a[l..r] can merge to one element, else -1
        int[][] val = new int[n + 1][n + 1];
        for (int[] row : val) Arrays.fill(row, -1);

        // Base cases
        for (int i = 1; i <= n; i++) val[i][i] = a[i];

        // Fill by length
        for (int len = 2; len <= n; len++) {
            for (int l = 1; l + len - 1 <= n; l++) {
                int r = l + len - 1;
                for (int m = l; m < r; m++) {
                    if (val[l][m] != -1 && val[m + 1][r] != -1 && val[l][m] == val[m + 1][r]) {
                        val[l][r] = val[l][m] + 1;
                        break; // found valid split
                    }
                }
            }
        }

        // DP for minimum final length
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE / 2);
        dp[0] = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] != Integer.MAX_VALUE / 2 && val[j + 1][i] != -1) {
                    dp[i] = Math.min(dp[i], dp[j] + 1);
                }
            }
        }

        System.out.println(dp[n]);
    }
}
