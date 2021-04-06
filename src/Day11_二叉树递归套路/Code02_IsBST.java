package Day11_二叉树递归套路;

import java.util.ArrayList;

/**
 * @Author : LiuYan
 * @create 2021/4/2 15:54
 * 二叉树的递归套路深度实践
 *   判断二叉树是否是搜索二叉树 （左树值小于x，右树值大于x）
 * 思路：
 *   1. x左树是BST  info：左树的最大值
 *   2. x右树是BST  info：右树的最小值
 *   所以向x子树要信息：最大值，最小值，是否是BST
 */
public class Code02_IsBST {
    public static class Node {
        int value;
        Node left;
        Node right;
        public Node(int val) {
            value = val;
        }
    }
    // 中序遍历，递增就是BST
    public static boolean isBST1(Node head) {
        if (head == null) {
            return true;
        }
        ArrayList<Node> list = new ArrayList<>();
        in(head, list);
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).value >= list.get(i + 1).value) {
                return false;
            }
        }
        return true;
    }

    private static void in(Node head, ArrayList<Node> list) {
        if (head == null) {
            return;
        }
        in(head.left, list);
        list.add(head);
        in(head.right, list);
    }

    public static boolean isBST2(Node head) {
        if (head == null) {
            return true;
        }
        return process(head).isBST;
    }
    public static class Info {
        boolean isBST;
        int max;
        int min;
        public Info(int ma, int mi, boolean i) {
            max = ma;
            min = mi;
            isBST = i;
        }
    }
    // 加工信息
    public static Info process(Node x) {
        if (x == null) {  // x是空，它的最小值最大值是什么不好设置
            return null;
        }
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);

        int max = x.value;
        if (x.left != null) {
            max = Math.max(max, leftInfo.max);
        }
        if (x.right != null) {
            max = Math.max(max, rightInfo.max);
        }
        int min = x.value;
        if (x.left != null) {
            min = Math.min(min, leftInfo.min);
        }
        if (x.right != null) {
            min = Math.min(min, rightInfo.min);
        }
        boolean isBST = true;
        if (x.left != null && !leftInfo.isBST) {
            isBST = false;
        }
        if (x.right != null && !rightInfo.isBST) {
            isBST = false;
        }

        if (x.left != null && leftInfo.max >= x.value) {
            isBST = false;
        }
        if (x.right != null && rightInfo.min <= x.value) {
            isBST = false;
        }
        return new Info(max, min, isBST);
    }
    // for test
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (isBST1(head) != isBST2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}
