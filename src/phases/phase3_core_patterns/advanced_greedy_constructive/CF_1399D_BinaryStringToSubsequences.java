package phases.phase3_core_patterns.advanced_greedy_constructive;

/*
 * ============================================================
 * CF 1399D — Binary String to Subsequences
 * Link   : https://codeforces.com/problemset/problem/1399/D
 * Rating : 1500  |  Tags: greedy, queues, strings
 * Topic  : Phase 3: Core Patterns > Advanced Greedy & Constructive
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given a binary string s. Partition it into the minimum number of subsequences
 * such that each subsequence looks like "0*1*" (all 0s before all 1s, any number of each).
 * Assign each character to a subsequence and output the subsequence number for each position.
 *
 * EXAMPLE
 * -------
 * Input:  4
 *         0011
 * Output: 2
 *         1 2 1 2
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Each subsequence has the form 0*1*. Once a subsequence has a '1', it cannot accept any '0'.
 * 2. Maintain two queues:
 *    - waitFor0: subsequences that can accept '0' (currently empty or have only 0s).
 *    - waitFor1: subsequences that can accept '1' (have only 0s, now waiting for 1s).
 * 3. Process characters left to right:
 *    - If s[i] = '0':
 *      If waitFor0 is non-empty: assign to one of those subsequences (it's still accepting 0s).
 *      Else: create a new subsequence. Push it to waitFor0.
 *      (After assignment, subsequence still in waitFor0 since it can accept more 0s or 1s.)
 *    - If s[i] = '1':
 *      If waitFor0 is non-empty: assign to one (move it from waitFor0 to waitFor1 since now has a 1).
 *      Else if waitFor1 is non-empty: assign to one (still in waitFor1).
 *      Else: create new subsequence starting with '1'. Put in waitFor1.
 * 4. Output: number of subsequences and assignment array.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1399D_BinaryStringToSubsequences {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            String s = br.readLine().trim();

            int[] ans = new int[n];
            Queue<Integer> waitFor0 = new ArrayDeque<>(); // subsequences that can still take '0'
            Queue<Integer> waitFor1 = new ArrayDeque<>(); // subsequences that took at least one '1'
            int numSeq = 0;

            for (int i = 0; i < n; i++) {
                if (s.charAt(i) == '0') {
                    if (!waitFor0.isEmpty()) {
                        // Assign to existing subsequence waiting for 0
                        int seqId = waitFor0.poll();
                        ans[i] = seqId;
                        waitFor0.offer(seqId); // can still accept more 0s or 1s
                    } else {
                        // Create new subsequence
                        numSeq++;
                        ans[i] = numSeq;
                        waitFor0.offer(numSeq);
                    }
                } else { // '1'
                    if (!waitFor0.isEmpty()) {
                        // Assign to a subsequence that had 0s; now it moves to "waiting for 1s only"
                        int seqId = waitFor0.poll();
                        ans[i] = seqId;
                        waitFor1.offer(seqId); // now only accepts 1s
                    } else if (!waitFor1.isEmpty()) {
                        // Assign to an existing subsequence already having 1s
                        int seqId = waitFor1.poll();
                        ans[i] = seqId;
                        waitFor1.offer(seqId);
                    } else {
                        // Create new subsequence starting with 1
                        numSeq++;
                        ans[i] = numSeq;
                        waitFor1.offer(numSeq);
                    }
                }
            }

            sb.append(numSeq).append('\n');
            for (int i = 0; i < n; i++) {
                if (i > 0) sb.append(' ');
                sb.append(ans[i]);
            }
            sb.append('\n');
        }

        System.out.print(sb);
    }
}
