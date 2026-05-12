package phases.phase1_foundations.c3_1000rated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1234B — Social Network
 * Link   : https://codeforces.com/problemset/problem/1234/B
 * Rating : 1000  |  Tags: implementation
 * Topic  : Phase 1: Foundations > Implementation
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * A social network feed has n posts, the i-th post has c[i] comments. The
 * news feed can show at most k posts at a time. Lena opens the app and reads
 * all posts in the current feed (up to k posts). How many comments total
 * will she read? (Just sum the first min(n, k) comment counts.)
 *
 * EXAMPLE
 * -------
 * Input:  3 2 / 2 5 1
 * Output: 7   (first 2 posts: 2 + 5 = 7)
 *
 * Input:  3 3 / 1 2 3
 * Output: 6
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Read n, k, and the array c.
 * 2. Sum the first min(n, k) elements of c.
 * 3. Output the sum.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1234B_SocialNetwork {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        long sum = 0;
        int limit = Math.min(n, k);
        for (int i = 0; i < n; i++) {
            long c = Long.parseLong(st.nextToken());
            if (i < limit) sum += c;
        }
        System.out.println(sum);
    }
}
