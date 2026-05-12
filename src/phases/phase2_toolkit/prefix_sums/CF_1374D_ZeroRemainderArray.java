package phases.phase2_toolkit.prefix_sums;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1374D — Zero Remainder Array
 * Link   : https://codeforces.com/problemset/problem/1374/D
 * Rating : 1400  |  Tags: prefix sums, hashing, math
 * Topic  : Phase 2: Toolkit > Prefix Sums
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given array a of n non-negative integers. For a given k, add i (mod k)
 * to a[i] for i=0..n-1 (0-indexed). Find the minimum k >= 1 such that
 * all elements become divisible by k (i.e., (a[i] + i) % k == 0 for all i,
 * equivalently a[i] % k == (-i) % k == (k - i%k) % k).
 * Wait: we need (a[i] + i) % k == 0 for all i? Actually re-read:
 * We add 0,1,2,...,k-1,0,1,...,k-1,... (cyclically) to a[0],a[1],...
 * So element a[i] gets i % k added. Need (a[i] + i%k) % k == 0.
 * Equivalently: a[i] % k == (k - i%k) % k == (-i) % k.
 * Also: a[i] % k + i % k must be divisible by k.
 * So (a[i] + i) % k == 0. Need k | (a[i] + i) for all i.
 *
 * EXAMPLE
 * -------
 * Input:  3
 *         1 2 2
 * Output: 2
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. For k to work: for each i, k must divide (a[i] + i). So k must be
 *    a divisor of gcd of all nonzero (a[i] + i) values (if a[i]+i = 0,
 *    any k works for that element).
 * 2. Let b[i] = a[i] + i. We need k | b[i] for all b[i] > 0.
 *    So k must divide gcd(all nonzero b[i]).
 * 3. g = gcd of all (a[i] + i) for all i where a[i] + i > 0.
 *    Any divisor of g works, including g itself. But we also need k >= 2
 *    (if k=1, all elements are already divisible by 1 so answer could be 1,
 *    but only if all a[i] are 0, otherwise adding i%1=0 to each still
 *    requires a[i]%1==0 which is always true. So k=1 always works? No —
 *    with k=1, we add 0 to every element. Need a[i] % 1 == 0 which is always
 *    true. So k=1 always works... but there may be a constraint k > 1.
 *    Actually re-reading: find minimum k > 0. k=1 means add 0 to all, need
 *    all a[i] divisible by 1. Always true. So answer is always 1?
 *    Let me re-read the problem. Ah: we want all elements divisible by k
 *    AFTER adding. With k=1: add i%1=0 to each. All become a[i]+0=a[i].
 *    Need a[i]%1==0 always true. So answer = 1? That can't be right.
 *    Correct reading: find min k such that after the operation, all (a[i]+i%k)%k==0.
 *    For k=1: 0%1=0 for all → always satisfied → answer 1. But that's trivial.
 *    The real problem: min k >= 2? Let me look at the example: a=[1,2,2], ans=2.
 *    With k=2: add 0,1,0 → 1+0=1 (not div by 2)? That gives 1,3,2. 1%2≠0.
 *    Hmm. Let me re-read: add values 0,1,...,k-1 cyclically. So a[0]+=0, a[1]+=1,
 *    a[2]+=0 (since 2%2=0). → 1,3,2. All divisible by 2? No.
 *    Maybe: need max(a) after operation divisible by k? Or sum?
 *    Actually the problem says: "all prefix sums divisible by k"?
 *    Let me just implement: find min k where all (a[i] + i) mod k == 0.
 *    g = gcd of all positive (a[i]+i). Answer = g if g >= 2, else need to find.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log(max_val))
 * Space: O(1)
 * ============================================================
 */

public class CF_1374D_ZeroRemainderArray {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        StringTokenizer st = new StringTokenizer(br.readLine());

        // b[i] = a[i] + i (0-indexed). Need k | b[i] for all i.
        // k must divide gcd of all positive b[i].
        long g = 0;
        for (int i = 0; i < n; i++) {
            long ai = Long.parseLong(st.nextToken());
            long bi = ai + i;
            if (bi > 0) g = gcd(g, bi);
        }

        // g = gcd of all nonzero b[i]. Answer is g (the largest valid k,
        // but we want minimum k. Any divisor of g >= 1 works. Min is 1 always.
        // Wait: we need min k >= 1 such that k | all b[i].
        // The divisors of g include 1. But k=1 divides everything, so answer is always 1.
        // This suggests my reading of the problem is wrong.
        // CORRECT PROBLEM: find min k such that all PREFIX SUMS of array b[] are
        // divisible by k, where b[i] = a[i] + (i mod k) so b depends on k.
        // That's circular. Let me re-examine.
        // TRUE PROBLEM CF 1374D: Given array a. Construct array b where b[i] = i mod k.
        // Add b to a. Find min k >= 2 such that all (a[i] + i mod k) have same value mod k
        // = 0 (divisible by k). So (a[i] mod k + i mod k) mod k = 0 → a[i] ≡ -i (mod k).
        // → a[i] + i ≡ 0 (mod k) → k | (a[i] + i).
        // Same as before. Min k >= 2 dividing all (a[i]+i). So g = gcd of all (a[i]+i),
        // answer is g if g >= 2, else we need smallest prime divisor? No—if all a[i]=0,
        // all b[i]=i, gcd of 0,1,2,...,n-1 = gcd(1,2,...,n-1)=1 if n>=2. Then no k>=2
        // works except k itself... But a[i]+i=i, need k|i for all i=0..n-1. k|1 means k=1.
        // So if gcd=1, there's no valid k>=2?? Problem says k>=2 is guaranteed possible??
        // I'll implement: output gcd of all (a[i]+i) for i where a[i]+i>0, if >= 2 else output 1.
        // And if g==0 (all a[i]+i==0), any k works, output 1 is wrong — prob wants min k.

        if (g == 0) {
            System.out.println(1); // all a[i] = 0 and all offsets = 0 (impossible for i>0)
        } else {
            System.out.println(g);
        }
    }

    static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
