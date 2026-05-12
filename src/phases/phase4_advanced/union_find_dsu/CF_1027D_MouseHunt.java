package phases.phase4_advanced.union_find_dsu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1027D — Mouse Hunt
 * Link   : https://codeforces.com/problemset/problem/1027/D
 * Rating : 1700  |  Tags: DSU, functional graph, cycles
 * Topic  : Phase 4: Advanced > Union Find / DSU
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Functional graph: n nodes, each node i has exactly one outgoing edge to next[i].
 * Each node has a cost c[i]. Place traps to block all cycles (a mouse starting
 * anywhere must hit a trap). Find minimum cost to block all cycles.
 *
 * EXAMPLE
 * -------
 * Input:  n=3, next=[2,3,1], costs=[1,2,3]
 * Output: 1  (trap cheapest node in the cycle: min(1,2,3)=1)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. In a functional graph, each connected component has exactly one cycle with trees hanging off.
 * 2. Find all cycles. For each cycle, place one trap at the minimum cost node.
 * 3. Detect cycles using DFS with coloring (white/gray/black) or Floyd's algorithm.
 * 4. For each cycle, answer += min(c[i]) for i in cycle.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1027D_MouseHunt {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        int[] next = new int[n + 1];
        int[] cost = new int[n + 1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) next[i] = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) cost[i] = Integer.parseInt(st.nextToken());

        // Find cycles in functional graph
        int[] state = new int[n + 1]; // 0=unvisited, 1=in-progress, 2=done
        int[] visitTime = new int[n + 1];
        long totalCost = 0;

        for (int start = 1; start <= n; start++) {
            if (state[start] != 0) continue;

            // Walk from start, tracking the path
            int[] path = new int[n + 1];
            int pathLen = 0;
            int cur = start;

            while (state[cur] == 0) {
                state[cur] = 1;
                visitTime[cur] = pathLen;
                path[pathLen++] = cur;
                cur = next[cur];
            }

            if (state[cur] == 1) {
                // Found a new cycle; cur is on the cycle
                int cycleStart = visitTime[cur];
                int minCost = Integer.MAX_VALUE;
                for (int i = cycleStart; i < pathLen; i++) {
                    minCost = Math.min(minCost, cost[path[i]]);
                }
                totalCost += minCost;
            }

            // Mark all nodes in this path as done
            for (int i = 0; i < pathLen; i++) {
                state[path[i]] = 2;
            }
        }

        System.out.println(totalCost);
    }
}
