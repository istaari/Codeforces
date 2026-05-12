package phases.phase1_foundations.c3_1000rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1176B — Merge It
 * Link   : https://codeforces.com/problemset/problem/1176/B
 * Rating : 1000  |  Tags: math
 * Topic  : Phase 1: Foundations > Math
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given an array of n integers, split it into the minimum number of
 * contiguous groups (subarrays) such that the sum of each group is
 * divisible by 3.
 *
 * EXAMPLE
 * -------
 * Input:  3 / 1 2 3
 * Output: 2   ({1,2} sum=3 ✓, {3} sum=3 ✓)
 *
 * Input:  5 / 1 1 1 1 1
 * Output: -1? No: 1+1+1=3 ✓, 1+1 only sum=2 not div... {1,1,1} + remaining {1,1} fails.
 *          But output: groups that each sum ≡ 0 (mod 3). Use prefix sums mod 3.
 *          Each group is from the last prefix-sum-0 position to current.
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Compute running prefix sum modulo 3.
 * 2. A group ends at position i if prefix[i] ≡ 0 (mod 3) and prefix > last group end.
 * 3. Count how many positions have prefix sum ≡ 0 (mod 3).
 * 4. If the total sum is not divisible by 3, output -1.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(1)
 * ============================================================
 */

public class CF_1176B_MergeIt {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            StringTokenizer st = new StringTokenizer(br.readLine());
            long prefixMod = 0;
            int groups = 0;
            for (int i = 0; i < n; i++) {
                long x = Long.parseLong(st.nextToken());
                prefixMod = (prefixMod + x % 3 + 3) % 3;
                if (prefixMod == 0) groups++;
            }
            sb.append(groups == 0 ? -1 : groups).append('\n');
        }
        System.out.print(sb);
    }
}
