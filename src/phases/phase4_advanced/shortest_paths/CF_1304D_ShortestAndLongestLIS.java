package phases.phase4_advanced.shortest_paths;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1304D — Shortest and Longest LIS
 * Link   : https://codeforces.com/problemset/problem/1304/D
 * Rating : 1700  |  Tags: constructive, greedy, LIS
 * Topic  : Phase 4: Advanced > Shortest Paths
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given a sequence of n-1 comparison characters (each '<' or '>') specifying
 * the relationship between consecutive elements of a permutation of 1..n.
 * Construct two permutations of 1..n:
 * 1. One that satisfies all comparisons and has the SHORTEST possible LIS.
 * 2. One that satisfies all comparisons and has the LONGEST possible LIS.
 *
 * EXAMPLE
 * -------
 * Input:  n=5, comparisons="<<>>"
 * Output: Shortest: 1 2 5 4 3 (LIS = 2)
 *         Longest:  1 2 5 4 3... need different construction
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. For LONGEST LIS: greedily assign values. For increasing runs (consecutive '<'),
 *    assign smallest available values in order. For decreasing runs ('>'), assign
 *    largest available values in decreasing order. This maximizes LIS.
 * 2. For SHORTEST LIS: greedily assign values to minimize LIS. For each increasing
 *    run of length k, we must use k distinct values. Assign them in decreasing order
 *    (zigzag pattern) to minimize LIS. Fill blocks of consecutive same-direction.
 * 3. Both constructions: process in "runs" of same comparison direction.
 *
 * Detailed LONGEST algorithm:
 *   - Assign values left to right. For '<', extend current increasing sequence.
 *   - At each '>', start descending. Assign largest remaining to create decreasing run.
 *
 * Detailed SHORTEST algorithm:
 *   - Find each maximal increasing block [i..i+k] (consecutive '<').
 *   - Within each block + surrounding '>' context, assign values to minimize LIS.
 *   - Fill blocks: for a run of k '<'s (positions i to i+k), assign k+1 values.
 *     Assign them in reverse within the block to minimize LIS contribution.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1304D_ShortestAndLongestLIS {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            String s = br.readLine().trim(); // length n-1

            // Build shortest LIS permutation:
            // Process each maximal block of '<' comparisons.
            // Within each block plus its bounding elements, assign values in reverse.
            int[] shortPerm = new int[n];
            {
                int lo = 1, hi = n;
                // Use two-pointer: assign values from ends
                // For '>' runs: assign decreasing from current 'hi' pointer
                // For '<' runs: assign increasing from current 'lo' pointer
                // Strategy: group consecutive same-direction runs, fill each run
                // with a contiguous block of values in the opposite order.

                // Identify blocks between '>' characters
                int i = 0;
                int ptr = 1; // next value to assign (1-indexed from lo)
                // Assign in blocks: each maximal increasing block gets values assigned
                // in DECREASING order (to minimize LIS).
                int cur = 1;
                while (i < n) {
                    int j = i;
                    // Find end of current block (either < run or > run)
                    // For shortest LIS: find each '>' boundary
                    if (i == n - 1 || s.charAt(i) == '>') {
                        // Single element or decreasing: assign next highest available
                        // Actually build using a different approach:
                        break;
                    }
                    break;
                }

                // Cleaner approach for shortest LIS:
                // Find runs: between consecutive '>' chars (or start/end), each run has all '<'
                // Within each run of length k+1 (k '<'s), assign k+1 values in decreasing order
                // This ensures within each run, elements decrease (LIS contribution = 1 per run)
                int left = 0, right = 0;
                ptr = n;
                for (int ii = 0; ii < n; ) {
                    // Find the end of current group (up to next '>' or end)
                    int jj = ii;
                    while (jj < n - 1 && s.charAt(jj) == '<') jj++;
                    // Block is [ii, jj] (size jj-ii+1)
                    // Assign values ptr, ptr-1, ..., ptr-(jj-ii) in this order
                    int blockSize = jj - ii + 1;
                    for (int k = 0; k < blockSize; k++) {
                        shortPerm[ii + k] = ptr - k;
                    }
                    ptr -= blockSize;
                    ii = jj + 1;
                }
            }

            // Build longest LIS permutation:
            // For each maximal decreasing block ('>'), assign values in increasing order.
            // For each maximal increasing block ('<'), assign values in decreasing order... no.
            // For longest LIS: assign so each '<' contributes to LIS.
            // Fill each block [ii..jj] (bounded by '<'s) with values in increasing order.
            int[] longPerm = new int[n];
            {
                int ptr = 1;
                for (int ii = 0; ii < n; ) {
                    // Find end of current group (up to next '<' or end)
                    int jj = ii;
                    while (jj < n - 1 && s.charAt(jj) == '>') jj++;
                    // Block [ii, jj]: assign values ptr, ptr+1, ..., ptr+(jj-ii)
                    int blockSize = jj - ii + 1;
                    for (int k = 0; k < blockSize; k++) {
                        longPerm[ii + k] = ptr + k;
                    }
                    ptr += blockSize;
                    ii = jj + 1;
                }
            }

            for (int i = 0; i < n; i++) {
                sb.append(shortPerm[i]);
                if (i < n - 1) sb.append(' ');
            }
            sb.append('\n');
            for (int i = 0; i < n; i++) {
                sb.append(longPerm[i]);
                if (i < n - 1) sb.append(' ');
            }
            sb.append('\n');
        }

        System.out.print(sb);
    }
}
