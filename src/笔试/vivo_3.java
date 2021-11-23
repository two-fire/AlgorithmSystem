package 笔试;


import java.lang.reflect.Array;
import java.util.*;

public class vivo_3 {
    static final int MOD = 1000000007;

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int x) {
            val = x;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    class Node {
        public int val;
        public Node next;
        public Node prev;
        public Node child;
    }

    public class Pair {
        char val;
        int count;

        public Pair(char v, int c) {
            val = v;
            count = c;
        }
    }

    // 想喝n瓶牛奶
    public int minMoney(int n) {
        int[] a = new int[1];
        int x = (int) Math.ceil(2 * n / 3.0); // 全额买x瓶

        return 5 * x + x / 2;
    }


    private void swap(int[] ans, int i, int j) {
        int tmp = ans[i];
        ans[i] = ans[j];
        ans[j] = tmp;
    }


    public static void main(String[] args) {
//
        //        LinkedList<String> list = new LinkedList<>();
        //        list.push("1");
        //        list.push("2");
        //        list.push("3");
        //
        //        System.out.println(list.pop());
        //        System.out.println(list.pollFirst());
        //        System.out.println(list.pollLast());
        //        System.out.println(list.size());
        //[1,2,3,null,null,4,5]
        TreeNode n1 = new TreeNode(-1);
        TreeNode n2 = new TreeNode(9);
        TreeNode n3 = new TreeNode(-10);
        TreeNode n4 = new TreeNode(3);
        TreeNode n5 = new TreeNode(-2);
        n1.right = n2;
        n2.left = n3;
        n2.right = n4;
        n4.right = n5;
        LinkedList<String> a = new LinkedList<>();
        a.add("11");
        a.add("12");
        System.out.println(a.toArray());
//        String s = String.valueOf(a);
        StringBuilder s1 = new StringBuilder();
        System.out.println(s1.append(1));
        System.out.println(s1.deleteCharAt(0));
        vivo_3 v = new vivo_3();
//        PriorityQueue<Integer> queue = new PriorityQueue<>((o1,o2)->o2 - o1);
//        queue.add(1);
//        queue.add(2);
//        System.out.println(queue.poll());
//        System.out.println("ab".contains("c"));
        HashMap<Integer, Integer> map = new HashMap();
        System.out.println(map.containsKey(1));
        System.out.println((1 << ("123".charAt(0) - '1')));
        System.out.println((char) ('a' + 1));
        StringBuilder sb = new StringBuilder();

        sb.append(1);
        String sb2 = new String(sb);
        sb.deleteCharAt(0);
        String s = "111";
        System.out.println(s.substring(0, 3));
        sb.append(s);
        System.out.println(sb);
        int[][] ans = new int[][]{{3, 0, 1, 4, 2}, {5, 6, 3, 2, 1}, {1, 2, 0, 1, 5}, {4, 1, 0, 1, 7}, {1, 0, 3, 0, 5}};
        System.out.println(String.valueOf(1));
//        System.out.println(v.countSubstrings("aba"));

        ListNode nn1 = new ListNode(1);
        ListNode nn2 = new ListNode(2);
        ListNode nn3 = new ListNode(3);
        ListNode nn4 = new ListNode(4);
        ListNode nn5 = new ListNode(5);
        nn1.next = nn2;
        nn2.next = nn3;
        nn3.next = nn4;
        nn4.next = nn5;
//        System.out.println(v.longestIncreasingPath(new int[][]{{9,9,4},{6,6,8},{2,1,1}}));
        List<List<String>> tickets = new ArrayList<>();
        List<String> ticket = new ArrayList<>();
        ticket.add("JFK");
        ticket.add("KUL");
        tickets.add(ticket);
        ticket = new ArrayList<>();
        tickets.add(ticket);
        ticket.add("JFK");
        ticket.add("NRT");
        ticket = new ArrayList<>();
        tickets.add(ticket);
        ticket.add("NRT");
        ticket.add("JFK");

        System.out.println();
        int[] aa = new int[]{1, 2, 3};
        Arrays.sort(aa, 0, 3);
    }

}
