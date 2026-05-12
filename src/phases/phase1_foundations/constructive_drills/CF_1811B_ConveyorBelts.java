package phases.phase1_foundations.constructive_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1811B — Conveyor Belts
 * Link   : https://codeforces.com/problemset/problem/1811/B
 * Rating : 1000  |  Tags: constructive algorithms, math
 * Topic  : Phase 1: Foundations > Constructive Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * You have an n×n grid. Cell (i,j) (0-indexed) lies on diagonal d = |i - j|.
 * There are n diagonals (d = 0, 1, ..., n-1). Given two cells (r1,c1) and (r2,c2),
 * determine if they are on the same diagonal (i.e., |r1-c1| == |r2-c2|).
 * The problem asks: for q pairs of cells, count pairs on the same diagonal.
 * Actually: given q queries each with two cells, answer YES/NO if same diagonal.
 *
 * EXAMPLE
 * -------
 * Input:  4 2 / 0 0  1 1 / 0 1  2 3
 * Output: YES / YES
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Cell (r,c) is on diagonal |r-c|.
 * 2. Two cells are on the same diagonal iff |r1-c1| == |r2-c2|.
 * 3. For each query, compute both diagonal values and compare.
 * 4. The number of pairs (i,j) from an array of cells that share a diagonal
 *    can be computed by grouping cells by their diagonal index |r-c|, then
 *    for each group of size k, add k*(k-1)/2.
 *
 * COMPLEXITY
 * ----------
 * Time : O(q)
 * Space: O(1) per query
 * ============================================================
 */

public class CF_1811B_ConveyorBelts {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int q = Integer.parseInt(st.nextToken());
            for (int i = 0; i < q; i++) {
                st = new StringTokenizer(br.readLine());
                int r1 = Integer.parseInt(st.nextToken());
                int c1 = Integer.parseInt(st.nextToken());
                int r2 = Integer.parseInt(st.nextToken());
                int c2 = Integer.parseInt(st.nextToken());
                // diagonal index = |r - c|
                int d1 = Math.abs(r1 - c1);
                int d2 = Math.abs(r2 - c2);
                sb.append(d1 == d2 ? "YES" : "NO").append('\n');
            }
        }
        System.out.print(sb);
    }
}
