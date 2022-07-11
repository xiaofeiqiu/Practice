package com.interview.practice;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Island {

    char WATER = '0';

    int[] dx = new int[]{1, -1, 0, 0};
    int[] dy = new int[]{0, 0, 1, -1};
    public int numIslands(char[][] grid) {
        if (grid == null) {
            return 0;
        }

        int m = grid.length;
        int n = grid[0].length;

        int count = 0;
        boolean[][] seen = new boolean[m][n];

        // for each cell, if seen or it is a water, continue
        // else, use bfs to mark all connected lands as seen
        // when bfs finished, all connected lands were marked as seen
        // increment island count
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (seen[i][j]) {
                    continue;
                }

                if (grid[i][j] == WATER) {
                    continue;
                }

                // I am use 2d array to represent a node, or you can just use Node type to increase the readability
                bfs(grid, new int[]{i, j}, seen);
                count++;
            }
        }

        return count;
    }

    private void bfs(char[][] grid, int[] point, boolean[][] visited) {

        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(point);
        visited[point[0]][point[1]] = true;

        while (!queue.isEmpty()) {
            int[] currPoint = queue.poll();

            List<int[]> neighbors = getNeighbors(currPoint);
            for (int[] neighbor : neighbors) {
                if (!isInBound(neighbor, grid.length, grid[0].length)) {
                    continue;
                }

                if (visited[neighbor[0]][neighbor[1]]) {
                    continue;
                }

                if (grid[neighbor[0]][neighbor[1]] == WATER) {
                    continue;
                }

                queue.offer(neighbor);
                visited[neighbor[0]][neighbor[1]] = true;
            }
        }
    }

    private List<int[]> getNeighbors(int[] point) {
        List<int[]> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            list.add(new int[]{point[0] + dx[i], point[1] + dy[i]});
        }
        return list;
    }

    private boolean isInBound(int[] point, int m, int n) {
        int x = point[0];
        int y = point[1];
        return x >= 0 && x < m && y >= 0 && y < n;
    }
}
