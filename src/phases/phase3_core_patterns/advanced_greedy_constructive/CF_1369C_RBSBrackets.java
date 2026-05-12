package phases.phase3_core_patterns.advanced_greedy_constructive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/*
 * ============================================================
 * CF 1369C — RBS Brackets
 * Link   : https://codeforces.com/problemset/problem/1369/C
 * Rating : 1500  |  Tags: greedy, strings
 * Topic  : Phase 3: Core Patterns > Advanced Greedy & Constructive
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given a balanced bracket sequence. Split it into two subsequences (each using
 * a disjoint subset of characters), each of which is a Regular Bracket Sequence (RBS).
 * Maximize the minimum length of the two RBSs.
 *
 * EXAMPLE
 * -------
 * Input:  (())
 * Output: 2
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. A Regular Bracket Sequence has equal '(' and ')' and all prefixes have #( >= #).
 * 2. Greedy: track running balance as we scan. When balance is 1 (before closing),
 *    assign to sequence A; when balance is 2, assign to sequence B; etc.
 *    But for two RBSs: assign each '(' and matching ')' to the same group.
 * 3. Key insight: at each '(', assign it to group 0 or 1. At each ')' closing a
 *    group's open bracket, assign it to that group. To maximize minimum length:
 *    alternate assignments of '(' to the two groups.
 * 4. Algorithm: maintain stack of open brackets. Track which group each open bracket
 *    belongs to. When we see ')', pop the top open bracket and assign the ')' to same group.
 *    Assign consecutive open brackets alternately to group 0 and group 1.
 *    Answer = 2 * min(count_in_group_0, count_in_group_1).
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1369C_RBSBrackets {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            String s = br.readLine().trim();
            int n = s.length();

            // Count pairs assigned to each group
            int[] groupCount = new int[2];
            java.util.ArrayDeque<Integer> stack = new java.util.ArrayDeque<>();
            int openCount = 0; // total open brackets seen so far

            for (int i = 0; i < n; i++) {
                if (s.charAt(i) == '(') {
                    // Assign to alternating group
                    int group = openCount % 2;
                    stack.push(group);
                    openCount++;
                } else {
                    // Match with top open bracket
                    int group = stack.pop();
                    groupCount[group]++;
                }
            }

            sb.append(2 * Math.min(groupCount[0], groupCount[1])).append('\n');
        }

        System.out.print(sb);
    }
}
