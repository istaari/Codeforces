package phases.phase3_core_patterns.number_theory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1154G — Minimum Possible LCM
 * Link   : https://codeforces.com/problemset/problem/1154/G
 * Rating : 1700  |  Tags: number theory, gcd, divisors
 * Topic  : Phase 3: Core Patterns > Number Theory
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given array a of n integers. Find two elements a[i] and a[j] (i != j) whose
 * LCM is minimum. Output this minimum LCM and the pair of indices.
 *
 * EXAMPLE
 * -------
 * Input:  4
 *         10 3 5 15
 * Output: 15
 *         2 4
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. LCM(a, b) = a * b / gcd(a, b). To minimize LCM, we want a * b / gcd(a,b) to be small.
 * 2. For each value g (potential GCD), find the two smallest multiples of g in the array.
 *    LCM = g1 * g2 / g where g1, g2 are the two smallest multiples.
 *    Since g1 = g * k1 and g2 = g * k2 with gcd(k1, k2) may not be 1... actually
 *    LCM(g*k1, g*k2) = g * LCM(k1, k2) = g * k1 * k2 / gcd(k1, k2).
 *    But if we just pick the two smallest multiples, their LCM could still have gcd > g.
 * 3. Correct approach: for each possible GCD value g (divisor of some element), find all
 *    elements divisible by g, take the two smallest. Compute their actual LCM.
 * 4. Iterate g from max(a) down to 1. For each g, collect elements a[i] divisible by g.
 *    If >= 2 such elements exist, compute LCM of the two smallest. Track global minimum.
 * 5. Range: g from 1 to max(a). For each g, scan multiples of g up to max(a).
 *    Use a frequency/value array. Total work: sum_{g=1}^{MAX} MAX/g = O(MAX log MAX).
 *
 * COMPLEXITY
 * ----------
 * Time : O(MAX log MAX) where MAX = max(a)
 * Space: O(MAX + n)
 * ============================================================
 */

public class CF_1154G_MinimumPossibleLCM {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        int[] a = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        int maxVal = 0;
        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(st.nextToken());
            maxVal = Math.max(maxVal, a[i]);
        }

        // For each value, store indices (1-indexed) — we need indices for output
        HashMap<Integer, List<Integer>> valToIdx = new HashMap<>();
        for (int i = 0; i < n; i++) {
            valToIdx.computeIfAbsent(a[i], x -> new ArrayList<>()).add(i + 1);
        }

        // For each value, store frequency
        int[] freq = new int[maxVal + 1];
        // Store first two indices for each value
        int[] firstIdx = new int[maxVal + 1];
        int[] secondIdx = new int[maxVal + 1];
        java.util.Arrays.fill(firstIdx, -1);
        java.util.Arrays.fill(secondIdx, -1);

        for (int i = 0; i < n; i++) {
            int v = a[i];
            if (firstIdx[v] == -1) firstIdx[v] = i + 1;
            else if (secondIdx[v] == -1) secondIdx[v] = i + 1;
            freq[v]++;
        }

        long bestLCM = Long.MAX_VALUE;
        int bestI = -1, bestJ = -1;

        // For each candidate GCD g, find smallest two multiples
        for (int g = 1; g <= maxVal; g++) {
            // Find first and second smallest multiples of g in array
            int cnt = 0;
            int m1 = -1, m2 = -1; // smallest and second smallest multiples
            int i1 = -1, i2 = -1; // their indices
            for (int mult = g; mult <= maxVal && cnt < 2; mult += g) {
                if (freq[mult] > 0) {
                    if (m1 == -1) {
                        m1 = mult;
                        i1 = firstIdx[mult];
                        cnt++;
                        // If same value appears twice, second is also this value
                        if (freq[mult] >= 2) {
                            m2 = mult;
                            i2 = secondIdx[mult];
                            cnt++;
                        }
                    } else {
                        m2 = mult;
                        i2 = firstIdx[mult];
                        cnt++;
                    }
                }
            }
            if (cnt >= 2) {
                // Compute LCM(m1, m2)
                long lcm = (long) m1 / gcd(m1, m2) * m2;
                if (lcm < bestLCM) {
                    bestLCM = lcm;
                    bestI = i1;
                    bestJ = i2;
                }
            }
        }

        System.out.println(bestLCM);
        System.out.println(bestI + " " + bestJ);
    }

    static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
