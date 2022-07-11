package com.interview.practice;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Orange {
    public int orangesRotting(int[][] grid) {
        if (grid == null) {
            return 0;
        }

        int m = grid.length;
        int n = grid[0].length;

        boolean[][] seen = new boolean[m][n];
        Queue<Node> queue = new ArrayDeque<>();
        int total = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                // put all rotten oranges to the queue because they are in the same layer
                // 所有已经坏掉的橘子都可以理解为他们在同一层. (抽象理解)
                if (grid[i][j] == 2) {
                    seen[i][j] = true;
                    queue.offer(new Node(i, j));
                }

                // total = total number of oranges =  total number of oranges need to be in rotten state
                if (grid[i][j] != 0) {
                    total++;
                }
            }
        }

        if (total == 0) {
            return 0;
        }

        int result = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                // rotten curr orange, decrement total number of oranges needs to be rotten
                Node curr = queue.poll();
                total--;

                // if total number of oranges need to be rotten is 0, return the result
                if (total == 0) {
                    return result;
                }

                // get neighbors
                List<Node> neighbors = getNeighbors(curr);
                for (Node neighbor : neighbors) {
                    if (!isInBound(neighbor, m, n)) {
                        continue;
                    }

                    if (seen[neighbor.x][neighbor.y]) {
                        continue;
                    }

                    if (grid[neighbor.x][neighbor.y] != 1) {
                        continue;
                    }

                    queue.offer(neighbor);
                    seen[neighbor.x][neighbor.y] = true;
                }
            }

            // increment time
            result++;
        }

        // if bfs ended, means we can not rotten all oranges in the given map
        return -1;
    }

    private boolean isInBound(Node node, int m, int n) {
        int x = node.x;
        int y = node.y;
        return x >= 0 && x < m && y >= 0 && y < n;
    }

    // If you are lazy, you can also do it this way. but not recommended
    private List<Node> getNeighbors(Node node) {
        List<Node> list = new ArrayList<>();
        list.add(new Node(node.x - 1, node.y));
        list.add(new Node(node.x + 1, node.y));
        list.add(new Node(node.x, node.y - 1));
        list.add(new Node(node.x, node.y + 1));
        return list;
    }
}
