package phases.phase1_foundations.c2_900rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1269A — Equation
 * Link   : https://codeforces.com/problemset/problem/1269/A
 * Rating : 900  |  Tags: math
 * Topic  : Phase 1: Foundations > Math
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given two distinct primes p and q. Find any positive integer x such that
 * x is divisible by p but NOT divisible by q. Print any such x.
 *
 * EXAMPLE
 * -------
 * Input:  2 3
 * Output: 2
 *
 * Input:  3 7
 * Output: 3
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. x = p is always a valid answer.
 * 2. Since p is prime, its only divisors are 1 and p.
 * 3. q is a different prime (q ≠ p), so q cannot divide p.
 * 4. Therefore p % p == 0 and p % q != 0.
 *
 * COMPLEXITY
 * ----------
 * Time : O(1)
 * Space: O(1)
 * ============================================================
 */

public class CF_1269A_Equation {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        long p = Long.parseLong(st.nextToken());
        // q is not needed for the solution
        System.out.println(p);
    }
}
