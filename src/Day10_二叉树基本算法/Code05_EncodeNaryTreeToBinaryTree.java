package Day10_二叉树基本算法;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : LiuYan
 * @create 2021/3/31 21:37
 * LeetCode 431. Encode N-ary Tree to Binary Tree
 * 多叉树 用某种二叉树的形式来表示，然后就认为这颗二叉树是这棵多叉树的序列化结果
 * 二叉树：多叉树中x节点的子节点放在二叉树x节点的左右子树上。
 */
public class Code05_EncodeNaryTreeToBinaryTree {
    public static class Node {
        public int value;
        public List<Node> children;
        public Node() {}
        public Node(int v) {
            value = v;
        }
        public Node(int v, List<Node> ch) {
            value = v;
            children = ch;
        }
    }
    public static class TreeNode {
        public int value;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { value = x; }
    }
    // 转二叉树
    public static TreeNode encode(Node head) {
        if (head == null) {
            return null;
        }
        TreeNode tHead = new TreeNode(head.value);
        tHead.left = en(head.children);
        return tHead;
    }
    private static TreeNode en(List<Node> children) {
        TreeNode head = null;
        TreeNode cur = null;
        for (Node node : children) {
            cur = new TreeNode(node.value);
            // 第一个孩子放到二叉树中x的左树上
            if (node == null) {
                head = cur;
            } else { // 其他的放到左树的右树上
                head.right = cur;
            }
            head = head.right;
            cur.left = en(node.children);
        }
        return head;
    }

    // 二叉树转为多叉树
    public static Node decode(TreeNode head) {
        if (head == null) {
            return null;
        }
        return new Node(head.value, de(head.left));
    }
    private static List<Node> de(TreeNode leftRoot) {
        List<Node> list = new ArrayList<>();
        TreeNode cur = leftRoot;
        Node ch = null;
        while (cur != null) {
            ch = new Node(cur.value, de(cur.left));
            list.add(ch);
            cur = cur.right;
        }
        return list;
    }
}
