package phases.phase4_advanced.segment_tree_bit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/*
 * ============================================================
 * CF 1537E — Erase and Extend
 * Link   : https://codeforces.com/problemset/problem/1537/E
 * Rating : 1600  |  Tags: greedy, strings
 * Topic  : Phase 4: Advanced > Segment Tree / BIT
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * String s of length n. Operation: pick k in [1, n-1], delete last k characters
 * (keep first n-k), then repeat remaining string enough times to get back to
 * length n. Apply this operation once (optimally) to get the lexicographically
 * smallest resulting string of length n.
 *
 * EXAMPLE
 * -------
 * Input:  n=5, s="abcde"
 * Output: "aaaaa" (keep "a", repeat 5 times)
 *         Actually need min string, so keep the smallest prefix that when repeated gives smallest string.
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. We want to find the prefix s[0..k-1] (1 <= k <= n) such that when repeated,
 *    gives the lexicographically smallest string of length n.
 * 2. For each prefix length k (1..n), the resulting string is s[0..k-1] repeated ceil(n/k) times (truncated to n).
 * 3. We want the minimum over all k. Naively O(n^2), but we can optimize:
 *    The optimal k is always a divisor... no. We need to compare prefix strings.
 * 4. Observation: repeating a prefix of length k gives a periodic string.
 *    Lexicographically minimum = we want s[0] as small as possible, then s[1], etc.
 * 5. Greedy: the best prefix is the ENTIRE string s[0..n-1]? No, sometimes shorter is better.
 *    E.g., "ba" -> keep "b" -> "bbbbb" vs keep "ba" -> "babab". "babab" < "bbbbb", so longer is better here.
 * 6. Iterate: start with prefix s[0..0]. For each i from 1 to n-1:
 *    Check if s[0..i] is lexicographically better than current best prefix repeated.
 *    If s[i] < s[i % current_len], extend prefix. Else keep current.
 *    Actually: compare s[0..i] repeated vs s[0..best-1] repeated. Complex.
 * 7. Simple O(n^2) is OK if n is small enough (n <= 1000). Build each candidate and compare.
 *    For large n: find the minimum rotation / Booth's algorithm variant.
 * 8. Efficient: for each k, compare s[0..k-1] (periodic) with the running minimum.
 *    Use Z-function to find periods and compare efficiently.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n^2) in worst case, practical O(n log n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1537E_EraseAndExtend {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        String s = br.readLine().trim();

        // Find best prefix length k in [1..n]
        // For each k, resulting string is s[0..k-1] repeated to length n
        // Compare: keep the lexicographically smallest result

        int bestLen = 1;
        for (int k = 2; k <= n; k++) {
            // Compare prefix of length k with current best prefix of length bestLen
            // Compare character by character in the resulting n-length strings
            // s[i % k] vs s[i % bestLen] for i = 0..n-1
            // Find first difference
            boolean kBetter = false;
            boolean kWorse = false;
            for (int i = 0; i < n; i++) {
                char ck = s.charAt(i % k);
                char cb = s.charAt(i % bestLen);
                if (ck < cb) { kBetter = true; break; }
                if (ck > cb) { kWorse = true; break; }
            }
            if (kBetter) bestLen = k;
        }

        // Build result
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) sb.append(s.charAt(i % bestLen));
        System.out.println(sb);
    }
}
