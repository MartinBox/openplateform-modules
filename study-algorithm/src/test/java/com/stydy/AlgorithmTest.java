package com.stydy;

import org.junit.Test;

import java.util.Arrays;

/**
 * Unit factories for simple App.
 */
public class AlgorithmTest {

    @FunctionalInterface
    interface OutputContent {
        Object out();
    }

    public void println(OutputContent content) {
        System.out.println(content.out());
    }

    Integer[] data = {15, 4, 7, 8, 78, 0, 695, 9, 3, 5};

    @Test
    public void bubbleSort_test() {
        println(() -> Arrays.deepToString(bubbleSort(data)));
    }

    /**
     * 冒泡排序
     */
    public Integer[] bubbleSort(Integer[] data) {
        if (data == null || data.length == 0) {
            return null;
        }
        for (int i = 0; i < data.length - 1; i++) {
            for (int j = 0; j < data.length - 1 - i; j++) {
                if (data[j] > data[j + 1]) {
                    int tmp = data[j];
                    data[j] = data[j + 1];
                    data[j + 1] = tmp;
                }
            }
        }
        return data;
    }

    @Test
    public void selectSort_test() {
        println(() -> Arrays.deepToString(selectSort(data)));
    }

    /**
     * 选择排序
     */
    public Integer[] selectSort(Integer[] data) {
        if (data == null || data.length == 0) {
            return null;
        }
        // 外层循环：对未排序空间逐步迭代
        for (int i = 0; i < data.length; i++) {
            int minIndex = i;
            // 内层循环：在未排序空间查找最小元素索引
            for (int j = i; j < data.length; j++) {
                if (data[minIndex] > data[j]) {
                    minIndex = j;
                }
            }
            int tmp = data[i];
            data[i] = data[minIndex];
            data[minIndex] = tmp;
        }
        return data;
    }

    @Test
    public void insertionSort_test() {
        System.out.println(Arrays.deepToString(insertionSort(data)));
    }

    /**
     * 插入排序
     */
    public Integer[] insertionSort(Integer[] data) {
        for (int i = 0; i < data.length - 1; i++) {
            int preIndex = i;
            int value = data[i + 1];
            while (preIndex >= 0 && value < data[preIndex]) {
                data[preIndex + 1] = data[preIndex];
                preIndex--;
            }
            data[preIndex + 1] = value;
        }
        return data;
    }
    /**
     * 没看懂？？？
     */
    @Test
    public void shellSort_test() {
        System.out.println(Arrays.deepToString(shellSort(data)));
    }

    /**
     * 希尔排序
     */
    public Integer[] shellSort(Integer[] array) {
        int len = array.length;
        int temp, gap = len / 2;
        while (gap > 0) {
            for (int i = gap; i < len; i++) {
                temp = array[i];
                int preIndex = i - gap;
                while (preIndex >= 0 && array[preIndex] > temp) {
                    array[preIndex + gap] = array[preIndex];
                    preIndex -= gap;
                }
                array[preIndex + gap] = temp;
            }
            gap /= 2;
        }
        return array;
    }

    @Test
    public void mergeSort_test() {
        println(() -> Arrays.deepToString(mergeSort(data)));
    }

    /**
     * 归并排序
     */
    public Integer[] mergeSort(Integer[] data) {
        if (data.length < 2) return data;
        int mid = data.length / 2;
        Integer[] left = Arrays.copyOfRange(data, 0, mid);
        Integer[] right = Arrays.copyOfRange(data, mid, data.length);
        return merge(mergeSort(left), mergeSort(right));
    }

    /**
     * 归并排序——将两段排序好的数组结合成一个排序数组
     *
     * @param left
     * @param right
     * @return
     */
    public Integer[] merge(Integer[] left, Integer[] right) {
        Integer[] result = new Integer[left.length + right.length];
        for (int index = 0, i = 0, j = 0; index < result.length; index++) {
            if (i >= left.length)
                result[index] = right[j++];
            else if (j >= right.length)
                result[index] = left[i++];
            else if (left[i] > right[j])
                result[index] = right[j++];
            else
                result[index] = left[i++];
        }
        return result;
    }

    /**
     * 没看懂？？？
     */
    @Test
    public void quickSort_test() {
        println(() -> Arrays.deepToString(quickSort(data, 0, data.length - 1)));
    }

    /**
     * 快速排序方法
     *
     * @param array
     * @param start
     * @param end
     * @return
     */
    public Integer[] quickSort(Integer[] array, int start, int end) {
        if (array.length < 1 || start < 0 || end >= array.length || start > end) {
            return null;
        }
        int smallIndex = partition(array, start, end);
        if (smallIndex > start) {
            quickSort(array, start, smallIndex - 1);
        }
        if (smallIndex < end) {
            quickSort(array, smallIndex + 1, end);
        }
        return array;
    }

    /**
     * 快速排序算法——partition
     *
     * @param array
     * @param start
     * @param end
     * @return
     */
    public int partition(Integer[] array, int start, int end) {
        int pivot = (int) (start + Math.random() * (end - start + 1));
        int smallIndex = start - 1;
        swap(array, pivot, end);
        for (int i = start; i <= end; i++) {
            if (array[i] <= array[end]) {
                smallIndex++;
                if (i > smallIndex) {
                    swap(array, i, smallIndex);
                }
            }
        }
        return smallIndex;
    }

    /**
     * 交换数组内两个元素
     *
     * @param array
     * @param i
     * @param j
     */
    public void swap(Integer[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
