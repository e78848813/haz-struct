package com.hazards;

import com.hazards.domain.tree.BinarySearchTree;
import com.hazards.domain.tree.avl.AVLTree;
import com.hazards.utils.printer.BinaryTrees;

import java.util.Scanner;

/**
 * 二叉树数据结构入口测试方法
 *
 * @author Hazards
 */
public class BinaryTreeMainApplication {


    public static void main(String[] args) {


        Integer[] data1 = new Integer[]{
                7, 4, 2, 1, 3, 5, 9, 8, 11, 10, 12, 66, 14
        };

        Integer[] data2 = new Integer[]{
                85, 19, 69, 3, 7, 99, 95, 2, 1, 70, 44, 58, 11, 21, 14, 93, 57, 4, 56
        };

        Integer[] data3= new Integer[]{
                4,2,6,1,3,5
        };

        Integer[] data4= new Integer[]{
                1,2,3,4,5,6
        };


        BinarySearchTree<Integer> myTree = new BinarySearchTree<>();

//        AVLTree<Integer> mtAVLTree = new AVLTree<>();


        for (Integer datum : data4) {
//            System.out.println("添加:" + datum);
//            mtAVLTree.add(datum);
            myTree.add(datum);
//            BinaryTrees.println(mtAVLTree);
        }

        //打印树状图
        BinaryTrees.println(myTree);


//        Scanner in = new Scanner(System.in);
//        while (true) {
//            System.out.print("请输入你要操作的节点:");
//            int inputElement = in.nextInt();
//            if (inputElement <= 0) {
//                System.out.println("结束...");
//                break;
//            }
//            mtAVLTree.remove(inputElement);
//            BinaryTrees.println(mtAVLTree);
//        }

    }


}
