package Day03_基本数据结构;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @Author : LiuYan
 * @create 2021/3/26 19:45
 * 数组实现栈和队列
 * 队列：设置三个变量
 */
public class Code04_RingArray {
    // 数组实现栈
    public static class MyStack {
        private int[] arr;
        private int index = 0;
        public MyStack() {
            arr = new int[200];
        }
        // 压入
        public void push(int value) {
            if (index < arr.length - 1) {
                arr[index] = value;
                index++;
            }
        }
        // 弹出
        public int pop() {
            if (index != -1) {
                index--;
                return arr[index + 1];
            }
            return -1;
        }
        public boolean isEmpty() {
            return index == -1;
        }
    }
    // 数组实现队列
    public static class MyQueue {
        private int[] arr;
        private int pushi; // 添加元素的指针
        private int popi; // 弹出元素的指针
        private int size; // 元素个数

        public MyQueue(int limit) {
            arr = new int[limit];
            pushi = 0;
            popi = 0;
            size = 0;
        }

        public void push(int value) {
            if (size < arr.length) {
                arr[pushi] = value;
                size++;
                pushi = nextIndex(pushi);
            } else {
                throw new RuntimeException("满了，不能再加了");
            }
        }

        public int pop() {
            if (size == 0) {
                throw new RuntimeException("空的，不能再取了");
            } else {
                int ans = arr[popi];
                popi = nextIndex(popi);
                size--;
                return ans;
            }
        }

        private int nextIndex(int i) {
            if (i == arr.length - 1) {
                return 0;
            } else {
                return i + 1;
            }
        }
    }

}

