package Day11_二叉树递归套路;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Author : LiuYan
 * @create 2021/4/2 15:37
 * 判断二叉树是否是完全二叉树
 * 会在后面的题目中用二叉树的递归套路来解这个题
 *
 * 思路：按层遍历，直到遇到没有右孩子的那一个节点之后，后面全是叶子节点。则是CBT
 *      x左树满，右树满，高度相同或者左树比右树大1
 *      x左树满，右树完全，高度相同
 *      x左树完全，右树满，左树高度比右树大1
 */
public class Code01_IsCBT {
    public static class Node {
        int value;
        Node left;
        Node right;
        public Node(int val) {
            value = val;
        }
    }
    public static boolean isCBT(Node head) {
        if (head == null) {
            return true;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        Node cur = null;
        while (!queue.isEmpty()) {
            cur = queue.poll();
            if (cur.left != null) {
                queue.add(cur.left);
            }
            if (cur.right != null) {
                queue.add(cur.right);
            }
            if (cur.right == null) {
                while (!queue.isEmpty()) {
                    cur = queue.poll();
                    if (cur.left != null || cur.right != null) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean isCBT2(Node head) {
        return process(head).isCBT;
    }
    public static class Info {
        private boolean isFull;
        private boolean isCBT;
        private int height;
        public Info(boolean f, boolean c, int h) {
            isFull = f;
            isCBT = c;
            height = h;
        }
    }
    private static Info process(Node x) {
        if (x == null) {
            return new Info(true, true, 0);
        }
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        boolean isFull = false;
        boolean isCBT = false;
        if (leftInfo.isFull && rightInfo.isFull) {
            if (leftInfo.height - rightInfo.height == 1) {
                isCBT = true;
            }
            if (leftInfo.height == rightInfo.height) {
                isFull = true;
                isCBT = true;
            }
        }
        if (leftInfo.isCBT && rightInfo.isFull && leftInfo.height - rightInfo.height == 1) {
            isCBT = true;
        }
        if (leftInfo.isFull && rightInfo.isCBT && leftInfo.height == rightInfo.height) {
            isCBT = true;
        }
        return new Info(isFull, isCBT, height);
    }
    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);

        System.out.println(isCBT(head));
    }
}
