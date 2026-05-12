package phases.phase3_core_patterns.advanced_greedy_constructive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;


/*
 * ============================================================
 * CF 1520F2 — Guess the K-th Zero (Hard version)
 * Link   : https://codeforces.com/problemset/problem/1520/F2
 * Rating : 1700  |  Tags: binary search, interactive
 * Topic  : Phase 3: Core Patterns > Advanced Greedy & Constructive
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Interactive problem. Hidden array a of n integers (0 or 1). You can query
 * "? l r" to get the sum of a[l..r]. Find the k-th zero (k-th position where a = 0)
 * using at most ceil(log2(n)) + 1 queries.
 *
 * EXAMPLE
 * -------
 * Query ? 1 n → get total ones → zeros = n - total_ones.
 * Binary search for k-th zero.
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Binary search on position. Maintain lo=1, hi=n.
 * 2. At each step, query ? lo mid. Count zeros in [lo, mid] = (mid - lo + 1) - query_result.
 * 3. If zeros in [lo, mid] >= k: search in [lo, mid], k unchanged.
 *    Else: k -= zeros_in[lo,mid], search in [mid+1, hi].
 * 4. Stop when lo == hi: output lo.
 * 5. Budget: ceil(log2(n)) queries for binary search. Within budget.
 *
 * COMPLEXITY
 * ----------
 * Time : O(log n) queries
 * Space: O(1)
 * ============================================================
 */

public class CF_1520F2_GuessTheKthZeroHard {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static PrintWriter pw = new PrintWriter(System.out);

    static long query(int l, int r) throws IOException {
        pw.println("? " + l + " " + r);
        pw.flush();
        return Long.parseLong(br.readLine().trim());
    }

    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken()); // number of queries (=k for each query)

        StringBuilder sb = new StringBuilder();
        for (int qi = 0; qi < q; qi++) {
            int k = Integer.parseInt(br.readLine().trim());

            // Binary search for k-th zero
            int lo = 1, hi = n;
            int kRemaining = k;
            while (lo < hi) {
                int mid = (lo + hi) / 2;
                long ones = query(lo, mid);
                long zeros = (mid - lo + 1) - ones;
                if (zeros >= kRemaining) {
                    hi = mid;
                } else {
                    kRemaining -= zeros;
                    lo = mid + 1;
                }
            }
            // lo is the answer
            pw.println("! " + lo);
            pw.flush();
            // Read "Correct" or handle response (some problems don't send OK)
            // Some variants don't require reading confirmation
        }
    }
}
