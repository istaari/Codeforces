package phases.phase3_core_patterns.graph_fundamentals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1294D — MEX Maximizing
 * Link   : https://codeforces.com/problemset/problem/1294/D
 * Rating : 1500  |  Tags: greedy, hashing, math
 * Topic  : Phase 3: Core Patterns > Graph Fundamentals
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Start with an empty array. Given q queries: either add value x to array,
 * or find max MEX achievable by adding any multiple of a fixed value k to
 * each element. (Adding k*t to element makes it equivalent to its value mod k
 * being preserved, but can reach any value with same residue mod k.)
 *
 * EXAMPLE
 * -------
 * Input:  7 3
 *         +6
 *         +3
 *         ?
 *         +0
 *         ?
 *         +3
 *         ?
 * Output: 2 3 3
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Adding k*t to x changes x but preserves x mod k. So we can rearrange
 *    each element's "multiple of k" offset freely. A value v is achievable
 *    from element x if v ≡ x (mod k) and v >= 0.
 * 2. To maximize MEX: we want 0, 1, 2, ..., mex-1 to all be represented.
 *    For each target t, we need at least one element x with x mod k == t mod k
 *    available. Since multiple elements can cover same residue, but each element
 *    can only produce one value, we need at least one element per required t.
 * 3. Keep a count array cnt[r] = number of elements with value mod k == r (mod k).
 *    To have MEX = m, we need: for all t in {0, ..., m-1}, cnt[t mod k] >= ceil(t/k) + 1?
 *    No: each element can be shifted to reach any value with the same residue.
 *    Element with residue r can cover any of: r, r+k, r+2k, ...
 *    We need to cover 0, 1, ..., mex-1. For residue r, we need to cover r, r+k, r+2k, ...
 *    up to mex-1. If cnt[r] elements have residue r, they can cover min(cnt[r], ?) values.
 *    Specifically: elements with residue r can cover ceil((mex - r) / k) of the required values
 *    (those are r, r+k, ...). So cnt[r] >= floor((mex-1-r)/k)+1 = number of values in {0,..,mex-1} with residue r.
 * 4. MEX is the largest m such that for all r in 0..k-1: cnt[r] >= number_of_targets_with_residue_r_in_0..m-1.
 *    Equivalently: for each r, cnt[r] >= floor((m-r-1)/k)+1 = floor((m-r+k-1)/k).
 *    Increment MEX greedily: start mex=0. Try to increment mex one by one.
 *    At mex = m: residue r = m % k. We need cnt[r] >= floor(m/k) + 1.
 *    If so, increment mex.
 *
 * COMPLEXITY
 * ----------
 * Time : O(q * (MEX/k)) amortized = O(q + total_MEX)
 * Space: O(max_val / k)
 * ============================================================
 */

public class CF_1294D_MEXMaximizing {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int q = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // cnt[r] = number of elements with value % k == r
        HashMap<Integer, Integer> cnt = new HashMap<>();
        int mex = 0;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < q; i++) {
            String line = br.readLine().trim();
            if (line.charAt(0) == '+') {
                int x = Integer.parseInt(line.substring(1).trim());
                int r = x % k;
                cnt.merge(r, 1, Integer::sum);
                // Try to extend mex
                while (cnt.getOrDefault(mex % k, 0) > mex / k) {
                    mex++;
                }
            } else {
                // Query
                sb.append(mex).append('\n');
            }
        }

        System.out.print(sb);
    }
}
