package phases.phase3_core_patterns.number_theory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1295D — Same GCDs
 * Link   : https://codeforces.com/problemset/problem/1295/D
 * Rating : 1600  |  Tags: number theory, euler totient, gcd
 * Topic  : Phase 3: Core Patterns > Number Theory
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given a and m. Count the number of integers b in [0, m-1] such that
 * gcd(a, m) == gcd(a + b, m).
 *
 * EXAMPLE
 * -------
 * Input:  4 12
 * Output: 2
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Let g = gcd(a, m). We want gcd(a+b, m) = g.
 *    This means g | (a+b) and g | m, so g | b (since g | a).
 *    Write a = g*a', m = g*m', b = g*b'. Then gcd(a+b, m) = g*gcd(a'+b', m').
 *    We want gcd(a'+b', m') = 1.
 * 2. b ranges over [0, m-1]. b = g*b' where b' in [0, m'-1] (m = g*m').
 *    We need gcd(a'+b', m') = 1.
 *    As b' varies over [0, m'-1], a'+b' mod m' takes all values 0..m'-1.
 *    So we count b' in [0,m'-1] with gcd(a'+b', m') = 1 = Euler's totient φ(m').
 * 3. Answer = φ(m / gcd(a, m)).
 *
 * COMPLEXITY
 * ----------
 * Time : O(sqrt(m/g)) per query
 * Space: O(1)
 * ============================================================
 */

public class CF_1295D_SameGCDs {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            long a = Long.parseLong(st.nextToken());
            long m = Long.parseLong(st.nextToken());

            long g = gcd(a, m);
            long m2 = m / g;
            long phi = eulerTotient(m2);
            sb.append(phi).append('\n');
        }

        System.out.print(sb);
    }

    static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    static long eulerTotient(long n) {
        long result = n;
        for (long p = 2; p * p <= n; p++) {
            if (n % p == 0) {
                while (n % p == 0) n /= p;
                result -= result / p;
            }
        }
        if (n > 1) result -= result / n;
        return result;
    }
}
