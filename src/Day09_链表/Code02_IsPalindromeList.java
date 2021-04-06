package Day09_链表;

import java.util.Stack;

/**
 * @Author : LiuYan
 * @create 2021/3/29 11:51
 * 给定一个单链表的头节点head，请判断该链表是否为回文结构。
 * 1）容器方法特别简单（笔试）
 * 2）改原链表的方法就需要注意边界了（面试）
 */
public class Code02_IsPalindromeList {
    public static class Node {
        int value;
        Node next;
        public Node(int v) {
            value = v;
        }
    }
    // 额外空间O(N)
    public static boolean isPalindrome1(Node head) {
        if (head == null || head.next == null) {
            return true;
        }
        Stack<Node> stack = new Stack<>();
        Node node = head;
        while (node != null) {
            stack.push(node);
            node = node.next;
        }
        node = head;
        while (!stack.isEmpty()) {
            if (stack.pop().value != node.value) {
                return false;
            }
            node = node.next;
        }
        return true;
    }
    // 额外空间O(N/2) 快慢指针 奇数：中点 偶数：前一个中点
    public static boolean isPalindrome2(Node head) {
        if (head == null || head.next == null) {
            return true;
        }

        Stack<Node> stack = new Stack<>();
        Node fast = head;
        Node node = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            node = node.next;
        }
        // 把后一半压栈
        node = node.next;
        while (node != null) {
            stack.push(node);
            node = node.next;
        }
        node = head;
        while (!stack.isEmpty()) {
            if (stack.pop().value != node.value) {
                return false;
            }
            node = node.next;
        }
        return true;
    }
    // 额外空间O(1) 后一半的指针反向
    public static boolean isPalindrome3(Node head) {
        if (head == null || head.next == null) {
            return true;
        }
        boolean res = true;
        Node fast = head;
        Node node = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            node = node.next;
        }
        // 反转指针
        Node pre = node;
        node = node.next;
        pre.next = null;
        while (node != null) {
            fast = node.next;
            node.next = pre;
            pre = node;
            node = fast;
        }
        //记录下终点
        Node n2 = pre; // cur
        // 两头比较
        node = head;
        while (node != null && pre != null) {
            if (node.value != pre.value) {
                res = false;
                break;
            }
            node = node.next;
            pre = pre.next;
        }
        // 还原链表
        Node n1 = null; // pre
        Node n3 = null; // next
        while (n2 != null) {
            n3 = n2.next;
            n2.next = n1;
            n1 = n2;
            n2 = n3;
        }
        return res;
    }
    public static void printLinkedList(Node node) {
        System.out.print("Linked List: ");
        while (node != null) {
            System.out.print(node.value + " ");
            node = node.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node head = null;
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.print(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(2);
        head.next.next.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(2);
        head.next.next.next.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");
    }
}
