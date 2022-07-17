package com.interview.practice;

import java.util.*;

// typical bfs problem
// Create a parentMap, key = node, value = node's parent. use preorder traversal to computer the map
// starting with target node, do the bfs
// neighbor = node.left + node.right + node.parent

// Time complexity for preorder function O(n)
// Time complexity for bfs O(n)
// Overall TC: O(n)

// Space Complexity: because we need to visit all nodes, and we use a set to store all seen nodes, So O(n)

public class AllNodesDistanceKinBinaryTree {
    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        if (root == null || target == null) {
            return new ArrayList<>();
        }

        Map<TreeNode, TreeNode> parentMap = new HashMap<>();
        preorder(root, null, parentMap);

        Queue<TreeNode> queue = new ArrayDeque<>();
        Set<TreeNode> seen = new HashSet<>();

        queue.offer(target);
        seen.add(target);

        // starting with layer 0
        int dist = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                TreeNode curr = queue.poll();
                // if layer == k, we need to add all nodes in this layer to the result
                if (dist == k) {
                    List<Integer> result = new ArrayList<>();
                    result.add(curr.val);
                    while (!queue.isEmpty()) {
                        result.add(queue.poll().val);
                    }
                    return result;
                }
                List<TreeNode> neighbors = getNeighbors(curr, parentMap);

                for (TreeNode neighbor : neighbors) {
                    if (seen.contains(neighbor)) {
                        continue;
                    }
                    queue.offer(neighbor);
                    seen.add(neighbor);
                }
            }
            // increment layer count
            dist++;
        }

        return new ArrayList<>();
    }

    // O(1)
    private List<TreeNode> getNeighbors(TreeNode curr, Map<TreeNode, TreeNode> parentMap) {
        List<TreeNode> nodes = new ArrayList<>();
        if (curr != null && curr.left != null) {
            nodes.add(curr.left);
        }

        if (curr != null && curr.right != null) {
            nodes.add(curr.right);
        }

        if (parentMap.containsKey(curr) && parentMap.get(curr) != null) {
            nodes.add(parentMap.get(curr));
        }
        return nodes;
    }


    // preorder traversal find parent node, each node visited once => O(n)
    private void preorder(TreeNode root, TreeNode parent, Map<TreeNode, TreeNode> parentMap) {
        if (root == null) {
            return;
        }

        parentMap.put(root, parent);
        preorder(root.left, root, parentMap);
        preorder(root.right, root, parentMap);
    }
}
