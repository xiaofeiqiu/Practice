package com.interview.practice;

import java.util.*;

public class AlienDictionary {
    public String alienOrder(String[] words) {

        // build graph
        Map<Character, Set<Character>> graph = buildGraph(words);
        if (graph == null) {
            return "";
        }

        // build indegree
        Map<Character, Integer> indegree = buildIndegree(words, graph);

        // topologicalsort
        return topologicalSort(graph, indegree);
    }

    private String topologicalSort(Map<Character, Set<Character>> graph, Map<Character, Integer> indegree) {

        StringBuilder sb = new StringBuilder();

        // put all chars that has indegree == 0 to queue
        Queue<Character> queue = new ArrayDeque<>();
        for (Character node : graph.keySet()) {
            if (indegree.get(node) == 0) {
                queue.offer(node);
            }
        }

        // bfs
        while (!queue.isEmpty()) {
            // poll curr node, and append to result
            Character cur = queue.poll();
            sb.append(cur);

            // decrement all neighbor's indegree, and add neighbor to queue if indegree == 0
            Set<Character> neighbors = graph.get(cur);
            for (Character neighbor : neighbors) {
                indegree.put(neighbor, indegree.get(neighbor) - 1);
                if (indegree.get(neighbor) == 0) {
                    queue.offer(neighbor);
                }
            }
        }

        // only return the result if all letters are included in the result
        if (sb.length() == indegree.size()) {
            return sb.toString();
        }

        // else topological sort does not exist
        return "";
    }

    private Map<Character, Integer> buildIndegree(String[] words, Map<Character, Set<Character>> graph) {

        // init all nodes indegree to 0
        Map<Character, Integer> indegree = new HashMap<>();
        for (Character c : graph.keySet()) {
            indegree.put(c, 0);
        }

        for (Character parent : graph.keySet()) {
            for (Character child : graph.get(parent)) {
                indegree.put(child, indegree.get(child) + 1);
            }
        }

        return indegree;
    }


    // use map to store map, key is node, value is neighbor
    private Map<Character, Set<Character>> buildGraph(String[] words) {
        Map<Character, Set<Character>> graph = new HashMap<>();

        // build nodes,
        // for each char in words, add it to the map, leave neighbor empty for now
        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j < words[i].length(); j++) {
                char c = words[i].charAt(j);
                if (!graph.containsKey(c)) {
                    graph.put(c, new HashSet<>());
                }
            }
        }

        // build neighbors/edges
        // comparing each word, find edges
        for (int i = 0; i < words.length - 1; i++) {
            int index = 0;
            // compare words[i] and words[i + 1]
            while (index < words[i].length() && index < words[i + 1].length()) {
                // if not equal, then it means words[i].charAt(index) < words[i + 1].charAt(index)
                // so words[i].charAt(index) is the parent
                if (words[i].charAt(index) != words[i + 1].charAt(index)) {
                    Character parent = words[i].charAt(index);
                    Character child = words[i + 1].charAt(index);
                    graph.get(parent).add(child);
                    break;
                }
                index++;
            }

            // Below if condition covers words = {"abc", "ab"} scenario
            // if index reaches the end of the word, and the word1.length > word2.length
            // then this is an invalid alien dictionary
            if (index == Math.min(words[i].length(), words[i + 1].length())) {
                if (words[i].length() > words[i + 1].length()) {
                    return null;
                }
            }
        }
        return graph;
    }
}
