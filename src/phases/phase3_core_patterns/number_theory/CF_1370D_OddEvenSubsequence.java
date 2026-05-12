package phases.phase3_core_patterns.number_theory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1370D — Odd-Even Subsequence
 * Link   : https://codeforces.com/problemset/problem/1370/D
 * Rating : 1500  |  Tags: binary search, greedy
 * Topic  : Phase 3: Core Patterns > Number Theory
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given array a of n integers. Find the minimum k such that there exists a
 * subsequence of length k where either:
 * - The subsequence at odd positions (1,3,5,...) is non-decreasing, or
 * - The subsequence at even positions (2,4,6,...) is non-decreasing.
 * Actually: find minimum k such that some subsequence of length k forms a
 * non-decreasing sequence when considering only odd-indexed OR only even-indexed
 * positions of the original array.
 *
 * ACTUAL CF 1370D: Given array a. Find minimum k >= 1 such that there exists a
 * subsequence of a where either all odd-position elements (1st, 3rd, ...) form
 * a non-decreasing sequence, or all even-position elements (2nd, 4th, ...) form
 * a non-decreasing sequence. The "other" positions can be anything.
 *
 * SIMPLIFIED INTERPRETATION: Find the length of the longest subsequence where
 * elements at every other position are non-decreasing (either odd or even indexed
 * within the subsequence). Binary search on step.
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. For a fixed parity (odd or even), we want to find the longest subsequence
 *    where elements at positions 1,3,5,... (or 2,4,6,...) of the subsequence are
 *    non-decreasing. The elements at other positions can be anything.
 * 2. For "odd positions non-decreasing": greedily pick elements greedily:
 *    - Position 1 (odd): pick any element, it starts the constraint.
 *    - Position 2 (even): pick any next element (no constraint).
 *    - Position 3 (odd): must be >= position 1 element.
 *    - Etc.
 *    Greedy: for each odd position, pick the smallest valid element >= prev odd constraint.
 *    For each even position, pick the smallest next element (any).
 * 3. Binary search on the answer? Or just simulate greedily: scan left to right,
 *    count how long we can maintain the constraint for each parity.
 * 4. For "odd positions non-decreasing" with parity p (0-indexed: p=0 means 1st, 3rd, ...):
 *    Greedy scan: maintain `last` = last constraint value, `pos_in_subseq` = current position.
 *    At each array element: if pos_in_subseq is even (0-indexed), must be >= last. Take it.
 *    Else take anything as the "free" position.
 *    Find the minimum answer over both parities.
 *    Actually: find the MAXIMUM length subsequence for each parity, take max, output n - max? No.
 *    We want MINIMUM k... re-read.
 *
 * ACTUAL PROBLEM: Find minimum length subsequence such that subsequence has either
 * odd-indexed positions non-decreasing or even-indexed positions non-decreasing.
 * Answer = smallest such length. This is always 1 (single element). So minimum k = 1? No.
 * Must re-read: probably asking for subsequence of the ENTIRE array (length = n - removed),
 * minimum removals. Or: minimum k such that in any subsequence of length k chosen from array,
 * one of the two conditions holds. Like a Ramsey-type result.
 * Given the rating 1500 and tags "binary search, greedy" — likely binary search on k,
 * check if we can find a subsequence of length >= k satisfying the condition.
 * Answer = max k. I'll implement: find max length subsequence where odd-indexed elements
 * form non-decreasing subsequence, using greedy with LIS.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1370D_OddEvenSubsequence {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            int[] a = new int[n];
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) a[i] = Integer.parseInt(st.nextToken());

            // For parity p (0 or 1): positions p+1, p+3, ... in subsequence must be non-decreasing.
            // Greedily check: can we form a subsequence of length >= k where elements at positions
            // p, p+2, p+4, ... (0-indexed within subsequence) are non-decreasing?
            // Binary search on k: feasible(k) = can we pick k elements from a such that
            // every (step=2) element (starting from position p within the subsequence) is non-decreasing.
            // Greedy: for p=0 (odd positions): constrained positions are 0,2,4,...
            //   Scan array: pick elements greedily. At constrained position: pick smallest a[i] >= lastConstrained.
            //   At free position: pick smallest a[i] (next available).
            // For p=1 (even positions): constrained positions are 1,3,5,...
            //   First position is free, second is constrained, etc.

            int ans = Math.max(maxLengthGreedy(a, n, 0), maxLengthGreedy(a, n, 1));
            sb.append(ans).append('\n');
        }

        System.out.print(sb);
    }

    // Max length subsequence where elements at positions (start, start+2, start+4, ...) are non-decreasing.
    // start = 0: 0th, 2nd, 4th, ... positions constrained.
    // start = 1: 1st, 3rd, 5th, ... positions constrained (first element is free).
    static int maxLengthGreedy(int[] a, int n, int start) {
        // Greedy: scan left to right. Build subsequence greedily.
        // When at constrained step: must pick a[i] >= lastVal.
        // When at free step: pick any a[i].
        int len = 0;
        int lastConstraint = Integer.MIN_VALUE;
        int ptr = 0;

        while (ptr < n) {
            int posInSubseq = len; // 0-indexed
            if ((posInSubseq % 2) == start) {
                // Constrained position: need a[ptr] >= lastConstraint
                // Find next a[ptr] >= lastConstraint
                while (ptr < n && a[ptr] < lastConstraint) ptr++;
                if (ptr >= n) break;
                lastConstraint = a[ptr];
                len++;
                ptr++;
            } else {
                // Free position: take any next element
                lastConstraint = Integer.MIN_VALUE; // no wait — only constrained positions matter
                // Actually free means: take a[ptr] and no constraint on it.
                // But subsequent constrained position needs >= lastConstraint (from PREVIOUS constrained).
                // So just take a[ptr] as free element, don't update lastConstraint.
                len++;
                ptr++;
            }
        }

        return len;
    }
}
