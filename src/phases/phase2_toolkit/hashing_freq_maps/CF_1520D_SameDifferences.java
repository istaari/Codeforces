package phases.phase2_toolkit.hashing_freq_maps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1520D — Same Differences
 * Link   : https://codeforces.com/problemset/problem/1520/D
 * Rating : 1200  |  Tags: hashing, frequency map, math
 * Topic  : Phase 2: Toolkit > Hashing & Frequency Maps
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given an array a of n integers. Count the number of pairs (i, j)
 * with i < j such that a[i] - i == a[j] - j.
 *
 * EXAMPLE
 * -------
 * Input:  6
 *         3 5 1 4 6 6
 * Output: 1
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Observation: a[i] - i == a[j] - j is equivalent to saying elements
 *    lie on the same "diagonal" when you shift: define key[i] = a[i] - i.
 * 2. Count pairs with equal key values. Use a HashMap: for each index i,
 *    the answer increases by freq[key[i]] (number of previous elements
 *    with the same key), then increment freq[key[i]].
 * 3. This is the standard "count pairs before updating" hash map pattern.
 *    Works in O(n) time.
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
                long key = val - i;
                ans += freq.getOrDefault(key, 0L);
                freq.merge(key, 1L, Long::sum);
            }

            sb.append(ans).append('\n');
        }

        System.out.print(sb);
    }
}
