package phases.phase1_foundations.c1_800rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 158A — Next Round
 * Link   : https://codeforces.com/problemset/problem/158/A
 * Rating : 800  |  Tags: implementation
 * Topic  : Phase 1: Foundations > Sheet C1 (800-rated)
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * n people participated in a round. The results are given in non-increasing
 * order. A person advances to the next round if their score is strictly
 * positive AND their score is >= the score of the k-th person.
 * Given n, k, and the scores, output how many advance.
 *
 * EXAMPLE
 * -------
 * Input:  8 5 / 10 9 8 7 7 7 5 5
 * Output: 6
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. The threshold is score[k-1] (0-indexed).
 * 2. Count all scores that are >= threshold AND > 0.
 * 3. Since scores are non-increasing, we can stop early, but linear scan is fine.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_158A_NextRound {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int[] a = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(st.nextToken());
        }
        int threshold = a[k - 1];
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (a[i] >= threshold && a[i] > 0) count++;
        }
        System.out.println(count);
    }
}
