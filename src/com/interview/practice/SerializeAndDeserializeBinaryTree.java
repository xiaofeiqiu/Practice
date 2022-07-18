package com.interview.practice;

import java.util.*;

public class SerializeAndDeserializeBinaryTree {

    // use BFS to traverse tree layer by layer
    // append string value to the result
    // time: O(n), n is number of nodes
    // space: O(n), we used a list to store the result
    public String serialize(TreeNode root) {
        if (root == null) {
            return "";
        }

        List<String> list = new ArrayList<>();

        // use linked list here because arrayDeque does not support adding null value
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode curr = queue.poll();
            // if null, append "#"
            if (curr == null) {
                list.add("#");
            } else {
                // else, convert value to string and append it to the list
                list.add(curr.val + "");
                queue.offer(curr.left);
                queue.offer(curr.right);
            }
        }

        // return joined string list
        return String.join(",", list);
    }

    // Decodes your encoded data to tree.
    // Time: O(n), visit all nodes once
    // Space: O(n), we need to store all nodes
    public TreeNode deserialize(String data) {
        if (data == null || data.isEmpty()) {
            return null;
        }

        // value queue stores node values
        Queue<String> valQueue = new ArrayDeque<>();
        // split input string by "," then add them all to the value queue
        valQueue.addAll(Arrays.asList(data.split(",")));

        // node queue stores tree node
        Queue<TreeNode> nodeQueue = new ArrayDeque<>();

        // create root node, and add root to the node queue
        TreeNode root = new TreeNode(Integer.valueOf(valQueue.poll()));
        nodeQueue.offer(root);

        // bfs. For each polled node, we need to compute its left node and right node.
        while (!nodeQueue.isEmpty()) {
            // poll curr node from node queue
            TreeNode curr = nodeQueue.poll();

            // poll left and right value from valQueue
            String left = valQueue.poll();
            String right = valQueue.poll();

            // convert left value to node if not null, and assign it to the curr.left
            if (!left.equals("#")) {
                curr.left = new TreeNode(Integer.valueOf(left));
                nodeQueue.offer(curr.left);
            }

            // convert right value to node if not null, and assign it to the curr.right
            if (!right.equals("#")) {
                curr.right = new TreeNode(Integer.valueOf(right));
                nodeQueue.offer(curr.right);
            }
        }

        return root;
    }
}
