package phases.phase5_cm_push.advanced_graphs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1118E — Yet Another Ball Problem
 * Link   : https://codeforces.com/problemset/problem/1118/E
 * Rating : 1800  |  Tags: graphs, constructive, 2-coloring
 * Topic  : Phase 5: CM Push > Advanced Graphs
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * n balls, each ball i has a row r[i] and column c[i].
 * Assign each ball to one of 2 boxes. The constraint is:
 * no two balls sharing the same row OR the same column can
 * be in the same box. Find a valid assignment or output -1.
 *
 * EXAMPLE
 * -------
 * Input:  4
 *         1 1
 *         1 2
 *         2 1
 *         2 2
 * Output: 1 2 2 1
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Build a graph: two balls conflict if they share a row or column.
 * 2. We need a 2-coloring of this graph (bipartite check).
 * 3. Instead of building edges between all pairs (O(n^2)):
 *    - Create virtual nodes for each row and column.
 *    - Ball i connects to node row[i] and node col[i].
 *    - The conflict graph of balls becomes: two balls conflict iff
 *      they share a row-node or col-node.
 * 4. BUT this approach gives us a graph where we need: in the
 *    bipartite structure, nodes at the same row/col are enemies.
 *    Actually: model as DSU with parity (bipartite DSU).
 *    - For each row r, all balls in row r must alternate between
 *      box 1 and box 2 → they form a chain that alternates.
 *    - Same for columns.
 * 5. Use a bipartite DSU (weighted DSU with parity):
 *    - Each ball ball[i] and its row partner must be in different boxes.
 *    - Process row groups: for each row, chain all balls in that row
 *      with alternating assignments. Same for columns.
 *    - If a contradiction is found (same ball forced into both boxes),
 *      output -1.
 * 6. Alternatively (cleaner): build graph where each row and column
 *    is a node, and ball i is an edge between row[i] and col[i].
 *    2-color the graph (bipartite check on this row/col graph).
 *    Each ball gets color based on its edge's endpoint coloring.
 *    If the graph is bipartite, solution exists; otherwise -1.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n * alpha(n)) with DSU
 * Space: O(n)
 * ============================================================
 */

public class CF_1118E_YetAnotherBallProblem {

    // Bipartite DSU with parity
    // parent[x], rank[x], parity[x] = parity relative to parent
    static int[] parent, rnk, parity;

    static int[] findWithParity(int x) {
        if (parent[x] == x) return new int[]{x, 0};
        int[] res = findWithParity(parent[x]);
        parent[x] = res[0];
        parity[x] ^= res[1]; // path compression with parity update
        return new int[]{parent[x], parity[x]};
    }

    // Unite x and y with constraint: parity(x) XOR parity(y) = p
    // Returns false if contradiction found
    static boolean unite(int x, int y, int p) {
        int[] rx = findWithParity(x);
        int[] ry = findWithParity(y);
        int rootX = rx[0], parX = rx[1];
        int rootY = ry[0], parY = ry[1];

        if (rootX == rootY) {
            // Check consistency: parX XOR parY should equal p
            return (parX ^ parY) == p;
        }

        // Merge: parity[rootX] relative to rootY = p XOR parX XOR parY
        if (rnk[rootX] < rnk[rootY]) {
            parent[rootX] = rootY;
            parity[rootX] = p ^ parX ^ parY;
        } else if (rnk[rootX] > rnk[rootY]) {
            parent[rootY] = rootX;
            parity[rootY] = p ^ parX ^ parY;
        } else {
            parent[rootY] = rootX;
            parity[rootY] = p ^ parX ^ parY;
            rnk[rootX]++;
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());

        int[] row = new int[n], col = new int[n];
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            row[i] = Integer.parseInt(st.nextToken()) - 1;
            col[i] = Integer.parseInt(st.nextToken()) - 1;
        }

        // Nodes 0..n-1: balls; n..2n-1: rows; 2n..3n-1: cols
        // Actually we need to map rows and cols to node IDs.
        // Row r -> node n + r; Col c -> node 2n + c
        // Ball i: must be in different box from row[i] and col[i].
        // So: unite(ball_i, row_node(row[i]), 1) and unite(ball_i, col_node(col[i]), 1)
        // This means ball and its row are in opposite boxes → same row balls differ.

        int total = 3 * n; // over-allocate; rows/cols up to n
        parent = new int[total];
        rnk = new int[total];
        parity = new int[total];
        for (int i = 0; i < total; i++) parent[i] = i;

        boolean ok = true;
        for (int i = 0; i < n && ok; i++) {
            int ballNode = i;
            int rowNode = n + row[i];
            int colNode = 2 * n + col[i];
            // ball must differ from row node (parity 1)
            ok = unite(ballNode, rowNode, 1);
            if (ok) ok = unite(ballNode, colNode, 1);
        }

        if (!ok) {
            System.out.println(-1);
            return;
        }

        // Assign boxes based on parity relative to root
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            int[] res = findWithParity(i);
            int box = parity[i] + 1; // parity 0 -> box 1, parity 1 -> box 2
            if (i > 0) sb.append(' ');
            sb.append(box);
        }
        System.out.println(sb);
    }
}
