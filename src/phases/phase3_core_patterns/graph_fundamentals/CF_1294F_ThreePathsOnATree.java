package phases.phase3_core_patterns.graph_fundamentals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


/*
 * ============================================================
 * CF 1294F — Three Paths on a Tree
 * Link   : https://codeforces.com/problemset/problem/1294/F
 * Rating : 1700  |  Tags: bfs, trees, diameter
 * Topic  : Phase 3: Core Patterns > Graph Fundamentals
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given a tree of n nodes. Find three vertices a, b, c such that the total
 * number of distinct edges in the union of paths (a,b), (a,c), (b,c) is maximized.
 * Output this maximum value and the three vertices.
 *
 * EXAMPLE
 * -------
 * Input:  5
 *         1 2
 *         2 3
 *         3 4
 *         4 5
 * Output: 4
 *         2 4 1
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. The union of paths (a,b), (a,c), (b,c) in a tree forms a "Y-shape" (Steiner tree).
 *    The total edges = (d(a,b) + d(a,c) + d(b,c)) / 2 where d is tree distance.
 * 2. To maximize this sum: find the diameter (a,b) first. Then find c maximizing
 *    d(a,c) + d(b,c) - d(a,b) = 2*d(c, path(a,b)). So find c farthest from path(a,b).
 *    Equivalently: find c that maximizes d(a,c) + d(b,c) (since d(a,b) is fixed).
 *    c = argmax d(a,c) + d(b,c).
 * 3. Algorithm:
 *    Step 1: BFS from any node to find diameter endpoint a.
 *    Step 2: BFS from a to find diameter endpoint b (and distances from a).
 *    Step 3: BFS from b to find distances from b.
 *    Step 4: Find c = argmax(da[c] + db[c]) over all nodes c.
 *    Step 5: Answer = (da[b] + da[c] + db[c]) / 2.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1294F_ThreePathsOnATree {

    static int n;
    static List<List<Integer>> adj;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine().trim());
        adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) adj.add(new ArrayList<>());
        for (int i = 0; i < n - 1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        // Step 1: find diameter endpoint a
        int a = bfsFar(1);
        // Step 2: find diameter endpoint b and distances from a
        int[] da = bfsDist(a);
        int b = maxNode(da);
        // Step 3: distances from b
        int[] db = bfsDist(b);

        // Step 4: find c maximizing da[c] + db[c]
        int c = -1;
        long maxSum = -1;
        for (int v = 1; v <= n; v++) {
            long s = (long) da[v] + db[v];
            if (s > maxSum) { maxSum = s; c = v; }
        }

        // Answer = (d(a,b) + d(a,c) + d(b,c)) / 2
        long ans = ((long) da[b] + da[c] + db[c]) / 2;

        System.out.println(ans);
        System.out.println(a + " " + b + " " + c);
    }

    static int bfsFar(int start) {
        return maxNode(bfsDist(start));
    }

    static int maxNode(int[] dist) {
        int best = 1;
        for (int i = 2; i <= n; i++) if (dist[i] > dist[best]) best = i;
        return best;
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
