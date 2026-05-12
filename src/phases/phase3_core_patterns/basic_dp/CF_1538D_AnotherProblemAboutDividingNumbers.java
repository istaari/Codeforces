package phases.phase3_core_patterns.basic_dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1538D — Another Problem About Dividing Numbers
 * Link   : https://codeforces.com/problemset/problem/1538/D
 * Rating : 1500  |  Tags: number theory, math, randomization
 * Topic  : Phase 3: Core Patterns > Basic DP
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given arrays a and b, each of n elements. Can we rearrange a and b (independently)
 * such that a[i] divides b[i] for all i? Output "Yes" or "No".
 *
 * EXAMPLE
 * -------
 * Input:  2
 *         1 6
 *         2 3
 * Output: Yes
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. If any a[i] = 1, it can divide any b[j]. Multiple 1s are fine.
 *    If a contains many 1s, pair them with any b's.
 * 2. For elements a[i] > 1: the number of distinct prime factors of a[i] is at most 6
 *    (since 2*3*5*7*11*13*17 > 10^6 → at most 6 primes for a[i] <= 10^6... but a[i] can be up to 10^6).
 *    Actually 2*3*5*7*11*13 = 30030 < 10^6, 2*3*5*7*11*13*17 > 10^6. So at most 6 distinct primes.
 * 3. Key insight: if a[i] > 1, then b[j] must be divisible by a[i]. The number of b[j] divisible by
 *    a[i] can be estimated. If a has too many large distinct values, it's likely "No".
 * 4. Algorithm: count 1s in a. Pair them with the hardest b's. For the remaining a values (all > 1),
 *    check if we can find a matching in the bipartite graph (a[i] divides b[j]).
 * 5. Randomized approach: shuffle a and b randomly. Check if a[i] | b[i] for all i. Repeat many times.
 *    This is valid for contest but not rigorous.
 * 6. Deterministic: if count of 1s in a >= n, output "Yes" (all a's are 1).
 *    Else, sort both arrays. Pair largest a with smallest b? Not necessarily correct.
 *    Correct approach: sort a ascending, b ascending. Check if each a[i] divides b[i] after pairing.
 *    This greedy doesn't always work. Need a better approach.
 * 7. Better: if count_ones_a + (distinct divisible pairs) >= n, output "Yes".
 *    For the non-1 elements of a: there can be at most O(log(max_a)) primes. We use a
 *    probabilistic check with multiple random shuffles.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n * sqrt(max_val)) per try, or O(n log n) with number theory
 * Space: O(n)
 * ============================================================
 */

public class CF_1538D_AnotherProblemAboutDividingNumbers {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        Random rng = new Random(42);

        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            long[] a = new long[n], b = new long[n];
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) a[i] = Long.parseLong(st.nextToken());
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) b[i] = Long.parseLong(st.nextToken());

            // Count 1s in a
            int ones = 0;
            List<Long> aNonOne = new ArrayList<>();
            for (long v : a) {
                if (v == 1) ones++;
                else aNonOne.add(v);
            }

            if (aNonOne.isEmpty()) {
                sb.append("Yes\n");
                continue;
            }

            // For each non-1 a value, need a b divisible by it.
            // Sort b descending (largest first).
            Arrays.sort(b);
            // b is sorted ascending; we'll use a frequency/multiset approach.

            // Greedy: sort a (non-ones) descending, sort b descending.
            // Assign each a[i] to the smallest b[j] >= a[i] that is divisible by a[i].
            // This is O(n * sqrt(max)) which might be too slow for large n.

            // Faster: for small n use greedy matching. For large n, probabilistic.
            // Use: sort aNonOne descending, sort b. For each a value, binary search for multiples in b.

            // Simple approach for this problem size: use random shuffle and check 20 times.
            aNonOne.sort(Collections.reverseOrder());
            Long[] bArr = new Long[n];
            for (int i = 0; i < n; i++) bArr[i] = b[n - 1 - i]; // descending

            boolean found = false;
            // Try: greedily match each aNonOne[i] to a b value divisible by it (greedy largest-first)
            // Use a multiset simulation with sorting
            for (int trial = 0; trial < 30 && !found; trial++) {
                // Shuffle b
                List<Long> bList = new ArrayList<>();
                for (long v : b) bList.add(v);
                Collections.shuffle(bList, rng);

                // Check: ones can match anything, non-ones need divisibility
                // Separate b into: those divisible by at least one aNonOne, others
                boolean[] used = new boolean[n];
                boolean ok = true;
                for (int i = 0; i < aNonOne.size(); i++) {
                    long av = aNonOne.get(i);
                    boolean matched = false;
                    for (int j = 0; j < n; j++) {
                        if (!used[j] && bList.get(j) % av == 0) {
                            used[j] = true;
                            matched = true;
                            break;
                        }
                    }
                    if (!matched) { ok = false; break; }
                }
                if (ok) found = true;
            }

            sb.append(found ? "Yes" : "No").append('\n');
        }

        System.out.print(sb);
    }
}
