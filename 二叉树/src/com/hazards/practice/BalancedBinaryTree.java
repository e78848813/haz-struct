package com.hazards.practice;

/**
 * 给定一个二叉树，判断它是否是二叉树
 * <br>
 * <a href="https://leetcode.cn/problems/balanced-binary-tree">力扣 第110题 平衡二叉树</a>
 */
public class BalancedBinaryTree {


    /**
     * 自上而下的递归，，，O(n²) 不推荐，可能每个节点都会递归一次 n*n
     * @param root
     * @return
     */
    public boolean isBalanced2(TreeNode root) {
        if(root == null) return true;
        int abs = Math.abs(height(root.left) - height(root.right));
        boolean childFlag = isBalanced2(root.left) && isBalanced2(root.right);
        return abs <= 1 && childFlag;
    }


    private int height2(TreeNode node) {
        if(node==null) return 0;
        return Math.max(height(node.left), height(node.right)) + 1;
    }



    /**
     * 自下而上的递归  O(n)，一次递归，但子节点不平衡时，直接返回-1到顶层，判断不为
     * @param node
     * @return
     */
    public boolean isBalanced(TreeNode node) {
        return height(node) != -1;
    }

    private int height(TreeNode node) {
        if(node==null) return 0;
        int leftHeight = height(node.left);
        int rightHeight = height(node.right);

        //这里一旦有节点是不平衡的，直接返回-1，不再递归
        if(leftHeight==-1 || rightHeight==-1 || Math.abs(leftHeight - rightHeight) > 1) {
            return -1;
        }else {
            return Math.max(leftHeight, rightHeight) + 1;
        }
    }


    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}


