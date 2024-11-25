package hazards.domain.tree.avl;

import hazards.domain.tree.BinarySearchTree;
import hazards.utils.printer.BinaryTreeInfo;
import hazards.utils.printer.BinaryTrees;

import java.util.Comparator;

/**
 * AVL树示例
 */
public class AVLTree<E> implements BinaryTreeInfo {


    protected AVLNode<E> root;

    protected int size;

    protected Comparator<E> comparator;


    protected void afterAdd(AVLNode<E> node) {
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

    public void remove(E element){
        AVLNode<E> node = node(element);

        if(node.left!=null&&node.right!=null){
            AVLNode<E> prev = successor(node);
            node.element = prev.element;
            node = prev;
        }

        AVLNode<E> replaceNode = node.left != null ? node.left : node.right;
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

        //删除后的逻辑处理
        afterRemove(node);

    }

    /**
     * 删除节点后是否平衡
     * @param node
     */
    protected void afterRemove(AVLNode<E> node) {
        if(node == null) {
            return;
        }

        while ((node = node.parent) != null) {
            if (isBalanced(node)) {
                updateHeight(node);
            }else {
                rebalanced(node);
            }
        }
    }

    /**
     * 更新指定节点的高度
     *
     * @param node 指定节点-高度最低的不平衡节点
     */
    private void updateHeight(AVLNode<E> node) {
        node.updateHeight();
    }

    /**
     * 恢复平衡
     *
     * @param grandNode 指定节点
     */
    private void rebalanced(AVLNode<E> grandNode) {
        AVLNode<E> parNode = grandNode.tallerChildNode();
        AVLNode<E> node = parNode.tallerChildNode();
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



    private AVLNode<E> node(E element){
        if (element==null) return null;

        AVLNode<E> curNode = this.root;
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


    /**
     * 对指定节点进行左旋转
     *
     * @param node 指定节点
     */
    private void rotateLeft(AVLNode<E> node) {
        AVLNode<E> childNode = node.right;
        AVLNode<E> grandSonNode = childNode.left;
        //更新当前节点和直接子节点的关系
        node.right = grandSonNode;
        childNode.left = node;

        //对各个节点的关系进行更新
        afterRotate(node,childNode,grandSonNode);

    }

    /**
     * 对指定节点进行右旋转
     *
     * @param node 指定节点
     */
    private void rotateRight(AVLNode<E> node) {
        AVLNode<E> childNode = node.left;
        AVLNode<E> grandSonNode = childNode.right;
        //更新当前节点与直接子节点的关系
        node.left = grandSonNode;
        childNode.right = node;
        //对各个节点的关系进行更新
        afterRotate(node,childNode,grandSonNode);
    }

    private void afterRotate(AVLNode<E> grandNode,AVLNode<E> parentNode,AVLNode<E> childNode) {
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

    private void elementCheck(E element) {
        if (element == null) {
            throw new IllegalArgumentException("element not allow be null!");
        }
    }

    public void add(E element) {
        elementCheck(element);
        //第一次添加
        if (root == null) {
            root = new AVLNode<>(element, null);
            size++;
            return;
        }
        //找到父节点
        AVLNode<E> node = this.root;
        AVLNode<E> parentNode = null;
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
        AVLNode<E> newNode = new AVLNode<>(element, parentNode);
        if (cmp > 0) {
            parentNode.left = newNode;
        } else {
            parentNode.right = newNode;
        }

        //添加节点的后续操作
        afterAdd(newNode);
    }



    private int compare(E element1, E element2) {
        if (comparator != null) {
            return comparator.compare(element1, element2);
        }
        return ((Comparable) element1).compareTo(element2);
    }

    @Override
    public Object root() {
        return this.root;
    }

    @Override
    public Object left(Object node) {
        return ((AVLNode<E>) node).left;
    }

    @Override
    public Object right(Object node) {
        return ((AVLNode<E>) node).right;
    }

    @Override
    public Object string(Object node) {
        return ((AVLNode<E>)node).element;
    }

    /**
     * AVL内部节点对象
     *
     * @param <E> 范型
     */
    private static class AVLNode<E>{

        //高度，默认为1，添加的时候为叶子节点
        int height = 1;
        public E element;
        public AVLNode<E> left;
        public AVLNode<E> right;
        public AVLNode<E> parent;

        public AVLNode(E element, AVLNode<E> parentNode) {
            this.element = element;
            this.parent = parentNode;
        }

        /**
         * 获取平衡因子
         *
         * @return 平衡因子
         */
        public int balanceFactor() {
            int leftHeight = left == null ? 0 :  left.height;
            int rightHeight = right == null ? 0 : right.height;
            return leftHeight - rightHeight;
        }

        public void updateHeight() {
            int leftHeight = left == null ? 0 : left.height;
            int rightHeight = right == null ? 0 : right.height;
            this.height = Math.max(leftHeight, rightHeight) + 1;
        }


        public AVLNode<E> tallerChildNode() {
            int leftHeight = left == null ? 0 :  left.height;
            int rightHeight = right == null ? 0 : right.height;
            if (leftHeight > rightHeight) {
                return left;
            }
            if (leftHeight < rightHeight) {
                return right;
            }
            return isLeftChild() ? left : right;
        }

        public boolean isLeftChild() {
            return parent!=null && parent.left==this;
        }

        public boolean isRightChild() {
            return parent!=null && parent.right==this;
        }

        @Override
        public String toString() {
            return element.toString();
        }
    }


    /**
     * 獲取後驅節點
     *
     * @param node 當前節點
     * @return 後驅節點
     */
    private AVLNode<E> successor(AVLNode<E> node) {
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
     * 当前节点是否平衡
     *
     * @param node 节点
     * @return 是否平衡
     */
    private boolean isBalanced(AVLNode<E> node) {
        return Math.abs(node.balanceFactor()) <= 1;
    }
}
