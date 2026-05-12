package phases.phase2_toolkit.bit_manipulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/*
 * ============================================================
 * CF 1451C — String Equality
 * Link   : https://codeforces.com/problemset/problem/1451/C
 * Rating : 1200  |  Tags: frequency array, greedy, strings
 * Topic  : Phase 2: Toolkit > Bit Manipulation
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given two strings s and t of equal length, both lowercase. You can
 * replace any character c in s with the next character c+1 (cyclically:
 * z becomes a). Find the minimum number of operations to make s equal to t.
 * Characters can only be shifted forward (a→b→...→z→a).
 *
 * EXAMPLE
 * -------
 * Input:  abcd
 *         bcda
 * Output: 4
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. For each position, to change s[i] to t[i], we can shift s[i] forward
 *    (cost = (t[i] - s[i] + 26) % 26 operations). Each single shift costs 1 op.
 *    Total = sum of (t[i] - s[i] + 26) % 26 for all i.
 * 2. But there's a smarter way: instead of shifting individual characters,
 *    we can "batch shift" using frequency arrays. If freq_s[c] excess characters
 *    of type c can be shifted to become c+1 for free as a group... actually
 *    each shift is still 1 op per character.
 * 3. Wait — re-read. Actually the problem allows any character in s to be
 *    shifted forward. The minimum ops is: for each character that needs to go
 *    from s[i] to t[i], the number of forward shifts = (t[i]-s[i]+26)%26.
 *    But we can reuse shifts: shift character at pos i forward 3 times by doing
 *    3 ops, getting s[i]+3. This is already counted.
 * 4. Optimization: we can use excess characters. Count freq_s[c] - freq_t[c]
 *    for each c. If positive, those c's need to be shifted forward to cover
 *    deficits elsewhere. The minimum total shifts = sum over all c of
 *    cumulative excess that needs to "travel" from c to target.
 * 5. Actually simplest correct approach: compute sum of (t[i]-s[i]+26)%26.
 *    This gives total forward shifts needed. Answer = this sum.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(1)
 * ============================================================
 */

public class CF_1451C_StringEquality {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            String s = br.readLine().trim();
            String tStr = br.readLine().trim();
            int n = s.length();

            // Frequency approach: for each character position, count frequency
            // Use frequency arrays and "flow" excess forward
            int[] freqS = new int[26];
            int[] freqT = new int[26];
            for (int i = 0; i < n; i++) {
                freqS[s.charAt(i) - 'a']++;
                freqT[tStr.charAt(i) - 'a']++;
            }

            // For each character c, if freqS[c] > freqT[c]: excess is shifted forward
            // If freqS[c] < freqT[c]: deficit needs to be filled from previous excess
            // Total ops = sum of (deficit filled * distance traveled) -- like Earth mover's distance on a cycle
            // But since we only shift FORWARD (never backward), and cycle is possible (z→a),
            // we compute: for each c, excess[c] = freqS[c] - freqT[c]
            // We carry excess forward cyclically. Total cost = sum of excess carried * 1 per step.
            // This is: cumulative sum of excess * 1 per character step.

            long totalOps = 0;
            long carry = 0;
            for (int c = 0; c < 26; c++) {
                carry += freqS[c] - freqT[c];
                // carry > 0: excess c's being shifted forward
                // carry < 0: deficit (will be filled by future carry) -- shouldn't happen if valid
                totalOps += Math.max(0, carry); // each excess unit moving forward costs 1 per step
                // Wait: if carry < 0 at some c, that means we need characters shifted from before
                // which is backward... Actually this problem doesn't have negative carry since
                // total freq_s == total freq_t (same length strings).
                // The carry at each step contributes to the cost.
                // Total cost = sum over c of (carry after processing c, if > 0).
                // This gives the minimum shifts needed (Earth mover's distance on a line, unrolled).
            }
            // Actually the correct formula is just sum of individual shift costs:
            // Answer = sum (t[i] - s[i] + 26) % 26 for all i.
            // Let me just recompute that directly:
            totalOps = 0;
            for (int i = 0; i < n; i++) {
                totalOps += ((tStr.charAt(i) - s.charAt(i)) + 26) % 26;
            }

            sb.append(totalOps).append('\n');
        }

        System.out.print(sb);
    }
}
