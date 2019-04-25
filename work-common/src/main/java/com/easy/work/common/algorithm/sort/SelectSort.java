package com.easy.work.common.algorithm.sort;

import java.util.Arrays;

public class SelectSort {

     /**
      * @Description: 选择排序
      * @param arr       排序的数组
      * @param asc       是否升序
      * @author Created by wuzhangwei on 2019/4/25 19:50
      */
    public static void sort(int[] arr, boolean asc) {
        for (int i = 0; i < arr.length; i++) {
            int index = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (asc) {
                    if (arr[index] > arr[j]) { // 升序，选择无序区最小的元素
                        index = j;
                    }
                } else {
                    if (arr[index] < arr[j]) {// 降序，选择无序区最大的元素
                        index = j;
                    }
                }
            }
            if (index != i) {
                swap(arr,index,i);
            }
        }

    }


     /**
      * @Description: 交换数组中的两个元素的位置
      * @param arr       // 数组
      * @param i         // 变量i 最小或最大得那个元素
      * @param j         // 变量j 最外层的for循环 下标从0开始
      * @author Created by wuzhangwei on 2019/4/25 19:51
      */
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = {1,6,45,28,36,19,24,63,56};
        System.out.println("排序数组：" + Arrays.toString(arr));
        sort(arr, true);
        System.out.println("升序排列：" + Arrays.toString(arr));
        sort(arr, false);
        System.out.println("降序排列：" + Arrays.toString(arr));

    }
}