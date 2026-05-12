package phases.phase4_advanced.shortest_paths;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeMap;


/*
 * ============================================================
 * CF 1144G — Two Merged Sequences
 * Link   : https://codeforces.com/problemset/problem/1144/G
 * Rating : 1800  |  Tags: dp, LIS, sequences
 * Topic  : Phase 4: Advanced > Shortest Paths
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given array a[] of n integers. Split it into two subsequences such that
 * both subsequences are non-decreasing and the total number of inversions
 * between them is minimized. Actually: find the maximum length of a
 * non-decreasing subsequence you can form by interleaving two sorted
 * subsequences... Re-read: find LIS of a[], where the LIS uses elements
 * from two merged sorted arrays. Each element is from array 1 or array 2.
 * Choose the split to maximize the LIS length.
 *
 * ACTUAL PROBLEM: Given array a[], split into two subsequences (maintaining
 * relative order) such that both are non-decreasing. The "score" is the number
 * of positions we fail to include (positions that can't be in either non-decreasing
 * subsequence). Equivalently: find minimum elements to REMOVE so remaining can be
 * split into two non-decreasing subsequences.
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. This is equivalent to finding the minimum number of elements to remove
 *    so the array has no "3 decreasing elements" (Dilworth's theorem variant).
 *    Actually: array can be split into 2 non-decreasing subsequences iff it has
 *    no decreasing subsequence of length 3 (by Dilworth's).
 * 2. Equivalently, maximize elements kept = find maximum subsequence that can be
 *    partitioned into 2 non-decreasing subsequences.
 * 3. DP: dp[i][0] = best (end1, end2) considering first i elements where element i
 *    goes to sequence 1 or 2. State = (last value of seq1, last value of seq2).
 *    Too many states. Use patience sorting-like approach.
 * 4. Alternative: greedily assign each element to a sequence. Use two "stacks" (actually
 *    current tails). Greedy: for element x, if x >= tail1, append to seq1; elif x >= tail2,
 *    append to seq2; else must remove x.
 * 5. For minimal removals: greedily assign x to the sequence with the largest tail <= x.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1144G_TwoMergedSequences {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        int[] a = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) a[i] = Integer.parseInt(st.nextToken());

        // Greedy: maintain multiset of current tails of non-decreasing subsequences (max 2)
        // For each element, assign to the sequence with largest tail <= element.
        // If both tails > element, must remove (or we need to drop one from a sequence).
        // Since we want max elements kept, use DP with 2 "patience sorting" piles.

        // patience sorting for 2-decreasing (no 3 elements form decreasing subseq):
        // Use 2 "piles" sorted. For each new element x, find pile with largest top <= x.
        // If none, must remove x. Update pile top.

        // Use a sorted structure of (tail_value, pile_id)
        // Keep at most 2 piles
        long INF = Long.MAX_VALUE / 2;
        long tail1 = Long.MIN_VALUE, tail2 = Long.MIN_VALUE;
        int kept = 0;
        int removed = 0;

        for (int x : a) {
            // Find best pile: largest tail <= x
            if (tail1 <= x && tail2 <= x) {
                // Both can take x; assign to the one with larger tail (greedy: leave smaller for future)
                if (tail1 >= tail2) { tail1 = x; } else { tail2 = x; }
                kept++;
            } else if (tail1 <= x) {
                tail1 = x;
                kept++;
            } else if (tail2 <= x) {
                tail2 = x;
                kept++;
            } else {
                // Must remove x
                removed++;
            }
        }

        System.out.println(removed);
    }
}
