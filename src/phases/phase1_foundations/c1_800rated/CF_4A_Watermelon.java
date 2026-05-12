package phases.phase1_foundations.c1_800rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 4A — Watermelon
 * Link   : https://codeforces.com/problemset/problem/4/A
 * Rating : 800  |  Tags: math, parity
 * Topic  : Phase 1: Foundations > Sheet C1 (800-rated)
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Pete and Billy want to split a watermelon of weight w into two parts,
 * each part weighing an even number. Given w (1 <= w <= 100), determine
 * whether this is possible. Both parts must be strictly positive integers
 * and both must be even.
 *
 * EXAMPLE
 * -------
 * Input:  8
 * Output: YES
 *
 * Input:  2
 * Output: NO   (only split is 1+1, both odd)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Both parts even → their sum (w) must also be even.
 * 2. Additionally w must be > 2 because the minimum valid split is 2+2=4.
 * 3. If w is even AND w > 2 → YES, otherwise NO.
 *
 * COMPLEXITY
 * ----------
 * Time : O(1)
 * Space: O(1)
 * ============================================================
 */

public class CF_4A_Watermelon {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int w = Integer.parseInt(br.readLine().trim());
        System.out.println((w > 2 && w % 2 == 0) ? "YES" : "NO");
    }
}
