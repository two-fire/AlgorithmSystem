package 笔试;

/**
 * 深信服
 * 求一个M*N的矩阵的最大子矩阵和。
 */
public class Emp_1 {
    public int max_sub_matrix(int[][] matrix) {
        int M = matrix.length; // 行
        int N = matrix[0].length; // 列
        int[][] total = new int[M][N]; // 每一层上下相加后得到的矩阵是total
        for (int i = 0; i < N; i++) {
            total[0][i] = matrix[0][i];
        }
        for (int i = 1; i < M; i++) {
            for (int j = 0; j < N; j++) {
                total[i][j] += total[i - 1][j];
            }
        }

        int maximum = Integer.MIN_VALUE;
        for (int i = 0; i < M; i++) {
            for (int j = i; j < N; j++) {
                int[] result = new int[N];
                for (int k = 0; k < N; k++) {
                    if (i == 0) {
                        result[k] = total[j][k];
                    } else {
                        result[k] = total[j][k] - total[i - 1][k]; //第 i 行到第 j 行之间上下值的和
                    }
                }
                int maximal = maxSubSequence(result);
                if (maximal > maximum) {
                    maximum = maximal;
                }
            }
        }
        return maximum;
    }

    private int maxSubSequence(int[] arr) {
        if (arr.length == 0) {
            return 0;
        }
        int max = Integer.MIN_VALUE;
        int[] maxSub = new int[arr.length];
        maxSub[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            maxSub[i] = (maxSub[i - 1] > 0) ? (maxSub[i - 1] + arr[i]) : arr[i];
            if (max < maxSub[i]) {
                max = maxSub[i];
            }
        }
        return max;

    }

}
