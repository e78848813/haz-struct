package com.hazards.inter;

public interface DynamicListInter<E> {

    /**
     * 增加一个元素到最后
     * @param e
     */
    void add(E e);

    /**
     * 移除一个元素
     * @param e
     */
    void remove(E e);

    /**
     * 移除指定下表的元素
     * @param index
     */
    void remove(int index);

    /**
     * 增加一个元素到指定下标位置
     * @param index
     * @param e
     */
    void add(int index, E e);

    /**
     * 更新一个下表位置的元素
     * @param index
     * @param e
     */
    void set(int index, E e);

    /**
     * 获取指定索引下表的元素
     * @param index
     * @return
     */
    E get(int index);

}
