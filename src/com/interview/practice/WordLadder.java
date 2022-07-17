package com.interview.practice;

import java.util.*;


// Time complexity analyze:
// it takes O(N) to convert wordList to wordSet, N is the number of words
// For bfs, worse case we need to visit all nodes to compute the distance
// And for each node, it takes us L^2 to compute the neighbor
// So the time complexity for BFS part is O (N * L * L)
// Overall  (N * L * L)

public class WordLadder {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        if (beginWord == null || endWord == null) {
            return 0;
        }

        // convert wordList to wordSet O(n), n is number of words
        Set<String> wordSet = new HashSet<>();
        for (int i = 0; i < wordList.size(); i++) {
            wordSet.add(wordList.get(i));
        }

        // if endWord does not in the wordSet, return 0
        if (!wordSet.contains(endWord)) {
            return 0;
        }

        // below are typical bfs
        Queue<String> queue = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();

        queue.offer(beginWord);
        visited.add(beginWord);

        int dist = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String curr = queue.poll();
                if (curr.equals(endWord)) {
                    return dist;
                }

                // get neighbors
                List<String> nextWords = getNextWord(curr, wordSet);
                for (String word : nextWords) {
                    if (visited.contains(word)) {
                        continue;
                    }

                    queue.offer(word);
                    visited.add(word);
                }
            }
            dist++;
        }

        return 0;
    }

    // getNextWord is a "getNeighbor" Func
    // O(WordLength * WordLength * 26) => O(L^2)
    private List<String> getNextWord(String curr, Set<String> wordSet) {
        List<String> list = new ArrayList<>();

        // for WordLength
        for (int i = 0; i < curr.length(); i++) {
            // for 26
            for (char c = 'a'; c <= 'z'; c++) {
                // replace char
                char[] chars = curr.toCharArray();
                chars[i] = c;
                String newWord = new String(chars); // this statement O(WordLength)

                // if wordSet contains newWord, that means the newWord is a valid neighbor
                if (wordSet.contains(newWord)) {
                    list.add(newWord);
                }
            }
        }
        return list;
    }
}
