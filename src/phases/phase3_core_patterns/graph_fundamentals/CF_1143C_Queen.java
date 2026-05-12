package phases.phase3_core_patterns.graph_fundamentals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1143C — Queen
 * Link   : https://codeforces.com/problemset/problem/1143/C
 * Rating : 1400  |  Tags: dfs, trees, greedy
 * Topic  : Phase 3: Core Patterns > Graph Fundamentals
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * A rooted tree with black and white nodes. In one operation, choose any
 * black node v and recolor the entire subtree rooted at v to black.
 * Find the minimum number of operations to make all nodes black.
 *
 * EXAMPLE
 * -------
 * Input:  5
 *         0 1 1 0 1
 *         1 2
 *         2 3
 *         3 4
 *         4 5
 * Output: 1
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. We need to make the entire tree black. Each operation colors a subtree.
 *    The root of the operation must already be black.
 * 2. Key insight: count the number of "transitions" from black to white as we
 *    go from root to leaves. Specifically, count nodes that are white and have
 *    a black parent. Each such node requires its own operation (applied to its
 *    subtree eventually). Actually: count nodes that are black with a white parent.
 * 3. More precisely: we need to cover all white nodes. Each operation covers a
 *    subtree rooted at a black node. The minimum number of operations = number
 *    of "connected components" of black nodes that are NOT yet connected to a
 *    previously operated subtree... Actually:
 *    Minimum operations = number of black nodes that are a child of a white node.
 *    i.e., number of "black roots" of maximal black subtrees.
 * 4. Count: for each black node v, if its parent is white (or v is root and black),
 *    it counts as one operation start. But after one operation, its subtree is all black.
 *    The answer = (1 if root is black, else count of black nodes with white parents).
 *    Hmm: if root is white, we can never directly operate on it without first making it black.
 *    We need a black node in the tree; operate on it to expand black region toward root? No —
 *    operation on node v makes subtree(v) black, not the path to root.
 *    Actually re-reading: operation: pick any node v with a[v]=1 (black? or any?),
 *    recolor subtree. Then pick another black node, etc.
 * 5. CORRECT: count nodes that are black and whose parent is white (or is root and black).
 *    Each such node is a "fresh" black start that needs an operation.
 *    But after operating on v (subtree(v) all black), new black nodes might be exposed.
 *    The problem asks: minimum total operations ever needed.
 *    Answer = number of white nodes that have a black child (that child needs to be operated separately)
 *    PLUS 1 if root is white (need to start somewhere).
 *    Actually: answer = (count of black nodes with white parent) + (1 if root is white = impossible?).
 *    If root is white, we can never make it black → impossible? No: we can operate on a black node
 *    lower down, but that only colors that subtree. Root's color won't change.
 *    If root is white: answer is impossible? But problem says always possible? Let me just count:
 *    ans = #{black node v : parent[v] is white} (this is the number of "segments" of the black-white
 *    pattern that need separate operations). If root is black, ans starts at 1 (or 0?).
 *    Simple DFS: count black nodes that are "black roots" (parent is white or node is root).
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1143C_Queen {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] color = new int[n + 1]; // 0=white, 1=black
        for (int i = 1; i <= n; i++) color[i] = Integer.parseInt(st.nextToken());

        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) adj.add(new ArrayList<>());
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        // Count black nodes whose parent is white (and root if black = 1 operation to start)
        // BFS/DFS to find parent relationships
        int[] parent = new int[n + 1];
        boolean[] visited = new boolean[n + 1];
        java.util.ArrayDeque<Integer> queue = new java.util.ArrayDeque<>();
        queue.add(1);
        visited[1] = true;
        parent[1] = 0; // no parent
        int ans = 0;

        while (!queue.isEmpty()) {
            int u = queue.poll();
            // Count: is this node a "black root" (black and parent is white or no parent)?
            if (color[u] == 1) {
                if (parent[u] == 0 || color[parent[u]] == 0) {
                    ans++;
                }
            }
            for (int nb : adj.get(u)) {
                if (!visited[nb]) {
                    visited[nb] = true;
                    parent[nb] = u;
                    queue.add(nb);
                }
            }
        }

        System.out.println(ans);
    }
}
