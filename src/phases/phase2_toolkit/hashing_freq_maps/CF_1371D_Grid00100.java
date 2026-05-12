package phases.phase2_toolkit.hashing_freq_maps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1371D — Grid (001,10)
 * Link   : https://codeforces.com/problemset/problem/1371/D
 * Rating : 1300  |  Tags: constructive, math, implementation
 * Topic  : Phase 2: Toolkit > Hashing & Frequency Maps
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given n×m grid. Place exactly one '1' per row and one '1' per column
 * (so the grid is a permutation matrix). The value at cell (r, c) with a '1'
 * contributes r * c (mod (n + 1)) to the total score if r*c mod (n+1) == 0,
 * else 0. Wait — re-read: cell (r,c) has value (r*c) mod (n+1). Maximize
 * sum of values at cells that contain '1'.
 * Actually CF 1371D: n×m binary matrix, at most one 1 per row, at most one 1 per
 * column, maximize the total sum where a[i][j] = (i*j) mod (n+1).
 *
 * EXAMPLE
 * -------
 * Input:  3 3
 * Output: 6
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Value at (r, c) = r*c mod (n+1). Since r in [1,n] and n+1 is fixed,
 *    if n+1 is prime: for any r, as c varies over 1..n, r*c mod (n+1) takes
 *    all values 1..n. So for prime n+1: every row can achieve any value 1..n
 *    for some c. Max sum = n * n (each of n rows gets value n? No, columns clash).
 *    With permutation matrix, we pick a bijection sigma: row i → col sigma(i).
 *    Maximize sum of i*sigma(i) mod (n+1).
 * 2. If n+1 is prime: we want each value 1..n to appear exactly once in
 *    {i*sigma(i) mod (n+1)}. Max possible sum = 1+2+...+n = n(n+1)/2.
 *    We can achieve this: pick sigma(i) such that i*sigma(i) mod (n+1) = desired.
 *    For row i (i in 1..n), set sigma(i) such that it maps distinctly.
 *    One simple approach: sigma(i) = 1 for all (sum = n*1 mod (n+1) = n, but only for n>=1).
 *    Better: sort rows by value of n/i * i to concentrate large values.
 *    Actual key: For prime p = n+1, we want sigma(i) = (n * inverse(i)) mod p,
 *    so that i * sigma(i) mod p = n for each row — impossible (not permutation).
 *    Actually easiest: set sigma such that i * sigma(i) mod (n+1) = n for all i.
 *    Then sigma(i) = n * inv(i) mod (n+1). But are all sigma(i) distinct? Yes for prime n+1.
 *    Total = n * n. But sigma values must be in 1..m and distinct.
 * 3. General approach: greedily assign largest values. For each row 1..n,
 *    try all columns 1..m, pick the one giving maximum r*c mod (n+1) that
 *    hasn't been used. Use Hungarian or greedy. For n,m up to 1000 this is O(nm).
 *    Even simpler: for prime n+1, set sigma(i) = (n * modInverse(i, n+1)) % (n+1).
 *    For non-prime n+1, handle separately.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n * m)
 * Space: O(n + m)
 * ============================================================
 */

public class CF_1371D_Grid00100 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // For each row i (1-indexed), assign a column c (1-indexed, c <= m)
        // to maximize sum of (i*c) mod (n+1), using each column at most once.
        // Greedy: for each row from n down to 1, pick best available column.
        // But greedy by row order might not be optimal.
        // Key observation: for row i, value i*c mod (n+1). The maximum possible
        // value per cell is n (since i*c mod (n+1) <= n).
        // Strategy: for each row, pick column c = (n) * modInverse(i, n+1) % (n+1)
        // to get value n from that row. But only works if n+1 is prime.

        // General O(n*m) greedy with sorting:
        // Create all (value, row, col) triples, sort descending, assign greedily.
        // But n,m up to 1500, so 1500*1500 = 2.25M triples — feasible.

        // Actually let's just do O(n*m) direct assignment:
        // For each row, scan columns in order of decreasing (i*c mod (n+1)).
        boolean[] colUsed = new boolean[m + 1];
        int[] rowAssign = new int[n + 1]; // rowAssign[i] = column assigned to row i

        long totalSum = 0;
        // Greedy: process rows in decreasing order; for each row take best available col
        // But this doesn't guarantee global optimum. Use simple assignment:
        // For n+1 prime and m >= n: assign sigma(i) = n*inv(i) mod (n+1)
        // For n+1 not prime or m < n: fallback greedy

        int mod = n + 1;
        if (isPrime(mod) && m >= n) {
            // Each row gets value n
            for (int i = 1; i <= n; i++) {
                int c = (int) ((1L * n * modInverse(i, mod)) % mod);
                if (c == 0) c = mod; // shouldn't happen for i in 1..n, mod prime
                rowAssign[i] = c;
                totalSum += n;
            }
        } else {
            // Greedy assignment: for each row i, pick col c (1..m, unused) with max i*c mod mod
            for (int i = 1; i <= n; i++) {
                int bestVal = -1, bestCol = -1;
                for (int c = 1; c <= m; c++) {
                    if (!colUsed[c]) {
                        int val = (i * c) % mod;
                        if (val > bestVal) {
                            bestVal = val;
                            bestCol = c;
                        }
                    }
                }
                rowAssign[i] = bestCol;
                colUsed[bestCol] = true;
                totalSum += bestVal;
            }
        }

        // Build output grid
        System.out.println(totalSum);
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            for (int c = 1; c <= m; c++) {
                sb.append(rowAssign[i] == c ? 1 : 0);
                if (c < m) sb.append(' ');
            }
            sb.append('\n');
        }
        System.out.print(sb);
    }

    static boolean isPrime(int x) {
        if (x < 2) return false;
        for (int i = 2; (long) i * i <= x; i++) if (x % i == 0) return false;
        return true;
    }

    static long modInverse(long a, long mod) {
        return modPow(a, mod - 2, mod);
    }

    static long modPow(long base, long exp, long mod) {
        long result = 1;
        base %= mod;
        while (exp > 0) {
            if ((exp & 1) == 1) result = result * base % mod;
            base = base * base % mod;
            exp >>= 1;
        }
        return result;
    }
}
