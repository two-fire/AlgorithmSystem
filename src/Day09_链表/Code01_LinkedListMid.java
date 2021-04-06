package Day09_链表;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * @Author : LiuYan
 * @create 2021/3/29 11:50
 *  快慢指针
 *  1）输入链表头节点，奇数长度返回中点，偶数长度返回上中点
 *  2）输入链表头节点，奇数长度返回中点，偶数长度返回下中点
 *  3）输入链表头节点，奇数长度返回中点前一个，偶数长度返回上中点前一个
 *  4）输入链表头节点，奇数长度返回中点前一个，偶数长度返回下中点前一个
 */
public class Code01_LinkedListMid {
    public static class Node {
        private int value;
        private Node next;

        public Node(int v) {
            value = v;
            next = null;
        }

    }
    // 奇数长度返回中点，偶数长度返回上中点
    public static Node midOrUp(Node head) {
        if (head == null) {
            return head;
        }
        Node fast = head;
        Node slow = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }
    // 奇数长度返回中点，偶数长度返回下中点
    public static Node midOrDown(Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        Node fast = head.next;
        Node slow = head.next;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }
    // 奇数长度返回中点前一个，偶数长度返回上中点前一个
    public static Node upOrUp(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }
        // 2个以上
        Node fast = head.next.next;
        Node slow = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }
    //奇数长度返回中点前一个，偶数长度返回下中点前一个
    public static Node upOrDown(Node head) {
        if (head == null || head.next == null) {
            return null;
        }
        // 1个以上
        Node fast = head.next;
        Node slow = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }
    // 奇数长度返回中点，偶数长度返回上中点
    public static Node right1(Node head) {
        if (head == null) {
            return null;
        }
        ArrayList<Node> list = new ArrayList<>();
        Node node = head;
        while (node != null) {
            list.add(node);
            node = node.next;
        }
        return list.get((list.size() - 1) / 2);
    }
    // 奇数长度返回中点，偶数长度返回下中点
    public static Node right2(Node head) {
        if (head == null) {
            return null;
        }
        ArrayList<Node> list = new ArrayList<>();
        Node node = head;
        while (node != null) {
            list.add(node);
            node = node.next;
        }
        return list.get(list.size() / 2);
    }
    // 奇数长度返回中点前一个，偶数长度返回上中点前一个
    public static Node right3(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }
        ArrayList<Node> list = new ArrayList<>();
        Node node = head;
        while (node != null) {
            list.add(node);
            node = node.next;
        }
        return list.get((list.size() - 3) / 2);
    }
    //奇数长度返回中点前一个，偶数长度返回下中点前一个
    public static Node right4(Node head) {
        if (head == null || head.next == null) {
            return null;
        }
        ArrayList<Node> list = new ArrayList<>();
        Node node = head;
        while (node != null) {
            list.add(node);
            node = node.next;
        }
        return list.get((list.size() - 2) / 2);
    }

    public static void main(String[] args) {
        Node test = null;
        test = new Node(0);
        test.next = new Node(1);
        test.next.next = new Node(2);
        test.next.next.next = new Node(3);
        test.next.next.next.next = new Node(4);
        test.next.next.next.next.next = new Node(5);
        test.next.next.next.next.next.next = new Node(6);
        test.next.next.next.next.next.next.next = new Node(7);
        test.next.next.next.next.next.next.next.next = new Node(8);

        Node ans1 = null;
        Node ans2 = null;
        ans1 = midOrUp(test);
        ans2 = right1(test);
        System.out.println(ans1 != null ? ans1.value : "无");
        System.out.println(ans2 != null ? ans2.value : "无");

        ans1 = midOrDown(test);
        ans2 = right2(test);
        System.out.println(ans1 != null ? ans1.value : "无");
        System.out.println(ans2 != null ? ans2.value : "无");

        ans1 = upOrUp(test);
        ans2 = right3(test);
        System.out.println(ans1 != null ? ans1.value : "无");
        System.out.println(ans2 != null ? ans2.value : "无");

        ans1 = upOrDown(test);
        ans2 = right4(test);
        System.out.println(ans1 != null ? ans1.value : "无");
        System.out.println(ans2 != null ? ans2.value : "无");
    }
}
