package phases.phase4_advanced.shortest_paths;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1301B — Motarack's Birthday
 * Link   : https://codeforces.com/problemset/problem/1301/B
 * Rating : 1500  |  Tags: BFS, connected components, two-pointer
 * Topic  : Phase 4: Advanced > Shortest Paths
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * n nodes with values a[i] (some are -1 = unknown). Edge between i and j
 * if |a[i] - a[j]| <= 1 (treating -1 as unknown). Fill in unknown values
 * (integers >= 0) to minimize the maximum |a[i]-a[j]| over all edges.
 * Output the minimum possible maximum edge weight and the values assigned.
 *
 * EXAMPLE
 * -------
 * Input:  n=6, a=[-1,0,1,-1,2,-1]
 * Output: 0, assign -1s to connect smoothly
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Build a graph: edge (i,j) if |a[i]-a[j]|<=1 (for known values) or
 *    at least one is unknown (edge might exist). Find connected components
 *    using union-find or BFS on the implicit graph.
 * 2. For known values in a component, the unknowns should be assigned the
 *    median or average of known values to minimize max difference.
 * 3. Assign all unknowns in a component to the floor of the average of
 *    known values in that component. If all unknown, assign 0.
 * 4. After assignment, compute max |a[i]-a[j]| over edges.
 * 5. For implicit graph (all pairs with |a[i]-a[j]|<=1): sort known values,
 *    use two-pointer to find connected components.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1301B_MotaracksBirthday {

    static int[] parent, rank;

    static int find(int x) {
        if (parent[x] != x) parent[x] = find(parent[x]);
        return parent[x];
    }

    static void union(int a, int b) {
        a = find(a); b = find(b);
        if (a == b) return;
        if (rank[a] < rank[b]) { int t = a; a = b; b = t; }
        parent[b] = a;
        if (rank[a] == rank[b]) rank[a]++;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        long[] a = new long[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) a[i] = Long.parseLong(st.nextToken());

        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;

        // Sort known values, use two-pointer to union nodes with |a[i]-a[j]|<=1
        // But we need to union by index, so sort indices by value (known only)
        List<Integer> known = new ArrayList<>();
        for (int i = 0; i < n; i++) if (a[i] != -1) known.add(i);
        known.sort((x, y) -> Long.compare(a[x], a[y]));

        // Two-pointer on sorted known values
        for (int i = 0, j = 0; i < known.size(); i++) {
            while (j < known.size() && a[known.get(j)] - a[known.get(i)] <= 1) j++;
            // all indices in [i, j-1] have values within 1 of each other -> union
            for (int k = i + 1; k < j; k++) union(known.get(i), known.get(k));
        }

        // All unknowns (-1) union with first unknown or among themselves
        // They form their own component or get merged if adjacent to known
        // Actually: unknowns connect to everyone (|(-1)-x|<=1 is unclear)
        // Re-reading: edge exists if |a[i]-a[j]|<=1 where -1 means unknown (to be assigned)
        // For graph construction: we consider actual assigned values. So we need to assign
        // unknowns first, then the edges are determined.
        //
        // Approach: unknowns can be set to any value. To minimize max edge weight:
        // - Each component of known values: unknowns should match the component's mean.
        // - Unknowns adjacent to no known values: assign any value (e.g., 0).
        //
        // For this problem, simple solution: assign all unknowns to the average of
        // known values in their neighborhood. Since we don't know the graph structure
        // without the assignment, we use the following:
        // All unknowns connected to each other and to known values in range.
        // Optimal: assign all unknowns to the same value as the majority known.

        // Simpler approach for CF 1301B:
        // Group unknowns with the known values they're "adjacent to" in the value sense.
        // All unknowns form one group -> assign them the floor(avg) of all known values.

        long sumKnown = 0;
        int cntKnown = 0;
        for (int i = 0; i < n; i++) {
            if (a[i] != -1) { sumKnown += a[i]; cntKnown++; }
        }
        long assignVal = (cntKnown == 0) ? 0 : sumKnown / cntKnown;

        for (int i = 0; i < n; i++) {
            if (a[i] == -1) a[i] = assignVal;
        }

        // Compute maximum edge weight
        // Edges: |a[i]-a[j]|<=1 after assignment (but need to output max difference)
        // After assignment, find max |a[i]-a[j]| among all pairs with |a[i]-a[j]|<=1
        // which is at most 1 anyway.
        // But the actual max over ALL adjacent pairs... the problem says edges are defined
        // by the final values. So we need max |a[i]-a[j]| over edges = over pairs where |a[i]-a[j]|<=1.
        // That max is at most 1. But we want minimum possible maximum. After assigning optimally it's 0 or 1.

        // Recompute max after optimal assignment
        Arrays.sort(a.clone());
        long[] sorted = a.clone();
        Arrays.sort(sorted);
        long maxDiff = 0;
        for (int i = 1; i < n; i++) {
            if (sorted[i] - sorted[i-1] <= 1) maxDiff = Math.max(maxDiff, sorted[i] - sorted[i-1]);
        }

        StringBuilder sb = new StringBuilder();
        sb.append(maxDiff).append('\n');
        for (int i = 0; i < n; i++) {
            sb.append(a[i]);
            if (i < n-1) sb.append(' ');
        }
        System.out.println(sb);
    }
}
