package phases.phase1_foundations.constructive_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1800B — Count the Pairs
 * Link   : https://codeforces.com/problemset/problem/1800/B
 * Rating : 1000  |  Tags: constructive algorithms, math, number theory
 * Topic  : Phase 1: Foundations > Constructive Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given an array of n integers, count the number of ordered pairs (i,j) with
 * i < j such that a[i] * a[j] > a[i] + a[j]. This is equivalent to finding
 * pairs where (a[i]-1)*(a[j]-1) > 1, i.e., both a[i] >= 2 and a[j] >= 2,
 * AND not both equal to 2 at the same time unless... Let's simplify:
 * a*b > a+b  ⟺  a*b - a - b > 0  ⟺  (a-1)(b-1) > 1.
 * So we need (a[i]-1)*(a[j]-1) > 1.
 * If both a[i],a[j] >= 3: (a[i]-1)(a[j]-1) >= 2*2=4 > 1. YES.
 * If a[i]=2 and a[j]=3: (1)(2)=2 > 1. YES.
 * If a[i]=2 and a[j]=2: (1)(1)=1. NO.
 * If either is 1: (0)(...)=0. NO.
 * So: let cnt1 = count of elements == 1, cnt2 = count of elements == 2,
 * cntBig = count of elements >= 3.
 * Valid pairs: C(cntBig,2) + cntBig*cnt2.
 *
 * EXAMPLE
 * -------
 * Input:  4 / 1 2 3 4
 * Output: 3
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Count elements that are 1, exactly 2, and >= 3.
 * 2. Pairs of type (big, big): C(cntBig, 2).
 * 3. Pairs of type (big, two): cntBig * cnt2.
 * 4. Sum these up.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(1)
 * ============================================================
 */

public class CF_1800B_CountThePairs {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            StringTokenizer st = new StringTokenizer(br.readLine());
            long cnt2 = 0, cntBig = 0;
            for (int i = 0; i < n; i++) {
                int x = Integer.parseInt(st.nextToken());
                if (x == 2) cnt2++;
                else if (x >= 3) cntBig++;
            }
            long ans = cntBig * (cntBig - 1) / 2 + cntBig * cnt2;
            sb.append(ans).append('\n');
        }
        System.out.print(sb);
    }
}
