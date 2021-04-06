package Day10_二叉树基本算法;

/**
 * @Author : LiuYan
 * @create 2021/3/31 13:33
 * 递归遍历二叉树
 * 先序：任何子树的处理顺序都是，先头、再左、然后右
 * 中序：任何子树的处理顺序都是，先左、再头、然后右
 * 后序：任何子树的处理顺序都是，先左、再右、然后头
 */
public class Code01_RecursiveTraversalBT {
    public static class Node {
        int value;
        Node left;
        Node right;
        public Node(int v) {
            value = v;
        }
    }
    // 先序
    public static void pre(Node root) {
        if (root == null) {
            return;
        }
        Node node = root;
        System.out.println(node.value);
        pre(node.left);
        pre(node.right);
    }
    // 中序
    public static void in(Node root) {
        if (root == null) {
            return;
        }
        Node node = root;
        in(node.left);
        System.out.println(node.value);
        in(node.right);
    }
    // 后序
    public static void pos(Node root) {
        if (root == null) {
            return;
        }
        Node node = root;
        pos(node.left);
        pos(node.right);
        System.out.println(node.value);
    }
    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);

        pre(head);
        System.out.println("========");
        in(head);
        System.out.println("========");
        pos(head);
        System.out.println("========");
    }
}
