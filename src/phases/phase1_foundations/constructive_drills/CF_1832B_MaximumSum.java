package phases.phase1_foundations.constructive_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1832B — Maximum Sum
 * Link   : https://codeforces.com/problemset/problem/1832/B
 * Rating : 900  |  Tags: constructive algorithms, greedy, sortings
 * Topic  : Phase 1: Foundations > Constructive Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given an array a of n integers, you must split it into two non-empty contiguous
 * parts: a[1..k] and a[k+1..n] for some 1 <= k <= n-1. The score is
 * max(a[1..k]) + max(a[k+1..n]). Maximize this score.
 *
 * EXAMPLE
 * -------
 * Input:  5 / 1 3 2 4 5
 * Output: 9
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. The global maximum will always be in one of the two parts.
 * 2. To maximize the sum, we want the global max in one part and the second
 *    distinct maximum (or a copy of max) in the other part.
 * 3. The answer is: global_max + max of (all elements except at the position of
 *    global_max on one side). Since we split at some k, the global max (at position
 *    p) will be in one part; the other part's max is maximized by putting the
 *    second largest element in the other part.
 * 4. Simpler: answer = max_element + second_largest (where second_largest is the
 *    largest element that is either strictly to the left or strictly to the right
 *    of the global max position). This equals: global_max + max(prefix_max[p-1], suffix_max[p+1]).
 * 5. Even simpler: The answer = max of all elements + max of remaining elements
 *    adjacent to a valid split. Since global max is at some index, the other part's
 *    max is the max of everything else. So answer = top1 + top2 where top2 is the
 *    second maximum value (could equal top1 if duplicates exist).
 * 6. Proof: split such that the global max is alone in one part (edge split) and
 *    the second max is in the other. This always works.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(1)
 * ============================================================
 */

public class CF_1832B_MaximumSum {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            StringTokenizer st = new StringTokenizer(br.readLine());
            long top1 = Long.MIN_VALUE, top2 = Long.MIN_VALUE;
            for (int i = 0; i < n; i++) {
                long x = Long.parseLong(st.nextToken());
                if (x >= top1) {
                    top2 = top1;
                    top1 = x;
                } else if (x > top2) {
                    top2 = x;
                }
            }
            sb.append(top1 + top2).append('\n');
        }
        System.out.print(sb);
    }
}
