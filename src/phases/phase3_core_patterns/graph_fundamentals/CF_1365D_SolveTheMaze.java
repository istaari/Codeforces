package phases.phase3_core_patterns.graph_fundamentals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


/*
 * ============================================================
 * CF 1365D — Solve the Maze
 * Link   : https://codeforces.com/problemset/problem/1365/D
 * Rating : 1500  |  Tags: bfs, implementation, greedy
 * Topic  : Phase 3: Core Patterns > Graph Fundamentals
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * n×m grid of cells: '.' (empty), '#' (wall), 'G' (good person), 'B' (bad person).
 * Modify the grid by placing additional walls ('#') such that:
 * 1. All good persons can reach the exit (bottom-right corner (n,m)).
 * 2. No bad person can reach the exit.
 * Minimize nothing — just determine if it's possible and output a valid grid or "No".
 *
 * EXAMPLE
 * -------
 * Input:  2 2
 *         G.
 *         .B
 * Output: Yes
 *         G#
 *         .B (or similar)
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Key insight: wall off all cells adjacent to 'B' persons (block their 4 neighbors).
 *    This isolates bad persons from the rest of the maze.
 *    Check feasibility: if blocking a B-neighbor would block a G person or the exit itself
 *    or a G-neighbor, it's impossible.
 * 2. After blocking B-neighbors, BFS from exit (n-1, m-1) to check all G persons are reachable.
 * 3. Also: B persons must not be able to reach the exit. After placing walls around B, they can't.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n * m)
 * Space: O(n * m)
 * ============================================================
 */

public class CF_1365D_SolveTheMaze {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            char[][] grid = new char[n][m];
            for (int i = 0; i < n; i++) grid[i] = br.readLine().toCharArray();

            boolean impossible = false;

            // Block all neighbors of B cells
            for (int i = 0; i < n && !impossible; i++) {
                for (int j = 0; j < m && !impossible; j++) {
                    if (grid[i][j] == 'B') {
                        for (int d = 0; d < 4; d++) {
                            int ni = i + dr[d], nj = j + dc[d];
                            if (ni < 0 || ni >= n || nj < 0 || nj >= m) continue;
                            if (grid[ni][nj] == 'G') { impossible = true; break; }
                            if (ni == n - 1 && nj == m - 1) { impossible = true; break; }
                            if (grid[ni][nj] == '.') grid[ni][nj] = '#';
                        }
                    }
                }
            }

            if (impossible) {
                sb.append("No\n");
                continue;
            }

            // BFS from exit, only through non-wall, non-B cells
            boolean[][] reached = new boolean[n][m];
            if (grid[n - 1][m - 1] != '#' && grid[n - 1][m - 1] != 'B') {
                Queue<int[]> q = new ArrayDeque<>();
                q.add(new int[]{n - 1, m - 1});
                reached[n - 1][m - 1] = true;
                while (!q.isEmpty()) {
                    int[] cur = q.poll();
                    int r = cur[0], c = cur[1];
                    for (int d = 0; d < 4; d++) {
                        int nr = r + dr[d], nc = c + dc[d];
                        if (nr < 0 || nr >= n || nc < 0 || nc >= m) continue;
                        if (reached[nr][nc] || grid[nr][nc] == '#' || grid[nr][nc] == 'B') continue;
                        reached[nr][nc] = true;
                        q.add(new int[]{nr, nc});
                    }
                }
            }

            // Check all G persons can reach exit
            boolean allGood = true;
            for (int i = 0; i < n && allGood; i++) {
                for (int j = 0; j < m && allGood; j++) {
                    if (grid[i][j] == 'G' && !reached[i][j]) allGood = false;
                }
            }

            if (!allGood) {
                sb.append("No\n");
            } else {
                sb.append("Yes\n");
                for (int i = 0; i < n; i++) {
                    sb.append(new String(grid[i])).append('\n');
                }
            }
        }

        System.out.print(sb);
    }
}
