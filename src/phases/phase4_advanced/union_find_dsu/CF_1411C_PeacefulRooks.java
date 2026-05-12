package phases.phase4_advanced.union_find_dsu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1411C — Peaceful Rooks
 * Link   : https://codeforces.com/problemset/problem/1411/C
 * Rating : 1700  |  Tags: DSU, graphs, cycles
 * Topic  : Phase 4: Advanced > Union Find / DSU
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * n rooks on an n×n board, one per row and column (rook i is at (r[i], c[i])).
 * You can move a rook along its row or column. Find the minimum number of moves
 * to make rook i be at (i, i) for all i (the diagonal).
 *
 * EXAMPLE
 * -------
 * Input:  n=3, rooks: (2,3),(3,1),(1,2)
 * Output: 3
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Rooks not on diagonal need to be moved. A rook at (r, c) with r != c needs moves.
 * 2. Model as graph: create node for each diagonal position 1..n. Rook at (r, c) creates
 *    an edge between r and c (it "connects" row r and column c in the diagonal graph).
 * 3. If the graph on diagonal positions is a forest (no cycles), answer = number of edges
 *    (one move per rook not on diagonal, since they form trees).
 *    Each cycle requires one extra move (the cycle forces one additional move).
 * 4. Use DSU: for each rook at (r, c) with r != c:
 *    - If find(r) != find(c): union them. 1 move.
 *    - If find(r) == find(c): cycle! 2 moves needed (one extra).
 * 5. Answer = (number of rooks not on diagonal) + (number of cycles).
 *
 * COMPLEXITY
 * ----------
 * Time : O(n * alpha(n))
 * Space: O(n)
 * ============================================================
 */

public class CF_1411C_PeacefulRooks {

    static int[] parent, rank;

    static int find(int x) {
        if (parent[x] != x) parent[x] = find(parent[x]);
        return parent[x];
    }

    static boolean union(int a, int b) {
        a = find(a); b = find(b);
        if (a == b) return false;
        if (rank[a] < rank[b]) { int t = a; a = b; b = t; }
        parent[b] = a;
        if (rank[a] == rank[b]) rank[a]++;
        return true;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        parent = new int[n + 1];
        rank = new int[n + 1];
        for (int i = 0; i <= n; i++) parent[i] = i;

        int moves = 0;
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            if (r == c) continue; // already on diagonal
            moves++; // needs at least 1 move
            if (!union(r, c)) {
                moves++; // cycle: needs extra move
            }
        }

        System.out.println(moves);
    }
}
