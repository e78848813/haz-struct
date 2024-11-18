package com.hazards;

import com.hazards.domain.tree.BinarySearchTree;
import com.hazards.utils.printer.BinaryTrees;

import java.util.Scanner;

/**
 * 二叉树数据结构入口测试方法
 * @author Hazards
 */
public class BinaryTreeMainApplication {


    public static void main(String[] args) {


        Integer[] data1 = new Integer[]{
                7,4,2,1,3,5,9,8,11,10,12,66,14
        };



        Integer[] data2 = new Integer[]{
                7,4,9,5,8
        };

        Integer[] data3= new Integer[]{
                1,4,3
        };

        BinarySearchTree<Integer> myTree = new BinarySearchTree<>();

        for (Integer datum : data1) {
            myTree.add(datum);
        }

        //打印树状图
        BinaryTrees.println(myTree);


        Scanner in = new Scanner(System.in);
        while (true){
            System.out.print("请输入你要删除的节点:");
            int inputElement = in.nextInt();
            if(inputElement<=0){
                System.out.println("结束...");
                break;
            }
            myTree.remove(inputElement);
            BinaryTrees.println(myTree);
        }

    }


}
