package Day07_加强堆;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * @Author : LiuYan
 * @create 2021/3/28 16:12
 * 手写堆！！！    数组结构
 * 1）建立反向索引表
 * 2）建立比较器
 * 系统提供的堆只能完成：给一个对象进来，帮你把之前所有对象和这个整理成堆；
 *                      要是想拿出就弹出一个。
 * 有些功能无法完成，比如：
 *    已经排好序的堆，里面一个节点的属性值突然改变，需要重写调整堆。对于系统API，此时成了无效堆
 *    系统提供的堆，没有反向索引表： [a b c] 没有说a，b，c放在了哪。所以假如b改了，就无法高效调整堆
 *    如果有索引表，找到b，向上调，只要logN的代价
 */

// T一定要是非基础类型，有基础类型需求包一层
public class HeapGreater<T> {
    private ArrayList<T> heap;
    private HashMap<T, Integer> indexMap; // 反向索引表
    private int heapSize;
    private Comparator<? super T> comparator;

    public HeapGreater(Comparator<T> c) {
        heap = new ArrayList<>();
        indexMap = new HashMap<>();
        heapSize = 0;
        comparator =c;
    }
    public boolean isEmpty() {
        return heapSize == 0;
    }
    public int size() {
        return heapSize;
    }
    public boolean contains(T obj) {
        return indexMap.containsKey(obj);
    }

    public T peek() {
        return heap.get(0);
    }
    public void push(T obj) {
        heap.add(obj);
        indexMap.put(obj, heapSize);
        heapSize++;
    }
    public T pop() {
        T res = heap.get(0);
        swap(0, --heapSize);
        heap.remove(heapSize);
        indexMap.remove(res);
        heapify(0);
        return res;
    }

    public void remove(T obj) {
        swap(indexMap.get(obj), --heapSize);
        indexMap.remove(obj);
        heap.remove(obj);
    }

    // 某一位修改，进行堆调整
    public void resign(T obj) {
        heapify(indexMap.get(obj));
        heapInseart(indexMap.get(obj));
    }

    // 请返回堆上的所有元素
    private List<T> getAllElements() {
        List<T> ans = new ArrayList<>();
        for (T c : heap) {
            ans.add(c);
        }
        return ans;
    }
    // 向下调整
    private void heapify(int index) {
        int left = index * 2 + 1;
        while (left < heapSize) {
            // 得到孩子中best
            int best = (left + 1 < heapSize) && comparator.compare(heap.get(left + 1), heap.get(left)) < 0 ? (left + 1) : left;
            best = comparator.compare(heap.get(best), heap.get(index)) < 0 ? best : index;
            if (best == index) {
                break;
            }
            swap(index, best);
            index = best;
            left = index * 2 + 1;
        }
    }
    // 向上调整
    private void heapInseart(int index) {
        int p = (index - 1) / 2;
        while (p >= 0 && comparator.compare(heap.get(p), heap.get(index)) < 0) {
            swap(p, index);
            index = (index - 1) / 2;
            p = (index - 1) / 2;
        }
    }

    public void swap(int i, int j) {
        T o1 = heap.get(i);
        T o2 = heap.get(j);
        heap.set(i, o2);
        heap.set(j, o1);
        indexMap.remove(o1, i);
        indexMap.remove(o2, j);
        indexMap.put(o1, j);
        indexMap.put(o2, i);
    }
}
