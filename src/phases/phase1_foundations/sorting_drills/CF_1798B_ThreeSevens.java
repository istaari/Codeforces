package phases.phase1_foundations.sorting_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1798B — Three Sevens
 * Link   : https://codeforces.com/problemset/problem/1798/B
 * Rating : 1000  |  Tags: strings, sortings, implementation
 * Topic  : Phase 1: Foundations > Sorting Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Given three strings s1, s2, s3 consisting of digits. Check if the subsequence
 * "777" appears in exactly one of the three strings. Output the index (1, 2, or 3)
 * of that string, or -1 if it appears in none or more than one.
 *
 * EXAMPLE
 * -------
 * Input:  777 / 77 / 7
 * Output: 1
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. For each string, check if "777" appears as a subsequence.
 * 2. Count how many strings contain "777" as a subsequence.
 * 3. If exactly one, output its index; else output -1.
 * 4. Checking "777" as subsequence: greedily match three '7's in order.
 *
 * COMPLEXITY
 * ----------
 * Time : O(|s1| + |s2| + |s3|)
 * Space: O(1)
 * ============================================================
 */

public class CF_1798B_ThreeSevens {

    static boolean hasSubseq777(String s) {
        int count = 0;
        for (char c : s.toCharArray()) {
            if (c == '7') count++;
            if (count == 3) return true;
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            String[] strs = new String[3];
            for (int i = 0; i < 3; i++) strs[i] = br.readLine().trim();
            int ans = -1;
            int cnt = 0;
            for (int i = 0; i < 3; i++) {
                if (hasSubseq777(strs[i])) {
                    cnt++;
                    ans = i + 1;
                }
            }
            sb.append(cnt == 1 ? ans : -1).append('\n');
        }
        System.out.print(sb);
    }
}
