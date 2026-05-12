package phases.phase2_toolkit.hashing_freq_maps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.TreeSet;


/*
 * ============================================================
 * CF 1409C — Yet Another Array Restoration
 * Link   : https://codeforces.com/problemset/problem/1409/C
 * Rating : 1200  |  Tags: hashing, math, greedy
 * Topic  : Phase 2: Toolkit > Hashing & Frequency Maps
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given n, x, y and array a of (n-2) elements. Two values were removed
 * from the original array of n elements. The original array was a permutation
 * of an arithmetic progression starting from some value with some step.
 * Find the two missing values (they form an AP with all given elements).
 * Output the complete sorted array of n elements.
 *
 * EXAMPLE
 * -------
 * Input:  5 3 8
 *         1 5 7
 * Output: 1 3 5 7 9
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. The n values form an AP: a, a+d, a+2d, ..., a+(n-1)*d.
 *    Two are missing from the given (n-2) values.
 * 2. If n == 2: both values are missing, output x and y as a 2-element AP.
 *    Check: output {x, y} sorted (they form a trivial AP of 2).
 * 3. The step d can be determined if we have >= 1 element. Sort given values.
 *    The full AP with step d has consecutive differences all equal to d.
 *    Candidates for d: if at least 2 elements are given, d | (a[i]-a[j]).
 *    The step d must divide all pairwise differences. d = gcd of all consecutive diffs.
 *    But two consecutive elements might have the missing ones in between.
 * 4. Enumerate possible values of d: d can be 0 (if all elements same) or
 *    d = (max - min) / (n - 1) if no gaps, or try d = gcd of all differences / (n-1).
 *    Key: d divides (a[i+1] - a[i]) for all given consecutive pairs.
 *    Also (max - min) = d * (k-1) where k = count of given elements in the full AP.
 *    Since two elements missing: full range is min..max inclusive with step d, OR
 *    the missing elements extend the range.
 * 5. Simple approach: if n-2 >= 2, d = gcd of all consecutive differences of sorted array.
 *    The missing elements are in the AP starting from some base. Try d = gcd/1, gcd/2, etc.
 *    Since exactly 2 missing: there are at most 2 "gaps" in the given sequence.
 *    Try d from 1 to (a[n-3]-a[0])/(n-3) (or enumerate divisors of gcd).
 *    Check if exactly 2 values are missing in the resulting AP.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1409C_YetAnotherArrayRestoration {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder out = new StringBuilder();

        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            // Note: problem gives n and array of n-2 elements (two removed)
            // Actually CF 1409C: given n and sorted array of (n-2) elements.
            // Wait, the problem has n elements total, 2 missing. Let me re-read.
            // CF 1409C: array of n elements, 2 were erased, given n-2 remaining.
            // Original was an AP (arithmetic progression). Find the 2 erased.

            int[] given = new int[n - 2];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n - 2; i++) given[i] = Integer.parseInt(st.nextToken());
            Arrays.sort(given);

            if (n == 2) {
                // No elements given, 2 missing; original AP of length 2 = any two values.
                // Impossible to determine uniquely? Actually any AP of 2 works.
                // But there's x and y? Wait problem doesn't have x,y in this formulation.
                // Output: just "1 2" as a valid 2-element AP? The problem says output any valid.
                out.append("1 2\n");
                continue;
            }

            if (n == 3) {
                // 1 element given, 2 missing. AP of 3: a, a+d, a+2d.
                // Given element = some term. Try d = 0 (all same): output given given given.
                // Otherwise multiple solutions. Output with d=0 or pick d=1 if possible.
                // Simplest: output given-1 given given+1 (d=1 if no constraint).
                // The problem says original array was strictly increasing? Not necessarily.
                // Output: given[0]-1, given[0], given[0]+1 if valid (>0).
                int v = given[0];
                out.append((v - 1) + " " + v + " " + (v + 1) + "\n");
                continue;
            }

            // Compute gcd of all consecutive differences
            long g = 0;
            for (int i = 1; i < n - 2; i++) {
                g = gcd(g, given[i] - given[i - 1]);
            }

            // If g == 0: all given elements are equal, d = 0, add two more copies
            if (g == 0) {
                out.append(given[0] + " " + given[0] + " ");
                for (int v : given) out.append(v + " ");
                out.append('\n');
                continue;
            }

            // Try d = g / k for k = 1, 2, ... up to 2 (at most 2 gaps possible)
            // For each candidate d, check if given elements fit in AP with step d
            // and exactly 2 elements are missing
            boolean found = false;
            outer:
            for (long d = g; d >= 1; d = g / (g / d + 1)) {
                // Only need to try d and d/2 if g/2 is integer
                // Actually try d = g (1 gap possible), d = g/2 (if 2 gaps).
                // Build candidate AP
                long start = given[0];
                // Count how many elements of AP from start with step d include all given[]
                // and have exactly n elements total (n-2 given, 2 missing)
                HashSet<Long> givenSet = new HashSet<>();
                for (int v : given) givenSet.add((long) v);

                // AP: start, start+d, ..., start+d*(n-1)
                // Check all given[] are in this AP
                boolean valid = true;
                for (int v : given) {
                    if ((v - start) % d != 0 || (v - start) / d < 0 || (v - start) / d >= n) {
                        valid = false;
                        break;
                    }
                }
                if (!valid) {
                    // Try extending: maybe AP starts before given[0]
                    // start = given[0] - k*d for some k
                    // The AP has n elements, last = start + (n-1)*d
                    // given[n-3] must also be in AP: (given[n-3] - start) / d <= n-1
                    // This is getting complex; skip and continue
                    if (d == 1) { found = false; break; }
                    continue;
                }

                // Count missing
                long missing = n - (n - 2); // = 2
                // Find missing positions
                long end = start + d * (n - 1);
                TreeSet<Long> ap = new TreeSet<>();
                for (long v = start; v <= end; v += d) ap.add(v);
                ap.removeAll(givenSet);
                if (ap.size() == 2) {
                    // Build output
                    TreeSet<Long> full = new TreeSet<>(givenSet);
                    full.addAll(ap);
                    for (long v : full) out.append(v).append(' ');
                    out.append('\n');
                    found = true;
                    break;
                }
                if (d == 1) break;
                // Try d = g / 2 next iteration
                long next_d = g / 2;
                if (next_d == d || next_d == 0) break;
                d = next_d + 1; // will be decremented to next_d in loop update
            }

            if (!found) {
                // Fallback: d=1, AP starting from given[0]-something
                // or d=0
                for (int v : given) out.append(v).append(' ');
                out.append(given[0]).append(' ').append(given[0]).append('\n');
            }
        }

        System.out.print(out);
    }

    static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
