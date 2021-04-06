package Day10_二叉树基本算法;

import java.util.Stack;

/**
 * @Author : LiuYan
 * @create 2021/3/31 13:45
 * 非递归方式实现二叉树的先序、中序、后序遍历
 *  1）任何递归函数都可以改成非递归
 *  2）自己设计压栈的来实现
 *
 *  先序：A X …
 *  后续：… X B
 *  A部分 ∩ B部分 就是x的祖先节点
 */
public class Code02_UnRecursiveTraversalBT {
    public static class Node {
        int value;
        Node left;
        Node right;
        public Node(int v) {
            value = v;
        }
    }
    // 先序
    // 有右压右，有左压左
    public static void pre(Node root) {
        if (root == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        stack.add(root);
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            System.out.println(node.value);
            if (node.right != null) {
                stack.add(node.right);
            }
            if (node.left != null) {
                stack.add(node.left);
            }
        }
    }
    // 中序
    // 1）整条左边界进栈，直到null，转2）
    // 2）栈中弹出节点打印，然后把当前节点变为右孩子，转1）
    // 3）栈空停
    public static void in(Node root) {
        if (root == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        Node node = stack.peek();
        while (!stack.isEmpty()) {
            while (node!= null && node.left != null) {
                stack.push(node.left);
                node = node.left;
            }
            System.out.println(stack.peek().value);
            node = stack.pop().right;
            if (node != null) {
               stack.push(node);
            }
        }
    }
    // 后序
    // 头 右 左 => 再压栈
    public static void pos(Node root) {
        if (root == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        Stack<Node> help = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            help.push(node);
            if (node.left != null) {
                stack.push(node.left);
            }
            if (node.right != null) {
                stack.push(node.right);
            }
        }
        while (!help.isEmpty()) {
            System.out.println(help.pop().value);
        }
    }
    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);

        pre(head);
        System.out.println("========");
        in(head);
        System.out.println("========");
        pos(head);
        System.out.println("========");

    }
}
