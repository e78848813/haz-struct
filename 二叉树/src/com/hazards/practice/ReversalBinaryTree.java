package com.hazards.practice;

import com.hazards.domain.BinarySearchTree;
import com.hazards.utils.printer.BinaryTrees;

/**
 * 反转二叉树
 */
public class ReversalBinaryTree {


    public static void main(String[] args) {
        Integer[] data = new Integer[]{
                7,4,2,1,3,5,9,8,11,10,12,66
        };

        BinarySearchTree<Integer> myTree = new BinarySearchTree<>();

        for (Integer datum : data) {
            myTree.add(datum);
        }

        //打印树状图
        BinaryTrees.println(myTree);

        System.out.println("反转后");
        myTree.reversal();
        //打印树状图
        BinaryTrees.println(myTree);

    }
}
