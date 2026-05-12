package phases.phase4_advanced.advanced_dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1385E — Directing Edges
 * Link   : https://codeforces.com/problemset/problem/1385/E
 * Rating : 1800  |  Tags: dp, topological sort, graphs
 * Topic  : Phase 4: Advanced > Advanced DP
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given n vertices and m directed edges representing "a must come before b"
 * constraints. Additionally, for each consecutive pair of integers (i, i+1)
 * that are both present, add an edge i -> i+1. Determine if a valid
 * topological ordering exists; if so, output it; else output -1.
 *
 * EXAMPLE
 * -------
 * Input:  n=4, edges: (1,3),(2,4)
 * Output: 1 2 3 4
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Build directed graph with given edges plus edges between consecutive integers.
 * 2. Run Kahn's algorithm (BFS-based topological sort).
 * 3. If all nodes processed, output order; else cycle exists, output -1.
 * 4. The consecutive edges ensure integers appear in natural order unless
 *    a constraint forces a cycle.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n + m)
 * Space: O(n + m)
 * ============================================================
 */

public class CF_1385E_DirectingEdges {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());

            List<List<Integer>> adj = new ArrayList<>();
            int[] indegree = new int[n + 1];
            for (int i = 0; i <= n; i++) adj.add(new ArrayList<>());

            // Add edges i -> i+1 for consecutive integers 1..n
            for (int i = 1; i < n; i++) {
                adj.get(i).add(i + 1);
                indegree[i + 1]++;
            }

            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                adj.get(a).add(b);
                indegree[b]++;
            }

            // Kahn's algorithm
            Queue<Integer> q = new LinkedList<>();
            for (int i = 1; i <= n; i++) {
                if (indegree[i] == 0) q.offer(i);
            }

            List<Integer> order = new ArrayList<>();
            while (!q.isEmpty()) {
                int u = q.poll();
                order.add(u);
                for (int v : adj.get(u)) {
                    if (--indegree[v] == 0) q.offer(v);
                }
            }

            if (order.size() != n) {
                sb.append(-1).append('\n');
            } else {
                for (int i = 0; i < n; i++) {
                    sb.append(order.get(i));
                    if (i < n - 1) sb.append(' ');
                }
                sb.append('\n');
            }
        }

        System.out.print(sb);
    }
}
