package phases.phase1_foundations.c1_800rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 443A — Anton and Letters
 * Link   : https://codeforces.com/problemset/problem/443/A
 * Rating : 800  |  Tags: implementation, set
 * Topic  : Phase 1: Foundations > Implementation
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Anton received a set of letters as a string in the form "{a, b, c, ...}".
 * Some letters may appear multiple times. Determine the number of distinct
 * letters in the set (i.e., the size of the actual mathematical set).
 * The input always uses the curly brace format with comma-separated entries.
 *
 * EXAMPLE
 * -------
 * Input:  {a, b, b, c, a}
 * Output: 3
 *
 * Input:  {}
 * Output: 0
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Extract the content between '{' and '}'.
 * 2. Split by ", " to get individual letter tokens.
 * 3. Insert all tokens into a HashSet to deduplicate.
 * 4. Output the set size (handle empty case: size 0).
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_443A_AntonAndLetters {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine().trim();
        // Remove braces
        String inner = line.substring(1, line.length() - 1).trim();
        HashSet<String> set = new HashSet<>();
        if (!inner.isEmpty()) {
            for (String token : inner.split(",\\s*")) {
                set.add(token.trim());
            }
        }
        System.out.println(set.size());
    }
}
