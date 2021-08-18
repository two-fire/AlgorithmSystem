package Day20_KMP算法;

import java.util.ArrayList;

/**
 * @Author : LiuYan
 * @create 2021/8/18 7:58
 * 两棵树，tree1是否有某一个子树和tree2的结构一模一样
 * 方法一：先序方式把两棵树都序列化为字符串，用kmp比对 O(N)
 */
public class Code03_TreeEqual {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }
    public static boolean containsTree1(Node big, Node small) {
        if (big == null) {
            return false;
        }
        if (small == null) {
            return true;
        }
        // 先序方式序列化两棵树
        ArrayList<String> preBig = preSerial(big);
        ArrayList<String> preSmall = preSerial(small);
        String[] b = new String[preBig.size()];
        String[] s = new String[preSmall.size()];
        for (int i = 0; i < b.length; i++) {
            b[i] = preBig.get(i);
        }
        for (int i = 0; i < s.length; i++) {
            s[i] = preSmall.get(i);
        }
        return getIndexOf(b, s) != -1;
    }

    private static ArrayList<String> preSerial(Node node) {
        ArrayList<String> ans = new ArrayList<>();
        pre(ans, node);
        return ans;
    }

    private static void pre(ArrayList<String> ans, Node node) {
        if (node == null) {
            ans.add("null");
        } else {
            ans.add(String.valueOf(node.value));
            pre(ans, node.left);
            pre(ans, node.right);
        }
    }

    private static int getIndexOf(String[] str1, String[] str2) {
        if (str1 == null || str2 == null || str1.length < 1 || str1.length < str2.length) {
            return -1;
        }
        int[] next = getNextArray(str2);
        int x = 0;
        int y = 0;
        while (x < str1.length && y < str2.length) {
            if (isEqual(str1[x], str2[y])) {
                x++;
                y++;
            } else if (next[y] == -1) {
                x++;
            } else {
                y = next[y];
            }
        }
        return y == str2.length ? x - y : -1;
    }

    private static int[] getNextArray(String[] str) {
        if (str.length == 1) {
            return new int[]{-1};
        }
        int[] next = new int[str.length];
        next[0] = -1;
        next[1] = 0;
        int x = 2;
        int y = 0;
        while (x < next.length) {
            if (isEqual(str[x - 1], str[y])) {
                next[x++] = ++y;
            } else if (y == 0) {
                next[x++] = 0;
            } else {
                y = next[y];
            }
        }
        return next;
    }

    private static boolean isEqual(String s1, String s2) {
        if (s1 == null && s2 == null) {
            return true;
        } else {
            if (s1 == null || s2 == null) {
                return false;
            } else {
                return s1.equals(s2);
            }
        }
    }


    public static boolean containsTree2(Node big, Node small) {
        if (big == null) {
            return false;
        }
        if (small == null) {
            return true;
        }
            if (isSameValueStructure(big, small)) {
                return true;
            }
            return containsTree2(big.left, small) || containsTree2(big.right, small);
        }

        public static boolean isSameValueStructure(Node head1, Node head2){
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
        public static Node generateRandomBST ( int maxLevel, int maxValue){
            return generate(1, maxLevel, maxValue);
        }

        // for test
        public static Node generate ( int level, int maxLevel, int maxValue){
            if (level > maxLevel || Math.random() < 0.5) {
                return null;
            }
            Node head = new Node((int) (Math.random() * maxValue));
            head.left = generate(level + 1, maxLevel, maxValue);
            head.right = generate(level + 1, maxLevel, maxValue);
            return head;
        }

        public static void main (String[] args){
            int bigTreeLevel = 7;
            int smallTreeLevel = 4;
            int nodeMaxValue = 5;
            int testTimes = 100000;
            System.out.println("test begin");
            for (int i = 0; i < testTimes; i++) {
                Node big = generateRandomBST(bigTreeLevel, nodeMaxValue);
                Node small = generateRandomBST(smallTreeLevel, nodeMaxValue);
                boolean ans1 = containsTree1(big, small);
                boolean ans2 = containsTree2(big, small);
                if (ans1 != ans2) {
                    System.out.println("Oops!");
                }
            }
            System.out.println("test finish!");

        }
    }

