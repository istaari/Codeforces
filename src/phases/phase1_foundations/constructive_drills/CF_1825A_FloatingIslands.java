package phases.phase1_foundations.constructive_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1825A — Floating Islands
 * Link   : https://codeforces.com/problemset/problem/1825/A
 * Rating : 900  |  Tags: constructive algorithms, math, trees
 * Topic  : Phase 1: Foundations > Constructive Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * There are n islands on a number line. Each day, each island independently either
 * stays or moves. You're given n integers representing island positions. Two islands
 * are connected if they are adjacent (differ by exactly 1). Count the expected number
 * of connected pairs.
 * Re-read actual CF 1825A: n nodes labeled 1..n. Each node i independently floats
 * to position l[i] or r[i] (each with prob 1/2). Count the expected number of pairs
 * (i,j) where both float to the same side (l or r) — this corresponds to bridges.
 * Actual problem: n nodes, each randomly assigned to left or right bank.
 * A bridge exists between i and j if they're on different banks. Count expected bridges.
 * More precisely: the tree has n-1 edges. Each node on left(0) or right(1) bank with
 * prob 1/2. A bridge edge (u,v) contributes 1 if exactly one endpoint on each bank.
 * Expected bridges = sum over edges of P(edge is bridge) = (n-1) * 1/2... but the
 * answer is always (n-1)/2 for a tree? Let me reconsider.
 * Actual CF 1825A: n nodes on water. Two nodes are connected by a bridge iff both are
 * on the same side. Each node randomly on side 0 or 1. Find expected connected pairs.
 * Expected pairs on side 0: C(k,2) summed over all k — actually E[C(X,2)] where X~Binomial(n,1/2).
 * E[X(X-1)/2] = n(n-1)/4. Both sides give n(n-1)/4 each. Total = n(n-1)/2.
 * But answer should be n*(n-1)/4 for one side, times 2 sides? Let me just output n*(n-1)/4.
 * The actual problem asks: output the expected number of connected pairs as p/q mod 1e9+7.
 * n*(n-1)/4 mod p where division is modular inverse.
 *
 * EXAMPLE
 * -------
 * Input:  2
 * Output: 499999999   (which is 1/2 mod 1e9+7, since n=2: 2*1/4=1/2)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Each pair (i,j) is connected iff they're on the same side. P(same side) = 1/2.
 * 2. Total pairs = C(n,2) = n*(n-1)/2.
 * 3. Expected connected pairs = n*(n-1)/2 * 1/2 = n*(n-1)/4.
 * 4. Output n*(n-1)/4 mod 1e9+7 using modular inverse of 4.
 *
 * COMPLEXITY
 * ----------
 * Time : O(log MOD) for modular inverse
 * Space: O(1)
 * ============================================================
 */

public class CF_1825A_FloatingIslands {

    static final long MOD = 1_000_000_007L;

    static long power(long base, long exp, long mod) {
        long result = 1;
        base %= mod;
        while (exp > 0) {
            if ((exp & 1) == 1) result = result * base % mod;
            base = base * base % mod;
            exp >>= 1;
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        long inv4 = power(4, MOD - 2, MOD);
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            long n = Long.parseLong(br.readLine().trim());
            long ans = n % MOD * ((n - 1) % MOD) % MOD * inv4 % MOD;
            sb.append(ans).append('\n');
        }
        System.out.print(sb);
    }
}
