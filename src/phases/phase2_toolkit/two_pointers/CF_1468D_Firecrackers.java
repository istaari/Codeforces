package phases.phase2_toolkit.two_pointers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1468D — Firecrackers
 * Link   : https://codeforces.com/problemset/problem/1468/D
 * Rating : 1400  |  Tags: two pointers, binary search, sortings
 * Topic  : Phase 2: Toolkit > Two Pointers
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * n firecrackers on positions 1..n in a line. A guard at position g.
 * You want to light as many firecrackers as possible such that each
 * lit firecracker explodes before the guard reaches it. Firecracker i
 * takes s[i] seconds to explode. You light them one per second at your
 * position, then move. Guard moves 1 step/sec toward each firecracker.
 * A firecracker at distance d from guard explodes safely if lit at time
 * t where t + s[i] <= d (guard reaches in d steps, must explode before).
 * Find max firecrackers you can safely light.
 *
 * EXAMPLE
 * -------
 * Input:  3 1
 *         1 1 2
 * Output: 2
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Sort firecrackers by their fuse length s[i]. We want to assign
 *    times 1, 2, ..., k to k firecrackers such that for firecracker
 *    assigned time t, s[i] + t <= dist(guard, position[i]).
 *    Equivalently: s[i] + t < |g - pos[i]| (strict since guard starts
 *    moving after you light it — check problem statement carefully:
 *    d = distance, safe if s[i] < d - t + 1, i.e., s[i] + t < d + 1,
 *    i.e., s[i] + t <= d, where d = |g - p[i]|).
 * 2. We want to choose k firecrackers and assign them times 1..k.
 *    Sort by s[i]. If we select k firecrackers (sorted by s), assign
 *    time j (1-indexed) to the j-th smallest s. Need s[j] + j <= d[j]
 *    where d[j] = dist(guard, position of j-th chosen firecracker).
 * 3. Binary search on k. Feasibility: pick firecrackers with largest
 *    (d[i] - s[i]) values, take the top k, assign times 1..k in sorted
 *    order of s. Check if s[j] + j <= d[j] for all j.
 *    Sort chosen by s ascending; assign time j=1..k.
 * 4. Two-pointer on sorted firecrackers: for fixed window size k, find
 *    best k consecutive (after sorting by s) to satisfy condition.
 *    Actually: sort by s, compute slack = dist - s for each. Then use
 *    sliding window of size k, check if the j-th element's slack >= j.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1468D_Firecrackers {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int g = Integer.parseInt(st.nextToken());

        int[] s = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) s[i] = Integer.parseInt(st.nextToken());

        // Compute (dist, fuse) for each firecracker at position i+1 (1-indexed)
        // dist = |g - (i+1)|, but 0 if at same position (can't safely explode there)
        // Sort by fuse length s[i]
        int[][] fc = new int[n][2]; // [fuse, dist]
        for (int i = 0; i < n; i++) {
            fc[i][0] = s[i];
            fc[i][1] = Math.abs(g - (i + 1));
        }
        Arrays.sort(fc, (a, b) -> a[0] - b[0]);

        // Binary search on answer k
        int lo = 0, hi = n;
        while (lo < hi) {
            int mid = (lo + hi + 1) / 2;
            if (feasible(fc, mid)) lo = mid;
            else hi = mid - 1;
        }

        System.out.println(lo);
    }

    static boolean feasible(int[][] fc, int k) {
        int n = fc.length;
        // Sliding window of size k over sorted-by-fuse array.
        // For window [i, i+k-1], check if we can assign times 1..k:
        // fc[i+j-1][0] + j <= fc[i+j-1][1] for j=1..k
        // i.e., fc[i+j-1][1] - fc[i+j-1][0] >= j
        // Equivalently slack[pos] = dist - fuse, need slack[pos] >= j.
        // Use two pointers: for each right endpoint r, maintain left l
        // such that all elements in [l,r] have slack >= (r - l + 1) assigned times.
        // Actually: for a window of size k starting at i (0-indexed),
        // element at offset j (0-indexed, j=0..k-1) gets time j+1.
        // Need fc[i+j][1] - fc[i+j][0] >= j+1.
        for (int i = 0; i + k - 1 < n; i++) {
            boolean ok = true;
            for (int j = 0; j < k; j++) {
                if (fc[i + j][1] - fc[i + j][0] < j + 1) {
                    ok = false;
                    break;
                }
            }
            if (ok) return true;
        }
        return false;
    }
}
