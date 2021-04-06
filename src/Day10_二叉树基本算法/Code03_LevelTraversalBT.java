package Day10_二叉树基本算法;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @Author : LiuYan
 * @create 2021/3/31 16:11
 * 实现二叉树的按层遍历
 *  1）其实就是宽度优先遍历，用队列
 *  2）可以通过设置flag变量的方式，来发现某一层的结束（看题目）
 */
public class Code03_LevelTraversalBT {
    public static class Node {
        int value;
        Node left;
        Node right;
        public Node(int v) {
            value = v;
        }
    }
    public static void level(Node root) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        Node node = root;
        while (!queue.isEmpty()) {
            node = queue.poll();
            System.out.println(node.value);
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
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

        level(head);

    }
}
