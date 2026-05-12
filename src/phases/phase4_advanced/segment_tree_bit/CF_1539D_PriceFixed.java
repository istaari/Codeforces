package phases.phase4_advanced.segment_tree_bit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1539D — PriceFixed
 * Link   : https://codeforces.com/problemset/problem/1539/D
 * Rating : 1600  |  Tags: two pointers, sorting, greedy
 * Topic  : Phase 4: Advanced > Segment Tree / BIT
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * n items, each with price a[i]. You have s coins. Items with price <= s/2
 * can be bought for free (you choose s = current coins, take item if a[i] <= s/2).
 * Actually: at each step you choose how many coins to spend. Items cost their
 * price. Find minimum total cost to buy all items.
 *
 * ACTUAL PROBLEM: n items with costs a[i], sorted. You have budget s and must
 * buy all n items. For items with cost <= floor(s/2), you can buy them for free.
 * You must spend to buy items with cost > floor(s/2). Minimize total cost.
 * After buying a free item, s doesn't change. After paying for an item of cost c,
 * s decreases by c... no.
 *
 * RESTATEMENT from CF: n items with prices a[i]. Initially have s coins.
 * In each operation: pick an item. If a[i] <= floor(s/2), buy it for free.
 * Otherwise buy it for a[i] (s decreases by a[i]). Minimize total coins spent.
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Sort items by price. Two pointers: lo (cheapest unpurchased) and hi (most expensive).
 * 2. If a[hi] <= floor(s/2): all items buy for free, total = 0. (But s is fixed)
 *    Wait: s is the CURRENT coin count, which decreases as we pay.
 * 3. Greedy: pair cheap free items with expensive paid items.
 *    Sort a[]. Use two pointers: lo=0 (cheapest), hi=n-1 (most expensive).
 *    If a[hi] <= s/2: it's free. hi--.
 *    Else: must pay for a[hi]. s -= a[hi]. hi--. Also lo++ if we "used" a cheap item.
 *    Wait: free items reduce the expensive count so we pay less overall.
 *    Key: if a[hi] <= s/2, buy hi for free; else buy hi for a[hi] (pay), s -= a[hi],
 *    and "match" with lo to bump lo++.
 * 4. Actually: each time we pay for hi (expensive item), we consume one free slot that
 *    could go to lo. So: if a[hi] <= s/2, buy hi for free (hi--). Else pay for hi,
 *    buy lo for free simultaneously (hi--, lo++). Until lo > hi.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1539D_PriceFixed {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            long s = Long.parseLong(st.nextToken());
            long[] a = new long[n];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) a[i] = Long.parseLong(st.nextToken());
            Arrays.sort(a);

            long totalCost = 0;
            int lo = 0, hi = n - 1;

            while (lo <= hi) {
                if (a[hi] <= s / 2) {
                    // All remaining items can be bought for free
                    break;
                } else {
                    // Must pay for a[hi]
                    totalCost += a[hi];
                    s -= a[hi];
                    hi--;
                    // The cheap item a[lo] can now be bought for free (match)
                    lo++;
                }
            }

            sb.append(totalCost).append('\n');
        }

        System.out.print(sb);
    }
}
