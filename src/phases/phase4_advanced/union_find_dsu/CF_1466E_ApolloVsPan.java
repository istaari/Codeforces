package phases.phase4_advanced.union_find_dsu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1466E — Apollo versus Pan
 * Link   : https://codeforces.com/problemset/problem/1466/E
 * Rating : 1800  |  Tags: DSU, bitmask, bipartite matching
 * Topic  : Phase 4: Advanced > Union Find / DSU
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Bipartite graph with n left nodes and m right nodes. Left nodes have values a[],
 * right nodes have values b[]. Matching: each left matched to a right.
 * Two matches (i, j) and (k, l) conflict if a[i] XOR b[j] != a[k] XOR b[l]
 * (they have different XOR values). Find maximum matching where all matched pairs
 * have the same XOR value (all XORs equal). Maximize matching size.
 *
 * EXAMPLE
 * -------
 * Input:  n=2, m=3, a=[1,2], b=[3,1,2]
 * Output: 2  (match 1->3 XOR=2, 2->1 XOR=3... or match to same XOR value)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. For each possible XOR value x: count how many left nodes can achieve XOR x
 *    (a[i] XOR x = b[j] exists in right nodes) and how many right nodes can achieve x.
 * 2. For XOR value x, count_left_x = number of i where (a[i] XOR x) is in b[].
 *    count_right_x = number of distinct b[j] values that are a[i] XOR x for some i.
 *    But we want maximum matching, so min(count_left_x, count_right_x) available.
 * 3. Iterate over all XOR values and find the one maximizing min(left_matches, right_matches).
 * 4. XOR values range from 0 to max(a,b). Store b[] in a HashSet.
 *    For each i and each b[j] value, XOR value = a[i] XOR b[j].
 *    But that's O(n*m). Instead: for each a[i], check which b[j]s it can pair with.
 *    Collect all possible XOR values from the actual edges.
 *
 * COMPLEXITY
 * ----------
 * Time : O((n+m) * max_bits) or O(n*m)
 * Space: O(n+m)
 * ============================================================
 */

public class CF_1466E_ApolloVsPan {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[] a = new int[n], b = new int[m];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) a[i] = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++) b[i] = Integer.parseInt(st.nextToken());

        // For each XOR value x, count how many a[i] have a[i] XOR x in b[],
        // and how many distinct b[j] values participate.
        Set<Integer> bSet = new HashSet<>();
        for (int x : b) bSet.add(x);

        // Map: XOR value -> set of left nodes that can achieve this XOR
        Map<Integer, Integer> leftCount = new HashMap<>();
        // Map: XOR value -> set of right values that achieve this XOR
        Map<Integer, Set<Integer>> rightVals = new HashMap<>();

        for (int i = 0; i < n; i++) {
            for (int bv : bSet) {
                int xor = a[i] ^ bv;
                leftCount.merge(xor, 1, Integer::sum);
                rightVals.computeIfAbsent(xor, k -> new HashSet<>()).add(bv);
            }
        }

        int ans = 0;
        for (Map.Entry<Integer, Integer> e : leftCount.entrySet()) {
            int xor = e.getKey();
            int lc = e.getValue();
            int rc = rightVals.get(xor).size();
            ans = Math.max(ans, Math.min(lc, rc));
        }

        System.out.println(ans);
    }
}
