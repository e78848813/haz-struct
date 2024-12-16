package hazards.domain.tree.bt;

/**
 * B树
 */
public class BTree{

    /**
     * B树阶级，默认为4阶B树
     */
    private int levelSize = 4;

    public BTree(){

    }

    public BTree(int levelSize){
        this.levelSize = levelSize;
    }

    /**
     * 添加元素
     * @param element 元素
     */
    public void add(int element){

    }

    /**
     * 删除元素
     * @param element 元素
     */
    public void remove(int element){

    }


    /**
     * B树节点对象
     */
    private class BNode {
        //元素
        int[] elements;
        //子节点
        BNode[] children;
        //父节点
        BNode parent;
        //节点存储元素的个数
        int size;


        public BNode() {
            //初始化一个新节点
            this.elements = new int[levelSize];
            this.children = new BNode[levelSize+1];
            this.size=0;
        }

        //节点上添加元素
        public void add(int element) {
            if(size==levelSize){
                return;
            }
        }

        public void remove(int element) {

        }

    }
}
