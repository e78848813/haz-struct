package com.hazards.domain.tree;


import com.hazards.domain.inter.TreeInterface;
import com.hazards.utils.printer.BinaryTreeInfo;

import java.util.*;

/**
 * 二叉搜索树实现类
 */
public class BinarySearchTree<E> implements TreeInterface<E>, BinaryTreeInfo {

    protected Node<E> root;

    protected int size;

    /**
     * 手动的比较器
     */
    protected Comparator<E> comparator;

    /**
     * 手动设置比较器
     *
     * @param comparator 比较器
     */
    public BinarySearchTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    public BinarySearchTree() {

    }

    /**
     * 添加元素
     *
     * @param element 元素
     */
    @Override
    public void add(E element) {
        elementCheck(element);
        //第一次添加
        if (root == null) {
            root = createNode(element, null);
            size++;
            return;
        }
        //找到父节点
        Node<E> node = this.root;
        Node<E> parentNode = null;
        int cmp = 0;
        while (node != null) {
            cmp = compare(node.element, element);
            parentNode = node;
            if (cmp > 0) {
                node = node.left;
            } else if (cmp < 0) {
                node = node.right;
            } else {
                return;
            }
        }
        //将新节点放入子节点中
        Node<E> newNode = createNode(element, parentNode);
        if (cmp > 0) {
            parentNode.left = newNode;
        } else {
            parentNode.right = newNode;
        }

        //添加节点的后续操作
        afterAdd(newNode);
    }

    protected void afterAdd(Node<E> node){

    }

    private int compare(E element1, E element2) {
        if (comparator != null) {
            return comparator.compare(element1, element2);
        }
        return ((Comparable) element1).compareTo(element2);
    }


    /**
     * 清空数据结构的数据
     */
    @Override
    public void clear() {
        this.root = null;
        this.size = 0;
    }


    @Override
    public boolean contain(E element) {
        return node(element)!=null;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    private void elementCheck(E element) {
        if (element == null) {
            throw new IllegalArgumentException("element not allow be null!");
        }
    }

    @Override
    public Object root() {
        return this.root;
    }

    @Override
    public Object left(Object node) {
        return ((Node<E>) node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node<E>) node).right;
    }

    @Override
    public Object string(Object node) {
        Node<E> eNode = (Node<E>) node;
        E eParent = eNode.parent==null?null:eNode.parent.element;
        return eNode.element+"_"+eParent;
    }


    /**
     * 节点内部类
     *
     * @param <E>
     */
    protected static class Node<E> {
        public E element;
        public Node<E> left;
        public Node<E> right;
        public Node<E> parent;

        public Node(E element, Node<E> parentNode) {
            this.element = element;
            this.parent = parentNode;
        }

        @Override
        public String toString() {
            return element + "_";
        }


        public boolean isLeafNode() {
            return left == null && right == null;
        }


        public boolean isLeftChild() {
            return parent!=null && parent.left==this;
        }

        public boolean isRightChild() {
            return parent!=null && parent.right==this;
        }
    }

    protected Node<E> createNode(E element, Node<E> parentNode){
        return new Node<>(element,parentNode);
    }

    /**
     * 前序遍历算法
     */
    public void preOrderTraversal(Visitor<E> visitor) {
        preOrderTraversal(root, visitor);

    }

    private void preOrderTraversal(Node<E> node, Visitor<E> visitor) {
        if (node == null || visitor.stopFlag) return;
        visitor.visit(node.element);
        preOrderTraversal(node.left, visitor);
        preOrderTraversal(node.right, visitor);
    }


    Queue<Node<E>> queue = new LinkedList<>();

    /**
     * 层级遍历算法
     */
    public void layerTraversal() {
        Visitor<E> defaultVisitor = new DefaultVisitor<E>();
        layerTraversal(defaultVisitor);
    }

    public void layerTraversal(Visitor<E> visitor) {
        if (root == null) return;

        //根节点入队
        queue.offer(root);
        while (!queue.isEmpty()) {
            //弹出最先进入的节点
            Node<E> peek = queue.poll();
            if (visitor.visit(peek.element)) {
                return;
            }
            //将左子节点的数据入队
            if (peek.left != null) {
                queue.offer(peek.left);
            }
            //将右子节点的数据入队
            if (peek.right != null) {
                queue.offer(peek.right);
            }
        }
    }

    public interface Visitor<E> {

        boolean stopFlag = false;

        boolean visit(E e);
    }

    private static class DefaultVisitor<E> implements Visitor<E> {

        @Override
        public boolean visit(E e) {
            System.out.println(e + "_");
            return true;
        }
    }


    /**
     * 获取该二叉搜索树的高度
     *
     * @return 高度
     */
    public int height() {
        return height(root);
    }


    /**
     * 获取某个节点树的高度
     *
     * @param node 主节点
     * @return 高度
     */
    private int height(Node<E> node) {
        if (root == null) return 0;

        int height = 0;
        //每层的需要出队的数据个数，初始为主节点，默认为1
        int levelSize = 1;
        //根节点入队
        queue.offer(root);
        while (!queue.isEmpty()) {
            //弹出最先进入的节点
            Node<E> peek = queue.poll();
            //出队一个，每层的数据减少一个
            levelSize--;

            //将左子节点的数据入队
            if (peek.left != null) {
                queue.offer(peek.left);
            }
            //将右子节点的数据入队
            if (peek.right != null) {
                queue.offer(peek.right);
            }


            //当执行完一次操作后，判断是否将该层的数据已经全部出队，表示已经走完一层的数据
            if (levelSize == 0) {
                //将下一层的数据个数更新到需要出队的个数
                levelSize = queue.size();
                height++;
            }
        }

        return height;
    }


