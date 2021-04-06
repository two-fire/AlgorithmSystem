package Day11_二叉树递归套路;

/**
 * @Author : LiuYan
 * @create 2021/4/2 16:37
 * 给定一棵二叉树的头节点head，返回这颗二叉树是不是满二叉树
 *
 * 思路：x向左右子树要信息：
 *      是否是满二叉树（可以由后面两个拼出），左树高度和右树高度，左树节点数和右树节点树
 */
public class Code04_isFull {
    public static class Node {
        int value;
        Node left;
        Node right;
        public Node(int val) {
            value = val;
        }
    }

    public static boolean isFull1(Node head) {
        if (head == null) {
            return true;
        }
        int height = h(head);
        int node = n(head);
        return (1 << height) - 1 == node;
    }
    private static int h(Node head) {
        if (head == null) {
            return 0;
        }
        return Math.max(h(head.left), h(head.right)) + 1;
    }
    private static int n(Node head) {
        if (head == null) {
            return 0;
        }
        return n(head.left) + n(head.right) + 1;
    }

    public static boolean isFull2(Node head) {
        return process(head).isFull;
    }

    public static class Info {
        private int height;
        private int node;
        private boolean isFull;
        public Info(int h, int n, boolean i) {
            height = h;
            node = n;
            isFull = i;
        }
    }
    public static Info process(Node head) {
        if (head == null) {
            return new Info(0, 0, true);
        }
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        int node = leftInfo.node + rightInfo.node + 1;
        boolean isFull = leftInfo.isFull && rightInfo.isFull;
        if (leftInfo.height != rightInfo.height) {
            isFull = false;
        }
        if (leftInfo.node != rightInfo.node) {
            isFull = false;
        }
        return new Info(height, node, isFull);
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
            if (isFull1(head) != isFull2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }


}
