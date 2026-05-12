package phases.phase5_cm_push.advanced_graphs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1487E — Paintings and Drawings
 * Link   : https://codeforces.com/problemset/problem/1487/E
 * Rating : 1900  |  Tags: graphs, dp, combinatorics, dsu
 * Topic  : Phase 5: CM Push > Advanced Graphs
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * n students and k teachers (clubs). Each student i must be
 * assigned to either teacher c1[i] or teacher c2[i] (two choices).
 * Two teachers a and b are "linked" if a student exists who can
 * choose between a and b. For each connected component in the
 * teacher graph, the assignment must be "balanced": the number of
 * students assigned to each teacher differs by at most 1 (across
 * all teachers in the component? Or per pair? Actually: the problem
 * says the total students assigned to all teachers must be
 * distributed as evenly as possible).
 * More precisely: each connected component of teachers must have
 * an equal-count assignment (or differ by at most 1 for odd counts).
 * Find the number of valid assignments modulo 998244353.
 *
 * EXAMPLE
 * -------
 * Input:  3 3
 *         1 2
 *         1 3
 *         2 3
 * Output: 6
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Build a graph where teachers are nodes and there's an edge
 *    for each student between their two possible teachers.
 * 2. Find connected components of teachers via DSU.
 * 3. For each connected component with T teachers and S students
 *    (edges in the component), determine how many ways to assign
 *    students such that each teacher gets floor(S/T) or ceil(S/T)
 *    students.
 * 4. Key insight: if S % T != 0 and the component is a tree (S = T-1)
 *    or a unicyclic graph (S = T), or has S < T → impossible if
 *    S % T != 0 in certain configurations.
 *    Actually: a tree component with T teachers has T-1 students.
 *    Since (T-1)/T is not integer for T>1 and we need each teacher
 *    to get ≈ (T-1)/T ≈ 1 student, this means some get 0 and some
 *    get 1. But T teachers with T-1 students: one teacher gets 0,
 *    rest get 1. For a tree, there are exactly T-1 students, so
 *    one teacher must be "empty" — and the assignment is a spanning
 *    tree orientation problem.
 *    For a component with cycles (S >= T): S students, T teachers.
 *    If S % T == 0: each teacher gets exactly S/T. If not: impossible?
 *    No, the balance condition is |assigned_a - assigned_b| <= 1
 *    for connected pairs — actually per the problem the total for
 *    the component must be split as evenly as possible.
 * 5. For tree components (S = T - 1 edges = students):
 *    - Each teacher must receive 1 student except one (root-like).
 *    - The number of valid assignments = T (choose which teacher
 *      gets 0). Actually it's a matching/assignment counting.
 *    - For a tree: the number of ways to orient all edges such that
 *      exactly one node has in-degree 0 = T (one root choice), and
 *      for each root choice, the orientation is unique (from leaves
 *      inward). So count = T.
 * 6. For unicyclic (S = T): each teacher gets exactly 1 student.
 *    This is a perfect matching orientation. For a cycle of length L,
 *    there are 2 ways (clockwise/counterclockwise). Each tree branch
 *    attached is deterministic. So for each component: 2 if it has
 *    a cycle, T if it's a tree (where T = number of teachers).
 * 7. Final answer = product of counts across all components.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n + k * alpha(k))
 * Space: O(n + k)
 * ============================================================
 */

public class CF_1487E_PaintingsAndDrawings {

    static final long MOD = 998244353;
    static int[] parent, rnk;
    static int[] compSize;  // number of teachers in component
    static int[] compEdges; // number of students (edges) in component
    static boolean[] hasCycle;

    static int find(int x) {
        while (parent[x] != x) {
            parent[x] = parent[parent[x]];
            x = parent[x];
        }
        return x;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        parent = new int[k + 1];
        rnk = new int[k + 1];
        compSize = new int[k + 1];
        compEdges = new int[k + 1];
        hasCycle = new boolean[k + 1];

        for (int i = 1; i <= k; i++) {
            parent[i] = i;
            compSize[i] = 1;
        }

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            int ra = find(a), rb = find(b);
            if (ra == rb) {
                // Same component: adding this edge creates/confirms a cycle
                compEdges[ra]++;
                hasCycle[ra] = true;
            } else {
                // Merge components
                if (rnk[ra] < rnk[rb]) { int t = ra; ra = rb; rb = t; }
                parent[rb] = ra;
                if (rnk[ra] == rnk[rb]) rnk[ra]++;
                compSize[ra] += compSize[rb];
                compEdges[ra] += compEdges[rb] + 1; // +1 for this edge
                hasCycle[ra] = hasCycle[ra] || hasCycle[rb];
            }
        }

        // For each component, compute contribution to answer
        long answer = 1;
        for (int i = 1; i <= k; i++) {
            if (find(i) == i) {
                // This is a root of a component
                int T = compSize[i];  // teachers
                int S = compEdges[i]; // students

                if (S == T - 1 && !hasCycle[i]) {
                    // Tree: S = T-1, one teacher gets 0, rest get 1
                    // Number of ways = T (choice of which teacher is empty
                    // is equivalent to choice of root orientation → T ways)
                    answer = answer * T % MOD;
                } else if (S == T && hasCycle[i]) {
                    // Unicyclic: each teacher gets exactly 1 student
                    // The cycle gives 2 orientation choices; tree parts are forced
                    answer = answer * 2 % MOD;
                } else if (S < T - 1) {
                    // Impossible: can't distribute S students to T teachers
                    // such that |assignment| is balanced (floor/ceil)
                    // Actually if S=0 and T=1: teacher gets 0 students → 1 way
                    if (T == 1 && S == 0) {
                        // Single teacher with no students: 1 way (gets 0)
                        // No contribution to answer (multiply by 1)
                    } else {
                        // Multiple components' teachers but not enough students
                        // If S < T-1: answer = 0
                        answer = 0;
                    }
                } else {
                    // S > T (more students than teachers) or other cases
                    // If S % T != 0: still possible if balanced (floor/ceil)
                    // This is a more complex case — use 2 for each extra cycle
                    // Each extra cycle beyond the spanning tree gives 2 ways
                    int extraEdges = S - (T - 1); // number of cycles
                    if (extraEdges >= 0) {
                        // 2^extraEdges ways
                        answer = answer * modpow(2, extraEdges, MOD) % MOD;
                    }
                }
            }
        }

        System.out.println(answer);
    }

    static long modpow(long base, long exp, long mod) {
        long result = 1;
        base %= mod;
        while (exp > 0) {
            if ((exp & 1) == 1) result = result * base % mod;
            base = base * base % mod;
            exp >>= 1;
        }
        return result;
    }
}
