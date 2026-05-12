package phases.phase1_foundations.c2_900rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1228A — Distinct Digits
 * Link   : https://codeforces.com/problemset/problem/1228/A
 * Rating : 900  |  Tags: brute force
 * Topic  : Phase 1: Foundations > Brute Force
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given two integers l and r. Find any integer x in the range [l, r] such
 * that all digits of x are distinct (no digit appears more than once).
 * If no such x exists, print -1.
 *
 * EXAMPLE
 * -------
 * Input:  1 9
 * Output: 1
 *
 * Input:  1 2
 * Output: 1
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Iterate from l to r.
 * 2. For each number, extract its digits and check for duplicates.
 * 3. Return the first number with all distinct digits.
 * 4. Note: any number with ≤ 10 digits can have at most 10 distinct digits.
 *    The range can be large, but numbers with distinct digits are common.
 *
 * COMPLEXITY
 * ----------
 * Time : O((r - l) * digits) in worst case, but in practice very fast
 * Space: O(digits)
 * ============================================================
 */

public class CF_1228A_DistinctDigits {

    static boolean hasDistinctDigits(int n) {
        boolean[] seen = new boolean[10];
        while (n > 0) {
            int d = n % 10;
            if (seen[d]) return false;
            seen[d] = true;
            n /= 10;
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int l = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());
            int answer = -1;
            for (int x = l; x <= r; x++) {
                if (hasDistinctDigits(x)) {
                    answer = x;
                    break;
                }
            }
            sb.append(answer).append('\n');
        }
        System.out.print(sb);
    }
}
