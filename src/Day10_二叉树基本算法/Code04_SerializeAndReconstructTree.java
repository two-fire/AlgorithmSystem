package Day10_二叉树基本算法;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @Author : LiuYan
 * @create 2021/3/31 16:37
 * 实现二叉树的序列化和反序列化
 * 1）先序方式序列化和反序列化
 * 2）后序方式序列化和反序列化
 * 3）按层方式序列化和反序列化
 *
 * 二叉树无法通过中序遍历的方式实现序列化和反序列化!!
 */
public class Code04_SerializeAndReconstructTree {
    public static class Node {
        int value;
        Node left;
        Node right;
        public Node(int v) {
            value = v;
        }
    }
    // 先序方式序列化
    public static Queue<String> preSerial(Node root) {
        Queue<String> ans = new LinkedList<>();
        pres(root, ans);
        return ans;
    }
    private static void pres(Node root, Queue<String> ans) {
        if (root == null) {
            ans.add(null);
        } else {
            ans.add(String.valueOf(root.value));
            pres(root.left, ans);
            pres(root.right, ans);
        }
    }
    // 先序方式反序列化
    public static Node buildByPreQueue(Queue<String> queue) {
        if (queue == null || queue.size() == 0) {
            return null;
        }
        return buildPre(queue);
    }
    private static Node buildPre(Queue<String> queue) {
        String s = queue.poll();
        if (s == null) {
            return null;
        }
        Node head = new Node(Integer.valueOf(s));
        head.left = buildPre(queue);
        head.right = buildPre(queue);
        return head;
    }

    // 后序方式序列化
    public static Queue<String> posSerial(Node root) {
        Queue<String> ans = new LinkedList<>();
        poss(root, ans);
        return ans;
    }
    private static void poss(Node root, Queue<String> ans) {
        if (root == null) {
            ans.add(null);
        } else {
            poss(root.left, ans);
            poss(root.right, ans);
            ans.add(String.valueOf(root.value));
        }
    }
    // 后序方式反序列化
    public static Node buildByPosQueue(Queue<String> queue) {
        if (queue == null || queue.size() == 0) {
            return null;
        }
        // 左右中   stack（中右左）
        Stack<String> stack = new Stack<>();
        while (!queue.isEmpty()) {
            stack.push(queue.poll());
        }
        return buildPos(stack);
    }
    private static Node buildPos(Stack<String> stack) {
        String s = stack.pop();
        if (s == null) {
            return null;
        }
        Node head = new Node(Integer.parseInt(s));
        head.right = buildPos(stack);
        head.left = buildPos(stack);
        return head;
    }
    // 按层方式序列化
    public static Queue<String> levelSerial(Node root) {
        Queue<String> ans = new LinkedList<>();
        if (root == null) {
            ans.add(null);
        } else {
            ans.add(String.valueOf(root.value));
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);
            while (!queue.isEmpty()) {
                Node cur = queue.poll();
                if (cur.left != null) {
                    queue.add(cur.left);
                    ans.add(String.valueOf(cur.left.value));
                } else {
                    ans.add(null);
                }
                if (cur.right != null) {
                    queue.add(cur.right);
                    ans.add(String.valueOf(cur.right.value));
                } else {
                    ans.add(null);
                }
            }
        }
        return ans;
     }
    // 按层方式反序列化
    public static Node buildByLevelQueue(Queue<String> levelList) {
        if (levelList == null || levelList.size() == 0) {
            return null;
        }
        Node head = generateNode(levelList.poll());
        Queue<Node> queue = new LinkedList<>();
        if (head != null) {
            queue.add(head);
        }
        Node node = null;
        while (!queue.isEmpty()) {
            node = queue.poll();
            node.left = generateNode(levelList.poll());
            node.right = generateNode(levelList.poll());
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
        return head;
    }
    // 由一个字串建出一个Node
    private static Node generateNode(String s) {
        if (s == null) {
            return null;
        } else {
            return new Node(Integer.parseInt(s));
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

    // for test
    public static boolean isSameValueStructure(Node head1, Node head2) {
        if (head1 == null && head2 != null) {
            return false;
        }
        if (head1 != null && head2 == null) {
            return false;
        }
        if (head1 == null && head2 == null) {
            return true;
        }
        if (head1.value != head2.value) {
            return false;
        }
        return isSameValueStructure(head1.left, head2.left) && isSameValueStructure(head1.right, head2.right);
    }

    // for test
    public static void printTree(Node head) {
        System.out.println("Binary Tree:");
        printInOrder(head, 0, "H", 17);
        System.out.println();
    }

    public static void printInOrder(Node head, int height, String to, int len) {
        if (head == null) {
            return;
        }
        printInOrder(head.right, height + 1, "v", len);
        String val = to + head.value + to;
        int lenM = val.length();
        int lenL = (len - lenM) / 2;
        int lenR = len - lenM - lenL;
        val = getSpace(lenL) + val + getSpace(lenR);
        System.out.println(getSpace(height * len) + val);
        printInOrder(head.left, height + 1, "^", len);
    }

    public static String getSpace(int num) {
        String space = " ";
        StringBuffer buf = new StringBuffer("");
        for (int i = 0; i < num; i++) {
            buf.append(space);
        }
        return buf.toString();
    }
     public static void main(String[] args) {
         int maxLevel = 5;
         int maxValue = 100;
         int testTimes = 1000000;
         System.out.println("test begin");
         for (int i = 0; i < testTimes; i++) {
             Node head = generateRandomBST(maxLevel, maxValue);
             Queue<String> pre = preSerial(head);
             Queue<String> pos = posSerial(head);
             Queue<String> level = levelSerial(head);
             Node preBuild = buildByPreQueue(pre);
             Node posBuild = buildByPosQueue(pos);
             Node levelBuild = buildByLevelQueue(level);
             if (!isSameValueStructure(preBuild, posBuild) || !isSameValueStructure(posBuild, levelBuild)) {
                 System.out.println("Oops!");
                 printTree(preBuild);
                 printTree(posBuild);
                 printTree(levelBuild);
                 break;
             }
         }
         System.out.println("test finish!");
     }
}
