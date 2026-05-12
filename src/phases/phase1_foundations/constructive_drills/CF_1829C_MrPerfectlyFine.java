package phases.phase1_foundations.constructive_drills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1829C — Mr. Perfectly Fine
 * Link   : https://codeforces.com/problemset/problem/1829/C
 * Rating : 900  |  Tags: constructive algorithms, greedy
 * Topic  : Phase 1: Foundations > Constructive Drills
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * There are n problems. Each problem can be solved on day a[i] (if you know topic 1)
 * or day b[i] (if you know topic 2). Initially you know neither topic. Solving a
 * problem on day a[i] teaches you topic 1; on day b[i] teaches you topic 2. You
 * can only solve a problem if you already know the required topic OR it is the first
 * problem of that type. Find the minimum possible "last day" you solve all problems,
 * or determine it is impossible.
 * Re-read: Each problem has two modes: type1 on day a[i], type2 on day b[i].
 * You want to pick one mode for each problem. Output the minimum max day.
 *
 * EXAMPLE
 * -------
 * Input:  3 / 1 5 / 2 4 / 3 3
 * Output: 4
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. For each problem, you pick either a[i] or b[i]. Minimize the maximum chosen day.
 * 2. Key insight: to learn topic 1, you need some problem solved via its a[i] value.
 *    To learn topic 2, you need some problem solved via its b[i] value. But you can
 *    only solve a type-a problem if you already know topic 1, etc.
 * 3. However, for each problem independently, you can choose the minimum of a[i] and
 *    b[i] since you always have the option to learn the needed topic from any prior
 *    problem. Wait — the actual problem: each card has type (1 or 2, exactly one).
 *    Cards of type 1: you can only read them if you've studied topic 1. Cards of
 *    type 2: only if topic 2 studied. To study a topic you must read a card of that type.
 *    That's circular — the trick is you can read ANY one card for free on day 1.
 *    Re-reading actual CF 1829C: n books, book i can be read on day a[i] if you know
 *    skill 1, or day b[i] if you know skill 2. You start knowing no skills. Reading a
 *    book on day a[i] gives skill 1; on day b[i] gives skill 2. Find minimum finishing day.
 * 4. You must pick at least one book to read with skill-1 mode and at least one with
 *    skill-2 mode (to gain both skills), unless all books give the same skill.
 *    Actually you need to be able to read each book — you need skill 1 for type-a and
 *    skill 2 for type-b. To minimize max day, for each book pick the mode that minimizes
 *    its day, but ensure at least one book is read in each mode needed.
 * 5. Simplified correct approach: find the book with minimum a[i] (call it minA) and
 *    the book with minimum b[i] (call it minB). The answer is max(minA, minB) IF both
 *    skills are needed. If we only need skill 1, answer is max of all a[i]. Etc.
 *    Since all books must be readable: for each book, we pick the mode, so we want
 *    min(a[i], b[i]) for each but we need both skills available.
 *    Best strategy: pick one book to give skill 1 (pick the one with min a[i]), one to
 *    give skill 2 (min b[i]), rest can use whichever is smaller. Answer = max over all books
 *    of min(a[i], b[i]) but that book providing skill 1 and skill 2 respectively selected.
 *    Actually: answer = max(min_a, min_b) where min_a = min of all a[i], min_b = min of all b[i].
 *    Because: we read the book with min_a on day min_a (gaining skill 1) and read the book
 *    with min_b on day min_b (gaining skill 2). Now for all other books, day = max(min_a, min_b).
 *    All other books can be read on day max(min_a, min_b) since both skills acquired by then.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(1)
 * ============================================================
 */

public class CF_1829C_MrPerfectlyFine {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            long minA = Long.MAX_VALUE, minB = Long.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                long a = Long.parseLong(st.nextToken());
                long b = Long.parseLong(st.nextToken());
                minA = Math.min(minA, a);
                minB = Math.min(minB, b);
            }
            sb.append(Math.max(minA, minB)).append('\n');
        }
        System.out.print(sb);
    }
}
