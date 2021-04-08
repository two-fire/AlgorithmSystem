package Day14_图;

import java.util.Set;

/**
 * @Author : LiuYan
 * @create 2021/4/8 22:48
 *
 * 最小生成树算法之Kruskal
 *
 * 1）总是从权值最小的边开始考虑，依次考察权值依次变大的边
 * 2）当前的边要么进入最小生成树的集合，要么丢弃
 * 3）如果当前的边进入最小生成树的集合中不会形成环，就要当前边
 * 4）如果当前的边进入最小生成树的集合中会形成环，就不要当前边
 * 5）考察完所有边之后，最小生成树的集合也得到了
 *
 * 需要一个小根堆，如果from和to不是一个集合就合并，否则舍弃
 */
public class Code04_Kruskal {
    public static Set<Edge> kruskal(Gragh gragh) {

    }
}
