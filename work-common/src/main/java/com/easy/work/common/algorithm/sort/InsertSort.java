package com.easy.work.common.algorithm.sort;

import java.util.Arrays;
public class InsertSort {

     /**
      * @Description: 插入排序
      * @param arr       排序数组
      * @param asc       是否升序
      * @author Created by wuzhangwei on 2019/4/25 20:00
      */
    public static void sort(int[] arr, boolean asc) {
        // 有序区最后一个元素位置
        int orderedLastIndex = 0;//开始排序时，将有序区结束位置设为0 (开始位置总是0)，对应的无序区范围就是 1-arr.length
        for (int i = orderedLastIndex + 1; i < arr.length; i++) {//迭代无序区中的每一个元素，依次插入有序区中
            int temp = arr[i];//记录无序区中的第一个元素值
            int insertIndex = i;//在有序区中插入的索引的位置，刚开始就设置为自己的位置
            for (int j = orderedLastIndex; j >= 0; j--) {//从有序区从后往前开始比较
                if (asc) {//升序，有序区中比当前无序区中元素大的都右移一个位置
                    if (arr[j] > temp) {
                        arr[j+1] = arr[j];
                        insertIndex --;//有序区每移动一次，将插入位置-1
                    } else {
                        break;//有序区当前位置元素<=无序区第一个元素，那么之前的元素都会<=，不需要继续比较
                    }
                } else {//降序，有序区中比当前无序区中元素小的都右移一个位置
                    if (arr[j] < temp) {
                        arr[j+1] = arr[j];
                        insertIndex --;
                    } else {
                        break;
                    }
                }
            }
            arr[insertIndex] = temp;
            orderedLastIndex ++;
        }
    }

    public static void main(String[] args) {
        int[] arr = {1,8,3,2,10,34,67,93,11};
        System.out.println("排序数组：" + Arrays.toString(arr));
        sort(arr,true);
        System.out.println("升序数组：" + Arrays.toString(arr));
        sort(arr,false);
        System.out.println("降序数组：" + Arrays.toString(arr));

    }
}
