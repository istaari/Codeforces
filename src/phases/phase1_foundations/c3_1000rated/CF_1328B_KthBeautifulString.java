package phases.phase1_foundations.c3_1000rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1328B — K-th Beautiful String
 * Link   : https://codeforces.com/problemset/problem/1328/B
 * Rating : 1000  |  Tags: constructive, math
 * Topic  : Phase 1: Foundations > Constructive
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * A beautiful string is a string of exactly n lowercase letters. They are
 * ordered lexicographically in reverse: the 1st beautiful string is "aa...a",
 * the 2nd is "aa...b", the k-th is the string with value k-1 in base 26
 * (reversed, so least significant digit is rightmost). Find the k-th beautiful
 * string of length n.
 *
 * EXAMPLE
 * -------
 * Input:  3 3
 * Output: aac
 *
 * Input:  3 25
 * Output: aay
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Represent (k-1) in base 26. The digits (from least to most significant)
 *    give the offsets from 'a' for positions right-to-left.
 * 2. Start with a string of n 'a's.
 * 3. For each digit position i (0 = rightmost), set char[n-1-i] = 'a' + digit_i.
 * 4. This naturally gives the k-th string in the described ordering.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1328B_KthBeautifulString {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());
            char[] result = new char[n];
            for (int i = 0; i < n; i++) result[i] = 'a';
            long val = k - 1;
            int pos = n - 1;
            while (val > 0 && pos >= 0) {
                result[pos--] = (char) ('a' + val % 26);
                val /= 26;
            }
            sb.append(new String(result)).append('\n');
        }
        System.out.print(sb);
    }
}
