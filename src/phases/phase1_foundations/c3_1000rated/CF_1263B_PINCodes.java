package phases.phase1_foundations.c3_1000rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/*
 * ============================================================
 * CF 1263B — PIN Codes
 * Link   : https://codeforces.com/problemset/problem/1263/B
 * Rating : 1000  |  Tags: greedy, implementation
 * Topic  : Phase 1: Foundations > Greedy & Implementation
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * There are n PIN codes, each exactly 4 digits. A PIN is "good" if not all
 * 4 digits are the same (i.e., not "0000", "1111", ..., "9999"). Make all
 * PINs good. In one operation, pick two PINs and swap any one digit between
 * them (replace one digit in one PIN with a digit from the other). Minimize
 * the total number of swap operations.
 *
 * EXAMPLE
 * -------
 * Input:  3 / 1337 / 0000 / 1234
 * Output: 1 / 0000 / 0337 / 1234   (swap '0' from 0000 with '3' from 1337)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Find all "bad" PINs (all digits the same).
 * 2. Each bad PIN needs 1 swap with a different-enough PIN to break uniformity.
 * 3. If two bad PINs have different digits (e.g., "0000" and "1111"), swap
 *    one digit between them → both become good with 1 operation.
 * 4. If a bad PIN has no matching partner, pair it with any good PIN.
 * 5. Greedily pair bad PINs with each other first (costs 1 swap per pair),
 *    then unpaired bad PINs with good PINs (costs 1 swap each).
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1263B_PINCodes {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            char[][] pins = new char[n][4];
            List<Integer> bad = new ArrayList<>();
            List<Integer> good = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                String s = br.readLine().trim();
                pins[i] = s.toCharArray();
                boolean allSame = (s.charAt(0) == s.charAt(1) && s.charAt(1) == s.charAt(2) && s.charAt(2) == s.charAt(3));
                if (allSame) bad.add(i);
                else good.add(i);
            }
            int ops = 0;
            // Pair bad PINs with each other
            int bi = 0;
            while (bi + 1 < bad.size()) {
                int p1 = bad.get(bi), p2 = bad.get(bi + 1);
                // Swap first digit of p1 into p2 (making both different)
                char tmp = pins[p1][0];
                pins[p1][0] = pins[p2][0];
                pins[p2][0] = tmp;
                ops++;
                bi += 2;
            }
            // Handle remaining unpaired bad PIN using a good PIN
            if (bi < bad.size()) {
                int p1 = bad.get(bi);
                int p2 = good.get(0); // any good pin works
                // Swap first digit that differs
                for (int d = 0; d < 4; d++) {
                    if (pins[p2][d] != pins[p1][0]) {
                        pins[p1][0] = pins[p2][d];
                        break;
                    }
                }
                ops++;
            }
            sb.append(ops).append('\n');
            for (char[] pin : pins) {
                sb.append(new String(pin)).append('\n');
            }
        }
        System.out.print(sb);
    }
}
