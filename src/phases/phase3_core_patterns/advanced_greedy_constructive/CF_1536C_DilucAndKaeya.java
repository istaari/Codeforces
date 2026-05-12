package phases.phase3_core_patterns.advanced_greedy_constructive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/*
 * ============================================================
 * CF 1536C — Diluc and Kaeya
 * Link   : https://codeforces.com/problemset/problem/1536/C
 * Rating : 1500  |  Tags: greedy, hashing, strings
 * Topic  : Phase 3: Core Patterns > Advanced Greedy & Constructive
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given a string of 'D' and 'K' characters. Find the maximum number of segments
 * you can split the string into such that each segment has the same ratio of D's to K's
 * (same D/K ratio across all segments). If a segment has 0 K's, it can only pair with
 * segments that also have 0 K's.
 *
 * EXAMPLE
 * -------
 * Input:  4
 *         DKDK
 * Output: 2
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Scan left to right. Greedily try to end a segment as early as possible.
 *    A valid cut can be made at position i if the ratio D/K of s[0..i] equals
 *    the ratio D/K of the first segment.
 * 2. The first segment ratio is determined by the first valid cut point.
 *    We scan until we have at least one D and one K (to determine ratio), or handle
 *    the all-D case.
 * 3. Alternative: for each prefix, compute (D_count, K_count). Normalize to fraction
 *    d/k = gcd-reduced. A segment boundary is valid when the running (D,K) count
 *    matches a multiple of the reduced fraction.
 * 4. Algorithm:
 *    - Compute total D and K counts.
 *    - The ratio for ALL segments must be totalD : totalK.
 *    - Find how many times we can cut: scan left to right. At each position,
 *      check if (currentD / gcd(totalD,totalK)) == (currentK / gcd(totalD,totalK)) * (totalD/gcd / totalK/gcd)?
 *      i.e., currentD * totalK == currentK * totalD.
 * 5. Simplified: totalD/gcd(total) = d0, totalK/gcd(total) = k0.
 *    A valid cut at position i: currentD = j*d0 and currentK = j*k0 for some positive j.
 *    This is equivalent to currentD * k0 == currentK * d0.
 *    Count cuts: number of positions where currentD * k0 == currentK * d0.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(1)
 * ============================================================
 */

public class CF_1536C_DilucAndKaeya {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            String s = br.readLine().trim();
            int n = s.length();

            // Count total D and K
            long totalD = 0, totalK = 0;
            for (char c : s.toCharArray()) {
                if (c == 'D') totalD++;
                else totalK++;
            }

            long g = gcd(totalD, totalK);
            long d0 = totalD / g, k0 = totalK / g;

            // Count valid cuts
            long curD = 0, curK = 0;
            int cuts = 0;
            for (int i = 0; i < n; i++) {
                if (s.charAt(i) == 'D') curD++;
                else curK++;
                // Valid cut: curD * k0 == curK * d0
                if (curD * k0 == curK * d0) cuts++;
            }
            // Last cut is always the end of string (full string is one segment minimum)
            // cuts includes the last position, so answer = cuts.
            sb.append(cuts).append('\n');
        }

        System.out.print(sb);
    }

    static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
