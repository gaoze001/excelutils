package util;


import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 集合工具类
 * @author ThinkPad
 *
 */
public class ListUtils {


    /**
     * 集合判空
     *
     * @param list 集合
     * @return 是否为空
     */
    public static boolean isEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }

    public static boolean isNotEmpty(List<?> list) {
        return !isEmpty(list);
    }

    public static interface MapKeyEntity<T> {
        String getMapKey(T t);
    }

    public static interface Converter<E, V> {
        V convert(E e);
    }

    /**
     * list 转为map
     *
     * @param list         集合
     * @param mapKeyEntity 获取key接口
     * @return map
     */
    public static <T> Map<String, T> listToMap(List<T> list, MapKeyEntity<T> mapKeyEntity) {
        if (isEmpty(list)) {
            return new HashMap<>();
        }
        Map<String, T> map = new HashMap<>((int) (list.size() / 0.75) + 1);
        for (T t : list) {
            String key = mapKeyEntity.getMapKey(t);
            map.put(key, t);
        }
        return map;
    }

    /**
     * list 转换成另外一个list，是list.stream.map.toList的封装简化写法
     *
     * @param list      list
     * @param converter 转换器
     * @return 新的list
     */
    private static <E, V> List<V> listToList(List<E> list, Converter<E, V> converter, boolean useParallel, boolean distinct) {
        if (isEmpty(list)) {
            return new ArrayList<>();
        }
        Stream<E> stream = useParallel ? list.parallelStream() : list.stream();
        Stream<V> vStream = stream.map(converter::convert);
        if (distinct) {
            vStream = vStream.distinct();
        }
        return vStream.collect(Collectors.toList());
    }

    /**
     * 使用普通流进行转换流
     *
     * @param list      集合
     * @param converter 转换器
     * @return 新的集合
     */
    public static <E, V> List<V> listToList(List<E> list, Converter<E, V> converter) {
        return listToList(list, converter, false, false);
    }

    public static <E, V> List<V> listToList(List<E> list, Converter<E, V> converter, boolean distinct) {
        return listToList(list, converter, false, distinct);
    }

    /**
     * 使用平行流进行转换流
     *
     * @param list      集合
     * @param converter 转换器
     * @return 新的集合
     */
    public static <E, V> List<V> parallelListToList(List<E> list, Converter<E, V> converter) {
        return listToList(list, converter, true, false);
    }


    /**
     * list分组， 是list.steam.collect(groupBy)的简化写法
     *
     * @param list      list
     * @param converter 转换器
     * @return 分组map
     */
    private static <E, V> Map<V, List<E>> groupBy(List<E> list, Converter<E, V> converter, boolean useParallel) {
        if (isEmpty(list)) {
            return new HashMap<>();
        }
        Stream<E> stream = useParallel ? list.parallelStream() : list.stream();
        return stream.collect(Collectors.groupingBy(converter::convert));
    }

    public static <E, V> Map<V, List<E>> groupBy(List<E> list, Converter<E, V> converter) {
        return groupBy(list, converter, false);
    }

    public static <E, V> Map<V, List<E>> parallelGroupBy(List<E> list, Converter<E, V> converter) {
        return groupBy(list, converter, true);
    }

    /**
     * 过滤数据，传入的条件为true则移除这个元素
     *
     * @param list       集合
     * @param predicates 移除的条件
     * @return 新的集合
     */
    public static <E> List<E> filter(List<E> list, List<Predicate<E>> predicates) {
        if (isEmpty(list)) {
            return list;
        }
        Iterator<E> iterator = list.iterator();
        while (iterator.hasNext()) {
            E next = iterator.next();
            for (Predicate<E> predicate : predicates) {
                if (predicate.test(next)) {
                    iterator.remove();
                    break;
                }
            }
        }
        return list;
    }

    /**
     * 过滤数据，传入的条件为true则移除这个元素
     *
     * @param list      集合
     * @param predicate 移除的条件
     * @return 新的集合
     */
    public static <E> List<E> filter(List<E> list, Predicate<E> predicate) {
        if (isEmpty(list)) {
            return list;
        }
        list.removeIf(predicate);
        return list;
    }
}