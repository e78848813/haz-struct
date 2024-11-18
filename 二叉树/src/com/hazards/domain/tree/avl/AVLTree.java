package com.hazards.domain.tree.avl;

import com.hazards.domain.tree.BinarySearchTree;

import java.util.Comparator;

/**
 * AVL树
 */
public class AVLTree<E> extends BinarySearchTree<E> {


    public AVLTree(){
        this(null);
    }

    public AVLTree(Comparator<E> comparator){
        super(comparator);
    }


    @Override
    public void add(E element) {
        super.add(element);


    }


    @Override
    protected void afterAdd(Node<E> node) {

    }

    @Override
    protected Node<E> createNode(E element, Node<E> parentNode) {
        return super.createNode(element, parentNode);
    }


    private static class AVLNode<E> extends Node<E>{

        int height;


        public AVLNode(E element, Node<E> parentNode) {
            super(element, parentNode);
        }

        /**
         * 获取平衡因子
         * @return 平衡因子
         */
        public int balanceFactor(){
            int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;
            return leftHeight - rightHeight;
        }
    }
}
