package phases.phase3_core_patterns.graph_fundamentals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1037D — Valid BFS?
 * Link   : https://codeforces.com/problemset/problem/1037/D
 * Rating : 1600  |  Tags: bfs, trees, implementation
 * Topic  : Phase 3: Core Patterns > Graph Fundamentals
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given a tree of n nodes and a permutation p[1..n]. Check if p is a valid
 * BFS traversal order of the tree starting from node p[1].
 *
 * EXAMPLE
 * -------
 * Input:  4
 *         1 2
 *         1 3
 *         1 4
 *         1 2 3 4
 * Output: Yes
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Simulate BFS. Use a queue, starting with p[0] (first element of permutation).
 *    At each step, dequeue a node u. Its neighbors in the BFS order must be
 *    the next elements in p (in some order).
 * 2. Process neighbors of u that haven't been visited yet. Count how many there are.
 *    They must appear as the next (count) elements in p (in any order among themselves).
 *    Check each: if the next element in p is not a neighbor of u, output "No".
 * 3. Use a pointer into p to track which element is next. Validate using a set of
 *    neighbors of current node.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1037D_ValidBFS {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());

        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) adj.add(new ArrayList<>());
        for (int i = 0; i < n - 1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        int[] p = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) p[i] = Integer.parseInt(st.nextToken());

        boolean[] visited = new boolean[n + 1];
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        queue.add(p[0]);
        visited[p[0]] = true;
        int ptr = 1; // next position in p to be consumed
        boolean valid = true;

        while (!queue.isEmpty() && valid) {
            int u = queue.poll();
            // Collect unvisited neighbors of u
            HashSet<Integer> children = new HashSet<>();
            for (int nb : adj.get(u)) {
                if (!visited[nb]) children.add(nb);
            }
            // Next |children| elements in p must exactly be children of u
            for (int i = 0; i < children.size(); i++) {
                if (ptr >= n) { valid = false; break; }
                int next = p[ptr++];
                if (!children.contains(next)) { valid = false; break; }
                visited[next] = true;
                queue.add(next);
            }
        }

        System.out.println(valid ? "Yes" : "No");
    }
}
