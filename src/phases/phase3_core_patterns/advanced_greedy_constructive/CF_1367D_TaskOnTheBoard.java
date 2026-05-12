package phases.phase3_core_patterns.advanced_greedy_constructive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;


/*
 * ============================================================
 * CF 1367D — Task On The Board
 * Link   : https://codeforces.com/problemset/problem/1367/D
 * Rating : 1500  |  Tags: greedy, constructive, strings
 * Topic  : Phase 3: Core Patterns > Advanced Greedy & Constructive
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given n×m board of characters. In one operation, pick a row r: for each column j,
 * if column j has at most 1 distinct character in the remaining rows, remove all
 * occurrences of that character in column j. Actually: remove rows to make each
 * remaining column have only ONE distinct character.
 *
 * ACTUAL CF 1367D: Given n strings of length m. Strike out rows until every remaining
 * column has at most one distinct character. Minimize rows struck out.
 * Output which rows to keep (or strike out).
 *
 * EXAMPLE
 * -------
 * Input:  5 4
 *         abbc
 *         aaac
 *         abbc
 *         aaac
 *         aaac
 * Output: 4
 *         2 4 5 3
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. We want to keep a maximum subset of rows such that in each column, all
 *    characters are the same (across kept rows).
 * 2. For each column, all kept rows must agree on the same character.
 *    Equivalent: each column has a single character in the kept rows.
 * 3. Greedy: find the rows that are "contradictory" — two rows have same set of
 *    column characters but one column differs. Remove the ones that cause conflicts.
 * 4. Simpler: sort rows into groups by their "signature". A column is "settled"
 *    if all kept rows have the same character there. Remove rows that have
 *    a character in a settled column different from the settled character.
 * 5. Algorithm: initially no columns are settled. Process rows. For each row,
 *    check if it conflicts with already-settled columns. If no conflict: keep row
 *    and settle all new columns. If conflict: mark row for removal.
 *    But order matters.
 * 6. Alternative greedy: find the character in each column that appears most often
 *    (in kept rows). Settle on it. Rows that disagree are removed.
 *    This needs iteration.
 * 7. Simple 2-pass: keep all rows, then for each column c, find most frequent char.
 *    Remove rows with a different char in column c. Repeat for all columns.
 *    This gives a valid answer but might remove too many rows.
 * 8. Optimal: keep rows that form a "consistent" assignment.
 *    Key insight: the kept rows must all have the same character in every column.
 *    So all kept rows are identical? No — two rows can differ in a column if that
 *    column is "settled" to a character that both agree on... wait: if two rows
 *    both have 'a' in column 1 and 'b' in column 2, and another row has 'a' and 'a',
 *    the first two can be kept (columns 1='a', column 2 will be restricted to 'b' but
 *    third row disagrees in col 2). So keep rows 1 and 2, remove row 3.
 *    "All kept rows have same character in every column" means all kept rows are identical!
 *    So answer = n - max_frequency_of_identical_rows.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n * m)
 * Space: O(n * m)
 * ============================================================
 */

public class CF_1367D_TaskOnTheBoard {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            java.util.StringTokenizer st = new java.util.StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            String[] rows = new String[n];
            for (int i = 0; i < n; i++) rows[i] = br.readLine().trim();

            // Count frequency of each row string
            HashMap<String, Integer> freq = new HashMap<>();
            for (String row : rows) freq.merge(row, 1, Integer::sum);

            // Find most common row
            String bestRow = null;
            int maxFreq = 0;
            for (var e : freq.entrySet()) {
                if (e.getValue() > maxFreq) {
                    maxFreq = e.getValue();
                    bestRow = e.getKey();
                }
            }

            // Rows to remove: those not equal to bestRow
            int removeCount = n - maxFreq;
            sb.append(removeCount).append('\n');
            if (removeCount > 0) {
                StringBuilder removed = new StringBuilder();
                boolean first = true;
                for (int i = 0; i < n; i++) {
                    if (!rows[i].equals(bestRow)) {
                        if (!first) removed.append(' ');
                        removed.append(i + 1);
                        first = false;
                    }
                }
                sb.append(removed).append('\n');
            }
        }

        System.out.print(sb);
    }
}
