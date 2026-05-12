package phases.phase3_core_patterns.graph_fundamentals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


/*
 * ============================================================
 * CF 1176E — Cover It!
 * Link   : https://codeforces.com/problemset/problem/1176/E
 * Rating : 1500  |  Tags: bfs, bipartite, greedy
 * Topic  : Phase 3: Core Patterns > Graph Fundamentals
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given a connected graph. Determine if you can select at most ceil(n/2) vertices
 * such that every edge has at least one endpoint in the selected set.
 * (Vertex cover of size <= ceil(n/2).)
 *
 * EXAMPLE
 * -------
 * Input:  4 4
 *         1 2
 *         2 3
 *         3 4
 *         4 1
 * Output: YES
 *         2
 *         2 4
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. For any BFS tree (spanning tree from any node), one of the two BFS levels
 *    (even or odd depth) forms a vertex cover. This is because every edge in the
 *    original graph either spans BFS levels (one endpoint at level d, other at d+1)
 *    or goes between nodes of the same level.
 *    Actually: for a BFS tree, all non-tree edges connect nodes of the same BFS level
 *    or adjacent levels. The tree edges connect consecutive levels. Selecting all
 *    odd-depth or all even-depth nodes covers all tree edges. Non-tree edges connect
 *    same or adjacent levels — the larger set (odd or even) covers them.
 * 2. In BFS from any node: nodes at even depth = one set, odd depth = other set.
 *    Both sets together form a vertex cover (every edge has at least one endpoint in
 *    one of the sets). Take the smaller set — it has at most ceil(n/2) nodes.
 * 3. Output the smaller of {even-depth nodes} or {odd-depth nodes} (by BFS from node 1).
 *
 * COMPLEXITY
 * ----------
 * Time : O(n + m)
 * Space: O(n + m)
 * ============================================================
 */

public class CF_1176E_CoverIt {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());

            List<List<Integer>> adj = new ArrayList<>();
            for (int i = 0; i <= n; i++) adj.add(new ArrayList<>());
            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                int u = Integer.parseInt(st.nextToken());
                int v = Integer.parseInt(st.nextToken());
                adj.get(u).add(v);
                adj.get(v).add(u);
            }

            // BFS from node 1
            int[] depth = new int[n + 1];
            Arrays.fill(depth, -1);
            depth[1] = 0;
            Queue<Integer> q = new ArrayDeque<>();
            q.add(1);
            while (!q.isEmpty()) {
                int u = q.poll();
                for (int nb : adj.get(u)) {
                    if (depth[nb] == -1) {
                        depth[nb] = depth[u] + 1;
                        q.add(nb);
                    }
                }
            }

            // Even-depth nodes and odd-depth nodes
            List<Integer> even = new ArrayList<>(), odd = new ArrayList<>();
            for (int i = 1; i <= n; i++) {
                if (depth[i] % 2 == 0) even.add(i);
                else odd.add(i);
            }

            // Take the smaller set
            List<Integer> chosen = even.size() <= odd.size() ? even : odd;
            sb.append(chosen.size()).append('\n');
            for (int i = 0; i < chosen.size(); i++) {
                if (i > 0) sb.append(' ');
                sb.append(chosen.get(i));
            }
            sb.append('\n');
        }

        System.out.print(sb);
    }
}
