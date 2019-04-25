package com.easy.work.common.algorithm.sort;

import java.util.Arrays;

public class BubbleSort {

     /**
      * @Description: 冒泡排序
      *
      * @param
      * @author Created by wuzhangwei on 2019/4/23 16:38
      */
    public static void sort(int[] arr, boolean asc) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i+1; j < arr.length; j++) {
                if (asc) {
                    if (arr[i] > arr[j]) {
                        swap(arr,i,j);
                    }
                } else {
                    if (arr[i] < arr[j]) {
                        swap(arr,i,j);
                    }
                }
            }
        }
    }


     /**
      * @Description:交换数组中的两个元素的位置
      * @param arr       // 数组
      * @param i         // 变量i
      * @param j         // 变量j
      * @author Created by wuzhangwei on 2019/4/23 16:39
      */
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = {1,3,2,5,9,11,15,36,33,48};
        System.out.println("排序数组：" + Arrays.toString(arr));
        sort(arr,true);
        System.out.println("升序排列：" + Arrays.toString(arr));
        sort(arr,false);
        System.out.println("降序排序：" + Arrays.toString(arr));
    }
}
