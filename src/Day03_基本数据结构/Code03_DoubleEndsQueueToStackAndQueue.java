package Day03_基本数据结构;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @Author : LiuYan
 * @create 2021/3/26 14:51
 * 双端链表实现栈和队列
 */
public class Code03_DoubleEndsQueueToStackAndQueue {
    public static class Node<T> {
        public T value;
        public Node<T> last;
        public Node<T> next;

        public Node(T data) {
            value =data;
        }
    }
    // 双端链表的功能类
    public static class DoubleEndsQueue<T> {
        public Node<T> head;
        public Node<T> tail;
        // 头插 不能加static
        public void addFromHead(T value) {
            Node<T> node =  new Node<>(value);
            if (head == null) {
                head = node;
                tail = node;
            } else {
                node.next = head;
                head.last = node;
                head = node;
            }
        }
        // 尾插
        public void addFromTail(T value) {
            Node<T> node =  new Node<>(value);
            if (head == null) {
                head = node;
                tail = node;
            } else {
                head.next = node;
                node.last = head;
                tail = node;
            }
        }
        // 头删
        public T popFromHead() {
            if (head == null) {
                return null;
            }
            Node<T> cur = head;
            if (head == tail) { // 只有一个节点
                head = null;
                tail = null;
            } else {
                head = head.next;
                head.last = null;
                cur.next = null;
            }
            return cur.value;
        }
        // 尾删
        public T popFromBottom() {
            if (head == null) {
                return null;
            }
            Node<T> cur = tail;
            if (head == tail) { // 只有一个节点
                head = null;
                tail = null;
            } else {
                tail = tail.last;
                tail.next = null;
                cur.last = null;
            }
            return cur.value;
        }
        // 判空
        public boolean isEmpty() {
            return head == null;
        }
    }
    // 双端链表实现栈
    public static class MyStack<T> {
        private DoubleEndsQueue<T> queue;
        public MyStack() {
            queue = new DoubleEndsQueue<>();
        }
        public void push(T value) {
            queue.addFromHead(value);
        }
        public T pop() {
            return queue.popFromHead();
        }
        public boolean isEmpty() {
            return queue.isEmpty();
        }

    }
    // 双端链表实现队列
    public static class MyQueue<T> {
        private DoubleEndsQueue<T> queue;
        public MyQueue() {
            queue = new DoubleEndsQueue<>();
        }
        public void push(T value) {
            queue.addFromHead(value);
        }
        public T poll() {
            return queue.popFromBottom();
        }
        public boolean isEmpty() {
            return queue.isEmpty();
        }
    }
    // test
    /*
    public static boolean isEqual(Integer o1, Integer o2) {
        if (o1 == null && o2 != null) {
            return false;
        }
        if (o1 != null && o2 == null) {
            return false;
        }
        if (o1 == null && o2 == null) {
            return true;
        }
        return o1.equals(o2);
    }
    public static void main(String[] args) {
        int oneTestDataNum = 100;
        int value = 10000;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            MyStack<Integer> myStack = new MyStack<>();
            MyQueue<Integer> myQueue = new MyQueue<>();
            Stack<Integer> stack = new Stack<>();
            Queue<Integer> queue = new LinkedList<>();
            for (int j = 0; j < oneTestDataNum; j++) {
                int nums = (int) (Math.random() * value);
                if (stack.isEmpty()) {
                    myStack.push(nums);
                    stack.push(nums);
                } else {
                    if (Math.random() < 0.5) {
                        myStack.push(nums);
                        stack.push(nums);
                    } else {
                        if (!isEqual(myStack.pop(), stack.pop())) {
                            System.out.println("oops!");
                        }
                    }
                }
                int numq = (int) (Math.random() * value);
                if (stack.isEmpty()) {
                    myQueue.push(numq);
                    queue.offer(numq);
                } else {
                    if (Math.random() < 0.5) {
                        myQueue.push(numq);
                        queue.offer(numq);
                    } else {
                        if (!isEqual(myQueue.poll(), queue.poll())) {
                            System.out.println("oops!");
                        }
                    }
                }
            }
        }
        System.out.println("finish!");
    }
    */
}
