package phases.phase4_advanced.advanced_dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeSet;


/*
 * ============================================================
 * CF 1327E — Count Buildings
 * Link   : https://codeforces.com/problemset/problem/1327/E
 * Rating : 1700  |  Tags: dp, combinatorics, sorting
 * Topic  : Phase 4: Advanced > Advanced DP
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * n buildings with heights 1..n (a permutation). Count the number of
 * pairs (i,j) with i < j such that all buildings k strictly between
 * i and j have height less than min(h[i], h[j]).
 * This means h[i] and h[j] can "see" each other (no taller building between).
 *
 * EXAMPLE
 * -------
 * Input:  n=4, h=[1,3,2,4]
 * Output: 5
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Two buildings i and j (i<j) see each other iff there is no building
 *    between them with height >= min(h[i],h[j]).
 * 2. Sort buildings by height. Process in increasing height order.
 * 3. When adding building with height v at position p, it can see all
 *    buildings to its left that are "visible" (i.e., no taller building
 *    between them that has been added already). Since we add in increasing
 *    order, all already-added buildings have height <= v.
 * 4. Use a data structure (BIT/segment tree) to count how many already-placed
 *    buildings are visible from position p. A building at q is visible from p
 *    if no building between q and p with height > min(h[q],h[p]) has been placed.
 *    Since we process in increasing order, all placed have height <= v, so
 *    visibility = there exists no placed building strictly between q and p.
 * 5. After sorting by height, for building at position p, it can see:
 *    - The nearest placed building to its left (if any).
 *    - The nearest placed building to its right (if any).
 *    These are the only two it can see at time of insertion.
 * 6. So answer = total pairs where each building only sees its immediate
 *    left and right neighbors in the insertion order. Use a sorted set of
 *    positions to find left and right neighbors when inserting.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1327E_CountBuildings {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            StringTokenizer st = new StringTokenizer(br.readLine());
            int[] h = new int[n + 1];
            int[] pos = new int[n + 1]; // pos[v] = position of building with height v
            for (int i = 1; i <= n; i++) {
                h[i] = Integer.parseInt(st.nextToken());
                pos[h[i]] = i;
            }

            // Process buildings in increasing height order
            // When we insert building of height v at position p,
            // it can see exactly the nearest placed building to left and right
            TreeSet<Integer> placed = new TreeSet<>();
            placed.add(0);       // sentinel left
            placed.add(n + 1);   // sentinel right
            long ans = 0;

            for (int v = 1; v <= n; v++) {
                int p = pos[v];
                Integer left = placed.lower(p);
                Integer right = placed.higher(p);
                if (left != 0) ans++;       // can see left neighbor
                if (right != n + 1) ans++;  // can see right neighbor
                placed.add(p);
            }

            sb.append(ans).append('\n');
        }

        System.out.print(sb);
    }
}
