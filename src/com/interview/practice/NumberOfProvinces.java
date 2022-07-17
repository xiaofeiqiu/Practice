package com.interview.practice;

import java.util.ArrayDeque;
import java.util.Queue;

// typical bfs problem
// for each unseen city, find its neighbors and mark them as seen using BFS

public class NumberOfProvinces {
    public int findCircleNum(int[][] isConnected) {
        if (isConnected == null || isConnected.length == 0) {
            return 0;
        }

        int n = isConnected.length;

        int result = 0;
        boolean[] seen = new boolean[n];

        // for city i to n - 1
        for (int i = 0; i < n; i++) {
            // if unseen, start bfs with that city, and mark all neighbors as seen
            if (!seen[i]) {
                bfs(isConnected, i, seen);
                // increment count after bfs finished
                result++;
            }
        }

        return result;
    }

    private void bfs(int[][] isConnected, int city, boolean[] visited) {

        int n = isConnected.length;

        // put starting city into queue, and mark as seen
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(city);
        visited[city] = true;

        // bfs
        while(!queue.isEmpty()) {
            int curr = queue.poll();

            // for all possible neighbors
            for (int neighbor = 0; neighbor < n; neighbor++) {

                // if seen, continue
                if (visited[neighbor]) {
                    continue;
                }

                // if not connected, continue
                if (isConnected[curr][neighbor] != 1) {
                    continue;
                }

                // add connected and unseen neighbor to the queue and mark then as seen
                queue.offer(neighbor);
                visited[neighbor] = true;
            }
        }
    }
}
