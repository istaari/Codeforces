package phases.phase1_foundations.sorting_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1794A — Prefix and Suffix Array
 * Link   : https://codeforces.com/problemset/problem/1794/A
 * Rating : 1000  |  Tags: strings, sortings, implementation
 * Topic  : Phase 1: Foundations > Sorting Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * You are given two strings: p (a prefix of unknown string s of length n-1) and
 * q (a suffix of s of length n-1). Recover s (of length n) or determine the number
 * of possible strings s. The prefix p = s[0..n-2] and suffix q = s[1..n-1].
 * So s[0..n-2] = p and s[1..n-1] = q. The overlap: s[1..n-2] = p[1..n-2] = q[0..n-3].
 * This must be consistent. Then s[0] = p[0] and s[n-1] = q[n-2].
 * Output: if p[1..n-2] != q[0..n-3], impossible (0 answers).
 * Otherwise, s = p + q[n-2] (append last char of q) = equivalently s[0]=p[0], middle=p[1..n-2], s[n-1]=q[n-2].
 * But we need to check if s[0] == s[n-1] or not to count distinct answers.
 * Actually: s = p[0] + p[1..n-2] + q[n-2]. Check if p[1..n-2] == q[0..n-3].
 * If inconsistent: print 0 and nothing. If consistent and p[0]==q[n-2]: one string.
 * If consistent and p[0]!=q[n-2]: two distinct strings (can be either).
 *
 * EXAMPLE
 * -------
 * Input:  3 / ab / bc
 * Output: 1 / abc
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Check: p[1..n-2] == q[0..n-3] (the shared middle part must agree).
 * 2. If not, output 0.
 * 3. If yes: s1 = p + q[n-2], s2 = q[0] + p. If s1 == s2, output 1 answer; else output 2.
 *    (Actually s1 has first char p[0], s2 has first char q[n-2].)
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
            String p = br.readLine().trim(); // prefix of length n-1
            String q = br.readLine().trim(); // suffix of length n-1
            // Middle overlap: p[1..n-2] should equal q[0..n-3]
            // p has indices 0..n-2, q has indices 0..n-2
            // p[1..n-2] = p.substring(1), q[0..n-3] = q.substring(0, n-2)
            String midP = p.substring(1);      // p[1..n-2]
            String midQ = q.substring(0, n - 2); // q[0..n-3]
            if (!midP.equals(midQ)) {
                sb.append(0).append('\n');
                continue;
            }
            // s = p[0] + middle + q[n-2]
            String s1 = p + q.charAt(n - 2);          // p[0] first
            String s2 = q.charAt(0) + p.substring(0, n - 2) + q.charAt(n - 2); // q[0] first
            // Actually s2: s[0] = q[0] (which could differ from p[0]), rest = midP, s[n-1] = q[n-2]
            // Wait: s = s[0] + midP + s[n-1]. s[0] = p[0] gives s1. s[0] = q[n-2] gives a different option.
            // Let me recalculate: p = s[0..n-2], q = s[1..n-1].
            // s[0] = p[0], s[n-1] = q[n-2]. The middle s[1..n-2] = midP (= midQ). Fully determined.
            // So there's only ONE possible string... unless we consider s[0] could also be q[n-2] if n=2?
            // For n=2: p = s[0], q = s[1]. Middle is empty, always consistent. s = p + q. One answer.
            // For n>2: s is uniquely determined as p[0] + midP + q[n-2].
            // Actually I was wrong about "two answers". The string is unique: s[0]=p[0], s[n-1]=q[n-2].
            // But if p[0] == q[n-2] (same first and last char), still one answer.
            // The "two answers" come if we consider swapping: what if s = q[n-2] + midP + p[0]?
            // That's only valid if q[n-2] = p[0] as s[0]... No. The answer is always exactly 1 or 0.
            // Wait re-read: the problem says "find s or say impossible". It says print the number of answers.
            // Since s is uniquely determined by p and q (given consistency), answer is 1.
            // Unless p[0] != q[n-2] means we can also have s = q[n-2] + midP + p[0] as another valid string?
            // Let me verify: if s = q[n-2]+midP+p[0], then prefix(n-1) = q[n-2]+midP = q (yes! since q=q[0..n-2]=q[n-2]+midQ=q[n-2]+midP).
            // And suffix(n-1) = midP+p[0] = p? Only if p = midP+p[0]... no. p = p[0]+midP. So suffix would be midP+p[0] = p reversed...
            // So yes: the second candidate s2 = q+p[0] (q[0..n-2] + p[0]) is also valid if consistent.
            // Check s2: prefix(n-1) = s2[0..n-2] = q[0..n-2] = q. Yes. suffix(n-1) = s2[1..n-1] = q[1..n-2]+p[0] = midQ+p[0] = midP+p[0].
            // We need this suffix = p. p = p[0]+midP. midP+p[0] = p only if p is a rotation and... not generally.
            // So s2 = q + p[0] has prefix = q (ok) but suffix = midQ + p[0]. Need suffix = p = p[0]+midP.
            // midQ + p[0] = p[0]+midP only if midQ = p[0..n-3] and p[0] = midP[n-3]... this gets complicated.
            // Simplest correct approach: try both candidates and check which are valid.
            String cand1 = "" + p.charAt(0) + midP + q.charAt(n - 2);
            String cand2 = "" + q.charAt(n - 2) + midP + p.charAt(0);
            boolean v1 = cand1.substring(0, n - 1).equals(p) && cand1.substring(1).equals(q);
            boolean v2 = cand2.substring(0, n - 1).equals(p) && cand2.substring(1).equals(q);
            if (!v1 && !v2) {
                sb.append(0).append('\n');
            } else if (v1 && v2 && !cand1.equals(cand2)) {
                sb.append(2).append('\n');
                sb.append(cand1).append('\n');
                sb.append(cand2).append('\n');
            } else {
                sb.append(1).append('\n');
                sb.append(v1 ? cand1 : cand2).append('\n');
            }
        }
        System.out.print(sb);
    }
}
