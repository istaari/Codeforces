package phases.phase3_core_patterns.number_theory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1176D — Recover It!
 * Link   : https://codeforces.com/problemset/problem/1176/D
 * Rating : 1600  |  Tags: number theory, sieve, greedy
 * Topic  : Phase 3: Core Patterns > Number Theory
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given n and an array of 2n integers. Determine if this array can be split into
 * two sets A and B (each of size n) such that every element of A is prime and
 * every element b in B has (b - 1) being prime and b in range [2, 2e6].
 * Equivalently: B elements are (prime + 1).
 *
 * EXAMPLE
 * -------
 * Input:  4
 *         3 5 7 8 4 6 12 2
 * Output: Yes
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Precompute prime sieve up to 2e6+1.
 * 2. For each element x in the array:
 *    - If x is prime AND (x-1) is prime: x could belong to either A or B.
 *    - If x is prime only: x must be in A.
 *    - If (x-1) is prime only: x must be in B.
 *    - Neither: impossible.
 * 3. Greedy: first assign elements that can only go to B (freq[x]-- for B, require prime(x-1)).
 *    Then assign ambiguous elements (both prime and prime-1) greedily.
 * 4. Count: let cntA = count of elements assigned to A, cntB = count to B. Need cntA = cntB = n.
 *    Greedy approach: for each element, if it must go to B (x-1 is prime, x is not prime): assign to B.
 *    If it must go to A (x is prime, x-1 is not prime): assign to A.
 *    For ambiguous (both): greedy — assign to whichever has fewer elements so far, or after determining
 *    fixed assignments, assign remaining to balance.
 *
 * COMPLEXITY
 * ----------
 * Time : O(MAX log log MAX + n)
 * Space: O(MAX)
 * ============================================================
 */

public class CF_1176D_RecoverIt {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        final int MAX = 2_000_002;
        boolean[] isPrime = new boolean[MAX];
        java.util.Arrays.fill(isPrime, true);
        isPrime[0] = isPrime[1] = false;
        for (int i = 2; (long) i * i < MAX; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j < MAX; j += i) isPrime[j] = false;
            }
        }

        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            int[] arr = new int[2 * n];
            StringTokenizer st = new StringTokenizer(br.readLine());
            HashMap<Integer, Integer> freq = new HashMap<>();
            for (int i = 0; i < 2 * n; i++) {
                arr[i] = Integer.parseInt(st.nextToken());
                freq.merge(arr[i], 1, Integer::sum);
            }

            // Need exactly n elements in B where each element x has isPrime(x-1)
            // and exactly n elements in A where each element is prime.
            // Greedy: for elements that MUST be in B (not prime themselves, but x-1 is prime):
            // assign first. Then for ambiguous, balance.

            // Count must-B, must-A, ambiguous
            int cntA = 0, cntB = 0;
            boolean impossible = false;

            // First pass: handle must-assigns
            for (int x : freq.keySet()) {
                int cnt = freq.get(x);
                boolean canA = x < MAX && isPrime[x];
                boolean canB = x - 1 >= 2 && x - 1 < MAX && isPrime[x - 1];

                if (!canA && !canB) {
                    impossible = true;
                    break;
                }
                if (!canA) {
                    // Must go to B
                    cntB += cnt;
                } else if (!canB) {
                    // Must go to A
                    cntA += cnt;
                }
                // ambiguous: handle after
            }

            if (!impossible) {
                // Second pass: assign ambiguous elements
                for (int x : freq.keySet()) {
                    int cnt = freq.get(x);
                    boolean canA = x < MAX && isPrime[x];
                    boolean canB = x - 1 >= 2 && x - 1 < MAX && isPrime[x - 1];
                    if (canA && canB) {
                        // Need cntA + cntB = 2n, cntA = cntB = n
                        // Assign min(needed_A, cnt) to A, rest to B
                        int neededA = n - cntA;
                        int toA = Math.min(neededA, cnt);
                        int toB = cnt - toA;
                        cntA += toA;
                        cntB += toB;
                    }
                }
            }

            sb.append(!impossible && cntA == n && cntB == n ? "Yes" : "No").append('\n');
        }

        System.out.print(sb);
    }
}
