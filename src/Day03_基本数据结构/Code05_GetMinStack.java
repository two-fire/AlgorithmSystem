package Day03_基本数据结构;

import java.util.Stack;

/**
 * @Author : LiuYan
 * @create 2021/3/26 20:40
 * 实现一个特殊的栈，在基本功能的基础上，再实现返回栈中最小元素的功能
 * 1）pop、push、getMin操作的时间复杂度都是 O(1)。
 * 2）设计的栈类型可以使用现成的栈结构。
 */
public class Code05_GetMinStack {
    public static class MyStack {
        private Stack<Integer> stack;
        private Stack<Integer> minStack;
        private int min;
        public MyStack() {
            stack = new Stack<>();
            minStack = new Stack<>();
            min = Integer.MAX_VALUE;
        }
        private void push(int value) {
            stack.push(value);
            min = Math.min(value, min);
            minStack.push(min);
        }
        private int pop() {
            if (minStack.isEmpty()){
                throw new RuntimeException("栈空！");
            }
            minStack.pop();
            return stack.pop();
        }
        private int getMin() {
            if (minStack.isEmpty()){
                throw new RuntimeException("栈空！");
            }
            return minStack.peek();
        }
    }

    public static void main(String[] args) {
        MyStack stack1 = new MyStack();
        stack1.push(2);
        stack1.push(3);
        stack1.push(1);
        System.out.println(stack1.getMin());
        System.out.println(stack1.pop());
        System.out.println(stack1.getMin());
    }
}
