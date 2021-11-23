package 笔试;

public class exm {
    public static void quickSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process(arr, 0, arr.length - 1);
    }

    private static void process(int[] arr, int l, int r) {
        if (l >= r) {
            return;
        }
        int x = l + (int) (Math.random() * (r - l + 1));
        swap(arr, x, r);
        int[] nether = netherLand(arr, l, r);
        process(arr, l, nether[0] - 1);
        process(arr, nether[1] + 1, r);
    }

    private static int[] netherLand(int[] arr, int l, int r) {
        int x = arr[r];
        int less = l;
        int more = r;
        int i = l;
        while (i < more) {
            if (arr[i] < x) {
                swap(arr, less++, i++);
            } else if (arr[i] > x) {
                swap(arr, --more, i);
            } else {
                i++;
            }
        }
        swap(arr, more, r);
        return new int[]{less + 1, more};
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{5, 4, 3, 2, 1};
        quickSort(arr);
        for (int i : arr) {
            System.out.println(i);
        }
    }
}
