package com.example.demo.shared.utils;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BaseStreamUtils {

  /**
   * @param source is a list of objects
   * @param key is an attribute of the object
   * @return Map of key and corresponding object, if duplicates => get the last one
   */
  public static <O, K> Map<K, O> toMap(List<O> source, Function<? super O, ? extends K> key) {
    return source.stream()
        .collect(Collectors.toMap(key, Function.identity(), (first, second) -> second));
  }

  /**
   * @param source is a list of objects
   * @param key is an attribute of the object
   * @return Map of key and list of objects with the same key
   */
  public static <O, K> Map<K, List<O>> toMapList(List<O> source, Function<? super O, ? extends K> key) {
    return source.stream().collect(Collectors.groupingBy(key));
  }

  /**
   * @param source is a list of objects
   * @param attribute is an attribute of the object
   * @return list of attributes
   */
  public static <O, V> List<V> getAttributes(List<O> source, Function<? super O, ? extends V> attribute) {
    return source.stream().map(attribute).collect(Collectors.toList());
  }
}
