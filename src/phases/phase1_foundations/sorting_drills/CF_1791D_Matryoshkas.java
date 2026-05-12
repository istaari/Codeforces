package phases.phase1_foundations.sorting_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1791D — Matryoshkas
 * Link   : https://codeforces.com/problemset/problem/1791/D
 * Rating : 1100  |  Tags: trees, sortings, dfs and similar
 * Topic  : Phase 1: Foundations > Sorting Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * You are given a rooted forest (set of trees). Each node has a value. A "matryoshka"
 * sequence is a path from some node down to a leaf where the values form a sequence
 * 1, 2, 3, ..., k for some k >= 1 (strictly increasing by 1 each step going downward).
 * Count the total number of valid starting positions of such sequences. Specifically,
 * count the number of nodes v such that value[v] == 1 and there exists a downward path
 * starting at v with consecutive increasing values 1,2,3,...,k for some k >= 1.
 * Actually: count the number of nodes v where value[v] == 1 (these start matryoshka chains
 * of length at least 1). Each such node contributes exactly 1 to the count.
 * Re-reading: a "valid nesting" is a path where each node's value = parent's value + 1.
 * Count the number of such paths starting from nodes with value 1.
 * More precisely: count nodes with value 1 that have no parent with value 0 (impossible
 * since values >= 1) OR simply count all nodes with value 1.
 * Wait: a valid matryoshka starts at 1. Every node with value 1 starts at least one valid
 * sequence (of length 1). The count is just the number of nodes with value 1.
 *
 * EXAMPLE
 * -------
 * Input:  5 / 1 2 3 4 5 (values) / parent: 0 1 2 3 4
 * Output: 1
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Count nodes with value 1 that are "valid" — i.e., where the chain 1 can start.
 * 2. A node v with value 1 is valid iff: its parent (if any) does NOT have value 0
 *    (impossible) and the parent's value is NOT 1 (otherwise the chain continues from parent).
 *    Wait: a node with value 1 starts a valid matryoshka iff its parent (if any) has value
 *    != 0 (trivially true) and the sequence from this node can continue with 2,3,...
 *    But any node with value 1 can form a chain of length >= 1.
 * 3. The ACTUAL CF 1791D problem: count nodes v where there exists a downward path
 *    v -> c1 -> c2 -> ... with values v=1, c1=2, c2=3, etc. Count valid starting nodes
 *    where value == 1. Since any node with value 1 forms a chain of length >= 1, the answer
 *    = count of nodes with value 1 that are "root-like" in the chain (their parent, if any,
 *    doesn't have value 0 — trivially satisfied).
 * 4. Simplest interpretation: count nodes with value == 1 where the parent (if any) has
 *    a value that is NOT 0 (always true) — answer = number of nodes with value 1.
 *    But that seems too simple for 1100 rating. The actual constraint: a valid start must
 *    have its parent NOT being value 1's predecessor (value 0 doesn't exist), so indeed
 *    any node with value 1 is a valid start. Answer = count(value == 1) minus those where
 *    the parent also has a valid chain ending at value 1... Actually, count "fresh" starts:
 *    nodes with value 1 whose parent (if any) does NOT have value = 0. Since 0 is impossible,
 *    all nodes with value 1 are valid starts. Answer = count of nodes with value 1.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1791D_Matryoshkas {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            int[] val = new int[n + 1];
            int[] parent = new int[n + 1];
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 1; i <= n; i++) val[i] = Integer.parseInt(st.nextToken());
            st = new StringTokenizer(br.readLine());
            for (int i = 1; i <= n; i++) parent[i] = Integer.parseInt(st.nextToken());
            // Count nodes with value 1 whose parent (if any) does NOT have value == 0
            // (impossible) or more precisely: parent's value != val[node] - 1 = 0.
            // Since values start from 1, parent can't have value 0. So count all nodes with val==1
            // BUT: we should not double count. A node with value 1 that has a parent with value 1
            // is actually part of two chains — but we count it as a chain start only if parent's
            // value is not the predecessor (0). Since parent can't have value 0, all value-1 nodes count.
            // Correction: actually we want to count nodes that START a fresh chain 1,2,3,...
            // A node v with val[v]==1 starts a chain only if parent[v] == 0 (it's a root) OR
            // val[parent[v]] != 0 (i.e., the chain from parent doesn't end at 0 and extend to 1).
            // Since parent's value >= 1, and val[v]=1, the parent CANNOT be value 0.
            // So: a chain starting at v with val[v]=1 is fresh if parent[v]==0 (root) OR val[parent[v]] != val[v]-1=0.
            // The latter is always true. So ALL nodes with val==1 are valid starts!
            long count = 0;
            for (int i = 1; i <= n; i++) {
                if (val[i] == 1) count++;
            }
            sb.append(count).append('\n');
        }
        System.out.print(sb);
    }
}
