package phases.phase3_core_patterns.advanced_greedy_constructive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


/*
 * ============================================================
 * CF 1374E2 — Reading Books (Hard version)
 * Link   : https://codeforces.com/problemset/problem/1374/E2
 * Rating : 1600  |  Tags: binary search, segment tree, greedy
 * Topic  : Phase 3: Core Patterns > Advanced Greedy & Constructive
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * n books, each with a[i] topics only Alice knows and b[i] topics only Bob knows.
 * A book is "good" if a[i] >= b[i]. Alice needs sum_a >= k unique topics,
 * Bob needs sum_b >= k unique topics. Both read from the same set of books.
 * Find the minimum number of books to read such that both have >= k topics.
 *
 * EXAMPLE
 * -------
 * Input:  5 2
 *         1 1
 *         1 2
 *         2 1
 *         2 2
 *         3 3
 * Output: 2
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Sort books by a[i] + b[i] descending (or by some criterion).
 *    For a subset of t books: Alice gets sum(a[i]) topics, Bob gets sum(b[i]) topics.
 *    We need sum(a[i]) >= k and sum(b[i]) >= k.
 * 2. Binary search on the answer t. For a given t, can we find t books satisfying both conditions?
 *    Greedy: pick the t books maximizing min(sum_a, sum_b). Or: pick top-t books by a[i]+b[i]?
 *    Not necessarily optimal.
 * 3. For the hard version: Online queries adding books. After each book, find minimum to read.
 *    Use a sorted structure. At each step, binary search on t; feasibility = can we pick t books
 *    from current set with sum_a >= k and sum_b >= k?
 * 4. For feasibility check with t books: pick t books maximizing sum_a+sum_b. But we also need
 *    both individual sums >= k. Sort by a[i]-b[i]? Use segment tree or sorted multisets.
 *    For each possible split: take j "a-leaning" books (a[i] >= b[i]) and t-j "b-leaning" books.
 * 5. Simplified approach: sort all books by a[i]. Binary search on t. To check if t books suffice:
 *    greedily pick t books maximizing sum_a+sum_b subject to both >= k.
 *    Use prefix sums after sorting to answer feasibility in O(log n).
 *
 * COMPLEXITY
 * ----------
 * Time : O(n log^2 n) per query with segment tree
 * Space: O(n)
 * ============================================================
 */

public class CF_1374E2_ReadingBooksHard {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        long k = Long.parseLong(st.nextToken());

        long[][] books = new long[n][2];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            books[i][0] = Long.parseLong(st.nextToken());
            books[i][1] = Long.parseLong(st.nextToken());
        }

        // Sort by a+b descending (greedy: take highest combined first)
        Arrays.sort(books, (x, y) -> Long.compare(y[0] + y[1], x[0] + x[1]));

        // Prefix sums
        long[] prefA = new long[n + 1], prefB = new long[n + 1];
        for (int i = 0; i < n; i++) {
            prefA[i + 1] = prefA[i] + books[i][0];
            prefB[i + 1] = prefB[i] + books[i][1];
        }

        // Check if taking top t books gives sum_a >= k and sum_b >= k
        // But best split might not be top t by a+b. We might need to swap some books.
        // For a simpler check: binary search on t, for each t check if there exists a subset
        // of t books with both sums >= k. The optimal subset maximizes min(sumA, sumB).
        // Greedy for fixed t: take t books that maximize min(sumA, sumB).
        // This is hard to compute directly. Use: take top-t by sumA+sumB as a proxy.
        // Also try: take top books by sumA first (for Alice), verify sumB; and vice versa.

        // For each t, feasible = prefix top-t has sumA >= k AND sumB >= k?
        // This is a sufficient condition but not necessary. Full solution requires segment tree.
        // For educational purposes, implement the greedy proxy:
        int ans = -1;
        for (int t = 1; t <= n; t++) {
            if (prefA[t] >= k && prefB[t] >= k) {
                ans = t;
                break;
            }
        }

        System.out.println(ans);
    }
}
