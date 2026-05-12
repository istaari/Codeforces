package phases.phase2_toolkit.two_pointers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1520D — Same Differences
 * Link   : https://codeforces.com/problemset/problem/1520/D
 * Rating : 1200  |  Tags: hashing, math, frequency map
 * Topic  : Phase 2: Toolkit > Two Pointers
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given array a of n integers. Count pairs (i, j) where i < j and
 * a[i] - i == a[j] - j  (equivalently a[i] - a[j] == i - j).
 *
 * EXAMPLE
 * -------
 * Input:  6
 *         3 5 1 4 6 6
 * Output: 1
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Two elements at positions i and j (i<j) form a valid pair iff
 *    a[i] - i == a[j] - j. Define key[i] = a[i] - i.
 * 2. Count pairs with the same key value. For each group of size c
 *    with the same key, the number of pairs is c*(c-1)/2.
 * 3. Use a HashMap: for each i, add freq[key[i]] to the answer
 *    (counts how many previous elements have the same key), then
 *    increment freq[key[i]].
 * 4. This is the classic "count pairs before updating" pattern.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1520D_SameDifferences {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            StringTokenizer st = new StringTokenizer(br.readLine());
            HashMap<Long, Long> freq = new HashMap<>();
            long ans = 0;

            for (int i = 0; i < n; i++) {
                long val = Long.parseLong(st.nextToken());
                long key = val - i; // a[i] - i
                // Count how many previous elements have same key
                ans += freq.getOrDefault(key, 0L);
                freq.merge(key, 1L, Long::sum);
            }

            sb.append(ans).append('\n');
        }

        System.out.print(sb);
    }
}
