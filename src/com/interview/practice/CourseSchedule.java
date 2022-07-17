package com.interview.practice;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class CourseSchedule {
    public boolean canFinish(int n, int[][] prerequisites) {
        if (n == 0 || prerequisites == null) {
            return false;
        }

        if (prerequisites.length == 0) {
            return true;
        }

        // init
        int[] indegree = new int[n];
        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        // build graph and indegree
        for (int[] prerequisite : prerequisites) {
            int course = prerequisite[0];
            int dependency = prerequisite[1];
            graph[dependency].add(course);
            indegree[course]++;
        }

        // add courses to queue if indegree == 0
        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }

        // topological sort
        int count = 0;
        while (!queue.isEmpty()) {
            // course taken ++
            int course = queue.poll();
            count++;

            // child indegree --
            for (int nextCourse : graph[course]) {
                indegree[nextCourse]--;
                // add to queue if indegree == 0
                if (indegree[nextCourse] == 0) {
                    queue.offer(nextCourse);
                }
            }
        }

        // return true if count == n
        return count == n;
    }
}
