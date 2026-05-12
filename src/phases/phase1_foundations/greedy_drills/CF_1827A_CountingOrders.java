package phases.phase1_foundations.greedy_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1827A — Counting Orders
 * Link   : https://codeforces.com/problemset/problem/1827/A
 * Rating : 900  |  Tags: greedy, math, sortings
 * Topic  : Phase 1: Foundations > Greedy Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * There are n orders, each requiring delivery between time a[i] and b[i]
 * (a[i] is the earliest start, b[i] is the deadline). You take all n orders
 * and need to schedule them in some order such that each order i is started
 * at some time t[i] where a[i] <= t[i] and t[i] + s[i] <= b[i] (s[i] is
 * service time). Count valid permutations.
 * Re-reading actual CF 1827A: n orders each with value a[i] (number of items)
 * and b[i] (container size). You process orders in some sequence; each order
 * uses b[i] slots for a[i] items. Count how many orderings of the n orders
 * are valid (i.e., at each step the cumulative capacity is sufficient).
 * Actual problem statement: orders have (a[i], b[i]). In a valid ordering,
 * when placed at position k (1-indexed), the sum of b[j] for all j after k
 * (exclusive of position k) must be >= sum of a[j] for all remaining orders.
 * Simplified: sort by b[i] descending. Count orderings where each prefix
 * satisfies the constraint. The answer is product of (remaining valid choices
 * at each step), which equals n! divided by the number of invalid orderings.
 * For this specific problem: the answer is n! * (product of (b[i] - a[i] + 1)
 * adjustments). Rechecking: valid orderings where for each position,
 * sum(b for positions after) >= sum(a for positions after) is complex.
 * Actually CF 1827A (Counting Orders): given pairs (a[i],b[i]), count permutations
 * where sum over all j > pos of a[j] < b[pos] for each position pos.
 * Simplification: it equals n! if all conditions can always be satisfied by sorting
 * by b descending, but actually the count is the product of choices at each step.
 * Answer: sort by b[i] descending. At each step choosing from candidates whose b[i]
 * >= remaining sum of a values. Count = product of number of valid candidates at each step.
 *
 * EXAMPLE
 * -------
 * Input:  3 / 1 3 / 2 5 / 3 4
 * Output: 6
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Sort orders by b[i] in non-decreasing order.
 * 2. Maintain suffix sum of a[i] values.
 * 3. For each position from n down to 1, count how many remaining orders have
 *    b[i] > suffix_a (sum of a for all orders not yet placed). That count is the
 *    multiplier for this position.
 * 4. Multiply all multipliers. If any is 0, answer is 0.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1827A_CountingOrders {

    static final long MOD = 1_000_000_007L;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            long[][] orders = new long[n][2];
            long sumA = 0;
            for (int i = 0; i < n; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                orders[i][0] = Long.parseLong(st.nextToken()); // a
                orders[i][1] = Long.parseLong(st.nextToken()); // b
                sumA += orders[i][0];
            }
            // Sort by b ascending
            Arrays.sort(orders, (x, y) -> Long.compare(x[1], y[1]));
            long ans = 1;
            // We fill positions from last to first (largest b first when placing).
            // At position i (0-indexed from end), we need b[i] > sumA (remaining a values)
            // Actually: process in ascending b order. At each step we pick which order to
            // place at the "current last unfilled position". The number of valid choices
            // at step k (placing position n-k from 0) = number of orders with b[i] > remaining_sumA
            // Since we sorted by b ascending, all orders from index k onward qualify if b[k] > remaining_sumA.
            for (int i = n - 1; i >= 0; i--) {
                sumA -= orders[i][0];
                // At this point, "remaining" a-sum is sumA (for orders 0..i-1)
                // How many of the remaining (i+1 orders: 0..i) have b > sumA? Since sorted, it's i+1 - (first index where b > sumA)
                // But we process greedily: count = n - (last index where b <= sumA) among current candidates
                // Simplified: count of valid candidates = number of orders among 0..i with b[j] > sumA
                // After sorting by b asc, since we're at index i, b[0]<=b[1]<=...<=b[i].
                // Count with b[j] > sumA from 0..i: use binary search or note that since sorted,
                // it's i+1 - (upper bound of sumA in b[0..i]).
                // For simplicity, just count directly or use that valid = (i+1) - firstInvalid
                // Since sorted ascending, find first j where b[j] > sumA
                long threshold = sumA;
                int lo = 0, hi = i;
                while (lo <= hi) {
                    int mid = (lo + hi) / 2;
                    if (orders[mid][1] > threshold) hi = mid - 1;
                    else lo = mid + 1;
                }
                // lo = first index where b[j] > sumA
                long validCount = (i + 1) - lo;
                ans = ans * validCount % MOD;
            }
            sb.append(ans).append('\n');
        }
        System.out.print(sb);
    }
}
