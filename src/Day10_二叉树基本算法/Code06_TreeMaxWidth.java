package Day10_二叉树基本算法;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Author : LiuYan
 * @create 2021/4/1 9:59
 * 求二叉树最宽的层有多少个节点
 */
public class Code06_TreeMaxWidth {
    public static class Node {
        int val;
        Node left;
        Node right;
        public Node(int v) {
            val = v;
        }
    }
    public static int maxWidth1(Node root) {
        if (root == null) {
            return 0;
        }
        int max = 1;
        int size = 1;
        int curSize = 0;  // 记录下一层的节点个数
        Node cur = null;
        Queue<Node> queue = new LinkedList<>(); // 辅助按层遍历
        queue.add(root);
        while (!queue.isEmpty()) {
            while (size-- > 0) {
                cur = queue.poll();
                if (cur.left != null) {
                    queue.add(cur.left);
                    curSize++;
                }
                if (cur.right != null) {
                    queue.add(cur.right);
                    curSize++;
                }
            }
            size = curSize;
            max = Math.max(max, size);
            curSize = 0;
        }
        return max;
    }

    public static int maxWidthNoMap(Node head) {
       if (head == null) {
           return 0;
       }
       int max = 1;
       int curLevelNode = 0; // 当前层的节点数
       Node curEnd = head;  // 当前层，最右节点是谁
       Node nextEnd = null; // 下一层，最右节点是谁
       Node cur = head;
       Queue<Node> queue = new LinkedList<>();
       queue.add(head);
        while (!queue.isEmpty()) {
            cur = queue.poll();
            if (cur.left != null) {
                queue.add(cur.left);
                curLevelNode++;
                nextEnd = cur.left;
            }
            if (cur.right != null) {
                queue.add(cur.right);
                curLevelNode++;
                nextEnd = cur.right;
            }
            if (cur == curEnd) {
                max = Math.max(max, curLevelNode);
                curLevelNode = 0;
                curEnd = nextEnd;
            }
        }
       return max;
    }
    // for test
    private static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }
    // for test
    private static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int)(Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static void main(String[] args) {
        int maxLevel = 10;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (maxWidth1(head) != maxWidthNoMap(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");

    }


}
