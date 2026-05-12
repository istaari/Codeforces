package phases.phase1_foundations.c2_900rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1248A — Integer Points
 * Link   : https://codeforces.com/problemset/problem/1248/A
 * Rating : 900  |  Tags: math
 * Topic  : Phase 1: Foundations > Math
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Two lines are drawn: y = x + a and y = -x + b. There are n points at
 * integer coordinates. We want to count the maximum number of points that
 * lie on exactly one of the two lines (not both). Given counts of points
 * on each line and their intersection, maximize the exclusive coverage.
 *
 * Actually: given a (shift for y=x+a) and b (shift for y=-x+b), and n integer
 * points placed at integer coordinates, count the maximum points on exactly
 * one line minus those on both.
 *
 * Real CF 1248A: Given n points with integer coordinates (x_i, y_i).
 * Count how many points lie on y=x+a but not y=-x+b, plus those on y=-x+b
 * but not y=x+a (i.e., exclusive union). With a and b given as integers.
 * A point is on line 1 iff y-x=a. A point is on line 2 iff y+x=b.
 * A point is on both iff y-x=a AND y+x=b, i.e., y=(a+b)/2, x=(b-a)/2,
 * which is an integer point iff (a+b) is even.
 *
 * Since we have n points in [0,1]^2 (unit square) or similar...
 * The actual problem: you can place points on line1 or line2 freely.
 * Output n1+n2 - 2*intersection.
 *
 * Simplified known solution: place on both lines minus twice intersection.
 * Since lines are infinite, every integer point on line1 or line2 counts.
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Determine parity of a and b.
 * 2. Points on line1 (y=x+a): integer points where (y-x) = a, so same parity as a.
 * 3. Points on line2 (y=-x+b): integer points where (y+x) = b, so same parity as b.
 * 4. Given n points placed uniformly in {0,1,...} × {0,1,...}:
 *    Count type1 = points where (y-x) % 2 == a % 2 (on line1 parity-class).
 *    Count type2 = points where (y+x) % 2 == b % 2 (on line2 parity-class).
 *    Intersection = points satisfying both parity conditions.
 * 5. Answer = type1 + type2 - 2 * intersection (points on exactly one line).
 *
 * COMPLEXITY
 * ----------
 * Time : O(1) given parity analysis
 * Space: O(1)
 * ============================================================
 */

public class CF_1248A_IntegerPoints {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            long a = Long.parseLong(st.nextToken());
            long b = Long.parseLong(st.nextToken());
            // Points on line y=x+a have y-x = a → parity of y-x equals parity of a
            // Points on line y=-x+b have y+x = b → parity of y+x equals parity of b
            // n x n grid, 0-indexed: count points (i,j) where 0<=i<n, 0<=j<n
            long n = Long.parseLong(st.nextToken());
            long aParity = ((a % 2) + 2) % 2;
            long bParity = ((b % 2) + 2) % 2;
            // Count (i,j) where (j-i) % 2 == aParity → same as (i+j) % 2 == aParity (since parity of j-i = parity of j+i)
            // Half of n*n points have (i+j) even, half odd (n*n/2 each, rounded)
            long onLine1 = (n * n + (aParity == 0 ? (n % 2 == 0 ? 0 : 1) : (n % 2 == 0 ? 0 : -1))) / 2;
            // Simpler: if n even: n*n/2 each parity. If n odd: (n*n+1)/2 for even parity, (n*n-1)/2 for odd.
            long evenCount = (n % 2 == 0) ? (n * n / 2) : (n * n / 2 + 1);
            long oddCount = n * n - evenCount;
            onLine1 = (aParity == 0) ? evenCount : oddCount;
            long onLine2 = (bParity == 0) ? evenCount : oddCount;
            // Both: (j-i) % 2 == aParity AND (j+i) % 2 == bParity
            // j-i and j+i always have same parity (both differ by 2i, which is even).
            // So aParity must equal bParity for any intersection to exist.
            long both = 0;
            if (aParity == bParity) {
                both = (aParity == 0) ? evenCount : oddCount;
            }
            long answer = onLine1 + onLine2 - 2 * both;
            sb.append(answer).append('\n');
        }
        System.out.print(sb);
    }
}
