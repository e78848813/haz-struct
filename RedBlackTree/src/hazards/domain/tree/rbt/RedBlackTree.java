package hazards.domain.tree.rbt;

import hazards.domain.inter.TreeInterface;
import hazards.domain.tree.BinarySearchTree;
import hazards.domain.tree.avl.AVLTree;
import sun.reflect.generics.tree.Tree;

import java.util.Comparator;

/**
 * 红黑树
 */
public class RedBlackTree<E> extends BinarySearchTree<E> {

    /**
     * 红色类型节点
     */
    private static final int COLOR_TYPE_RED = 0;

    /**
     * 黑色类型节点
     */
    private static final int COLOR_TYPE_BLACK = 1;


    public RedBlackTree() {
        super();
    }

    public RedBlackTree(Comparator<E> comparator) {
        super(comparator);
    }

    /**
     * 添加元素后的逻辑
     * @param node
     */
    @Override
    protected void afterAdd(Node<E> node) {
        super.afterAdd(node);
    }


    /**
     * 删除后的处理逻辑
     * @param node
     */
    @Override
    protected void afterRemove(Node<E> node) {
        super.afterRemove(node);
    }


    /**
     * 指定节点的颜色
     * @param node 节点
     * @param color 颜色编码
     * @return 染色后的节点
     */
    private Node<E> color(Node<E> node,int color) {
        if(node == null) return null;
        ((RBNode)node).color = color;
        return node;
    }

    /**
     * 指定节点为红色
     * @param node 指定节点
     * @return 染色后的节点
     */
    private Node<E> red(Node<E> node) {
        if(node == null) return null;
        return color(node,COLOR_TYPE_RED);
    }

    /**
     * 指定节点为黑色
     * @param node 指定节点
     * @return 染色后的节点+
     *
     *
     */
    private Node<E> black(Node<E> node) {
        if(node == null) return null;
        return color(node,COLOR_TYPE_BLACK);
    }


    /**
     * 红黑树的节点
     * @param <E> 枚举类型
     */
    private static class RBNode<E> extends Node<E> {

        //高度，默认为1，添加的时候为叶子节点
        int height = 1;
        public RBNode<E> parent;

        public int color;

        public RBNode(E element, RBNode<E> parentNode) {
            super(element, parentNode);
        }


        public boolean isLeftChild() {
            return parent!=null && parent.left==this;
        }

        public boolean isRightChild() {
            return parent!=null && parent.right==this;
        }


        /**
         * 兄弟节点
         * @param node 指定节点
         * @return 兄弟节点
         */
        public Node<E> broNode(Node<E> node) {
            if(isLeftChild()){
                return parent.right;
            }

            if(isRightChild()){
                return parent.left;
            }

            return node;
        }



        @Override
        public String toString() {
            return element.toString();
        }
    }
}
