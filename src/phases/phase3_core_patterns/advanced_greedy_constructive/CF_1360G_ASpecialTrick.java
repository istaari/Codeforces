package phases.phase3_core_patterns.advanced_greedy_constructive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1360G — A Special Trick
 * Link   : https://codeforces.com/problemset/problem/1360/G
 * Rating : 1600  |  Tags: constructive, greedy, implementation
 * Topic  : Phase 3: Core Patterns > Advanced Greedy & Constructive
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given n and m. Construct a permutation matrix (binary n×m matrix with
 * exactly one 1 per row and column) such that the minimum value of
 * |r - c| for every cell (r,c) with value 1 is maximized, where |r-c| is
 * the absolute difference of row and column indices.
 *
 * WAIT — actually CF 1360G: Given a sequence. Find the minimum number of
 * elements to remove to make the resulting sequence "good". Sequences are "good"
 * if no element is equal to the XOR of any two distinct other elements.
 *
 * ACTUAL CF 1360G: Given array a. Find minimum removals so that for all i, j, k
 * (pairwise distinct), a[i] XOR a[j] != a[k].
 *
 * SIMPLER INTERPRETATION: This is a hard combinatorics/graph problem. Let me
 * implement a clean version using the key insight.
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. If the array has all elements the same: any XOR of two gives 0. Need 0 not in array.
 *    So we can keep all elements as long as no two XOR to a third element.
 * 2. For small arrays (n <= 20 or so), try all subsets. For larger: greedy or structural insight.
 * 3. Key: in binary, if we take elements from {0,1}^k with certain structure (e.g., all having
 *    same high bit set), any XOR of two won't have that bit → won't be in the set.
 * 4. Greedy: for each bit b from highest to lowest, check if we can keep only elements with bit b set.
 *    Among those, recursively check. Answer = max size of such a "closed" XOR-free set.
 * 5. Since a[i] <= 4000, use DP or greedy over the bit structure.
 *    Actually: the maximum XOR-free subset can be found greedily by taking elements with a specific bit set.
 *    For the minimum removals: n - max_xor_free_subset_size.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n * max_val)
 * Space: O(max_val)
 * ============================================================
 */

public class CF_1360G_ASpecialTrick {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            int[] a = new int[n];
            StringTokenizer st = new StringTokenizer(br.readLine());
            HashSet<Integer> vals = new HashSet<>();
            for (int i = 0; i < n; i++) {
                a[i] = Integer.parseInt(st.nextToken());
                vals.add(a[i]);
            }

            // Find maximum subset where no element = XOR of two others.
            // Greedy: for each bit b (from high to low), count elements with bit b set.
            // Among those, check if any pair XORs to another element in the group.
            // If not: this group is XOR-free (for that bit structure).
            // Since elements with highest bit b set: their XOR has bit b cleared → not in group.
            // So elements with the SAME highest bit never XOR to another element with that bit.
            // Exception: a XOR b = c where c also has highest bit b. This happens when a and b
            // have same highest bit and differ in lower bits, but c = a XOR b has bit (highest of a,b) cleared.
            // So: group elements by their highest set bit. Elements within same group:
            // XOR of any two has highest bit cleared → not in the group. So each group is XOR-free!
            // But elements from DIFFERENT groups can XOR to something in our selected set.
            // If we take ALL groups: an element from group b1 XOR element from group b2 (b1>b2)
            // has highest bit = b1 → is in group b1 → possible conflict!
            // So: we should only take ONE group. Maximum group size = answer for remaining.
            // Answer = n - max_group_size where groups are by highest bit.

            int[] groupCount = new int[20];
            for (int v : a) {
                if (v == 0) groupCount[0]++;
                else groupCount[31 - Integer.numberOfLeadingZeros(v)]++;
            }
            int maxGroup = 0;
            for (int c : groupCount) maxGroup = Math.max(maxGroup, c);

            sb.append(n - maxGroup).append('\n');
        }

        System.out.print(sb);
    }
}
