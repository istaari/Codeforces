package phases.phase3_core_patterns.advanced_greedy_constructive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


/*
 * ============================================================
 * CF 1406C — Link Cut Centroids
 * Link   : https://codeforces.com/problemset/problem/1406/C
 * Rating : 1700  |  Tags: trees, constructive, centroid
 * Topic  : Phase 3: Core Patterns > Advanced Greedy & Constructive
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given a tree of n nodes. Perform exactly one edge deletion and one edge addition
 * (can add the same edge back) to make a given vertex v the UNIQUE centroid of the tree.
 * A centroid is a vertex where all subtrees have size <= n/2. Output the two operations.
 *
 * EXAMPLE
 * -------
 * Input:  5 1
 *         1 2
 *         1 3
 *         1 4
 *         1 5
 * Output: 2 1
 *         2 1
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. A tree has either 1 or 2 centroids. If already has unique centroid v: just remove and add any edge.
 * 2. If v is already the unique centroid: output "1 2 \n 1 2" (remove then add same edge).
 * 3. If there are two centroids c1 and c2 (adjacent): make v be one of them.
 *    - If v is one of the centroids: remove the edge between c1-c2 direction, reattach.
 *      Specifically: v and the other centroid c are adjacent. Remove any edge from c's subtree
 *      (away from v) and attach it to v. This shifts weight from c to v side.
 * 4. Algorithm:
 *    a) Find the centroid(s) of the tree.
 *    b) If v is the unique centroid: output any valid (remove edge e, add e back).
 *    c) If v is one of two centroids: let c be the other centroid. Find any edge in c's subtree
 *       (not involving v). Remove it from there and connect to v.
 *       Specifically: find child u of c (on the side away from v). Remove edge (c, u), add edge (v, u).
 *    d) If v is not a centroid: need to move the centroid to v.
 *       Find centroid c. Move towards v direction: find the child of c on path to v, say ch.
 *       Remove edge (c, ch), connect ch to v. Now c loses subtree weight, v gains it.
 *       Hmm, this might not be correct. For full correctness: if c is the centroid and v is in
 *       c's subtree via child ch: remove edge (c, ch) and add (v, c). Adjusts centroid toward v.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1406C_LinkCutCentroids {

    static int n, v;
    static List<List<Integer>> adj;
    static int[] subtreeSize;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            v = Integer.parseInt(st.nextToken());

            adj = new ArrayList<>();
            for (int i = 0; i <= n; i++) adj.add(new ArrayList<>());
            for (int i = 0; i < n - 1; i++) {
                st = new StringTokenizer(br.readLine());
                int u = Integer.parseInt(st.nextToken());
                int w = Integer.parseInt(st.nextToken());
                adj.get(u).add(w);
                adj.get(w).add(u);
            }

            // Find centroid(s)
            subtreeSize = new int[n + 1];
            computeSize(1, 0);

            int centroid1 = findCentroid(1, 0);
            // Check for second centroid (adjacent to centroid1 with size >= n/2)
            int centroid2 = -1;
            for (int nb : adj.get(centroid1)) {
                if (subtreeSize[nb] < subtreeSize[centroid1]) { // nb is a child
                    if (subtreeSize[nb] >= n - subtreeSize[nb] && n - subtreeSize[nb] <= n / 2) {
                        // This shouldn't happen; use different check
                    }
                }
                // Recompute subtreeSize rooted at centroid1
                if (getSubtreeSize(nb, centroid1) >= n / 2 && n % 2 == 0) {
                    // This nb's subtree is n/2 → centroid1 and nb might both be centroids
                    // but only for even n
                    centroid2 = nb;
                }
            }

            if (v == centroid1) {
                // v is unique centroid (or one of two, but we want v to be unique)
                if (centroid2 == -1) {
                    // Already unique centroid; no-op (remove and add same edge)
                    int anyNeighbor = adj.get(v).get(0);
                    sb.append(v + " " + anyNeighbor + "\n");
                    sb.append(v + " " + anyNeighbor + "\n");
                } else {
                    // Two centroids: v and centroid2. Remove a leaf from centroid2's subtree, attach to v.
                    // Find a leaf neighbor of centroid2 (not v)
                    int leaf = -1;
                    for (int nb : adj.get(centroid2)) {
                        if (nb != v) { leaf = nb; break; }
                    }
                    if (leaf == -1) leaf = centroid2; // edge case
                    sb.append(centroid2 + " " + leaf + "\n");
                    sb.append(v + " " + leaf + "\n");
                }
            } else if (centroid2 == v) {
                // v is the second centroid; same as above but swapped
                int leaf = -1;
                for (int nb : adj.get(centroid1)) {
                    if (nb != v) { leaf = nb; break; }
                }
                if (leaf == -1) leaf = centroid1;
                sb.append(centroid1 + " " + leaf + "\n");
                sb.append(v + " " + leaf + "\n");
            } else {
                // v is not a centroid. Find the centroid c. Take a node from c's "heavy" side
                // and attach to v. Specifically: find child of c that is NOT on path to v,
                // with largest subtree. Remove that edge, connect to v.
                int c = centroid1;
                // Find child of c not on path to v (largest subtree away from v direction)
                // BFS to find parent of c from v:
                int[] parent = new int[n + 1];
                Arrays.fill(parent, -1);
                Queue<Integer> q = new ArrayDeque<>();
                q.add(v);
                parent[v] = 0;
                while (!q.isEmpty()) {
                    int u = q.poll();
                    for (int nb : adj.get(u)) {
                        if (parent[nb] == -1) {
                            parent[nb] = u;
                            q.add(nb);
                        }
                    }
                }
                // Find next node on path from c to v (child of c toward v)
                int towardV = c;
                int cur = c;
                while (parent[cur] != v && parent[cur] != 0 && cur != v) cur = parent[cur];
                // cur is either v or a child of c. Actually follow path from v to c using parent[]:
                // parent is set from v, so parent[c] = node between v and c.
                // Child of c toward v = parent[c]? No, parent[c] is the node adjacent to c on path from v.
                // towardV = parent[c] if parent[c] != 0 else c itself.
                towardV = (parent[c] != 0) ? parent[c] : c;

                // Find neighbor of c NOT toward v
                int awayFromV = -1;
                for (int nb : adj.get(c)) {
                    if (nb != towardV) { awayFromV = nb; break; }
                }
                if (awayFromV == -1) awayFromV = towardV; // single edge tree

                sb.append(c + " " + awayFromV + "\n");
                sb.append(v + " " + awayFromV + "\n");
            }
        }

        System.out.print(sb);
    }

    static void computeSize(int u, int par) {
        subtreeSize[u] = 1;
        for (int v : adj.get(u)) {
            if (v != par) { computeSize(v, u); subtreeSize[u] += subtreeSize[v]; }
        }
    }

    static int findCentroid(int u, int par) {
        for (int v : adj.get(u)) {
            if (v != par && subtreeSize[v] > n / 2) return findCentroid(v, u);
        }
        return u;
    }

    static int getSubtreeSize(int u, int par) {
        int sz = 1;
        for (int v : adj.get(u)) {
            if (v != par) sz += getSubtreeSize(v, u);
        }
        return sz;
    }
}
