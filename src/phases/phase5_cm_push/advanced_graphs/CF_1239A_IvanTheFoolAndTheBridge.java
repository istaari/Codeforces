package phases.phase5_cm_push.advanced_graphs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1239A — Ivan the Fool and the Bridge
 * Link   : https://codeforces.com/problemset/problem/1239/A
 * Rating : 1800  |  Tags: graphs, math, combinatorics
 * Topic  : Phase 5: CM Push > Advanced Graphs
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * A bridge has r red planks and b blue planks, with r + b total.
 * Person crosses from side 1 to side 2. They must use planks
 * alternating red-blue (no two consecutive same color).
 * All r red and b blue planks must be used exactly once.
 * Count the number of valid arrangements (permutations of planks
 * such that colors alternate). Answer mod 10^9 + 7.
 *
 * EXAMPLE
 * -------
 * Input:  2 2
 * Output: 4
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. For colors to alternate with r red and b blue:
 *    - If |r - b| > 1: impossible → answer 0.
 *    - If r == b: can start with either red or blue → 2 sequences.
 *      Number of arrangements = 2 * r! * b!
 *    - If r == b + 1: must start with red → 1 sequence of colors.
 *      Number of arrangements = r! * b!
 *    - If b == r + 1: must start with blue → 1 sequence of colors.
 *      Number of arrangements = r! * b!
 * 2. Within each color, the planks are distinct (permutable): r!
 *    ways for red, b! ways for blue. The color sequence is fixed
 *    once we choose which color starts.
 * 3. Compute factorials modulo 10^9 + 7.
 *
 * COMPLEXITY
 * ----------
 * Time : O(r + b) for factorial computation
 * Space: O(1)
 * ============================================================
 */

public class CF_1239A_IvanTheFoolAndTheBridge {

    static final long MOD = 1_000_000_007;

    static long factorial(int n) {
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result = result * i % MOD;
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        long r = Long.parseLong(st.nextToken());
        long b = Long.parseLong(st.nextToken());

        long diff = Math.abs(r - b);
        if (diff > 1) {
            System.out.println(0);
            return;
        }

        long rf = factorial((int) r);
        long bf = factorial((int) b);

        long answer;
        if (r == b) {
            // Can start with either color: 2 * r! * b!
            answer = 2 * rf % MOD * bf % MOD;
        } else {
            // |r - b| == 1: must start with the larger group
            answer = rf * bf % MOD;
        }

        System.out.println(answer);
    }
}
