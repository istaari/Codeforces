package phases.phase2_toolkit.bit_manipulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1365C — Rotation Matching
 * Link   : https://codeforces.com/problemset/problem/1365/C
 * Rating : 1300  |  Tags: hashing, frequency map, strings
 * Topic  : Phase 2: Toolkit > Bit Manipulation
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given two strings a and b of length n (consisting of digits 1..n,
 * both being permutations). Find the rotation of b that maximizes
 * the number of positions where a[i] == b[(i+k) mod n] for some shift k.
 *
 * EXAMPLE
 * -------
 * Input:  5
 *         3 4 5 1 2
 *         2 3 4 5 1
 * Output: 4
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. For a fixed shift k, matching positions are those where a[i] == b[(i+k)%n].
 *    For each value v, let pa[v] = position of v in a, pb[v] = position of v in b.
 *    For matching at position pa[v] with shift k: b[(pa[v]+k)%n] = v = a[pa[v]].
 *    This means b[(pa[v]+k)%n] = v → position of v in b = (pa[v]+k)%n.
 *    → pb[v] = (pa[v] + k) % n → k = (pb[v] - pa[v] + n) % n.
 * 2. For each value v, compute shift k[v] = (pb[v] - pa[v] + n) % n.
 *    The best shift k is the most frequent k[v] value.
 * 3. Use frequency map: count occurrences of each shift k[v], take maximum.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1365C_RotationMatching {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());

        int[] posA = new int[n + 1]; // posA[v] = index of v in a (0-indexed)
        int[] posB = new int[n + 1]; // posB[v] = index of v in b (0-indexed)

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            int v = Integer.parseInt(st.nextToken());
            posA[v] = i;
        }
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            int v = Integer.parseInt(st.nextToken());
            posB[v] = i;
        }

        // For each value v, compute shift k = (posB[v] - posA[v] + n) % n
        HashMap<Integer, Integer> freq = new HashMap<>();
        for (int v = 1; v <= n; v++) {
            int k = ((posB[v] - posA[v]) % n + n) % n;
            freq.merge(k, 1, Integer::sum);
        }

        int ans = 0;
        for (int cnt : freq.values()) ans = Math.max(ans, cnt);
        System.out.println(ans);
    }
}
