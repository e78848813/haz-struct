package com.hazards.practice;

import com.hazards.domain.BinarySearchTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class MiddleOrderIter {

    public static void main(String[] args) {
        //根节点为空，直接返回

    }


    public List<Integer> inorderTraversal(TreeNode root) {
        if(root==null) return Collections.emptyList();

        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        List<Integer> res=  new ArrayList<>();
        stack.push(node);
        while (!stack.isEmpty()){

            while (node.left!=null){
                node = node.left;
                stack.push(node);
            }

            TreeNode pop = stack.pop();
            res.add(pop.val);

            if(pop.right!=null){
                node = pop.right;
                stack.push(node);
            }
        }
        return res;
    }


    public List<Integer> postorderTraversal(TreeNode root) {
        if(root==null) return Collections.emptyList();

        Stack<TreeNode> stack = new Stack<>();

        TreeNode curNode = root;
        TreeNode prev=null;
        List<Integer> res = new ArrayList<>();
        while (!stack.isEmpty()||curNode!=null){
            while (curNode!=null){
                stack.push(curNode);
                curNode = curNode.left;
            }
            curNode = stack.pop();
            if(curNode.right==prev||curNode.right==null){
                res.add(curNode.val);
                prev = curNode;
                curNode = null;
            }else {
                stack.push(curNode);
                curNode = curNode.right;
            }
        }
        return res;
    }
    
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
