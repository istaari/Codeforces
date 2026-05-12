package phases.phase1_foundations.c1_800rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 122A — Lucky Division
 * Link   : https://codeforces.com/problemset/problem/122/A
 * Rating : 800  |  Tags: math, brute force
 * Topic  : Phase 1: Foundations > Math & Brute Force
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * A lucky number contains only the digits 4 and 7 (e.g. 4, 7, 44, 47, 74, 77,
 * 444, ...). Given a positive integer n, determine if n is divisible by at
 * least one lucky number. Print "YES" if so, "NO" otherwise.
 *
 * EXAMPLE
 * -------
 * Input:  28
 * Output: YES   (28 is divisible by 4)
 *
 * Input:  1
 * Output: NO
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Generate all lucky numbers up to 10^9 by BFS/DFS branching on 4 and 7.
 * 2. For each lucky number L, check if n % L == 0.
 * 3. If any divisor is found, output YES; otherwise NO.
 * 4. There are only 2 + 4 + 8 + ... + 2^9 = 1022 lucky numbers up to 10^9.
 *
 * COMPLEXITY
 * ----------
 * Time : O(1) — fixed set of ~1022 lucky numbers up to 10^9
 * Space: O(1)
 * ============================================================
 */

public class CF_122A_LuckyDivision {

    private static List<Long> luckyNumbers = new ArrayList<>();

    static {
        // Generate all lucky numbers using BFS
        List<Long> queue = new ArrayList<>();
        queue.add(4L);
        queue.add(7L);
        for (int i = 0; i < queue.size(); i++) {
            long cur = queue.get(i);
            luckyNumbers.add(cur);
            if (cur * 10 <= 1_000_000_000L) {
                queue.add(cur * 10 + 4);
                queue.add(cur * 10 + 7);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        long n = Long.parseLong(br.readLine().trim());
        for (long lucky : luckyNumbers) {
            if (n % lucky == 0) {
                System.out.println("YES");
                return;
            }
        }
        System.out.println("NO");
    }
}
