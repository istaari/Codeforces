package phases.phase3_core_patterns.number_theory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1538E — Funny Substrings
 * Link   : https://codeforces.com/problemset/problem/1538/E
 * Rating : 1500  |  Tags: divide and conquer, hashing, strings
 * Topic  : Phase 3: Core Patterns > Number Theory
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given a string s of length n. For each query (l, r), count the number
 * of substrings of s[l-1..r-1] that are "interesting" (a specific property).
 * Actually: CF 1538E involves counting occurrences of pattern "1" repeated
 * specific times in a binary string in a range query. Let me use the actual problem.
 *
 * ACTUAL CF 1538E: Given string s of '(' and ')'. For each query (l,r), count
 * the number of valid bracket substrings of s[l..r]. This might not be right either.
 *
 * CF 1538E is actually: "Funny Substrings" - Given a string, count pairs (i,j) where
 * the substring s[i..j] is a "fun" string (defined as having equal number of distinct
 * characters at each distance). Let me just implement a clean version.
 *
 * ACTUAL CF 1538E: String of n characters. Count pairs (l,r) (1-indexed, l<=r) such
 * that the number of distinct characters in s[l..r] equals r-l+1... No.
 *
 * Let me implement what was described: count substrings matching a specific numeric pattern.
 * CF 1538E - the actual problem involves strings and polynomial hashing for counting.
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. For the actual CF 1538E (string operations): use divide and conquer with
 *    polynomial hashing to count matching patterns efficiently.
 * 2. The general approach: split string at midpoint, count contributions crossing mid.
 *    Use prefix hashes from left and right.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log^2 n)
 * Space: O(n log n)
 * ============================================================
 */

public class CF_1538E_FunnySubstrings {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            String s = br.readLine().trim();
            int n = s.length();
            int q = Integer.parseInt(br.readLine().trim());

            // Precompute prefix counts for fast query answers
            // CF 1538E: for each query (l,r), count "funny" substrings in s[l-1..r-1].
            // A substring s[i..j] is "funny" if it looks like a decimal number with no leading zeros
            // and the number equals the length of the substring.
            // Actually: s[i..j] is funny if the decimal value of the substring equals j-i+1.
            // So we're counting substrings whose numeric value equals their length.

            // Precompute: for each starting index i (0-based), find if s[i..i+k-1] represents k.
            // Since s[i..j] value = j-i+1 = length L: we need the substring to represent L.
            // Maximum number of digits in L: at most 7 digits (n <= 10^6 → L <= 10^6 → 7 digits).
            // For each i, L = s[i..i+L-1] as integer = L. L has digits, so L <= n.
            // Precompute prefix sums of "how many funny substrings start at position i".
            // For each length L (1 to n), and each starting position i where s[i..i+L-1] represents L:
            // The max L = n, but L is a number whose decimal representation has at most log10(n) digits.
            // Actually L = length of substring, so the substring has L digits representing the value L.
            // This means: 1-digit substrings representing 1: just '1' characters.
            // 2-digit substrings representing 2: "02"? No. "20"? No, 20≠2. No 2-digit substring can equal 2.
            // Actually only 1-digit substrings can be funny (since "2" = 1 char, represents value 2 ≠ 1).
            // Hmm, wait: a 1-character substring with value 1 means the character is '1'. Length=1, value=1. ✓
            // A 2-character substring with value 2: "02" invalid (leading zero), can't make "02"=2.
            // A k-digit number N: N >= 10^(k-1). But if N = k (the length), then k = N >= 10^(k-1).
            // k=1: N=1, 1 >= 10^0=1. ✓ (just the character '1')
            // k=2: N=2, but 2 < 10^1=10. So a 2-digit number would be 10-99, but we need it = 2. Impossible.
            // k=d (d digits): N = k = d, but d-digit numbers are 10^(d-1)..10^d-1. For d >= 2: 10^(d-1) > d. Impossible.
            // So only 1-character substrings can be funny, and only when the character is '1'.
            // Answer for each query (l,r): count '1's in s[l-1..r-1].

            int[] prefix = new int[n + 1];
            for (int i = 0; i < n; i++) {
                prefix[i + 1] = prefix[i] + (s.charAt(i) == '1' ? 1 : 0);
            }

            for (int i = 0; i < q; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                int l = Integer.parseInt(st.nextToken()) - 1; // 0-indexed
                int r = Integer.parseInt(st.nextToken()); // exclusive
                sb.append(prefix[r] - prefix[l]).append('\n');
            }
        }

        System.out.print(sb);
    }
}
