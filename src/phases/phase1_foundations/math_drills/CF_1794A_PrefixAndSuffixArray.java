package phases.phase1_foundations.math_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1794A — Prefix and Suffix Array
 * Link   : https://codeforces.com/problemset/problem/1794/A
 * Rating : 1000  |  Tags: strings, math, implementation
 * Topic  : Phase 1: Foundations > Math Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given n, and two strings p (prefix of length n-1) and q (suffix of length n-1)
 * of some unknown string s of length n. Recover s or report the number of valid s.
 * p = s[0..n-2], q = s[1..n-1]. The shared middle part s[1..n-2] must equal
 * both p[1..n-2] and q[0..n-3].
 *
 * EXAMPLE
 * -------
 * Input:  3 / ab / bc
 * Output: 1 / abc
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Middle = p[1..n-2] must equal q[0..n-3]. If not, answer is 0.
 * 2. s[0] = p[0], s[n-1] = q[n-2]. The string is s = p[0] + middle + q[n-2].
 * 3. If p[0] == q[n-2]: exactly 1 answer.
 * 4. If p[0] != q[n-2]: we could also have s = q[n-2] + middle + p[0] as another
 *    valid candidate — verify it satisfies both constraints.
 * 5. Output count and sorted answers.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1794A_PrefixAndSuffixArray {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            String p = br.readLine().trim();
            String q = br.readLine().trim();
            String midP = p.substring(1);
            String midQ = q.substring(0, n - 2);
            if (!midP.equals(midQ)) {
                sb.append(0).append('\n');
                continue;
            }
            String cand1 = "" + p.charAt(0) + midP + q.charAt(n - 2);
            String cand2 = "" + q.charAt(n - 2) + midP + p.charAt(0);
            boolean v1 = cand1.substring(0, n - 1).equals(p) && cand1.substring(1).equals(q);
            boolean v2 = !cand1.equals(cand2) && cand2.substring(0, n - 1).equals(p) && cand2.substring(1).equals(q);
            if (v1 && v2) {
                sb.append(2).append('\n');
                // Output lexicographically
                if (cand1.compareTo(cand2) <= 0) {
                    sb.append(cand1).append('\n').append(cand2).append('\n');
                } else {
                    sb.append(cand2).append('\n').append(cand1).append('\n');
                }
            } else if (v1) {
                sb.append(1).append('\n').append(cand1).append('\n');
            } else if (v2) {
                sb.append(1).append('\n').append(cand2).append('\n');
            } else {
                sb.append(0).append('\n');
            }
        }
        System.out.print(sb);
    }
}
