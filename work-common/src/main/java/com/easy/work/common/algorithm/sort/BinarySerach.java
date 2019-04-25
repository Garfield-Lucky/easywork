package com.easy.work.common.algorithm.sort;

public class BinarySerach {

     /**
      * @Description: 非递归二分查找
      * @param arr[] 数组
      * @param x 查找的值
      * @author Created by wuzhangwei on 2019/4/25 19:22
      */
    public static int  BinarySearch(int arr[], int x)
    {
        //如果传入的数组为空或者数组长度<=0那么就返回-1。防御性编程
        if (arr == null || arr.length < 0) {
            return -1;
        }
        int low = 0;
        int high = arr.length - 1;
        while (low <= high) {
            int middle = (low + high) / 2;
            if(x == arr[middle]) {
                return middle;
            } else if(x > arr[middle]) {
                low = middle + 1;
            } else {
                high = middle - 1;
            }
        }
        return -1;
    }

     /**
      * @Description: 递归的二分查找
      * @param arr[] 数组
      * @param x 查找的值
      * @param low 数组下标头
      * @param low 数组下标尾
      * @author Created by wuzhangwei on 2019/4/25 19:24
      */
    public static int nBinarySearch(int arr[], int x, int low, int high)
    {
        if(low > high) return -1;
        int middle = (low + high) / 2;
        if(x == arr[middle]) return middle;
        else if(x > arr[middle]) {
            return nBinarySearch(arr, x,middle+1,high);
        } else {
            return nBinarySearch(arr, x, low,middle-1);
        }
    }


    public static void main(String[] args) {

        //定义排序好的数组
        int arr[] = {1,3,5,6,7,9,10,15};
        int x = 5;

        System.out.println("非递归方式查找后的数组索引下标:" + BinarySearch(arr, x));

        System.out.println("递归方式查找后的数组索引下标:" + nBinarySearch(arr, x, 0, arr.length - 1));
    }

}
