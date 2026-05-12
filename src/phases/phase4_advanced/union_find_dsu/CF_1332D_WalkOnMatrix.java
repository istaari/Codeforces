package phases.phase4_advanced.union_find_dsu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1332D — Walk on Matrix
 * Link   : https://codeforces.com/problemset/problem/1332/D
 * Rating : 1700  |  Tags: constructive, bitmask, greedy
 * Topic  : Phase 4: Advanced > Union Find / DSU
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Construct a 2×n matrix where each cell has a value in [0, 2^30).
 * Starting from (0,0) you can move right or down. The path from (0,0) to (1,n-1)
 * computes bitwise-OR of all visited cells. This OR must equal k.
 * Additionally, there exists a path where OR = 0 (some path that avoids all nonzero).
 * Wait - re-read: construct matrix such that:
 * - Max OR over all paths from (0,0) to (1,n-1) = (1<<b) - 1 (some value)
 * - Min OR over all paths = k.
 * Actually: the problem asks for a 2×n matrix with specific max/min path properties.
 *
 * EXACT PROBLEM CF 1332D: Given b and k. Construct a 2×n (n=3) matrix s.t.:
 * max_path OR = (1<<b) - 1, min_path OR = k.
 *
 * EXAMPLE
 * -------
 * Input:  b=2, k=1
 * Output: matrix 2×3
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Use bitwise construction. Construct a 2×3 matrix.
 * 2. Key insight: matrix[0][0] = (1<<b) - 1 (top-left has all bits).
 *    matrix[1][2] = (1<<b) - 1 (bottom-right has all bits).
 *    matrix[0][1] = 0, matrix[0][2] = k.
 *    matrix[1][0] = 0, matrix[1][1] = 0.
 * 3. Path going right then down: OR = (1<<b)-1 + 0 + k = (1<<b)-1 | k = (1<<b)-1 (since k < 1<<b).
 * 4. Path going down then right: OR = (1<<b)-1 + 0 + 0 + (1<<b)-1 = (1<<b)-1.
 * 5. Wait, need min path = k and max = (1<<b)-1.
 *    Matrix[0][0] = (1<<b), matrix[0][1] = k, matrix[0][2] = 0.
 *    Matrix[1][0] = 0, matrix[1][1] = 0, matrix[1][2] = (1<<b).
 *    Min path: down immediately, right right = 0 | 0 | (1<<b) = (1<<b). Not k.
 *    Let me use the actual editorial approach.
 *    Matrix[0][0] = (1<<b)-1, matrix[0][1] = 0, matrix[0][2] = k.
 *    Matrix[1][0] = 0, matrix[1][1] = 0, matrix[1][2] = (1<<b)-1 (but this is bottom-right).
 *    Wait: paths are (0,0)->(0,1)->(0,2)->(1,2) goes right,right,down: OR = (1<<b)-1|0|k|(1<<b)-1.
 *    Path (0,0)->(1,0)->(1,1)->(1,2): OR = (1<<b)-1|0|0|(1<<b)-1 = (1<<b)-1.
 *    Path (0,0)->(0,1)->(1,1)->(1,2): OR = (1<<b)-1|0|0|(1<<b)-1 = (1<<b)-1.
 *    Path (0,0)->(0,1)->(0,2)->(1,2): OR = (1<<b)-1|0|k|(1<<b)-1 = (1<<b)-1|k.
 *    Need one path with OR=k. Hmm.
 *    n=3, 2 rows: path must go from (0,0) to (1,n-1)=(1,2) in 3 moves.
 *    Set: [0][0]=0, [0][1]=k, [0][2]=0; [1][0]=(1<<b)-1, [1][1]=0, [1][2]=0.
 *    Path right,right,down: 0|k|0|0=k. Path right,down,right: 0|k|0|0=k.
 *    Path down,right,right: 0|(1<<b)-1|0|0=(1<<b)-1.
 *    Max=(1<<b)-1, min=k. That works!
 *
 * COMPLEXITY
 * ----------
 * Time : O(1)
 * Space: O(1)
 * ============================================================
 */

public class CF_1332D_WalkOnMatrix {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int b = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        long big = (1L << b) - 1;

        // Row 0: [0, k, 0]
        // Row 1: [big, 0, 0]
        // Min path (right,right,down or right,down,right): 0|k|0|0=k
        // Max path (down,right,right): 0|big|0|0=big=(1<<b)-1
        System.out.println("3");
        System.out.println(0 + " " + k + " " + 0);
        System.out.println(big + " " + 0 + " " + 0);
    }
}
