package phases.phase3_core_patterns.graph_fundamentals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


/*
 * ============================================================
 * CF 1360F — Spy Detective
 * Link   : https://codeforces.com/problemset/problem/1360/F
 * Rating : 1500  |  Tags: dfs, trees, hashing
 * Topic  : Phase 3: Core Patterns > Graph Fundamentals
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * A tree of n nodes. A "bug" is placed on one node. Over k steps, the bug moves
 * along edges (can stay or move to adjacent node). Given the sequence of nodes
 * where the bug WAS seen (one per step from t=0 to t=k), find which nodes the
 * bug could be at at time t=k (given uncertainty in movement). Actually:
 *
 * ACTUAL CF 1360F: tree, n nodes. One node is selected (spy). For k days,
 * each day the spy is at some node. The spy can stay at the same node or move
 * to an adjacent node each step. Given n and k, find nodes that could be the
 * spy's position on day k if they are reachable from any starting node in k steps
 * (i.e., within distance k from any node? Or: find node(s) reachable from ALL
 * starting nodes in exactly k steps?).
 *
 * RE-READ: CF 1360F - "Spy" starts at any node. Over k days, makes k moves (each
 * move goes to adjacent node or stays). Find all nodes v such that v is reachable
 * from EVERY node in at most k steps. (The spy could have started anywhere and
 * reached v in k steps.) The spy is "caught" at nodes reachable from all starting
 * positions in exactly k steps.
 * Answer: nodes v where max_distance_from_any_node(v) <= k, i.e., eccentricity(v) <= k.
 * These are nodes within the "k-center" of the tree.
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Eccentricity of v = max distance from v to any other node = max(dist(v, u)) for all u.
 * 2. In a tree, eccentricity(v) = max(dist to one end of diameter, dist to other end).
 * 3. Find diameter endpoints u1, u2. Eccentricity of v = max(dist(v,u1), dist(v,u2)).
 * 4. Nodes with eccentricity <= k are the answer. Count and output them.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1360F_SpyDetective {

    static int n;
    static List<List<Integer>> adj;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) adj.add(new ArrayList<>());
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        // Find diameter endpoints
        int u1 = bfsFar(1);
        int u2 = bfsFar(u1);

        // Distances from u1 and u2
        int[] d1 = bfsDist(u1);
        int[] d2 = bfsDist(u2);

        int ans = 0;
        for (int v = 1; v <= n; v++) {
            if (Math.max(d1[v], d2[v]) <= k) ans++;
        }

        System.out.println(ans);
    }

    static int bfsFar(int start) {
        int[] dist = bfsDist(start);
        int far = start;
        for (int i = 1; i <= n; i++) if (dist[i] > dist[far]) far = i;
        return far;
    }

    static int[] bfsDist(int start) {
        int[] dist = new int[n + 1];
        Arrays.fill(dist, -1);
        dist[start] = 0;
        Queue<Integer> q = new ArrayDeque<>();
        q.add(start);
        while (!q.isEmpty()) {
            int u = q.poll();
            for (int nb : adj.get(u)) {
                if (dist[nb] == -1) {
                    dist[nb] = dist[u] + 1;
                    q.add(nb);
                }
            }
        }
        return dist;
    }
}
