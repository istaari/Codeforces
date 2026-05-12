package phases.phase2_toolkit.binary_search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;


/*
 * ============================================================
 * CF 1486C2 — Guessing the Greatest (Hard version)
 * Link   : https://codeforces.com/problemset/problem/1486/C2
 * Rating : 1400  |  Tags: binary search, interactive
 * Topic  : Phase 2: Toolkit > Binary Search
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Interactive problem. Hidden array of n distinct integers.
 * You can ask at most 2*ceil(log2(n))+1 queries.
 * Query "? l r" → returns the INDEX (1-based) of the second-maximum
 * element in a[l..r]. Find and print the index of the maximum element.
 *
 * EXAMPLE
 * -------
 * Input:  5
 *         4
 *         ...
 * Output: ! 2
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Query ? 1 n → get index p of second-maximum in the whole array.
 *    The maximum is either in [1, p-1] or [p+1, n].
 * 2. To determine which side: query ? 1 p.
 *    - If answer == p, then the maximum of [1,p] equals the max of [1,p-1],
 *      meaning the global max is in [1, p-1]. Set lo=1, hi=p-1.
 *    - Otherwise (answer < p), the max of [1,p] is NOT at boundary,
 *      so the global max must be in [p+1, n]. Set lo=p+1, hi=n.
 *    (Special cases: p==1 → max in [2,n]; p==n → max in [1,n-1])
 * 3. Binary search in [lo, hi]: while lo < hi, mid=(lo+hi)/2.
 *    Query ? lo mid. Let s = answer.
 *    - If s is in [lo, mid-1]: the max of [lo,mid] is NOT at mid, but the
 *      second-max of [lo,mid] is in [lo,mid-1]. Actually we need a smarter
 *      check: the global max is at position where the second-max "points away".
 *    Cleaner: query ? lo hi each time, use second-max to narrow.
 *    But that's O(n) queries. Instead:
 *    Query ? mid hi. If answer (second-max of [mid,hi]) == mid, then the
 *    max of [mid,hi] is not mid (mid is second), so max of [mid,hi] is
 *    in [mid+1,hi]. The global max is in that range → lo = mid+1.
 *    Otherwise max of [mid,hi] is mid (mid is max, second is in [mid+1,hi]),
 *    BUT global max might still be in [lo,mid-1]. We need to check [lo,mid].
 *    Actually: The global max in [lo,hi] is either in [lo,mid-1] or [mid,hi].
 *    Query [mid, hi]: second-max index returned = s.
 *    If s == mid: mid is second-max of [mid,hi], so max of [mid,hi] is in
 *    [mid+1,hi]. Global max is NOT at mid → if global max is in [lo,hi] and
 *    not in [mid,hi]'s max... need to narrow. → lo = mid+1.
 *    If s != mid (s > mid): max of [mid,hi] is at mid. This means a[mid] >
 *    all of a[mid+1..hi]. But global max could be in [lo,mid-1]. Check by
 *    querying [lo, mid]: second = t.
 *    If t == mid: then a[mid] is also the second-max of [lo,mid], meaning
 *    a[lo..mid-1] all < a[mid], so a[mid] is the max of [lo,hi] → answer=mid.
 *    If t != mid: max of [lo,mid] is in [lo,mid-1], meaning global max is
 *    in [lo,mid-1] → hi = mid-1.
 *    This uses 2 queries per step but is within budget for log2(n) steps.
 *
 * COMPLEXITY
 * ----------
 * Time : O(log n) queries
 * Space: O(1)
 * ============================================================
 */

public class CF_1486C2_GuessingTheGreatestHard {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static PrintWriter pw = new PrintWriter(System.out);

    static int query(int l, int r) throws IOException {
        pw.println("? " + l + " " + r);
        pw.flush();
        return Integer.parseInt(br.readLine().trim());
    }

    public static void main(String[] args) throws IOException {
        int n = Integer.parseInt(br.readLine().trim());

        if (n == 1) {
            pw.println("! 1");
            pw.flush();
            return;
        }

        // Step 1: Find second-max of entire array → narrows the search to one side
        int p = query(1, n);

        int lo, hi;

        if (p == 1) {
            lo = 2;
            hi = n;
        } else if (p == n) {
            lo = 1;
            hi = n - 1;
        } else {
            // Query [1, p]: if result == p, max is in [1, p-1]; else max is in [p+1, n]
            int leftSec = query(1, p);
            if (leftSec == p) {
                lo = 1;
                hi = p - 1;
            } else {
                lo = p + 1;
                hi = n;
            }
        }

        // Step 2: Binary search in [lo, hi]
        while (lo < hi) {
            int mid = (lo + hi) / 2;

            // Query [mid, hi]: get second-max index in [mid..hi]
            int s = query(mid, hi);

            if (s == mid) {
                // mid is the second-max of [mid,hi] → max of [mid,hi] is in [mid+1,hi]
                // So global max (which is in [lo,hi]) is in [mid+1,hi]
                lo = mid + 1;
            } else {
                // max of [mid,hi] is mid (s > mid means a[mid] > all in [mid+1..hi])
                // Global max is either mid or in [lo, mid-1]
                // Query [lo, mid] to check
                int t = query(lo, mid);
                if (t == mid) {
                    // mid is second-max of [lo,mid] → max of [lo,mid] is in [lo,mid-1]
                    // But we established a[mid] > [mid+1,hi], so global max is in [lo,mid-1]
                    hi = mid - 1;
                } else {
                    // max of [lo,mid] is mid → a[mid] > a[lo..mid-1] and a[mid] > a[mid+1..hi]
                    // Therefore a[mid] is the global max
                    lo = mid;
                    hi = mid;
                }
            }
        }

        pw.println("! " + lo);
        pw.flush();
    }
}
