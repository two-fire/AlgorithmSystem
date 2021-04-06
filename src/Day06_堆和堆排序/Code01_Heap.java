package Day06_堆和堆排序;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Author : LiuYan
 * @create 2021/3/27 22:02
 * 手写堆
 *  堆是完全二叉树结构
 */
public class Code01_Heap {
    public static class MyMaxHeap {
        private int[] heap;
        private final int limit;
        private int heapSize;

        public MyMaxHeap(int limit) {
            heap = new int[limit];
            this.limit = limit;
            heapSize = 0;
        }

        public boolean isEmpty() {
            return heapSize == 0;
        }

        public boolean isFull() {
            return heapSize == limit;
        }

        public void push(int value) {
            if (!isFull()) {
                heap[heapSize] = value;
                heapInseart(heap, heapSize++);
            } else {
                System.out.println("堆满！");
            }
        }

        public int pop() {
            if (isEmpty()) {
                System.out.println("堆空！");
            }
            int res = heap[0];
            swap(heap, 0, --heapSize);
            heapify(heap, 0, heapSize);
            return res;
        }

        public int peek() {
            if (isEmpty()) {
                System.out.println("堆空！");
            }
            return heap[heapSize - 1];
        }

        // 从index处向上调整
        private void heapInseart(int[] arr, int index) {
           while (arr[(index - 1) / 2] < arr[index]) { // 不需要i>=0,因为这一句隐含了这个条件
               swap(arr, (index - 1) / 2, index);
               index = (index - 1) / 2;
           }
        }
        // 从index处向下调整
        private void heapify(int[] arr, int index, int heapSize) {
            int i = index * 2 + 1;
            while (i < heapSize) { // 有左孩子
                // 把大的孩子下标赋给largest
                int largest = i + 1 < heapSize && arr[i + 1] > arr[i] ? i + 1 : i;
                largest = arr[largest] > arr[index] ? largest : index;
                if (largest == index) {
                   break;
                }
                swap(arr, largest, index);
                index = largest;
                i = index * 2 + 1;
            }
        }

        private void swap(int[] arr, int i, int j) {
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
    }
    //test
/*
    public static class RightMaxHeap {
        private int[] arr;
        private final int limit;
        private int size;

        public RightMaxHeap(int limit) {
            arr = new int[limit];
            this.limit = limit;
            size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public boolean isFull() {
            return size == limit;
        }

        public void push(int value) {
            if (size == limit) {
                throw new RuntimeException("heap is full");
            }
            arr[size++] = value;
        }

        public int pop() {
            int maxIndex = 0;
            for (int i = 1; i < size; i++) {
                if (arr[i] > arr[maxIndex]) {
                    maxIndex = i;
                }
            }
            int ans = arr[maxIndex];
            arr[maxIndex] = arr[--size];
            return ans;
        }

    }

    public static void main(String[] args) {

        int value = 1000;
        int limit = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            int curLimit = (int) (Math.random() * limit) + 1;
            MyMaxHeap my = new MyMaxHeap(curLimit);
            RightMaxHeap test = new RightMaxHeap(curLimit);
            int curOpTimes = (int) (Math.random() * limit);
            for (int j = 0; j < curOpTimes; j++) {
                if (my.isEmpty() != test.isEmpty()) {
                    System.out.println("Oops!");
                }
                if (my.isFull() != test.isFull()) {
                    System.out.println("Oops!");
                }
                if (my.isEmpty()) {
                    int curValue = (int) (Math.random() * value);
                    my.push(curValue);
                    test.push(curValue);
                } else if (my.isFull()) {
                    if (my.pop() != test.pop()) {
                        System.out.println("Oops!");
                    }
                } else {
                    if (Math.random() < 0.5) {
                        int curValue = (int) (Math.random() * value);
                        my.push(curValue);
                        test.push(curValue);
                    } else {
                        if (my.pop() != test.pop()) {
                            System.out.println("Oops!");
                        }
                    }
                }
            }
        }
        System.out.println("finish!");
    }

 */
}
