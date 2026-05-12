package phases.phase4_advanced.shortest_paths;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/*
 * ============================================================
 * CF 1307C — Cow and Message
 * Link   : https://codeforces.com/problemset/problem/1307/C
 * Rating : 1500  |  Tags: dp, string, counting
 * Topic  : Phase 4: Advanced > Shortest Paths
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given string s of a's and b's. Count the number of subsequences equal
 * to "ab" and the number equal to "ba". Return the maximum of the two counts.
 *
 * EXAMPLE
 * -------
 * Input:  "ab"
 * Output: 1
 *
 * Input:  "aab"
 * Output: 2
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Count subsequences "ab": for each 'b' at position i, count of 'a's before i.
 * 2. Count subsequences "ba": for each 'a' at position i, count of 'b's before i.
 * 3. Maintain running counts of 'a's seen (countA) and 'b's seen (countB).
 * 4. For each character: if 'b', countAB += countA; if 'a', countBA += countB.
 * 5. Answer = max(countAB, countBA).
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(1)
 * ============================================================
 */

public class CF_1307C_CowAndMessage {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine().trim();

        long countA = 0, countB = 0;
        long countAB = 0, countBA = 0;

        for (char c : s.toCharArray()) {
            if (c == 'a') {
                countBA += countB;
                countA++;
            } else { // 'b'
                countAB += countA;
                countB++;
            }
        }

        System.out.println(Math.max(countAB, countBA));
    }
}
