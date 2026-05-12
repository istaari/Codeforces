package phases.phase1_foundations.constructive_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1807B — Grab the Candies
 * Link   : https://codeforces.com/problemset/problem/1807/B
 * Rating : 800  |  Tags: constructive algorithms
 * Topic  : Phase 1: Foundations > Constructive Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Alice and Bob are grabbing candies from an array. Alice takes all even-valued
 * candies and Bob takes all odd-valued candies. Compare the sums: if Alice's sum
 * is strictly greater, print "Alice"; if Bob's sum is strictly greater, print "Bob";
 * otherwise print "Equal".
 *
 * EXAMPLE
 * -------
 * Input:  5 / 1 2 3 4 5
 * Output: Alice
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Iterate through the array.
 * 2. Sum all even numbers into aliceSum.
 * 3. Sum all odd numbers into bobSum.
 * 4. Compare and output accordingly.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(1)
 * ============================================================
 */

public class CF_1807B_GrabTheCandies {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            StringTokenizer st = new StringTokenizer(br.readLine());
            long alice = 0, bob = 0;
            for (int i = 0; i < n; i++) {
                int x = Integer.parseInt(st.nextToken());
                if (x % 2 == 0) alice += x;
                else bob += x;
            }
            if (alice > bob) sb.append("Alice\n");
            else if (bob > alice) sb.append("Bob\n");
            else sb.append("Equal\n");
        }
        System.out.print(sb);
    }
}
