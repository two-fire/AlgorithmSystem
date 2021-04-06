package Day11_二叉树递归套路;

import java.util.ArrayList;

/**
 * @Author : LiuYan
 * @create 2021/4/4 14:18
 * 给定一棵二叉树的头节点head，
 * 返回这颗二叉树中最大的二叉搜索子树的头节点
 * 思路:
 *      maxBST在左树
 *      maxBST在右树
 *      maxBST以x为头
 *      总之，需要：子树的最大值最小值，子树是否是搜索二叉树，子树的最大搜索二叉树的头节点
 */
public class Code07_MaxSubBSTHead {
    public static class Node {
        int value;
        Node left;
        Node right;
        public Node(int val) {
            value = val;
        }
    }
    public static int getBSTSize(Node head) {
        if (head == null) {
            return 0;
        }
        ArrayList<Node> arr = new ArrayList<>();
        in(head, arr);
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i).value <= arr.get(i - 1).value) {
                return 0;
            }
        }
        return arr.size();
    }

    public static void in(Node head, ArrayList<Node> arr) {
        if (head == null) {
            return;
        }
        in(head.left, arr);
        arr.add(head);
        in(head.right, arr);
    }

    public static Node maxSubBSTHead1(Node head) {
        if (head == null) {
            return null;
        }
        if (getBSTSize(head) > 0) {
            return head;
        }
        Node left = maxSubBSTHead1(head.left);
        Node right = maxSubBSTHead1(head.right);
        return getBSTSize(left) >= getBSTSize(right) ? left : right;

    }
    public static Node maxSubBSTHead2(Node head) {
        if (head == null) {
            return null;
        }
        return process(head).maxBSTHead;
    }
    public static class Info {
        int max;
        int min;
        int maxBSTSize;
        Node maxBSTHead;
        public Info(int ma, int mi, Node h, int m) {
            max = ma;
            min = mi;
            maxBSTHead = h;
            maxBSTSize = m;
        }
    }
    public static Info process(Node x) {
        if (x == null) {
            return null;
        }
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);
        int max = x.value;
        int min = x.value;
        boolean leftBST = false;
        int s1 = 0; // maxBST在左树上，size
        if (leftInfo != null) {
            max = Math.max(max, leftInfo.max);
            min = Math.min(min, leftInfo.min);
            if (leftInfo.maxBSTHead == x.left) {
                leftBST = true;
            }
            s1 = leftInfo.maxBSTSize;
        } else {
            leftBST = true;
        }
        boolean rightBST = false;
        int s2 = 0; // maxBST在右树上，size
        if (rightInfo != null) {
            max = Math.max(max, rightInfo.max);
            min = Math.min(min, rightInfo.min);
            if (rightInfo.maxBSTHead == x.right) {
                rightBST = true;
            }
            s2 = rightInfo.maxBSTSize;
        } else {
            rightBST =true;
        }
        int s3 = 0;
       // 左右子树都是二叉搜索子树
        if (leftBST && rightBST) {
            boolean leftMaxLessX = leftInfo == null ? true : (leftInfo.max < x.value);
            boolean rightMinMoreX = rightInfo == null ? true : (rightInfo.min > x.value);
            if (leftMaxLessX && rightMinMoreX) {
               s3 = s1 + s2 + 1;
            }
        }
        int maxBSTSize = Math.max(s1, Math.max(s2, s3));
        Node maxBSTHead = null;
        if (maxBSTSize == s1) {
            maxBSTHead = leftInfo.maxBSTHead;
        } else if (maxBSTSize == s2) {
            maxBSTHead = rightInfo.maxBSTHead;
        } else {
            maxBSTHead = x;
        }
        return new Info(max, min, maxBSTHead, maxBSTSize);

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
            if (maxSubBSTHead1(head) != maxSubBSTHead2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
