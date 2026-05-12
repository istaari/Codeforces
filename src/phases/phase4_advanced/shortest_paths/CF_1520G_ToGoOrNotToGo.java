package phases.phase4_advanced.shortest_paths;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1520G — To Go or Not to Go
 * Link   : https://codeforces.com/problemset/problem/1520/G
 * Rating : 1800  |  Tags: BFS, shortest path, grids
 * Topic  : Phase 4: Advanced > Shortest Paths
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * Two n×m grids (grid A and grid B) placed side by side. You start at
 * top-left of grid A and want to reach bottom-right of grid B. Movement
 * is 4-directional. You can teleport from any cell (i,j) in grid A to
 * the same cell (i,j) in grid B (or vice versa). Cells can be walls.
 * Find the minimum number of moves to reach the destination.
 *
 * EXAMPLE
 * -------
 * Input:  2x2 grids, all free cells
 * Output: 4
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. BFS from top-left of grid A to get dist_A[i][j].
 * 2. BFS from bottom-right of grid B to get dist_B[i][j].
 * 3. Without teleport: direct path doesn't exist across grids (they're separate).
 *    Must teleport. Optimal teleport at cell (r,c): total = dist_A[r][c] + 1 + dist_B[r][c].
 *    (1 for the teleport step, then continue from (r,c) in grid B.)
 * 4. Find minimum over all (r,c) of dist_A[r][c] + dist_B[r][c] + 1.
 * 5. Handle unreachable cells (INF distances).
 *
 * COMPLEXITY
 * ----------
 * Time : O(n*m)
 * Space: O(n*m)
 * ============================================================
 */

public class CF_1520G_ToGoOrNotToGo {

    static int N, M;
    static int INF = Integer.MAX_VALUE / 2;
    static int[] DR = {0, 0, 1, -1};
    static int[] DC = {1, -1, 0, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        char[][] gridA = new char[N][M];
        char[][] gridB = new char[N][M];

        for (int i = 0; i < N; i++) gridA[i] = br.readLine().trim().toCharArray();
        for (int i = 0; i < N; i++) gridB[i] = br.readLine().trim().toCharArray();

        int[][] distA = bfs(gridA, 0, 0);
        int[][] distB = bfs(gridB, N - 1, M - 1);

        long ans = Long.MAX_VALUE;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (distA[i][j] < INF && distB[i][j] < INF) {
                    long total = (long) distA[i][j] + distB[i][j] + 1;
                    ans = Math.min(ans, total);
                }
            }
        }

        System.out.println(ans == Long.MAX_VALUE ? -1 : ans);
    }

    static int[][] bfs(char[][] grid, int sr, int sc) {
        int[][] dist = new int[N][M];
        for (int[] row : dist) Arrays.fill(row, INF);
        if (grid[sr][sc] == '#') return dist;
        dist[sr][sc] = 0;
        Queue<int[]> q = new ArrayDeque<>();
        q.offer(new int[]{sr, sc});
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int r = cur[0], c = cur[1];
            for (int d = 0; d < 4; d++) {
                int nr = r + DR[d], nc = c + DC[d];
                if (nr >= 0 && nr < N && nc >= 0 && nc < M && grid[nr][nc] != '#' && dist[nr][nc] == INF) {
                    dist[nr][nc] = dist[r][c] + 1;
                    q.offer(new int[]{nr, nc});
                }
            }
        }
        return dist;
    }
}
