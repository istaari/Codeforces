package phases.phase1_foundations.c1_800rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 339A — Helpful Maths
 * Link   : https://codeforces.com/problemset/problem/339/A
 * Rating : 800  |  Tags: sorting, strings
 * Topic  : Phase 1: Foundations > Sorting & Strings
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given a mathematical expression in the form "a+b+c+..." where each
 * operand is a single digit (1, 2, or 3), rearrange the expression so
 * that the operands are in non-decreasing order. Output the sorted
 * expression with '+' signs between the digits.
 *
 * EXAMPLE
 * -------
 * Input:  3+2+1
 * Output: 1+2+3
 *
 * Input:  1+1+1+2+1
 * Output: 1+1+1+1+2
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Split the input string by "+".
 * 2. Sort the resulting array (lexicographic or numeric, same for digits).
 * 3. Rejoin with "+" and print.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log n) where n is the number of operands
 * Space: O(n)
 * ============================================================
 */

public class CF_339A_HelpfulMaths {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine().trim();
        String[] parts = line.split("\\+");
        Arrays.sort(parts);
        System.out.println(String.join("+", parts));
    }
}
