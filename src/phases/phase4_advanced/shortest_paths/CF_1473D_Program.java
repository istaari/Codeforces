package phases.phase4_advanced.shortest_paths;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1473D — Program
 * Link   : https://codeforces.com/problemset/problem/1473/D
 * Rating : 1700  |  Tags: prefix sums, implementation
 * Topic  : Phase 4: Advanced > Shortest Paths
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * A program consists of n operations, each either "+1" or "-1" on variable x.
 * Given queries: for each query (l, r), we run the program but SKIP operations
 * l..r (i.e., remove those operations). Find how many distinct starting values x_0
 * in [1, 10^9] produce a final value also in [1, 10^9].
 *
 * EXAMPLE
 * -------
 * Input:  n=4, ops=[+1,-1,+1,+1], q=2, queries: (2,3),(1,4)
 * Output: 10^9, 10^9-1
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Compute prefix sums p[i] = sum of first i operations. Total = p[n].
 * 2. When skipping [l,r], total effect = p[n] - (p[r] - p[l-1]) = p[n] - p[r] + p[l-1].
 * 3. But also need: x_0 + p[i] stays in valid range for all intermediate i?
 *    No - we only care about final value in [1, 10^9].
 * 4. Actually: the problem runs all operations except those in [l,r] in order.
 *    So: apply ops 1..l-1, skip l..r, apply r+1..n.
 *    Final value = x_0 + p[l-1] + (p[n] - p[r]).
 *    Let delta = p[l-1] + p[n] - p[r].
 *    We need: 1 <= x_0 + delta <= 10^9.
 *    So: 1 - delta <= x_0 <= 10^9 - delta.
 *    Intersect with x_0 in [1, 10^9].
 *    Count = max(0, min(10^9, 10^9 - delta) - max(1, 1 - delta) + 1).
 * 5. But also need all intermediate values to stay valid? Re-reading:
 *    only final value needs to be in [1, 10^9].
 *    Also starting x_0 must be in [1, 10^9].
 * 6. Actually we also need: during prefix ops 1..l-1, x_0 never goes out?
 *    No - only final value constraint, not intermediate.
 *    Hmm, but re-read: "find how many starting values x_0 make FINAL value in [1,10^9]"
 *    AND x_0 itself in [1,10^9]. So just the range calculation above.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n + q)
 * Space: O(n)
 * ============================================================
 */

public class CF_1473D_Program {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        String ops = br.readLine().trim();
        int q = Integer.parseInt(br.readLine().trim());

        long[] p = new long[n + 1]; // prefix sums
        for (int i = 1; i <= n; i++) {
            p[i] = p[i - 1] + (ops.charAt(i - 1) == '+' ? 1 : -1);
        }

        long MAXVAL = 1_000_000_000L;
        StringBuilder sb = new StringBuilder();

        while (q-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int l = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());

            // Delta when skipping [l,r]: apply p[l-1] then (p[n]-p[r])
            long delta = p[l - 1] + (p[n] - p[r]);

            // Need: 1 <= x_0 + delta <= MAXVAL AND 1 <= x_0 <= MAXVAL
            // x_0 in [1 - delta, MAXVAL - delta] intersected with [1, MAXVAL]
            long lo = Math.max(1L, 1L - delta);
            long hi = Math.min(MAXVAL, MAXVAL - delta);
            long count = Math.max(0L, hi - lo + 1);
            sb.append(count).append('\n');
        }

        System.out.print(sb);
    }
}
