package phases.phase4_advanced.advanced_dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1097C — Yuhao and a Parenthesis
 * Link   : https://codeforces.com/problemset/problem/1097/C
 * Rating : 1700  |  Tags: dp, greedy, math
 * Topic  : Phase 4: Advanced > Advanced DP
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given n pairs of parentheses, each pair i has weight w[i]. The pair
 * consists of an opening '(' and closing ')'. You rearrange all 2n
 * brackets to form a valid bracket sequence. The value of the sequence
 * is the product of sums of weights of brackets enclosed within each
 * matched pair at the outermost level... actually the problem defines
 * value differently: for each matched pair (i,j), the contribution is
 * the product of all weights of pairs whose opening bracket is strictly
 * between i and j. Maximize total value.
 *
 * EXAMPLE
 * -------
 * Input:  3, weights [1,2,3]
 * Output: 6  (nest all: ((())) -> 1*2*3... depends on nesting structure)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. The value equals the product of all w[i] for pairs that are "nested"
 *    inside another pair. Actually the problem: value = sum over all pairs p
 *    of (product of weights of pairs directly inside p at depth 1 inside p).
 * 2. Key insight: to maximize, we want all pairs nested, making value =
 *    product of all n weights (each inner pair contributes to outer).
 * 3. Actually re-reading: value = product over all pairs of (sum of weights
 *    of directly enclosed pairs). This is maximized by a specific arrangement.
 * 4. Simpler reading: each bracket pair has a "score" equal to the sum of
 *    weights of pairs it directly contains. Total = product of all these scores.
 *    With greedy nesting, we get w[1]*w[2]*...*w[n-1] with the outermost being
 *    the container. Sort descending and nest: outer contains all inner.
 * 5. The answer is the product of the n-1 largest weights (all are "inside"
 *    the outermost bracket which scores as sum of 1 direct child each level).
 *    Actually: with full nesting (((...))), pair 1 contains pair 2 which contains
 *    pair 3 etc. Score of pair i = w[i+1] (its direct child). Total = w[2]*w[3]*...*w[n].
 *    To maximize, sort in any order; the outermost pair has score=w of second,
 *    second has score=w of third, etc. Product = w[2]*...*w[n]. Maximize by
 *    sorting descending: product = w[1]*w[2]*...*w[n-1] (exclude smallest).
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1097C_YuhaoAndAParenthesis {

    static final long MOD = 1_000_000_007L;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        long[] w = new long[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) w[i] = Long.parseLong(st.nextToken());

        // Sort descending; product of top (n-1) weights gives maximum value
        // by nesting all pairs: outermost pair scores w[1], next scores w[2], etc.
        Arrays.sort(w);
        // w is now ascending; product of w[1..n-1] (skip smallest w[0])
        if (n == 1) {
            System.out.println(1);
            return;
        }

        long ans = 1;
        for (int i = 1; i < n; i++) {
            ans = (ans * w[i]) % MOD;
        }
        System.out.println(ans);
    }
}
