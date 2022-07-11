package com.interview.practice;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class TheMaze {

    public int[] dx = new int[]{0, -1, 0, 1};
    public int[] dy = new int[]{-1, 0, 1, 0};

    public boolean getDistance(int[][] grid, Node start, Node end) {

        if (grid == null) {
            return false;
        }

        int m = grid.length;
        int n = grid[0].length;

        Queue<Node> queue = new ArrayDeque<>();
        boolean[][] seen = new boolean[m][n];

        queue.offer(start);
        seen[start.x][start.y] = true;

        while (!queue.isEmpty()) {

            Node cur = queue.poll();
            // return true if destination reached
            if (cur.x == end.x && cur.y == end.y) {
                return true;
            }

            List<Node> neighbors = getNeighbors(grid, cur);

            for (Node neighbor : neighbors) {

                // we already checks the inbound in getNeighbors function, no need to check at here again
                // just need to check if this neighbor was seen or not

                if (seen[neighbor.x][neighbor.y]) {
                    continue;
                }

                queue.offer(neighbor);
                seen[neighbor.x][neighbor.y] = true;
            }
        }
        return false;
    }

    public List<Node> getNeighbors(int[][] grid, Node node) {
        List<Node> result = new ArrayList<>();

        int maxRow = grid.length;
        int maxCol = grid[0].length;

        for (int i = 0; i < 4; i++) {

            int newX = node.x;
            int newY = node.y;

            // keep rolling if newX newY is inbound and have not hit the wall
            while (newX >= 0 && newX < maxRow && newY >= 0 && newY < maxCol && grid[newX][newY] == 0) {
                newX += dx[i];
                newY += dy[i];
            }

            result.add(new Node(newX, newY));
        }
        return result;
    }
}
