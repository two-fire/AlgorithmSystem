package 笔试;

import java.util.Scanner;

/**
 * 小美开始学习健身操。我们可以将一套健身操简化为两个动作（记为动作0，动作1）和中途休息（即不做任何动作）的组合。
 *
 * 如果小美在健身操要求做某个动作时做出错误的动作，或者说在应该中途休息是做了某个动作，则被称为“错误操作”（应该做某个动作而没有做某个动作不算“错误操作”）。如果一段连续的错误操作的持续时间小于阈值k，则被称为“小错误”。
 *
 * 例如，要求的动作为：1~3时刻动作0，小美的实际动作为：0~4时刻动作0，阈值为2，则小美有两次“小错误”，分别在0~1时刻和3~4时刻。
 *
 * 再例如，要求的动作为：1~3时刻动作0，小美的实际动作为：2~4时刻动作1，阈值为2。虽然2~3和3~4都是“错误操作”，但是它们是连续的，所以只算一段错误操作，时间等于2，达到阈值，因此小美没有“小错误”。
 *
 * 给出健身操要求的动作以及小美的实际动作，求小美有多少次“小错误”。
 * 3 3 2
 * 1 2 0
 * 5 6 1
 * 8 10 1
 * 0 3 0
 * 5 6 0
 * 7 9 1
 *
 * 4
 */
public class meituan1 {
    // n 动作数量； m 实际动作数量； k 阈值
    // arr[0] l arr[1] r arr[2] v  1~r时刻动作v
    public static int errorNum(int n, int m, int k, int[][] arr) {
        int ans = 0;
        int l1, r1, v1; // 标准
        int l2, r2, v2; // 实际
        for (int i = 0; i < n; i++) {
            l1 = arr[i][0];
            r1 = arr[i][1];
            v1 = arr[i][2];
            for (int j = n + i; j < arr.length; j++) {
                l2 = arr[j][0];
                r2 = arr[j][1];
                v2 = arr[j][2];
                if (r2 <= l1) {
                    if (r2 - l2 > k) {
                        ans++;
                    }
                } else if (r2 <= r1) {
                    if (l2 < l1) {
                        ans += l1 - l2 > k ? 0 : 1;
                    } else {
                        if (v1 != v2) {
                            ans += r2 - l2 > k ? 0 : 1;
                        }
                    }
                } else { // r2 > r1
                    if (i >= n - 1 || (i < n - 1 && r2 < arr[i + 1][0])) {
                        if (r1 > l2) {
                            ans += r2 - r1 > k ? 0 : 1;
                            if (l1 <= l2 && v1 != v2) {
                                ans += r1 - l2 > k ? 0 : 1;

                            } else if (l1 > l2) {
                                ans += l1 - l2 > k ? 0 : 1;
                            }
                        } else {
                            ans += r2 - l2 > k ? 0 : 1;
                        }
                    } else {
                        break;
                    }
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        String[] str = s.trim().split(" ");
        int n = Integer.parseInt(str[0]);
        int m = Integer.parseInt(str[1]);
        int k = Integer.parseInt(str[2]);
        int[][] arr = new int[n + m][3];
        for (int i = 0; i < arr.length; i++) {
            arr[i][0] = sc.nextInt();
            arr[i][1] = sc.nextInt();
            arr[i][2] = sc.nextInt();
        }
        System.out.println(errorNum(n, m, k, arr));
    }

}
