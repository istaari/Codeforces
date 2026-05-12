package phases.phase1_foundations.c1_800rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1A — Theatre Square
 * Link   : https://codeforces.com/problemset/problem/1/A
 * Rating : 800  |  Tags: math
 * Topic  : Phase 1: Foundations > Math
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * The Theatre Square in the capital of Berland is a rectangular N×M field.
 * The mayor wants to pave it with square a×a flagstones. Flagstones may not
 * be cut. Count the minimum number of flagstones needed to cover the entire
 * square (flagstones may extend beyond the edges of the square).
 *
 * EXAMPLE
 * -------
 * Input:  6 6 4
 * Output: 4
 *
 * Input:  1000000000 1000000000 1
 * Output: 1000000000000000000
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Along each dimension, use ceiling division: ceil(N/a) = (N + a - 1) / a.
 * 2. Total flagstones = ceil(N/a) * ceil(M/a).
 * 3. Use long to avoid integer overflow.
 *
 * COMPLEXITY
 * ----------
 * Time : O(1)
 * Space: O(1)
 * ============================================================
 */

public class CF_1A_TheatreSquare {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        long n = Long.parseLong(st.nextToken());
        long m = Long.parseLong(st.nextToken());
        long a = Long.parseLong(st.nextToken());
        long tilesN = (n + a - 1) / a;
        long tilesM = (m + a - 1) / a;
        System.out.println(tilesN * tilesM);
    }
}
