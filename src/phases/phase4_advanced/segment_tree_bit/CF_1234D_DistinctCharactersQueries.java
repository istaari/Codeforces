package phases.phase4_advanced.segment_tree_bit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1234D — Distinct Characters Queries
 * Link   : https://codeforces.com/problemset/problem/1234/D
 * Rating : 1500  |  Tags: BIT, implementation, strings
 * Topic  : Phase 4: Advanced > Segment Tree / BIT
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given string s. Process q queries of type (l, r): count distinct characters
 * in s[l..r] (1-indexed). Output answer for each query.
 *
 * EXAMPLE
 * -------
 * Input:  s="abcab", queries: (1,5),(1,3),(2,4)
 * Output: 3, 3, 3
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. For each character c in 'a'..'z', precompute sorted list of positions where c appears.
 * 2. For a query (l, r): character c is present if there's any occurrence in [l, r].
 *    Use binary search on sorted positions list: if lower_bound(l) <= r, then c is present.
 * 3. Count distinct = number of characters with at least one occurrence in [l, r].
 * 4. Time per query: O(26 * log n).
 *
 * COMPLEXITY
 * ----------
 * Time : O((n + q) log n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1234D_DistinctCharactersQueries {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine().trim();
        int n = s.length();

        @SuppressWarnings("unchecked")
        List<Integer>[] pos = new List[26];
        for (int i = 0; i < 26; i++) pos[i] = new ArrayList<>();
        for (int i = 0; i < n; i++) pos[s.charAt(i) - 'a'].add(i + 1); // 1-indexed

        int q = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < q; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int l = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());
            int count = 0;
            for (int c = 0; c < 26; c++) {
                List<Integer> p = pos[c];
                if (p.isEmpty()) continue;
                // Binary search for first position >= l
                int lo = 0, hi = p.size();
                while (lo < hi) {
                    int mid = (lo + hi) / 2;
                    if (p.get(mid) < l) lo = mid + 1;
                    else hi = mid;
                }
                if (lo < p.size() && p.get(lo) <= r) count++;
            }
            sb.append(count).append('\n');
        }

        System.out.print(sb);
    }
}
