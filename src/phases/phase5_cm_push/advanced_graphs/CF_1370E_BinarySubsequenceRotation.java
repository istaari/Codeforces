package phases.phase5_cm_push.advanced_graphs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;


/*
 * ============================================================
 * CF 1370E — Binary Subsequence Rotation
 * Link   : https://codeforces.com/problemset/problem/1370/E
 * Rating : 1900  |  Tags: greedy, strings, constructive
 * Topic  : Phase 5: CM Push > Advanced Graphs
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given two binary strings s and t of equal length n.
 * Partition the indices of s into the minimum number of groups
 * (subsequences) such that within each group, the subsequence
 * of s at those indices can be transformed into the corresponding
 * subsequence of t by a sequence of operations where each
 * operation flips a character (0→1 or 1→0) and the flips must
 * alternate: first flip changes 0→1, second 1→0, etc. (or vice
 * versa). Equivalently: within each group, consecutive characters
 * where s[i] != t[i] must alternate between (s='0',t='1') and
 * (s='1',t='0') at positions where they differ.
 * Output the minimum number of groups and the group assignment.
 *
 * EXAMPLE
 * -------
 * Input:  6
 *         101101
 *         011010
 * Output: 3
 *         1 2 3 2 1 3
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. For each position i:
 *    - If s[i] == t[i]: this position doesn't need a flip; it can
 *      go to any group without affecting the alternation requirement.
 *      Actually: we must still assign it to a group. Since it requires
 *      no flip, it can join any group as a "free" position.
 * 2. For positions where s[i] != t[i], they have a "type":
 *    - Type A: s[i]='0', t[i]='1' (need 0→1 flip)
 *    - Type B: s[i]='1', t[i]='0' (need 1→0 flip)
 *    Within a group, these types must strictly alternate.
 * 3. Greedy: maintain a list of groups, each tracking their
 *    last "type needed" (A or B — the type of the next required flip).
 *    For each position i where s[i] != t[i]:
 *    - Find a group whose last needed type matches s[i]→t[i].
 *    - If found, assign to that group and update its last type.
 *    - If not found, open a new group.
 *    For positions where s[i] == t[i]: assign to group 1 (or any).
 * 4. For efficiency, maintain two stacks: one for groups ending
 *    with type A, one for groups ending with type B.
 *    When we see a type A position, take a group from the type A
 *    stack (its next needed type is A) — wait, the group needs
 *    the NEXT type to match. After adding a type A position,
 *    the group's next type is B (alternating).
 *    So: groups are in two buckets by their "last added type".
 *    For a new type A position: take from the "last was B" bucket.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1370E_BinarySubsequenceRotation {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        String s = br.readLine().trim();
        String t = br.readLine().trim();

        int[] assignment = new int[n];

        // Two stacks: groups whose last type was 0 (s='0',t='1')
        // and groups whose last type was 1 (s='1',t='0')
        // Each stack stores group IDs
        Deque<Integer> lastWas0 = new ArrayDeque<>(); // groups last added type 0→1
        Deque<Integer> lastWas1 = new ArrayDeque<>(); // groups last added type 1→0

        int groupCount = 0;

        for (int i = 0; i < n; i++) {
            char si = s.charAt(i);
            char ti = t.charAt(i);

            if (si == ti) {
                // No flip needed. Assign to any existing group or new group.
                // We can assign to a group from either bucket; it doesn't change the bucket.
                // To minimize groups, prefer reusing. Assign from group in lastWas0 or lastWas1,
                // and put it back in the same bucket (since s[i]==t[i] doesn't change last type).
                if (!lastWas0.isEmpty()) {
                    int g = lastWas0.peek(); // don't remove, just reuse
                    assignment[i] = g;
                    // Group's last type is still 0 (we didn't change it)
                } else if (!lastWas1.isEmpty()) {
                    int g = lastWas1.peek();
                    assignment[i] = g;
                } else {
                    // No groups yet
                    groupCount++;
                    lastWas0.push(groupCount); // arbitrary bucket
                    assignment[i] = groupCount;
                }
            } else if (si == '0') {
                // Type: 0→1, i.e., "last type = 0"
                // To alternate, we need a group whose last type was 1
                if (!lastWas1.isEmpty()) {
                    int g = lastWas1.pop();
                    assignment[i] = g;
                    lastWas0.push(g); // now last type is 0
                } else {
                    // No suitable group, open new
                    groupCount++;
                    assignment[i] = groupCount;
                    lastWas0.push(groupCount);
                }
            } else {
                // si == '1', type: 1→0, "last type = 1"
                // Need a group whose last type was 0
                if (!lastWas0.isEmpty()) {
                    int g = lastWas0.pop();
                    assignment[i] = g;
                    lastWas1.push(g);
                } else {
                    groupCount++;
                    assignment[i] = groupCount;
                    lastWas1.push(groupCount);
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append(groupCount).append('\n');
        for (int i = 0; i < n; i++) {
            if (i > 0) sb.append(' ');
            sb.append(assignment[i]);
        }
        System.out.println(sb);
    }
}
