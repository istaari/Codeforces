package phases.phase1_foundations.sorting_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1833A — Musical Puzzle
 * Link   : https://codeforces.com/problemset/problem/1833/A
 * Rating : 900  |  Tags: strings, implementation, hashing
 * Topic  : Phase 1: Foundations > Sorting Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * You have two strings s and t. You want to find the number of distinct two-character
 * substrings (consecutive pairs) that appear in either s or t. Count the total number
 * of distinct pairs (s[i],s[i+1]) union (t[j],t[j+1]).
 * Re-reading actual CF 1833A: given two strings s and t, a "puzzle" is a string that
 * can be made by interleaving s and t. Count distinct adjacent pairs that must appear
 * in all valid puzzles... actually count pairs (bigrams) in both strings combined.
 * The problem: output the number of distinct substrings of length 2 across both s and t.
 *
 * EXAMPLE
 * -------
 * Input:  ab / ba
 * Output: 2
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Collect all substrings of length 2 from both s and t into a HashSet.
 * 2. The answer is the size of this HashSet.
 *
 * COMPLEXITY
 * ----------
 * Time : O(|s| + |t|)
 * Space: O(|s| + |t|)
 * ============================================================
 */

public class CF_1833A_MusicalPuzzle {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            String s = br.readLine().trim();
            String u = br.readLine().trim();
            HashSet<String> bigrams = new HashSet<>();
            for (int i = 0; i + 1 < s.length(); i++) bigrams.add(s.substring(i, i + 2));
            for (int i = 0; i + 1 < u.length(); i++) bigrams.add(u.substring(i, i + 2));
            sb.append(bigrams.size()).append('\n');
        }
        System.out.print(sb);
    }
}
