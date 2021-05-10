package Day18_单调栈;

import java.util.Stack;

/**
 * @Author : LiuYan
 * @create 2021/4/24 12:47
 * 给定一个二维数组matrix，其中的值不是0就是1，
 * 返回全部由1组成的最大子矩形，内部有多少个1
 *
 * 思路：
 *  暴力：随机找两个点作为矩形的左上角和右下角（n^2 * n^2），然后遍历矩形是否全是1（n^2）
 *  单调栈：准备一个长度和maxtrix长度一样的一维数组。按行遍历，以该行作为基底，
 *     如果自己是0，就填0，否则加上上面的值。数组的每一位的值就代表这一列的高度。
 *     然后就是上一道题的思路。以每一列做矩形高度，找到最大的矩阵。
 * 测试链接：https://leetcode.com/problems/maximal-rectangle/
 */
public class Code04_MaximalRectangle {
    public static int max1(char[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return 0;
        }
        int row = matrix.length;
        int col = matrix[0].length;
        boolean key;
        int max = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                for (int k = i; k < row; k++) {
                    for (int l = j; l < col; l++) {
                        key = true;
                        for (int m = i; m <= k && key; m++) {
                            for (int n = j; n <= l; n++) {
                                if (matrix[m][n] == '0') {
                                    key = false;
                                }
                            }
                        }
                        if (key) {
                            max = Math.max(max, (k - i + 1) * (l - j + 1));
                        }
                    }
                }
            }
        }
        return max;
    }
    public static int max2(char[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return 0;
        }
        int row = matrix.length;
        int col = matrix[0].length;
        int[] arr= new int[col];
        int max = 0;
        for (int r = 0; r < row; r++) {
            for (int j = 0; j < col; j++) {
                if (matrix[r][j] == '0') {
                    arr[j] = 0;
                } else {
                    arr[j] += Integer.parseInt(String.valueOf(matrix[r][j]));
                }
            }

            int[] stack = new int[col];
            int size = -1;
            for (int i = 0; i < col; i++) {
                while (size != -1 && arr[stack[size]] >= arr[i]) {
                    int cur = stack[size--];
                    int leftLess = size == -1 ? -1 : stack[size];
                    max = Math.max(max, (i - leftLess - 1) * arr[cur]);
                }
                stack[++size] = i;
            }
            while (size != -1) {
                int cur = stack[size--];
                int leftLess = size == -1 ? -1 : stack[size];
                max = Math.max(max, (arr.length - leftLess - 1) * arr[cur]);
            }

        }

        return max;
    }

    public static void main(String[] args) {
        char[][] matrix = {{'0','0','0','0','0','0','1'},{'0','0','0','0','1','1','1'},{'1','1','1','1','1','1','1'},{'0','0','0','1','1','1','1'}};
        System.out.println(max1(matrix));
        System.out.println(max2(matrix));
        System.out.println(Integer.parseInt(String.valueOf('1')) + 1);
    }
}
