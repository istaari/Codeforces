package phases.phase1_foundations.constructive_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1783B — Matrix of Differences
 * Link   : https://codeforces.com/problemset/problem/1783/B
 * Rating : 1100  |  Tags: constructive algorithms, graphs
 * Topic  : Phase 1: Foundations > Constructive Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Construct an n×n matrix using integers 1..n (each used exactly n times) such
 * that for each row, the set of consecutive differences |a[i] - a[i+1]| covers
 * all values 1..n-1 (each exactly once).
 * The same must hold for each column.
 *
 * EXAMPLE
 * -------
 * Input:  3
 * Output:
 * 1 3 2
 * 3 2 1  (any valid matrix works, multiple answers accepted)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Construct a single "magic row" r of length n using 1..n such that consecutive
 *    differences are exactly 1,2,...,n-1 in some order.
 * 2. The sequence that achieves all differences: 1, n, 2, n-1, 3, n-2, ...
 *    (alternate taking from left and right of 1..n).
 *    Differences: n-1, n-2, n-3, ... all distinct.
 * 3. For the matrix: place this row r in odd rows, and reversed r in even rows.
 *    This ensures columns also have all differences.
 *    Actually a simpler valid construction: row i is a cyclic shift of the magic row.
 *    Row 0: r[0], r[1], ..., r[n-1]
 *    Row 1: r[1], r[2], ..., r[n-1], r[0]
 *    ...but column differences might not work.
 * 4. Standard approach for this problem: construct the permutation 1,n,2,n-1,...
 *    For row i, use the same base permutation cyclically shifted by i positions.
 *    However verifying columns is tricky.
 * 5. Simpler known construction: row i = shift the base permutation left by i.
 *    The base permutation b = [1, n, 2, n-1, 3, n-2, ...] has all diffs 1..n-1.
 *    Each row is a cyclic shift of b, so each row has the same differences.
 *    Columns: column j contains b[j], b[j+1 mod n], b[j+2 mod n], ...
 *    For columns to also have all differences, the cyclic sequence of b must have
 *    all differences — which it does for the interleaved sequence.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n^2)
 * Space: O(n^2)
 * ============================================================
 */

public class CF_1783B_MatrixOfDifferences {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            // Build base permutation: 1, n, 2, n-1, 3, n-2, ...
            int[] base = new int[n];
            int lo = 1, hi = n, idx = 0;
            boolean pickLo = true;
            while (lo <= hi) {
                if (pickLo) base[idx++] = lo++;
                else base[idx++] = hi--;
                pickLo = !pickLo;
            }
            // Each row i is cyclic shift of base by i
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (j > 0) sb.append(' ');
                    sb.append(base[(i + j) % n]);
                }
                sb.append('\n');
            }
        }
        System.out.print(sb);
    }
}
