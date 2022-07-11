package com.interview.practice;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

class Node {
    public int x;
    public int y;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class MatrixBFSBasic {

    public int[] dx = new int[]{0, -1, 0, 1};
    public int[] dy = new int[]{-1, 0, 1, 0};

    public int getDistance(int[][] grid, Node start, Node end) {

        if (grid == null) {
            return -1;
        }

        int m = grid.length;
        int n = grid[0].length;

        Queue<Node> queue = new ArrayDeque<>();
        boolean[][] seen = new boolean[m][n];

        queue.offer(start);
        seen[start.x][start.y] = true;

        int result = 0;

        while (!queue.isEmpty()) {

            int size = queue.size();
            for (int i = 0; i < size; i ++) {
                Node cur = queue.poll();
                if (cur.x == end.x && cur.y == end.y) {
                    return result;
                }

                // get all possible neighbors
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
            result++;
        }
        return result;
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
