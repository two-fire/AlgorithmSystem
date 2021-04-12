package Day15_暴力递归;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * @Author : LiuYan
 * @create 2021/4/9 18:26
 * 打印n层汉诺塔从最左边移动到最右边的全部过程
 */
public class Code01_Hanoi {
    /* 第一种，启发性递归 相互依赖 */
    public static void hanoi1(int n) {
        leftToRight(n);
    }

    // n层从左-> 右
    private static void leftToRight(int n) {
        if (n == 1) {
            System.out.println("move 1 from left to right");
            return;
        }
        leftToMid(n - 1);
        System.out.println("move " + n + " from left to right");
        midToRight(n - 1);
    }
    // n层从中-> 右
    private static void midToRight(int n) {
        if (n == 1) {
            System.out.println("move 1 from mid to right");
            return;
        }
        midToLeft(n - 1);
        System.out.println("move " + n + " from mid to right");
        leftToRight(n - 1);
    }
    // n层从中-> 左
    private static void midToLeft(int n) {
        if (n == 1) {
            System.out.println("move 1 from mid to left");
            return;
        }
        midToRight(n - 1);
        System.out.println("move " + n + " from mid to left");
        rightToLeft(n - 1);
    }

    // n层从左-> 中
    private static void leftToMid(int n) {
        if (n == 1) {
            System.out.println("move 1 from left to mid");
            return;
        }
        leftToRight(n - 1);
        System.out.println("move " + n + " from left to mid");
        rightToMid(n - 1);
    }
    // n层从右-> 左
    private static void rightToLeft(int n) {
        if (n == 1) {
            System.out.println("move 1 from right to left");
            return;
        }
        rightToMid(n - 1);
        System.out.println("move " + n + " from right to left");
        midToLeft(n - 1);
    }
    // n层从右-> 中
    private static void rightToMid(int n) {
        if (n == 1) {
            System.out.println("move 1 from right to mid");
            return;
        }
        rightToLeft(n - 1);
        System.out.println("move " + n + " from right to mid");
        leftToMid(n - 1);
    }

    /* 第二种，增加参数递归 三个可变参数代替6个过程*/
    public static void hanoi2(int n) {
        func(n, "left", "right", "mid");
    }
    // n层从from -> to
    private static void func(int n, String from, String to, String other) {
        if (n == 1) {
            System.out.println("move 1 from " + from + " to " + to);
            return;
        }
        func(n - 1, from, other, to);
        System.out.println("move " + n + " from " + from + " to " + to);
        func(n - 1, other, to, from);
    }

    public static void main(String[] args) throws IOException {
//        Scanner sc = new Scanner(System.in);
//        while (sc.hasNextInt('#')) {
//            int n = sc.nextInt();
//            hanoi1(n);
//        }
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String s = null;
        while ((s = bf.readLine()) != null) {
            int n = Integer.parseInt(s);
            hanoi2(n);
        }
    }
}
