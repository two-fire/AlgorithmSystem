package Day18_单调栈;

import java.util.Stack;

/**
 * @Author : LiuYan
 * @create 2021/4/24 15:41
 *
 * 给定一个二维数组matrix，其中的值不是0就是1，
 * 返回全部由1组成的子矩形数量
 *
 * 思路：
 * 1、准备一个长度和maxtrix长度一样的一维数组arr。按行遍历，以该行作为基底，
 * 如果自己是0，就填0，否则加上上面的值。数组的每一位的值就代表这一列的高度。
 * 2、以每一列做矩形高度h，找到最大的矩阵。计算在这个矩阵中，高度为h,有几个矩阵；高度为h-1，有几个矩阵..
 * 注意，高度不能小于这个矩阵两侧的高度
 *
 * 测试链接：https://leetcode.com/problems/count-submatrices-with-all-ones
 */
public class Code05_CountSubmatricesWithAllOnes {
    public static int countSubMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int row = matrix.length;
        int col = matrix[0].length;
        int[] arr = new int[col];
        int count = 0;
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                if (matrix[r][c] == 0) {
                    arr[c] = 0;
                } else {
                    arr[c] += matrix[r][c];
                }
            }
            int[] stack = new int[col];
            int size = -1;
            for (int i = 0; i < col; i++) {
                while (size != -1 && arr[stack[size]] >= arr[i]) {
                    int cur = stack[size--];
                    if (arr[cur] > arr[i]) {
                        int leftLess = size == -1 ? -1 : stack[size];
                        int len = i - leftLess - 1;
                        int down = (leftLess == -1 ? arr[i] : Math.max(arr[leftLess], arr[i])) + 1;

                            count += ((1 + len) * len / 2)* (arr[cur] - down + 1);

                    }
                }
                stack[++size] = i;
            }
            while (size != -1) {
                int cur = stack[size--];
                int leftLess = size == -1 ? -1 : stack[size];
                int len = arr.length - leftLess - 1;
                int down =  leftLess == -1 ? 0 : arr[leftLess];
                count += ((1 + len) * len / 2) * (arr[cur] - down);

            }
        }
        return count;
    }

    public static void main(String[] args) {
        int[][] matrix = {{1},{1},{1}};
        System.out.println(countSubMatrix(matrix));
    }
}
