package phases.phase1_foundations.math_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1789A — Serval and Mocha's Array
 * Link   : https://codeforces.com/problemset/problem/1789/A
 * Rating : 1100  |  Tags: math, greedy, constructive
 * Topic  : Phase 1: Foundations > Math Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given an array a[1..n]. Determine if there exist two indices i and j such that:
 * - |i - j| <= 2 (they are within distance 2 of each other)
 * - i and j have the same parity (both odd or both even indices, 1-indexed)
 * - a[i] == a[j]
 * Output YES if such a pair exists, NO otherwise.
 *
 * EXAMPLE
 * -------
 * Input:  5 / 1 2 1 2 1
 * Output: YES
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Same parity with |i-j| <= 2 means |i-j| is exactly 2 (or 0 for same element, trivial).
 * 2. So check if a[i] == a[i+2] for any i. This covers all pairs with same parity and dist 2.
 * 3. Additionally check dist 0 (same index, always equal), but we need distinct i,j.
 * 4. So: scan and check if a[i] == a[i+2] for any valid i.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(1)
 * ============================================================
 */

public class CF_1789A_ServalAndMochasArray {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            StringTokenizer st = new StringTokenizer(br.readLine());
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = Integer.parseInt(st.nextToken());
            boolean found = false;
            for (int i = 0; i + 2 < n; i++) {
                if (a[i] == a[i + 2]) {
                    found = true;
                    break;
                }
            }
            sb.append(found ? "YES" : "NO").append('\n');
        }
        System.out.print(sb);
    }
}
