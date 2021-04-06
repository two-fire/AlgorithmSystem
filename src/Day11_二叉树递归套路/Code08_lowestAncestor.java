package Day11_二叉树递归套路;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @Author : LiuYan
 * @create 2021/4/4 14:18
 * 给定一棵二叉树的头节点head，和另外两个节点a和b。
 * 返回a和b的最低公共祖先
 * 思路：
 *  生成一张反向索引表map，里面记录值和它的父亲节点。a的祖先全放到set中，b向上跳，如果出现在set中就找到
 *
 *  a，b都在同一侧子树上
 *  a，b分别在左右子树
 */
public class Code08_lowestAncestor {
    public static class Node {
        int value;
        Node left;
        Node right;
        public Node(int val) {
            value = val;
        }
    }

    public static Node lowestAncestor1(Node head, Node a, Node b) {
        if (head == null) {
            return null;
        }
        HashMap<Node, Node> map = new HashMap<>();
        map.put(head, null);
        in(head, map);
        HashSet<Node> set = new HashSet<>();
        while (a != null) {
            set.add(a);
            a = map.get(a);
        }
        while (b != null) {
            if (set.contains(b)) {
                return b;
            }
            b = map.get(b);
        }
        return null;
    }
    private static void in(Node head, HashMap<Node, Node> map) {
        if (head == null) {
            return;
        }
        in(head.left, map);
        if (head.left != null) {
            map.put(head.left, head);
        }
        if (head.right != null) {
            map.put(head.right, head);
        }
        in(head.right, map);
    }

    public static Node lowestAncestor2(Node head, Node a, Node b) {
        return process(head, a, b).ancestor;
    }

    public static class Info {
        private Node ancestor;
        private boolean findA;
        private boolean findB;
        public Info(Node ancestor, boolean fa, boolean fb) {
            this.ancestor = ancestor;
            findA = fa;
            findB = fb;
        }
    }

    public static Info process(Node x, Node a, Node b) {
        if (x == null) {
            return new Info(null, false, false);
        }
        Info leftInfo = process(x.left, a, b);
        Info rightInfo = process(x.right, a, b);
        boolean findA = leftInfo.findA || rightInfo.findA || x == a;
        boolean findB = leftInfo.findB || rightInfo.findB || x == b;
        Node ancestor = null;
        if (leftInfo.ancestor != null) {
            ancestor = leftInfo.ancestor;
        } else if (rightInfo.ancestor != null) {
            ancestor = rightInfo.ancestor;
        } else {
            if (findA && findB) {
                ancestor = x;
            }
        }
        return new Info(ancestor, findA, findB);
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
    public static Node pickRandomOne(Node head) {
        if (head == null) {
            return null;
        }
        ArrayList<Node> arr = new ArrayList<>();
        fillPrelist(head, arr);
        int randomIndex = (int) (Math.random() * arr.size());
        return arr.get(randomIndex);
    }

    // for test
    public static void fillPrelist(Node head, ArrayList<Node> arr) {
        if (head == null) {
            return;
        }
        arr.add(head);
        fillPrelist(head.left, arr);
        fillPrelist(head.right, arr);
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            Node o1 = pickRandomOne(head);
            Node o2 = pickRandomOne(head);
            if (lowestAncestor1(head, o1, o2) != lowestAncestor2(head, o1, o2)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}
