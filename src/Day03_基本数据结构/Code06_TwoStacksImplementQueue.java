package Day03_基本数据结构;

import java.util.Stack;

/**
 * @Author : LiuYan
 * @create 2021/3/26 20:52
 * 如何用栈结构实现队列结构
 * 两个栈来实现
 * 两个原则：
 * 要取数据的时候，pop栈为空才需要从push栈倒数据；如果要倒，每次必须一次性倒完
 */
public class Code06_TwoStacksImplementQueue {
     public static class MyQueue {
         public Stack<Integer> stackPush;
         public Stack<Integer> stackPop;
         public MyQueue() {
             stackPush = new Stack<>();
             stackPop = new Stack<>();
         }
         // stack1向stack2倒数据
         public void pushData() {
             if (stackPop.isEmpty()) {
                 while (!stackPush.isEmpty()) {
                     stackPop.push(stackPush.pop());
                 }
             }
         }

         public void add(int value) {
             stackPush.push(value);
         }
         // 全部倒到stack2再取出一个
         public int pop() {
             if (stackPush.isEmpty() && stackPop.isEmpty()) {
                 throw new RuntimeException("栈空！");
             }
            pushData();
            return stackPop.pop();
         }
         public int peek() {
             if (stackPush.isEmpty() && stackPop.isEmpty()) {
                 throw new RuntimeException("栈空！");
             }
            pushData();
            return stackPop.peek();
         }
     }
}
