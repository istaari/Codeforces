package phases.phase3_core_patterns.graph_fundamentals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1324D — Pair of Topics
 * Link   : https://codeforces.com/problemset/problem/1324/D
 * Rating : 1400  |  Tags: sorting, binary search, two pointers
 * Topic  : Phase 3: Core Patterns > Graph Fundamentals
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given two arrays a and b of length n. Count pairs (i,j) with i < j such
 * that a[i] + a[j] > b[i] + b[j].
 *
 * EXAMPLE
 * -------
 * Input:  4
 *         6 8 1 3
 *         3 2 5 2
 * Output: 3
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Transform: let c[i] = a[i] - b[i]. Then a[i]+a[j] > b[i]+b[j] iff c[i]+c[j] > 0.
 * 2. Sort c. Count pairs (i,j) with i < j and c[i]+c[j] > 0.
 * 3. Use two pointers: for each right pointer r, find the smallest l such that
 *    c[l] + c[r] > 0. Then add (r - l) to the count.
 *    Since c is sorted, as r increases, c[r] increases, so the minimum valid l decreases.
 *    Two pointers work in O(n).
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1324D_PairOfTopics {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());

        long[] a = new long[n], b = new long[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) a[i] = Long.parseLong(st.nextToken());
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) b[i] = Long.parseLong(st.nextToken());

        long[] c = new long[n];
        for (int i = 0; i < n; i++) c[i] = a[i] - b[i];
        Arrays.sort(c);

        // Count pairs (i,j) i<j with c[i]+c[j] > 0
        // Two pointers: l=0, r=n-1
        long ans = 0;
        int l = 0, r = n - 1;
        while (l < r) {
            if (c[l] + c[r] > 0) {
                // All pairs (l, r), (l+1, r), ..., (r-1, r) are valid (since c is sorted)
                ans += (r - l);
                r--;
            } else {
                l++;
            }
        }

        System.out.println(ans);
    }
}
