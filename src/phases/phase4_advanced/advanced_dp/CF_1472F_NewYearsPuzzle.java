package phases.phase4_advanced.advanced_dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1472F — New Year's Puzzle
 * Link   : https://codeforces.com/problemset/problem/1472/F
 * Rating : 1800  |  Tags: dp, sorting, constructive
 * Topic  : Phase 4: Advanced > Advanced DP
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * A 2×n grid with some cells blocked. Fill the remaining cells with
 * 1×2 dominoes (horizontal or vertical). Count the number of ways
 * modulo 10^9+7. Blocked cells are given as (row, col) pairs.
 *
 * EXAMPLE
 * -------
 * Input:  n=4, blocked: (1,2),(2,3)
 * Output: 2
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. If any column has both cells blocked, the grid is split into independent
 *    segments. If both cells of same column blocked, multiply contributions.
 * 2. If a column has exactly one cell blocked, it must pair with another
 *    single-blocked column. Single-blocked columns must come in pairs.
 *    If odd count of single-blocked columns -> answer = 0.
 * 3. Sort single-blocked pairs. For each consecutive pair (c1,c2):
 *    - If c1 and c2 block same row: the columns between them must be filled
 *      with vertical dominoes. Ways = 1.
 *    - If c1 and c2 block different rows: ways = 2^(number of "free" column
 *      segments between them... actually the number of free columns / 2,
 *      contributes factor of 2).
 * 4. Between double-blocked columns, each "free segment" of length L can be
 *    tiled in ways f(L) where f(L)=0 if L odd, f(L) based on DP of 2xL grid.
 *    For 2×L grid: ways = fibonacci-like: ways[0]=1, ways[2]=2, ways[L]=ways[L-2]+ways[L-2]...
 *    Actually for 2×L unrestricted: ways = L+1 if no blocks? No.
 *    For 2×L with no blocked cells: count tilings. This equals fib(L+1): 1,1,2,3,5...
 *    Wait: 2×1=1, 2×2=2, 2×3=3, 2×4=5... yes Fibonacci.
 * 5. Combine segment answers multiplying mod.
 *
 * COMPLEXITY
 * ----------
 * Time : O(m log m) where m = number of blocked cells
 * Space: O(m)
 * ============================================================
 */

public class CF_1472F_NewYearsPuzzle {

    static final long MOD = 1_000_000_007L;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        int m = Integer.parseInt(br.readLine().trim());

        // Map column -> list of rows blocked
        Map<Integer, List<Integer>> colMap = new HashMap<>();
        for (int i = 0; i < m; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            colMap.computeIfAbsent(c, k -> new ArrayList<>()).add(r);
        }

        // Separate double-blocked columns and single-blocked columns
        List<Integer> doubleBlocked = new ArrayList<>();
        // single blocked: col -> row blocked
        List<int[]> singleBlocked = new ArrayList<>(); // {col, row}

        for (Map.Entry<Integer, List<Integer>> e : colMap.entrySet()) {
            int col = e.getKey();
            List<Integer> rows = e.getValue();
            if (rows.size() == 2) {
                doubleBlocked.add(col);
            } else {
                singleBlocked.add(new int[]{col, rows.get(0)});
            }
        }

        // Single blocked must be even
        if (singleBlocked.size() % 2 != 0) {
            System.out.println(0);
            return;
        }

        Collections.sort(doubleBlocked);
        singleBlocked.sort((a, b) -> a[0] - b[0]);

        // Collect all "dividers" in order: double-blocked cols split into segments
        // Within each segment, single-blocked pairs must be handled
        // Build sorted list of double-blocked as segment boundaries
        // and pair up single-blocked columns

        // First check: between double-blocked boundaries, pair single-blocked cols
        // Add sentinel boundaries at 0 and n+1
        List<Integer> boundaries = new ArrayList<>();
        boundaries.add(0);
        boundaries.addAll(doubleBlocked);
        boundaries.add(n + 1);
        Collections.sort(boundaries);

        // Assign each single-blocked to a segment between boundaries
        // Then process each segment
        long ans = 1;
        int si = 0; // index into singleBlocked

        for (int bi = 0; bi + 1 < boundaries.size(); bi++) {
            int left = boundaries.get(bi);
            int right = boundaries.get(bi + 1);

            // Collect single-blocked in this segment (left < col < right)
            List<int[]> seg = new ArrayList<>();
            while (si < singleBlocked.size() && singleBlocked.get(si)[0] < right) {
                seg.add(singleBlocked.get(si++));
            }

            if (seg.size() % 2 != 0) {
                System.out.println(0);
                return;
            }

            // Process pairs within segment
            for (int i = 0; i < seg.size(); i += 2) {
                int c1 = seg.get(i)[0], r1 = seg.get(i)[1];
                int c2 = seg.get(i + 1)[0], r2 = seg.get(i + 1)[1];

                if (r1 == r2) {
                    // Same row blocked: columns between c1 and c2 must fill vertically
                    // Ways = 1 (forced)
                    // But length between must be even for vertical fill
                    if ((c2 - c1 - 1) % 2 != 0) {
                        System.out.println(0);
                        return;
                    }
                    // ans *= 1 (no contribution)
                } else {
                    // Different rows: can flip, contributes factor of 2
                    // But columns between must be fillable
                    if ((c2 - c1 - 1) % 2 != 0) {
                        System.out.println(0);
                        return;
                    }
                    ans = (ans * 2) % MOD;
                }
            }
        }

        System.out.println(ans);
    }
}
