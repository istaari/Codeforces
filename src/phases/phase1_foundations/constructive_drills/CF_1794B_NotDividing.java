package phases.phase1_foundations.constructive_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.TreeSet;


/*
 * ============================================================
 * CF 1794B — Not Dividing
 * Link   : https://codeforces.com/problemset/problem/1794/B
 * Rating : 1000  |  Tags: constructive algorithms, math, number theory
 * Topic  : Phase 1: Foundations > Constructive Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given n positive integers a[1..n]. You can increase any element by 1 (for a cost
 * of 1 per increment). After operations, for all i != j, a[i] should not divide a[j].
 * Find the minimum total cost (sum of increments).
 *
 * EXAMPLE
 * -------
 * Input:  4 / 2 2 2 2
 * Output: 2
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. First, make all 1s into 2s (since 1 divides everything, increment each 1 to 2,
 *    cost += 1 per such element).
 * 2. For the "no element divides another" condition: all elements must be distinct
 *    primes? No, they just need to be pairwise non-dividing.
 * 3. A clean solution: after handling 1s, sort the array. For each element,
 *    ensure it's not a divisor of any later element. But checking all pairs is O(n^2).
 * 4. Better insight: if we make all elements distinct primes (>= 2), no element
 *    divides another (since each is prime and distinct). But that might cost a lot.
 * 5. Key observation: we only need to increase each a[i] to the next prime >= a[i]
 *    that is not already used? No, two equal primes would divide each other... wait,
 *    equal values: a divides a. So all elements must also be distinct!
 * 6. Strategy: first ensure no element is 1 (make them 2). Then ensure all elements
 *    are distinct prime values. For each element (after sorting), find the smallest
 *    prime >= current value not yet used.
 * 7. But that might over-count. The actual minimum: we need a set of values where
 *    no one divides another. The minimum increment is achieved by making all values
 *    distinct primes >= 2. Each element is increased to the next available prime.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n * sqrt(max_val)) roughly, since we sieve or check primality
 * Space: O(n)
 * ============================================================
 */

public class CF_1794B_NotDividing {

    static boolean isPrime(int n) {
        if (n < 2) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;
        for (int i = 3; (long)i * i <= n; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }

    static int nextPrime(int n) {
        while (!isPrime(n)) n++;
        return n;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            StringTokenizer st = new StringTokenizer(br.readLine());
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = Integer.parseInt(st.nextToken());
            // Step 1: make all 1s into 2
            long cost = 0;
            for (int i = 0; i < n; i++) {
                if (a[i] == 1) {
                    cost++;
                    a[i] = 2;
                }
            }
            // Step 2: sort, then for each element find next available prime >= a[i]
            Arrays.sort(a);
            TreeSet<Integer> used = new TreeSet<>();
            for (int i = 0; i < n; i++) {
                int p = nextPrime(a[i]);
                while (used.contains(p)) p = nextPrime(p + 1);
                cost += (p - a[i]);
                used.add(p);
            }
            sb.append(cost).append('\n');
        }
        System.out.print(sb);
    }
}
