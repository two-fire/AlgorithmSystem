package Day11_二叉树递归套路;

/**
 * @Author : LiuYan
 * @create 2021/4/2 16:37
 * 给定一棵二叉树的头节点head，返回这颗二叉树是不是平衡二叉树
 * 思路：
 *  1. x的左树是否平衡，还有高度
 *  2. x的右树是否平衡，还有高度
 *  总之，需要向子树要信息: 是否平衡，高度
 */
public class Code03_isBalanced {
    public static class Node {
        int value;
        Node left;
        Node right;
        public Node(int val) {
            value = val;
        }
    }

    public static boolean isBalanced1(Node head) {
        boolean[] isBalanced = new boolean[1];
        isBalanced[0] = true;
        process1(head, isBalanced);
        return isBalanced[0];
    }

    private static int process1(Node head, boolean[] isBalanced) {
        if (!isBalanced[0] || head == null) {
            return -1;
        }
        int leftHeight = process1(head.left, isBalanced);
        int rightHeight = process1(head.right, isBalanced);
        if (Math.abs(leftHeight - rightHeight) > 1) {
            isBalanced[0] = false;
        }
        return Math.max(leftHeight, rightHeight) + 1;
    }


    public static boolean isBalanced2(Node head) {
        return process(head).isBalanced;
    }
    private static Info process(Node x) {
        if (x == null) {
            return new Info(true, 0);
        }
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;

        boolean isBalanced = leftInfo.isBalanced && rightInfo.isBalanced;
        if (Math.abs(leftInfo.height-rightInfo.height) > 1) {
            isBalanced = false;
        }
        return new Info(isBalanced, height);
    }
    public static class Info {
        boolean isBalanced;
        int height;
        public Info(boolean i, int h) {
            isBalanced = i;
            height = h;
        }
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
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (isBalanced1(head) != isBalanced2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}
