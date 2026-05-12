package phases.phase4_advanced.union_find_dsu;

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
 * CF 1100E — Andrew and Taxi
 * Link   : https://codeforces.com/problemset/problem/1100/E
 * Rating : 1800  |  Tags: DSU, binary search, topological sort
 * Topic  : Phase 4: Advanced > Union Find / DSU
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Directed graph with n nodes and m edges, each with cost c[i].
 * Find the minimum cost C such that reversing all edges with cost <= C
 * makes the graph a DAG (acyclic). Then output one valid topological ordering
 * of the resulting DAG.
 *
 * EXAMPLE
 * -------
 * Input:  n=3, m=3, edges: (1,2,1),(2,3,2),(3,1,3)
 * Output: C=2, order: 1 2 3 (after removing cost<=2 edges: only edge 3->1 remains,
 *         but that's still a cycle? Wait: removing cost<=C means we reverse them,
 *         so for C=2, we reverse edges (1,2,1) and (2,3,2). 1->2 becomes 2->1,
 *         2->3 becomes 3->2. Remaining directed edges: 3->2, 2->1, 3->1. DAG? Yes.)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Binary search on C in [0, max_cost].
 * 2. For a given C: keep only edges with cost > C (ignore/remove edges with cost <= C).
 *    Check if remaining directed graph is a DAG (topological sort exists).
 * 3. If DAG exists at cost C but not at C-1: C is minimum.
 * 4. Binary search: check feasibility using Kahn's topological sort.
 * 5. Output: use the topological ordering from the final C check.
 *
 * COMPLEXITY
 * ----------
 * Time : O((n+m) log(max_cost))
 * Space: O(n+m)
 * ============================================================
 */

public class CF_1100E_AndrewAndTaxi {

    static int n, m;
    static int[] eu, ev, ew;

    static int[] topoSort(int minCost) {
        List<Integer>[] adj = new List[n + 1];
        int[] indegree = new int[n + 1];
        for (int i = 1; i <= n; i++) adj[i] = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            if (ew[i] > minCost) {
                adj[eu[i]].add(ev[i]);
                indegree[ev[i]]++;
            }
        }

        Queue<Integer> q = new LinkedList<>();
        for (int i = 1; i <= n; i++) if (indegree[i] == 0) q.offer(i);

        int[] order = new int[n];
        int idx = 0;
        while (!q.isEmpty()) {
            int u = q.poll();
            order[idx++] = u;
            for (int v : adj[u]) {
                if (--indegree[v] == 0) q.offer(v);
            }
        }

        return idx == n ? order : null;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        eu = new int[m]; ev = new int[m]; ew = new int[m];
        int maxW = 0;
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            eu[i] = Integer.parseInt(st.nextToken());
            ev[i] = Integer.parseInt(st.nextToken());
            ew[i] = Integer.parseInt(st.nextToken());
            maxW = Math.max(maxW, ew[i]);
        }

        // Binary search on minimum C
        int lo = 0, hi = maxW, ans = maxW;
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            if (topoSort(mid) != null) {
                ans = mid;
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }

        int[] order = topoSort(ans);
        StringBuilder sb = new StringBuilder();
        sb.append(ans).append('\n');
        for (int i = 0; i < n; i++) {
            sb.append(order[i]);
            if (i < n - 1) sb.append(' ');
        }
        System.out.println(sb);
    }
}
