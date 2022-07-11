package com.interview.practice;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class CanReach {

    // define 4 directions. you can also do:  int[][] dir = new int[][]{{0,-1},{-1,0},{0,1},{1,0}}
    // either way works, for this code example, we will separate them
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

            // Else, search continues.
            // Get all possible neighbors
            List<Node> neighbors = getNeighbors(cur);

            // for each neighbor, skip invalid neighbors. only add valid neighbors to the queue
            for (Node neighbor : neighbors) {
                if (!isInBound(neighbor, m, n)) {
                    continue;
                }

                if (grid[neighbor.x][neighbor.y] == 1) {
                    continue;
                }

                if (seen[neighbor.x][neighbor.y]) {
                    continue;
                }

                queue.offer(neighbor);
                seen[neighbor.x][neighbor.y] = true;
            }
        }
        return false;
    }

    public boolean isInBound(Node node, int maxRow, int maxCol) {
        return node.x >= 0 && node.x < maxRow && node.y >= 0 && node.y < maxCol;
    }

    public List<Node> getNeighbors(Node node) {
        List<Node> result = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            result.add(new Node(dx[i], dy[i]));
        }
        return result;
    }
}
