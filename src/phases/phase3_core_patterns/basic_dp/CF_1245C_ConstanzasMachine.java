package phases.phase3_core_patterns.basic_dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/*
 * ============================================================
 * CF 1245C — Constanze's Machine
 * Link   : https://codeforces.com/problemset/problem/1245/C
 * Rating : 1400  |  Tags: dp, strings, combinatorics
 * Topic  : Phase 3: Core Patterns > Basic DP
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Constanze's machine replaces 'u' with 'uu' and 'v' with 'vv' (each
 * character might have been produced by either itself or the doubeled form
 * of the other character? No — machine replaces u→uu and v→vv).
 * Given the output string, count the number of possible original strings.
 * If two adjacent identical characters are both 'u' or both 'v', they
 * could come from one 'u' or one 'v' (or they were two separate originals).
 * 'n' and 'm' cannot be the result of any doubling, so they always came alone.
 *
 * EXAMPLE
 * -------
 * Input:  uuvvww
 * Output: 4
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Fibonacci-like DP. Process characters left to right.
 *    dp[i] = number of ways to decode s[0..i-1].
 * 2. For each position i (1-indexed):
 *    - Single character: s[i] can always stand alone (unless it's 'n' or 'm'
 *      which can't — wait, actually 'n' and 'm' in the output are invalid since
 *      they were NOT produced by the machine (which only produces u,u,v,v plus
 *      unchanged other chars). If output contains 'n' or 'm' it's invalid: output 0.
 *    - Pair: if s[i] == s[i-1] and s[i] is 'u' or 'v', the pair could come
 *      from one original character → dp[i+1] += dp[i-1].
 * 3. dp[i] = dp[i-1] + (dp[i-2] if s[i] == s[i-1] and s[i] in {u,v}).
 *    Answer mod 1e9+7.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(1)
 * ============================================================
 */

public class CF_1245C_ConstanzasMachine {

    static final long MOD = 1_000_000_007L;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine().trim();
        int n = s.length();

        // Check for invalid characters (n or m in the output = impossible)
        for (char c : s.toCharArray()) {
            if (c == 'n' || c == 'm') {
                System.out.println(0);
                return;
            }
        }

        // dp: Fibonacci-like
        long prev2 = 1, prev1 = 1; // dp[0]=1 (empty), dp[1]=1 (first char)
        for (int i = 1; i < n; i++) {
            long cur = prev1;
            char c = s.charAt(i);
            // Can form a pair with previous?
            if ((c == 'u' || c == 'v') && s.charAt(i - 1) == c) {
                cur = (cur + prev2) % MOD;
            }
            prev2 = prev1;
            prev1 = cur;
        }

        System.out.println(prev1);
    }
}
