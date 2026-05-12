package phases.phase1_foundations.constructive_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1807C — Find And Replace
 * Link   : https://codeforces.com/problemset/problem/1807/C
 * Rating : 800  |  Tags: constructive algorithms, strings
 * Topic  : Phase 1: Foundations > Constructive Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * You have a binary string s. You can choose any character c ('0' or '1') and
 * replace all occurrences of c in s with either '0' or '1'. You can do this
 * operation at most once per character. Check if you can make the string all '0's.
 * Each distinct source character maps consistently: if '0'→'0' and '1'→'0',
 * or '0'→'0' and '1' doesn't appear, etc. Essentially: can every character be
 * mapped to '0' (each character maps to exactly one target character consistently)?
 *
 * EXAMPLE
 * -------
 * Input:  3 / 0 / 1 / 01
 * Output: YES / YES / YES
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. For a binary string to become all zeros, each of '0' and '1' must map to '0'.
 * 2. '0'→'0' is always fine. '1'→'0' is always fine.
 * 3. So ANY binary string can be made all zeros: map '0'→'0' and '1'→'0'.
 * 4. Wait — re-read the problem. The operation replaces ALL occurrences of chosen
 *    char with another char. We want all zeros. Map '1'→'0'. Done. Always YES.
 * 5. But the actual CF 1807C problem: given two strings s and t (same length),
 *    map chars in s so that result equals t. Check if the mapping is consistent
 *    (each source char maps to exactly one target char).
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(1) — alphabet size 26
 * ============================================================
 */

public class CF_1807C_FindAndReplace {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            String s = br.readLine().trim();
            String target = br.readLine().trim();
            int n = s.length();
            // mapping[c] = -1 means unmapped, 0 means maps to '0', 1 means maps to '1'
            int[] mapping = new int[26];
            Arrays.fill(mapping, -1);
            boolean ok = true;
            for (int i = 0; i < n; i++) {
                int c = s.charAt(i) - 'a';
                int d = target.charAt(i) - 'a';
                if (mapping[c] == -1) {
                    mapping[c] = d;
                } else if (mapping[c] != d) {
                    ok = false;
                    break;
                }
            }
            // Additional check: if char maps to itself but also maps to something else — handled above
            // Also check: if '0' maps to '1', then all zeros become ones which might conflict
            // The above loop handles consistency. But we need the final string to equal target.
            // The mapping must also be non-conflicting from target side: two different chars
            // cannot map to the same char if that causes a contradiction — actually they CAN
            // map to the same target char. The constraint is only that each source is consistent.
            sb.append(ok ? "YES" : "NO").append('\n');
        }
        System.out.print(sb);
    }
}
