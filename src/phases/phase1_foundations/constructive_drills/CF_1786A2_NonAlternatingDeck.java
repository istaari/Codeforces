package phases.phase1_foundations.constructive_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1786A2 — Non-alternating Deck
 * Link   : https://codeforces.com/problemset/problem/1786/A2
 * Rating : 1100  |  Tags: constructive algorithms, implementation
 * Topic  : Phase 1: Foundations > Constructive Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * You have a deck of 2n cards: first n are red (R) and last n are black (B).
 * You perfectly interleave them in two ways:
 *   - "In-shuffle": RBRB... (red at odd positions 1,3,5,...)
 *   - "Out-shuffle": RBRB... (red at even positions 0,2,4,...)
 * Wait — the actual CF 1786A2: you have n red and n black cards. You interleave them
 * to form a deck of 2n. The deck is "non-alternating" if some two adjacent cards have
 * the same color. You're given the result of interleaving and must determine if it
 * was the in-shuffle or out-shuffle.
 * Re-reading: given a deck of 2n cards with colors, determine if there exists a
 * split into two halves and an interleave such that the result is non-alternating.
 * The actual problem: n red and n black cards. Two types of riffle shuffles (top half
 * and bottom half interleaved). For each possible pair (top,bottom) of halves that
 * interleave to give the observed deck, check if the deck is non-alternating.
 * Simplified: given a string of 'R' and 'B' of length 2n, check if ANY two adjacent
 * characters are the same color (i.e., the string is NOT purely alternating).
 *
 * EXAMPLE
 * -------
 * Input:  3 / RBRBRB
 * Output: NO
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. A deck is non-alternating if any two adjacent cards have the same color.
 * 2. Check consecutive pairs; if any match, output YES (it's non-alternating).
 * 3. If all pairs differ (perfectly alternating), output NO.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1786A2_NonAlternatingDeck {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            String s = br.readLine().trim();
            boolean nonAlt = false;
            for (int i = 1; i < s.length(); i++) {
                if (s.charAt(i) == s.charAt(i - 1)) {
                    nonAlt = true;
                    break;
                }
            }
            sb.append(nonAlt ? "YES" : "NO").append('\n');
        }
        System.out.print(sb);
    }
}
