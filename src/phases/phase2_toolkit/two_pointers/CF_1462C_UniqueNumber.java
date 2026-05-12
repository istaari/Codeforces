package phases.phase2_toolkit.two_pointers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/*
 * ============================================================
 * CF 1462C — Unique Number
 * Link   : https://codeforces.com/problemset/problem/1462/C
 * Rating : 1100  |  Tags: two pointers, greedy, math
 * Topic  : Phase 2: Toolkit > Two Pointers
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Find the minimum positive integer whose decimal representation
 * contains all digits strictly unique (no digit repeated) and
 * the sum of its digits equals x. If impossible, output -1.
 *
 * EXAMPLE
 * -------
 * Input:  10
 * Output: 19
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Digits must all be distinct (1-9, since leading zeros don't help
 *    and 0 costs nothing to add at the end). Max achievable sum with
 *    distinct non-zero digits = 1+2+...+9 = 45.
 * 2. If x > 45, output -1.
 * 3. To minimize the number, use fewest digits possible (fewer digits =
 *    smaller number), and among same digit count, sort digits in ascending
 *    order (smaller digits first = smaller number).
 * 4. Greedily pick the largest possible distinct digits to reach sum x
 *    with fewest digits. Start from 9 downward, greedily take digit d
 *    if d <= remaining sum and digits used so far + more digits can still
 *    reach the sum.
 * 5. Once we have the digits, sort ascending and print.
 *
 * COMPLEXITY
 * ----------
 * Time : O(1) (at most 9 digits)
 * Space: O(1)
 * ============================================================
 */

public class CF_1462C_UniqueNumber {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int x = Integer.parseInt(br.readLine().trim());

        if (x > 45) {
            System.out.println(-1);
            return;
        }

        // Greedy: pick largest digits first to use fewest digits
        List<Integer> digits = new ArrayList<>();
        int remaining = x;
        for (int d = 9; d >= 1 && remaining > 0; d--) {
            if (d <= remaining) {
                digits.add(d);
                remaining -= d;
            }
        }

        if (remaining != 0) {
            System.out.println(-1);
            return;
        }

        // Sort ascending to form smallest number
        Collections.sort(digits);
        StringBuilder sb = new StringBuilder();
        for (int d : digits) sb.append(d);
        System.out.println(sb.toString());
    }
}
