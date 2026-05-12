package phases.phase2_toolkit.binary_search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/*
 * ============================================================
 * CF 1612C — Chat Ban
 * Link   : https://codeforces.com/problemset/problem/1612/C
 * Rating : 1300  |  Tags: binary search, math
 * Topic  : Phase 2: Toolkit > Binary Search
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * A user sends messages in rounds. In round 1 they can send 1 message,
 * round 2 they can send 2, ..., round k they can send k messages.
 * However they stop after sending exactly k messages total (i.e., total
 * messages = 1+2+...+k where the last round may be partial).
 * Given n (total messages allowed), find the round k at which the ban
 * happens (the round where cumulative sum first reaches or exceeds n).
 *
 * EXAMPLE
 * -------
 * Input:  5
 * Output: 3
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. The total after k complete rounds is k*(k+1)/2.
 * 2. Binary search on k: find the smallest k where k*(k+1)/2 >= n.
 * 3. Use long arithmetic as n can be up to 10^18, so k*(k+1)/2 can
 *    overflow int. Cap intermediate at 2*10^18 to avoid overflow.
 * 4. lo=1, hi=2e9 (since sqrt(2*1e18) ~ 1.4e9).
 *
 * COMPLEXITY
 * ----------
 * Time : O(log n)
 * Space: O(1)
 * ============================================================
 */

public class CF_1612C_ChatBan {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        long n = Long.parseLong(br.readLine().trim());

        // Find smallest k such that k*(k+1)/2 >= n
        long lo = 1, hi = 2_000_000_000L;
        while (lo < hi) {
            long mid = (lo + hi) / 2;
            // Compute mid*(mid+1)/2 safely
            long total;
            if (mid > 2_000_000_000L) {
                total = Long.MAX_VALUE; // overflow guard
            } else {
                total = mid * (mid + 1) / 2;
            }
            if (total >= n) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }

        System.out.println(lo);
    }
}
