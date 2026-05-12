package phases.phase4_advanced.advanced_dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;


/*
 * ============================================================
 * CF 1367F1 — Flying Sort (Easy Version)
 * Link   : https://codeforces.com/problemset/problem/1367/F1
 * Rating : 1700  |  Tags: dp, sorting, binary search
 * Topic  : Phase 4: Advanced > Advanced DP
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given two arrays a[] and b[] of length n. Sort b to get sorted_b.
 * We can "fly" some elements from a. An element a[i] is "correct" if
 * it equals sorted_b[rank] for the proper rank. Find the maximum number
 * of elements that do NOT need to be flown (i.e., already in correct
 * relative order matching sorted_b). Equivalently, find the longest
 * subsequence of a that is a subsequence of sorted_b in order.
 * Output n minus that maximum (minimum elements to fly).
 *
 * EXAMPLE
 * -------
 * Input:  n=5, a=[3,1,2,3,5], b=[3,1,2,3,5]
 * Output: 0
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Sort b to get sorted_b. Map each value to its sorted rank.
 * 2. For elements of a that appear in b (with correct multiplicity),
 *    compute their ranks in sorted_b. Elements not in b must be flown.
 * 3. Find the LIS (non-decreasing since duplicates allowed) of the rank
 *    sequence derived from a — this is the max elements already in order.
 * 4. Answer = n - LIS_length.
 * 5. Handle duplicates carefully: each occurrence of value v in a can
 *    only match one occurrence in sorted_b (use counting).
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1367F1_FlyingSortEasy {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            int[] a = readArray(br, n);
            int[] b = readArray(br, n);

            int[] sortedB = b.clone();
            Arrays.sort(sortedB);

            // Map value -> list of ranks in sortedB (0-indexed positions)
            // For duplicates, each occurrence gets a unique increasing rank
            Map<Integer, List<Integer>> rankMap = new HashMap<>();
            for (int i = 0; i < n; i++) {
                rankMap.computeIfAbsent(sortedB[i], k -> new ArrayList<>()).add(i);
            }

            // For each element in a, assign rank if possible
            // We need to track usage counts
            Map<Integer, Integer> usedCount = new HashMap<>();
            int[] ranks = new int[n];
            int validCount = 0;

            // First pass: assign ranks to elements in a that exist in b
            // We must assign them in a way that makes LIS maximal
            // Strategy: for a[i], use the next available rank for value a[i]
            for (int i = 0; i < n; i++) {
                int val = a[i];
                List<Integer> positions = rankMap.get(val);
                if (positions == null) {
                    ranks[i] = -1; // not in b
                } else {
                    int used = usedCount.getOrDefault(val, 0);
                    if (used < positions.size()) {
                        ranks[i] = positions.get(used);
                        usedCount.put(val, used + 1);
                        validCount++;
                    } else {
                        ranks[i] = -1; // excess occurrences
                    }
                }
            }

            // Now find LIS (non-decreasing) on the valid ranks
            // Skip elements with rank -1
            // patience sorting for LIS of non-decreasing sequence
            List<Integer> tails = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if (ranks[i] == -1) continue;
                int r = ranks[i];
                // non-decreasing LIS: find first tail > r
                int lo = 0, hi = tails.size();
                while (lo < hi) {
                    int mid = (lo + hi) / 2;
                    if (tails.get(mid) <= r) lo = mid + 1;
                    else hi = mid;
                }
                if (lo == tails.size()) tails.add(r);
                else tails.set(lo, r);
            }

            sb.append(n - tails.size()).append('\n');
        }

        System.out.print(sb);
    }

    private static int[] readArray(BufferedReader br, int n) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = Integer.parseInt(st.nextToken());
        return arr;
    }
}
