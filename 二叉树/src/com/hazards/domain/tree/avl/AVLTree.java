package com.hazards.domain.tree.avl;

import com.hazards.domain.tree.BinarySearchTree;

import java.util.Comparator;

/**
 * AVL树
 */
public class AVLTree<E> extends BinarySearchTree<E> {


    public AVLTree() {
        this(null);
    }

    public AVLTree(Comparator<E> comparator) {
        super(comparator);
    }


    @Override
    protected void afterAdd(Node<E> node) {
        //如果节点为null
        if (node == null) {
            return;
        }
        while ((node = node.parent) != null) {
            if (isBalanced(node)) {
                //该节点是平衡树根节点
                updateHeight(node);
            } else {
                //恢复平衡
                rebalanced(node);
                break;
            }
        }
    }

    /**
     * 更新指定节点的高度
     * @param node 指定节点-高度最低的不平衡节点
     */
    private void updateHeight(Node<E> node) {
        ((AVLNode<E>) node).updateHeight();
    }

    /**
     * 恢复平衡
     * @param grandNode 指定节点
     */
    private void rebalanced(Node<E> grandNode) {
        Node<E> parNode = ((AVLNode<E>) grandNode).tallerChildNode();
        Node<E> node = ((AVLNode<E>) parNode).tallerChildNode();
        if(parNode.isLeftChild()){
            //L
            if(node.isLeftChild()){
                //LL
                rotateLeft(grandNode);
            }else {
                //LR
                rotateLeft(parNode);
                rotateRight(grandNode);
            }
        }else {
            //R
            if(node.isLeftChild()){
                //RL
                rotateRight(parNode);
                rotateLeft(grandNode);
            }else  {
                //RR
                rotateRight(grandNode);
            }
        }
    }


    /**
     * 对指定节点进行左旋转
     * @param node 指定节点
     */
    private void rotateLeft(Node<E> node) {
        Node<E> childNode = ((AVLNode<E>)node).tallerChildNode();
        if(node.isLeftChild()){
            node.parent.left = childNode;
        }else {
            node.parent.right = childNode;
        }
        node.left = childNode.right;
    }

    /**
     * 对指定节点进行右旋转
     * @param node 指定节点
     */
    private void rotateRight(Node<E> node) {
        Node<E> childNode = ((AVLNode<E>)node).tallerChildNode();
        if(node.isLeftChild()){
            node.parent.left = childNode;
        }else {
            node.parent.right = childNode;
        }
        node.right = childNode.left;
    }

    @Override
    protected Node<E> createNode(E element, Node<E> parentNode) {
        return super.createNode(element, parentNode);
    }


    /**
     * AVL内部节点对象
     *
     * @param <E> 范型
     */
    private static class AVLNode<E> extends Node<E> {

        //高度，默认为1，添加的时候为叶子节点
        int height = 1;


        public AVLNode(E element, Node<E> parentNode) {
            super(element, parentNode);
        }

        /**
         * 获取平衡因子
         *
         * @return 平衡因子
         */
        public int balanceFactor() {
            int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;
            return leftHeight - rightHeight;
        }

        public void updateHeight() {
            int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;
            this.height = Math.max(leftHeight, rightHeight) + 1;
        }


        public Node<E> tallerChildNode() {
            int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;
            if(leftHeight> rightHeight) return left;
            if(leftHeight < rightHeight) return right;
            return isLeftChild() ? left : right;
        }
    }


    /**
     * 当前节点是否是平衡
     *
     * @param node 节点
     * @return 是否平衡
     */
    private boolean isBalanced(Node<E> node) {
        return Math.abs(((AVLNode<E>) node).balanceFactor()) <= 1;
    }
}
