package com.interview.practice;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class CourseScheduleII {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        if (prerequisites == null) {
            return new int[0];
        }

        // init
        List<Integer>[] graph = new ArrayList[numCourses];
        for (int i = 0; i < numCourses; i++) {
            graph[i] = new ArrayList<>();
        }

        // build graph and indegree
        int[] indegree = new int[numCourses];
        for (int[] prerequisite : prerequisites) {
            int child = prerequisite[0];
            int parent = prerequisite[1];
            graph[parent].add(child);
            indegree[child]++;
        }

        // init queue, put courses to queue if indegree == 0
        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < indegree.length; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }

        int index = 0;
        int[] result = new int[numCourses];

        while (!queue.isEmpty()) {
            // add course to result, and i++
            int course = queue.poll();
            result[index++] = course;

            // child indegree --
            for (int nextCourse : graph[course]) {
                indegree[nextCourse]--;
                // add child to queue if indegre == 0
                if (indegree[nextCourse] == 0) {
                    queue.offer(nextCourse);
                }
            }
        }

        // return result if course taken == numCourses
        if (index == numCourses) {
            return result;
        }

        // else return empty array
        return new int[0];
    }
}
