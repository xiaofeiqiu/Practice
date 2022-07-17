package com.interview.practice;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

// O(m * n), because we visited all cell once
public class MaxAreaOfIsland {
    int WATER = 0;

    int[] dx = new int[]{0, 1, 0, -1};
    int[] dy = new int[]{1, 0, -1, 0};

    public int maxAreaOfIsland(int[][] grid) {
        if (grid == null || grid[0] == null) {
            return 0;
        }

        int m = grid.length;
        int n = grid[0].length;

        boolean[][] seen = new boolean[m][n];
        int max = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // skip seen
                if (seen[i][j]) {
                    continue;
                }

                // skip water
                if (grid[i][j] == WATER) {
                    continue;
                }

                max = Math.max(max, bfs(grid, new Node(i, j), seen));
            }
        }


        return max;
    }

    // start with unseen land, mark all connected land as seen and return the connected lands
    private int bfs(int[][] grid, Node node, boolean[][] seen) {

        Queue<Node> queue = new ArrayDeque<>();
        queue.offer(node);
        seen[node.x][node.y] = true;

        int area = 0;
        while (!queue.isEmpty()) {
            Node curr = queue.poll();
            area++; // count connected land

            List<Node> neighbors = getNeighbors(curr);
            for (Node neighbor : neighbors) {
                if (!isInBound(neighbor, grid.length, grid[0].length)) {
                    continue;
                }

                if (seen[neighbor.x][neighbor.y]) {
                    continue;
                }

                if (grid[neighbor.x][neighbor.y] == WATER) {
                    continue;
                }

                queue.offer(neighbor);
                seen[neighbor.x][neighbor.y] = true;
            }
        }

        return area;
    }

    private boolean isInBound(Node node, int maxRow, int maxCol) {
        return node.x >= 0 && node.x < maxRow && node.y >= 0 && node.y < maxCol;
    }

    private List<Node> getNeighbors(Node node) {
        List<Node> nodes = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            nodes.add(new Node(node.x + dx[i], node.y + dy[i]));
        }
        return nodes;
    }
}