    public boolean isComplete() {
        return isComplete(root);
    }

    /**
     * 是否是完全二叉树
     */
    private boolean isComplete(Node<E> node) {
        if (node == null) return false;

        boolean needLeaf = false;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(node);
        while (!queue.isEmpty()) {
            Node<E> poll = queue.poll();
            System.out.println(poll.element);
            if (needLeaf && !poll.isLeafNode()) {
                return false;
            }

            if (poll.left != null) {
                queue.offer(poll.left);
            } else if (poll.right != null) {
                return false;
            }

            if (poll.right != null) {
                queue.offer(poll.right);
            } else {
                needLeaf = true;
            }
        }
        return true;
    }


    public void reversal() {
        if (root == null) return;

        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node<E> poll = queue.poll();

            Node<E> tep = poll.left;
            poll.left = poll.right;
            poll.right = tep;


            if (poll.left != null) {
                queue.offer(poll.left);
            }

            if (poll.right != null) {
                queue.offer(poll.right);
            }
        }
    }


    /**
     * 獲取前驅節點
     *
     * @param node 節點
     * @return 前區節點
     */
    private Node<E> precursor(Node<E> node) {
        if (node == null) return null;

        if (node.left != null) {
            node = node.left;
            while (node.right != null) {
                node = node.right;
            }
            return node;
        }

        while (node.parent != null && node != node.parent.right) {
            node = node.parent;
        }

        return node.parent == null ? createNode(null, null) : node.parent;
    }

    /**
     * 獲取後驅節點
     *
     * @param node 當前節點
     * @return 後驅節點
     */
    private Node<E> successor(Node<E> node) {
        if (node == null) return null;


        if (node.right != null) {
            node = node.right;
            while (node.left != null) {
                node = node.left;
            }
            return node;
        }

        while (node.parent != null && node != node.parent.left) {
            node = node.parent;
        }

        return node.parent;
    }


    /**
     * 迭代算法方式前序遍历
     */
    public void preOrderIter() {
        //根节点
        if (root == null) return;

        Node<E> node = this.root;
        Stack<Node<E>> stack = new Stack<>();
        while (!stack.isEmpty() || node != null) {


            //循環遍歷到最左側的節點
            while (node != null) {
                System.out.print(node.element);
//                visitor.visit(predesessor(node).element);
                System.out.print("--前驅:" + precursor(node).element);
                System.out.println("--後驅" + successor(node).element);
                stack.push(node);
                node = node.left;
            }

            //找到當前
            node = stack.pop();
            node = node.right;
        }

    }


    /**
     * 中序遍历迭代方式实现
     */
    public void inOrderTraversal() {
        //根节点为空，直接返回
        if (root == null) return;
        Stack<Node<E>> stack = new Stack<>();
        Node<E> node = this.root;
        while (!stack.isEmpty()||node!=null) {
            //循环到最左的一个节点
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            //按左节点的顺序弹出遍历，并判断是否有有紫薯，将当前节点当作右子树根节点，进行重复操作
            node = stack.pop();
            System.out.println(node);
            node = node.right;
        }
    }


    /**
     * 后续遍历迭代方法实现
     */
    public void postOrderTraversal() {
        if (root == null) return;

        Stack<Node<E>> stack = new Stack<>();
        Node<E> curNode = this.root;
        Node<E> prev = null;
        while (!stack.isEmpty() || curNode != null) {
            while (curNode != null) {
                stack.push(curNode);
                curNode = curNode.left;
            }
            curNode = stack.pop();
            if(curNode.right==prev||curNode.right==null){
                System.out.println(curNode);
                prev = curNode;
                curNode = null;
            }else {
                stack.push(curNode);
                curNode = curNode.right;
            }
        }

    }



    public void postOrderTraversal2() {
        if (root == null) return;

        Stack<Node<E>> stack = new Stack<>();
        Node<E> curNode = this.root;
        Node<E> prev = null;
        while (!stack.isEmpty() || curNode != null) {
            while (curNode != null) {
                stack.push(curNode);
                curNode = curNode.left;
            }

            curNode = stack.pop();

            if(curNode.right==null||curNode.right==prev){
                prev = curNode;
                curNode=null;
                System.out.println(prev);
            }else {
                stack.push(curNode);
                curNode = curNode.right;
            }
        }

    }


    @Override
    public void remove(E element){
        Node<E> node = node(element);

        if(node.left!=null&&node.right!=null){
            Node<E> prev = successor(node);
            node.element = prev.element;
            node = prev;
        }


        Node<E> replaceNode = node.left != null ? node.left : node.right;
        if(replaceNode!=null){
            replaceNode.parent = node.parent;
            if(node.parent==null){
                root = replaceNode;
            }else if(node.parent.left==node){
                node.parent.left = replaceNode;
            }else {
                node.parent.right = replaceNode;
            }
        }else if(node.parent==null){
            root = null;
        }else{
            if(node.parent.left==node){
                node.parent.left = null;
            }else {
                node.parent.right=  null;
            }
        }


    }


    private Node<E> node(E element){
        if (element==null) return null;

        Node<E> curNode = this.root;
        while (curNode!=null){
            int cmp = compare(curNode.element, element);
            if(cmp==0){
                return curNode;
            }else if(cmp>0){
                curNode = curNode.left;
            }else {
                curNode = curNode.right;
            }
        }

        return null;
    }
}
