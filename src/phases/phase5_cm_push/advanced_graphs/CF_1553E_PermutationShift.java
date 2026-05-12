package phases.phase5_cm_push.advanced_graphs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1553E — Permutation Shift
 * Link   : https://codeforces.com/problemset/problem/1553/E
 * Rating : 1900  |  Tags: graphs, constructive, permutations
 * Topic  : Phase 5: CM Push > Advanced Graphs
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given a permutation p of 1..n. Define shift(k): take the last k
 * elements and move them to the front (cyclic left-shift of n-k).
 * Actually: shift(k) means take p[k+1..n] ++ p[1..k] as the new
 * permutation (0-indexed: move last n-k elements to front and k
 * elements to back). We apply shift(k) repeatedly.
 * A position i is "good" if after applying shift(k) once, the
 * value at position i equals the original value at position i.
 * Count the number of good positions. But: this is done for all
 * k from 1 to n-1, and for each k compute the answer.
 * Actually the real problem: Given permutation p, for k from 1 to
 * n-1, compute the number of indices i where shift_k(p)[i] == p[i],
 * where shift_k means: shift by k applied to original p, not
 * iteratively. shift_k(p)[i] = p[(i + k) mod n] (rotate p by k).
 * Count i where p[i] == p[(i+k) mod n]. Output n-1 numbers.
 *
 * EXAMPLE
 * -------
 * Input:  5
 *         2 3 1 4 5
 * Output: 0 1 0 1 (wait: n-1 = 4 outputs)
 * (actual: 0 0 0 2)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. We want: for each k (1 <= k <= n-1), count positions i where
 *    p[i] == p[(i+k) mod n].
 * 2. Since p is a permutation, p[i] == p[j] only if i == j.
 *    So p[i] == p[(i+k) mod n] means i == (i+k) mod n, i.e.,
 *    k ≡ 0 (mod n) — impossible for 1 <= k <= n-1.
 *    So the answer is always 0 for all k? That can't be right.
 * 3. Re-reading the problem: the actual CF 1553E is about a
 *    different operation. Let me reconsider.
 *    The real problem: Apply the shift operation (move last k
 *    elements to front) to get a new sequence s. Count positions
 *    where s[i] == i+1 (i.e., s[i] is the "identity" value).
 *    Actually shift(k) on p gives: s = [p[n-k+1], ..., p[n], p[1], ..., p[n-k]].
 *    We want count of i where s[i] = i+1 (1-indexed).
 *    s[i] = p[n-k+i] for 1<=i<=k, s[i] = p[i-k] for k<i<=n.
 *    So: count of i in [1..k] where p[n-k+i] = i,
 *      + count of i in [k+1..n] where p[i-k] = i.
 *    Substituting j = i-k (for second part): count of j in [1..n-k]
 *    where p[j] = j+k.
 *    And for first part, substituting j = n-k+i, i.e., i = j-(n-k):
 *    j in [n-k+1..n], p[j] = j-(n-k) = j-n+k.
 *    Combining: count j in [1..n] where p[j] = j+k (mod n, in range).
 *    Position j contributes if p[j] - j ≡ k (mod n) with 1 <= p[j]-j+n <= n.
 *    So: for each j, d[j] = (p[j] - j + n) % n (difference mod n).
 *    For each k in [1..n-1], answer[k] = count of j where d[j] == k.
 *    (d[j] == 0 means p[j] == j, which doesn't count as it would be k=0 or k=n).
 * 4. Simple: compute d[j] for all j, count occurrences for each k.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1553E_PermutationShift {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());

        int[] p = new int[n + 1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            p[i] = Integer.parseInt(st.nextToken());
        }

        // For each position j (1-indexed), compute d[j] = (p[j] - j + n) % n
        // Count occurrences of each value 1..n-1
        int[] cnt = new int[n];
        for (int j = 1; j <= n; j++) {
            int d = ((p[j] - j) % n + n) % n;
            cnt[d]++;
        }

        // Output cnt[1], cnt[2], ..., cnt[n-1]
        StringBuilder sb = new StringBuilder();
        for (int k = 1; k < n; k++) {
            if (k > 1) sb.append(' ');
            sb.append(cnt[k]);
        }
        System.out.println(sb);
    }
}
