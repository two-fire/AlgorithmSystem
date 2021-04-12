package Day15_暴力递归;

import java.util.Stack;

/**
 * @Author : LiuYan
 * @create 2021/4/10 9:04
 * 给你一个栈，请你逆序这个栈，
 * 不能申请额外的数据结构，
 * 只能使用递归函数。 如何实现?
 *  调用f函数，每次得到栈底元素，并移除。栈为空时，开始从栈顶往栈中加入。实现逆序。
 */
public class Code04_ReverseStackUsingRecursive {
    public static void reverse(Stack<Integer> stack) {
        if (stack.isEmpty()) {
            return;
        }
        int last = f(stack);
        reverse(stack);
        stack.push(last);
    }

    // 最底层元素移除，上面的元素盖下去，返回移除的栈底元素
    public static int f(Stack<Integer> stack) {
        if (stack.size() == 1) { // 栈底了，把元素打包向上传
            return stack.pop();
        }
        int top = stack.pop(); // 得到当前栈顶元素
        int last = f(stack); // 获得栈底元素
        stack.push(top);
        return last;
    }
    public static void main(String[] args) {
        Stack<Integer> test = new Stack<Integer>();
        test.push(1);
        test.push(2);
        test.push(3);
        test.push(4);
        test.push(5);
        reverse(test);
        while (!test.isEmpty()) {
            System.out.println(test.pop());
        }

    }
}

