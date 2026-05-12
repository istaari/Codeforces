package phases.phase1_foundations.constructive_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1790C — Premutation
 * Link   : https://codeforces.com/problemset/problem/1790/C
 * Rating : 1100  |  Tags: constructive algorithms, sortings
 * Topic  : Phase 1: Foundations > Constructive Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * You are given n-1 integers: p[2], p[3], ..., p[n]. These form the last n-1
 * elements of some permutation p[1..n] of 1..n. You must find all valid values
 * of p[1] such that p is a permutation where for every prefix of length k >= 2,
 * the minimum element of the prefix is p[k] (i.e., min decreases at each step).
 * More precisely: the permutation has the property that p[i] < p[i-1] for all i
 * from 2..n? No — the condition is that p[1] > p[2] > ... is not required either.
 * Re-reading: the actual condition is that the permutation was obtained by
 * repeatedly inserting the minimum at the front. So p[n] < p[n-1] < ... < p[2] < p[1]?
 * Actually CF 1790C: given the sequence p[2..n], find all valid p[1] such that
 * there exists a valid permutation of 1..n where any prefix has its minimum as
 * the last element added (i.e., it's a "stack-sortable" type condition).
 * The actual problem: the first element p[1] is missing. The given p[2..n] must
 * satisfy: each p[i] is less than all previous p[1..i-1]. I.e., p is strictly
 * decreasing? No. The condition is: p[2] < p[1], p[3] < min(p[1],p[2]), etc.
 * That means p must be strictly decreasing: p[1] > p[2] > ... > p[n].
 * If p[2..n] is already strictly decreasing, then p[1] can be any value > p[2]
 * that is not in {p[2],...,p[n]}. The number of valid p[1] values equals the
 * count of integers in 1..n not in {p[2],...,p[n]} that are > p[2].
 *
 * EXAMPLE
 * -------
 * Input:  4 / 3 2 1
 * Output: 1 / 4
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Check if p[2..n] is strictly decreasing. If not, answer is 0.
 * 2. If yes, find all values in 1..n not in {p[2..n]} that are > p[2].
 * 3. Output count, then those values sorted.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1790C_Premutation {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            StringTokenizer st = new StringTokenizer(br.readLine());
            int[] p = new int[n - 1];
            for (int i = 0; i < n - 1; i++) p[i] = Integer.parseInt(st.nextToken());

            // Check if p[2..n] (stored as p[0..n-2]) is strictly decreasing
            boolean strictlyDecreasing = true;
            for (int i = 1; i < n - 1; i++) {
                if (p[i] >= p[i - 1]) {
                    strictlyDecreasing = false;
                    break;
                }
            }

            if (!strictlyDecreasing) {
                sb.append(0).append('\n').append('\n');
                continue;
            }

            // Find values in 1..n not in p[0..n-2] that are > p[0]
            HashSet<Integer> used = new HashSet<>();
            for (int x : p) used.add(x);
            List<Integer> valid = new ArrayList<>();
            for (int v = 1; v <= n; v++) {
                if (!used.contains(v) && v > p[0]) {
                    valid.add(v);
                }
            }
            sb.append(valid.size()).append('\n');
            StringBuilder line = new StringBuilder();
            for (int v : valid) {
                if (line.length() > 0) line.append(' ');
                line.append(v);
            }
            sb.append(line).append('\n');
        }
        System.out.print(sb);
    }
}
