package phases.phase1_foundations.c3_1000rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1350B — Arithmetic Progressions
 * Link   : https://codeforces.com/problemset/problem/1350/B
 * Rating : 1000  |  Tags: sorting, math
 * Topic  : Phase 1: Foundations > Sorting & Math
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given an array of n integers (not necessarily sorted). Find the minimum
 * number of elements to add to make the array an arithmetic progression.
 * The added elements can be any integers.
 *
 * EXAMPLE
 * -------
 * Input:  2 / 1 3
 * Output: 1   (add 2: gives 1 2 3)
 *
 * Input:  3 / 1 3 7
 * Output: 3   (gcd of diffs is 2: 1,3,5,7 needs 3,5 → 2 additions? Actually gcd(2,4)=2, span=6, total terms=4, missing=4-3=1? Let me recount: 3-1=2, 7-3=4, gcd=2, total=(7-1)/2+1=4 terms, have 3 → add 1)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Sort the array. Compute differences between consecutive elements.
 * 2. Compute GCD of all differences. This is the common difference d of the AP.
 * 3. Special case: if all differences are 0 (all equal), answer is 0.
 * 4. Total terms in AP = (a[n-1] - a[0]) / d + 1.
 * 5. Answer = total_terms - n (existing terms already in AP).
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log n + n log(max_val))
 * Space: O(n)
 * ============================================================
 */

public class CF_1350B_ArithmeticProgressions {

    static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            long[] a = new long[n];
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) a[i] = Long.parseLong(st.nextToken());
            Arrays.sort(a);
            if (n == 1) {
                sb.append(0).append('\n');
                continue;
            }
            long g = 0;
            for (int i = 1; i < n; i++) {
                g = gcd(g, a[i] - a[i - 1]);
            }
            if (g == 0) {
                // All elements equal
                sb.append(0).append('\n');
            } else {
                long totalTerms = (a[n - 1] - a[0]) / g + 1;
                sb.append(totalTerms - n).append('\n');
            }
        }
        System.out.print(sb);
    }
}
