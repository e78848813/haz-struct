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
                rebalanced2(node);
                break;
            }
        }
    }

    /**
     * 更新指定节点的高度
     *
     * @param node 指定节点-高度最低的不平衡节点
     */
    private void updateHeight(Node<E> node) {
        ((AVLNode<E>) node).updateHeight();
    }

    /**
     * 恢复平衡
     *
     * @param grandNode 指定节点
     */
    private void rebalanced(Node<E> grandNode) {
        Node<E> parNode = ((AVLNode<E>) grandNode).tallerChildNode();
        Node<E> node = ((AVLNode<E>) parNode).tallerChildNode();
        if (parNode.isLeftChild()) {
            //L
            if (node.isLeftChild()) {
                //LL
                rotateRight(grandNode);
            } else {
                //LR
                rotateLeft(parNode);
                rotateRight(grandNode);
            }
        } else {
            //R
            if (node.isLeftChild()) {
                //RL
                rotateRight(parNode);
                rotateLeft(grandNode);
            } else {
                //RR
                rotateLeft(grandNode);
            }
        }
    }


    private void rebalanced2(Node<E> grandNode) {
        Node<E> parNode = ((AVLNode<E>) grandNode).tallerChildNode();
        Node<E> node = ((AVLNode<E>) parNode).tallerChildNode();
        if (parNode.isLeftChild()) {
            //L
            if (node.isLeftChild()) {
                //LL
                rotate(grandNode,node.left,node,node.right,parNode,parNode.right,grandNode,grandNode.right);
            } else {
                //LR
                rotate(grandNode,parNode.left,parNode,node.left,node,node.right,grandNode,grandNode.right);

            }
        } else {
            //R
            if (node.isLeftChild()) {
                //RL
                rotate(grandNode,grandNode.left,grandNode,node.left,node,node.right,parNode,parNode.right);
            } else {
                //RR
                rotate(grandNode,grandNode.left,grandNode,parNode.left,parNode,node.left,node,node.right);
            }
        }
    }


    /**
     * 对指定节点进行左旋转
     *
     * @param node 指定节点
     */
    private void rotateLeft(Node<E> node) {
        Node<E> childNode = node.right;

        //更新当前节点和直接子节点的关系
        node.right = childNode.left;
        childNode.left = node;

        //对各个节点的关系进行更新
        afterRotate(node,childNode,childNode.left);

    }

    /**
     * 对指定节点进行右旋转
     *
     * @param node 指定节点
     */
    private void rotateRight(Node<E> node) {
        Node<E> childNode = node.left;
        //更新当前节点与直接子节点的关系
        node.left = childNode.right;
        childNode.right = node;
        //对各个节点的关系进行更新
        afterRotate(node,childNode,childNode.right);
    }

    private void afterRotate(Node<E> grandNode,Node<E> parentNode,Node<E> childNode) {
        //更新当前子树的根节点
        if (grandNode.isLeftChild()) {
            grandNode.parent.left = parentNode;
        } else if(grandNode.isRightChild()){
            grandNode.parent.right = parentNode;
        }else {
            root = parentNode;
        }
        parentNode.parent = grandNode.parent;
        //更新父节点
        if(childNode != null){
            childNode.parent = grandNode;
        }
        grandNode.parent = parentNode;
        //更新高度
        updateHeight(grandNode);
        updateHeight(parentNode);
    }

    /**
     * 按照规律旋转
     */
    private void rotate(Node<E> rNode,
                        Node<E> aNode,Node<E> bNode,Node<E> cNode,
                        Node<E> dNode,
                        Node<E> eNode,Node<E> fNode,Node<E> gNode) {
        //根节点
        dNode.parent = rNode.parent;
        if(rNode.isLeftChild()){
            rNode.parent.left = dNode;
        }else if(rNode.isRightChild()){
            rNode.parent.right = dNode;
        }else {
            root = dNode;
        }

        //根节点左子树 a-b-c
        if(aNode!=null){
            aNode.parent = bNode;
        }
        if(cNode!=null){
            cNode.parent = bNode;
        }
        bNode.left = aNode;
        bNode.right = cNode;

        //根节点右子树 e-f-g
        if(eNode!=null){
            eNode.parent = fNode;
        }
        if(gNode!=null){
            gNode.parent = fNode;
        }
        fNode.left=eNode;
        fNode.right=gNode;

        //根节点  a-d-f
        dNode.left=bNode;
        dNode.right=fNode;
        bNode.parent = dNode;
        fNode.parent = dNode;
    }

    @Override
    protected Node<E> createNode(E element, Node<E> parentNode) {
        return new AVLNode<>(element, parentNode);
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
            if (leftHeight > rightHeight) {
                return left;
            }
            if (leftHeight < rightHeight) {
                return right;
            }
            return isLeftChild() ? left : right;
        }

        @Override
        public String toString() {
//            String parentStr="";
//            if(parent!=null){
//                parentStr = parent.element+"";
//            }
//            return element+"_p("+parentStr+")_h("+height+")";
            return element.toString();
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
