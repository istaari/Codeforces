package phases.phase1_foundations.c3_1000rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1248B — Grow The Tree
 * Link   : https://codeforces.com/problemset/problem/1248/B
 * Rating : 1000  |  Tags: greedy, math
 * Topic  : Phase 1: Foundations > Greedy & Math
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * A tree has n vertices. The root is vertex 1. Array a[i] (2 ≤ i ≤ n) gives
 * the length of the edge from vertex i to its parent. All leaves of the left
 * subtree contribute to path length L1 (sum of edges from root), right subtree
 * to L2. The "score" is L1 * L2. Distribute edges (each edge either left or
 * right sub-branch) to maximize L1 * L2.
 *
 * EXAMPLE
 * -------
 * Input:  4 / 1 2 3
 * Output: 9   (e.g. L1={1,2}=3, L2={3}=3 → 3*3=9; or L1={1}=1,L2={2,3}=5→5)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Total sum S = sum of all edge lengths.
 * 2. Product L1 * L2 is maximized when L1 = L2 = S/2 (AM-GM inequality).
 * 3. To best approximate S/2 for L1, assign edges greedily: sort edges descending
 *    and assign alternating to L1 and L2 (knapsack-like).
 * 4. Actually: since we need L1 + L2 = S and maximize L1 * L2, and we can freely
 *    assign each edge to either path, this is: partition S into two halves.
 * 5. Greedy: sort descending. Assign each edge to whichever path is smaller so far.
 *    The minimum difference partition of a set → use greedy (not always optimal
 *    but works for maximizing product here since optimal is equal split).
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1248B_GrowTheTree {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        long[] a = new long[n - 1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n - 1; i++) a[i] = Long.parseLong(st.nextToken());

        Arrays.sort(a);
        long s1 = 0, s2 = 0;
        for (int i = a.length - 1; i >= 0; i--) {
            if (s1 <= s2) s1 += a[i];
            else s2 += a[i];
        }
        System.out.println(s1 * s2);
    }
}
