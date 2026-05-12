package phases.phase1_foundations.c1_800rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 50A — Domino Piling
 * Link   : https://codeforces.com/problemset/problem/50/A
 * Rating : 800  |  Tags: math
 * Topic  : Phase 1: Foundations > Math
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given an M×N board, find the maximum number of 1×2 dominoes that can
 * be placed on it without overlapping and without going outside the board.
 * Each domino covers exactly 2 cells. Dominoes can be placed horizontally
 * or vertically.
 *
 * EXAMPLE
 * -------
 * Input:  2 4
 * Output: 4
 *
 * Input:  3 3
 * Output: 4
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. The board has M*N cells total.
 * 2. Each domino covers exactly 2 cells.
 * 3. Maximum dominoes = floor(M * N / 2) (integer division).
 * 4. This is always achievable by a simple tiling strategy.
 *
 * COMPLEXITY
 * ----------
 * Time : O(1)
 * Space: O(1)
 * ============================================================
 */

public class CF_50A_DominoPiling {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        long m = Long.parseLong(st.nextToken());
        long n = Long.parseLong(st.nextToken());
        System.out.println((m * n) / 2);
    }
}
