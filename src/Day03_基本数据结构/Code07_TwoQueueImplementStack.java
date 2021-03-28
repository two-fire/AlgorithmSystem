package Day03_基本数据结构;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @Author : LiuYan
 * @create 2021/3/26 21:58
 * 两个队列拼一个栈
 * 取数据时，从不空的queue1中倒n-1个数据倒queue2中，取出queue1中预留的一个,queue1和queue2名字交换；
 * 加数据时，继续向queue1中加数据
 */
public class Code07_TwoQueueImplementStack {
    public static class MyStack<T>{
        private Queue<T> queue1;
        private Queue<T> queue2;

        public MyStack() {
            queue1 = new LinkedList<>();
            queue2 = new LinkedList<>();
        }

        public void pushData() {
            while (queue1.size() > 1) {
                queue2.offer(queue1.poll());
            }
        }

        public void push(T value) {
            queue1.offer(value);
        }

        public T poll() {
            pushData();
            T ans = queue1.poll();
            Queue<T> tmp = queue2;
            queue2 = queue1;
            queue1 = tmp;
            return ans;
        }

        public T peek() {
            pushData();
            T ans = queue1.peek();
            queue2.offer(queue1.poll());
            Queue<T> tmp = queue2;
            queue2 = queue1;
            queue1 = tmp;
            return ans;
        }
        public boolean isEmpty() {
            return queue1.isEmpty();
        }
        // 错误，值传递
        /*
        public void swap(Queue<T> queue1, Queue<T> queue2) {
            Queue<T> tmp = queue2;
            queue2 = queue1;
            queue1 = tmp;
        }*/
    }
    public static void main(String[] args) {
        System.out.println("test begin");
        MyStack<Integer> myStack = new MyStack();
        Stack<Integer> test = new Stack<>();
        int testTime = 1000000;
        int max = 1000000;
        for (int i = 0; i < testTime; i++) {
            if (myStack.isEmpty()) {
                if (!test.isEmpty()) {
                    System.out.println("Oops");
                }
                int num = (int) (Math.random() * max);
                myStack.push(num);
                test.push(num);
            } else {
                if (Math.random() < 0.25) {
                    int num = (int) (Math.random() * max);
                    myStack.push(num);
                    test.push(num);
                } else if (Math.random() < 0.5) {
                    if (!myStack.peek().equals(test.peek())) {
                        System.out.println("Oops");
                    }
                } else if (Math.random() < 0.75) {
                    if (!myStack.poll().equals(test.pop())) {
                        System.out.println("Oops");
                    }
                } else {
                    if (myStack.isEmpty() != test.isEmpty()) {
                        System.out.println("Oops");
                    }
                }
            }
        }

        System.out.println("test finish!");

    }
}
