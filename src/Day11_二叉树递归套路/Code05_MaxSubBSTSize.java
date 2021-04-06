package Day11_二叉树递归套路;

import java.util.ArrayList;

/**
 * @Author : LiuYan
 * @create 2021/4/4 9:29
 * 给定一棵二叉树的头节点head，
 * 返回这颗二叉树中最大的二叉搜索子树的大小
 * 思路：
 *      x不做头：x左 maxSubBSTSize；x右 maxSubBSTSize
 *      x做头：x左 isBST；x右 isBST；x左 max；x右 min；x左 size；x右 size
 *      总之，x需要向子树要：子树是否是BST，子树大小size，最小值，最大值，
 *      子树的最大二叉搜索子树的大小maxBSTSize
 */
public class Code05_MaxSubBSTSize {
    public static class Node {
        int value;
        Node left;
        Node right;
        public Node(int val) {
            value = val;
        }
    }
    public static int maxSubBSTSize1(Node head) {
        if (head == null) {
            return 0;
        }
        int s = getBSTSize(head);
        if (s != 0) {
            return s;
        }
        return Math.max(maxSubBSTSize1(head.left), maxSubBSTSize1(head.right));
    }
    private static int getBSTSize(Node head) {
        if (head == null) {
            return 0;
        }
        ArrayList<Node> arr = new ArrayList<>();
        in(head, arr);
        for (int i = 0; i < arr.size() - 1; i++) {
            if (arr.get(i).value >= arr.get(i + 1).value) {
                return 0;
            }
        }
        return arr.size();
    }
    private static void in(Node head, ArrayList<Node> arr) {
        if (head == null) {
            return;
        }
        in(head.left, arr);
        arr.add(head);
        in(head.right, arr);
    }

    public static int maxSubBSTSize2(Node head) {
        if (head == null) {
            return 0;
        }
        return process(head).maxBSTSize;
    }
    public static class Info {
        private int maxBSTSize;
        private int allSize;
        private int max;
        private int min;
        public Info(int ms, int s, int ma, int mi) {
            maxBSTSize = ms;
            allSize = s;
            max = ma;
            min = mi;
        }
    }
    public static Info process(Node head) {
        if (head == null) {
            return null;
        }
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        int allSize = 1;
        int max = head.value;
        int min = head.value;
        if (leftInfo != null) {
            max = Math.max(max, leftInfo.max);
            min = Math.min(min, leftInfo.min);
            allSize += leftInfo.allSize;
        }
        if (rightInfo != null) {
            max = Math.max(max, rightInfo.max);
            min = Math.min(min, rightInfo.min);
            allSize += rightInfo.allSize;
        }
        int p1 = -1;
        if (leftInfo != null) {
            p1 = leftInfo.maxBSTSize;
        }
        int p2 = -1;
        if (rightInfo != null) {
            p2 = rightInfo.maxBSTSize;
        }
        int p3 = -1;
        boolean leftBST = leftInfo == null ? true : leftInfo.maxBSTSize == leftInfo.allSize;
        boolean rightBST = rightInfo == null ? true : rightInfo.maxBSTSize == rightInfo.allSize;
        if (leftBST && rightBST) {
            boolean leftMaxLessX = (leftInfo == null) ? true : (leftInfo.max < head.value);
            boolean rightMinMoreX = (rightInfo == null) ? true : (rightInfo.min > head.value);
            if (leftMaxLessX && rightMinMoreX) {
                int leftSize = leftInfo == null ? 0 : leftInfo.maxBSTSize;
                int rightSize = rightInfo == null ? 0 : rightInfo.maxBSTSize;
                p3 = leftSize + rightSize + 1;
            }
        }
        return new Info(Math.max(p1, Math.max(p2, p3)), allSize, max, min);
    }
    // for test
    public static Node generateRandomBST(int maxLevel, int maxValue){
        return generate(1, maxLevel, maxValue);
    }
    public static Node generate(int level, int maxLevel, int maxValue){
        if (Math.random() < 0.5 || level > maxLevel) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }
    public static void main(String[] args) {
        int testTime = 10000;
        int maxValue = 100;
        int maxLevel = 4;
        for (int i = 0; i < testTime; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (maxSubBSTSize1(head) != maxSubBSTSize2(head)) {
                throw new RuntimeException("error!");
            }
        }
        System.out.println("success!");
    }
}
