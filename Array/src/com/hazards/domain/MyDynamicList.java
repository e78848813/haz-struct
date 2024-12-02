package com.hazards.domain;

import com.hazards.inter.DynamicListInter;

public class MyDynamicList<E> implements DynamicListInter<E> {

    E[] elements;
    int size = 0;


    private static final int DEFAULT_CAPACITY = 10;

    /**
     * 默认长度
     */
    public MyDynamicList() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * 初始花动态数组长度
     * @param size 指定长度
     */
    public MyDynamicList(int size) {
        if(size <=0){
            throw new IllegalArgumentException("Size must be greater than 0");
        }
        elements = (E[]) new Object[size];
    }


    public int size() {
        return this.size;
    }


    /**
     * 添加元素
     * @param e 元素
     */
    public void add(E e) {
        ensureCapacity(size+1);
        elements[size++] = e;
    }


    /**
     * 添加元素
     * @param e 元素
     * @param index 索引
     */
    public void add(int index,E e) {
        rangeCheckAdd(index);
        ensureCapacity(size+1);
        for (int i = size; i > index; i--) {
            elements[i] = elements[i - 1];
        }
        elements[index] = e;
        size++;
    }

    /**
     * 容量需求
     * @param minCapacity
     */
    private void ensureCapacity(int minCapacity) {
        int oldCapacity = elements.length;
        if(oldCapacity > minCapacity){
            //原有容量大于所需容量，无需扩容
            return;
        }
        //扩容,变为以前的1.5倍
        E[] newElements = (E[]) new Object[oldCapacity>>1];
        System.arraycopy(elements, 0, newElements, 0, elements.length);
    }
    /**
     * 删除元素
     * @param e
     */
    public void remove(E e) {
        //判断数组容量进行缩容
        int index = getIndex(e);
        if(index == -1) {
            return;
        }
        rangeCheck(index);
        for (int i = index+1; i < size; i++) {
            elements[i-1] = elements[i];
        }
        elements[--size] = null;
    }


    public void remove(int index) {
        rangeCheck(index);
        for (int i = index+1; i < size; i++) {
            elements[i-1] = elements[i];
        }
        elements[--size] = null;
    }

    /**
     * 更新指定位置的与元素
     * @param index
     * @param e
     */
    @Override
    public void set(int index, E e) {
        rangeCheck(index);
        elements[index] = e;
    }

    private void rangeCheck(int index) {
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    /**
     * 添加元素的索引合法性校验
     * @param index
     */
    private void rangeCheckAdd(int index) {
        if(index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    /**
     * 获取元素
     * @param index 索引
     * @return 元素
     */
    public E get(int index) {
        //如果索引比数组的容量大，抛出异常
        rangeCheck(index);
        return elements[index];
    }

    /**
     * 获取指定元素的索引
     * @param element 指定
     * @return 元素索引
     */
    private int getIndex(E element) {
        //遍历查找元素
        for (int i = 0; i < size; i++) {
            if(elements[i].equals(element)) {
                return i;
            }
        }
        //如果没找打元素，直接返回-1
        return -1;
    }


}
