package com.interview.practice;

import java.util.ArrayDeque;
import java.util.Deque;

// if the given tree is BST, we can still use solution from SerializeAndDeserializeBinaryTree
// but since it is a BST, we can use BST's feature to do some optimization

// idea:
// 如果用先序遍历去Serialize 一个 binary tree， Deserialize 的时候有可能会得到多个符合条件的tree
// 因为binary tree 没有order，同一个node，既可以是左孩子也可以是右孩子

// 但是如果用先序遍历去Serialize 一个binary search tree，那么在Deserialize的时候只会得到一个唯一的结果。
// 因为binary search tree 一定要满足左孩子小于root， root小于右孩子
// 我们可以利用整个点，来做serialization 和 deserialization

public class SerializeAndDeserializeBST {

    public String serialize(TreeNode root) {
        if (root == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        preorder(root, sb);
        return sb.substring(0, sb.length() - 1);
    }

    // use preorder to build the string
    private void preorder(TreeNode root, StringBuilder sb) {
        if (root == null) {
            return;
        }

        sb.append(root.val + "" + ",");
        preorder(root.left, sb);
        preorder(root.right, sb);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data == null || data.isEmpty()) {
            return null;
        }

        // deque stores all node values
        Deque<Integer> deque = new ArrayDeque<>();
        String[] vals = data.split(",");
        for (String val : vals) {
            deque.offer(Integer.valueOf(val));
        }

        // use dfs to build tree
        return builder(deque, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private TreeNode builder(Deque<Integer> deque, int leftLimit, int rightLimit) {
        if (deque.isEmpty()) {
            return null;
        }

        // check the current root value, if root value < left limit or greater than right limit
        // return null because this is not the right spot
        int val = deque.peek();
        if (val < leftLimit || val > rightLimit) {
            return null;
        }

        // else the value is a value root. remove it from the deque.
        deque.poll();

        // use the value to create the root
        TreeNode root = new TreeNode(val);

        // build left subtree, update right limit to current root val
        root.left = builder(deque, leftLimit, val);

        // build right subtree, update left limit to current root val
        root.right = builder(deque, val, rightLimit);

        // return root
        return root;
    }
}
