package phases.phase1_foundations.sorting_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1810A — Beautiful Sequence
 * Link   : https://codeforces.com/problemset/problem/1810/A
 * Rating : 1000  |  Tags: sortings, greedy, constructive
 * Topic  : Phase 1: Foundations > Sorting Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given an array a of n integers (not necessarily distinct). After sorting the array
 * in non-decreasing order, count the number of elements that are NOT equal to their
 * 1-indexed position. I.e., count positions i where sorted_a[i] != i.
 *
 * EXAMPLE
 * -------
 * Input:  4 / 2 3 1 4
 * Output: 0
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Sort the array.
 * 2. Count positions i (1-indexed) where a[i-1] != i.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1810A_BeautifulSequence {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            int[] a = new int[n];
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) a[i] = Integer.parseInt(st.nextToken());
            Arrays.sort(a);
            int count = 0;
            for (int i = 0; i < n; i++) {
                if (a[i] != i + 1) count++;
            }
            sb.append(count).append('\n');
        }
        System.out.print(sb);
    }
}
