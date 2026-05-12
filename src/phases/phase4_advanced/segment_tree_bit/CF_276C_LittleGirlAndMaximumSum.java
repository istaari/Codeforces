package phases.phase4_advanced.segment_tree_bit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 276C — Little Girl and Maximum Sum
 * Link   : https://codeforces.com/problemset/problem/276/C
 * Rating : 1500  |  Tags: prefix sums, sorting, difference array
 * Topic  : Phase 4: Advanced > Segment Tree / BIT
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Array of n elements. Process q queries, each adding 1 to all elements in [l,r].
 * After all updates, you can rearrange the array elements to maximize the total sum
 * of array values multiplied by their "query frequency" (how many queries covered
 * each position). Output the maximum sum.
 *
 * EXAMPLE
 * -------
 * Input:  n=3, q=2, a=[1,2,3], queries: (1,2),(2,3)
 * Output: 12  (freq=[1,2,1], assign largest a value to highest freq)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Compute frequency of each position using a difference array for queries.
 * 2. Sort frequencies descending, sort array values descending.
 * 3. Pair largest frequency with largest value: total = sum(freq[i] * a[i]).
 *
 * COMPLEXITY
 * ----------
 * Time : O((n + q) log n)
 * Space: O(n)
 * ============================================================
 */

public class CF_276C_LittleGirlAndMaximumSum {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        long[] a = new long[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) a[i] = Long.parseLong(st.nextToken());

        long[] diff = new long[n + 2];
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int l = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());
            diff[l]++;
            diff[r + 1]--;
        }

        // Compute frequencies
        long[] freq = new long[n + 1];
        long cur = 0;
        for (int i = 1; i <= n; i++) {
            cur += diff[i];
            freq[i] = cur;
        }

        // Sort and multiply
        long[] freqArr = new long[n];
        for (int i = 0; i < n; i++) freqArr[i] = freq[i + 1];
        Arrays.sort(freqArr);
        Arrays.sort(a);

        long ans = 0;
        for (int i = 0; i < n; i++) {
            ans += a[i] * freqArr[i];
        }

        System.out.println(ans);
    }
}
