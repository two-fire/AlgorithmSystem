package Day10_二叉树基本算法;

/**
 * @Author : LiuYan
 * @create 2021/4/2 10:34
 * 二叉树结构如下定义：
 * Class Node {
 * 	V value;
 * 	Node left;
 * 	Node right;
 * 	Node parent;
 * }
 * 给你二叉树中的某个节点，返回该节点的后继节点
 *
 * 思路：如果有右树，后继就是右树最左孩子；如果没有右树，向左上寻找直到没有，然后右上的父亲就是后继节点
 */

public class Code07_SuccessorNode {
    public static class Node {
        int value;
        Node left;
        Node right;
        Node parent;
        public Node(int v) {
            value = v;
        }
    }
    // 返回node节点的后继节点
    public static Node getSuccessorNode(Node node) {
        if (node == null) {
            return null;
        }
        if (node.right != null) { // 有右树
            return getLeftMost(node.right);
        } else { // 没有右树
            Node parent = node.parent;
            while (parent != null && node == parent.right) {
                node = parent;
                parent = node.parent;
            }
            return parent;
        }

    }
    // 返回node的最左孩子 (node不为null)
    private static Node getLeftMost(Node node) {
        while (node != null && node.left != null) {
            node = node.left;
        }
        return node;
    }
    public static void main(String[] args) {
        Node head = new Node(6);
        head.parent = null;
        head.left = new Node(3);
        head.left.parent = head;
        head.left.left = new Node(1);
        head.left.left.parent = head.left;
        head.left.left.right = new Node(2);
        head.left.left.right.parent = head.left.left;
        head.left.right = new Node(4);
        head.left.right.parent = head.left;
        head.left.right.right = new Node(5);
        head.left.right.right.parent = head.left.right;
        head.right = new Node(9);
        head.right.parent = head;
        head.right.left = new Node(8);
        head.right.left.parent = head.right;
        head.right.left.left = new Node(7);
        head.right.left.left.parent = head.right.left;
        head.right.right = new Node(10);
        head.right.right.parent = head.right;

        Node test = head.left.left;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.left.left.right;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.left;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.left.right;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.left.right.right;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.right.left.left;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.right.left;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.right;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.right.right; // 10's next is null
        System.out.println(test.value + " next: " + getSuccessorNode(test));
    }
}
