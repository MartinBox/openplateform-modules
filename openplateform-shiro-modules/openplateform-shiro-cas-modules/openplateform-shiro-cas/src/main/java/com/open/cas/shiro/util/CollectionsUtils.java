/**
 * Copyright (c) 2005-2012 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.open.cas.shiro.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author erick
 *
 */
public class CollectionsUtils {

	/**
	 * 提取集合中的对象的两个属性(通过Getter函数), 组合成Map.
	 * 
	 * @param collection 来源集合.
	 * @param keyPropertyName 要提取为Map中的Key值的属性名.
	 * @param valuePropertyName 要提取为Map中的Value值的属性名.
	 */
	public static Map extractToMap(final Collection collection, final String keyPropertyName, final String valuePropertyName) {
		Map map = new HashMap(collection.size());

		try {
			for (Object obj : collection) {
				map.put(PropertyUtils.getProperty(obj, keyPropertyName), PropertyUtils.getProperty(obj, valuePropertyName));
			}
		} catch (Exception e) {
			throw ReflectionUtils.convertReflectionExceptionToUnchecked(e);
		}

		return map;
	}

	/**
	 * 提取集合中的对象的属性(通过Getter函数), 组合成Map.
	 * 
	 * @param collection 来源集合.
	 * @param keyPropertyName 要提取为Map中的Key值的属性名.
	 */
	public static Map extractToMap(final Collection collection, final String keyPropertyName) {
		Map map = new HashMap();

		try {
			for (Object obj : collection) {
				map.put(PropertyUtils.getProperty(obj, keyPropertyName), obj);
			}
		} catch (Exception e) {
			throw ReflectionUtils.convertReflectionExceptionToUnchecked(e);
		}

		return map;
	}

	/**
	 * 提取集合中的对象的一个属性(通过Getter函数), 组合成List.
	 * 
	 * @param collection 来源集合.
	 * @param propertyName 要提取的属性名.
	 */
	public static List extractToList(final Collection collection, final String propertyName) {
		List list = new ArrayList(collection.size());

		try {
			for (Object obj : collection) {
				list.add(PropertyUtils.getProperty(obj, propertyName));
			}
		} catch (Exception e) {
			throw ReflectionUtils.convertReflectionExceptionToUnchecked(e);
		}

		return list;
	}

	/**
	 * 提取集合中的对象的一个属性(通过Getter函数), 组合成由分割符分隔的字符串.
	 * 
	 * @param collection 来源集合.
	 * @param propertyName 要提取的属性名.
	 * @param separator 分隔符.
	 */
	public static String extractToString(final Collection collection, final String propertyName, final String separator) {
		List list = extractToList(collection, propertyName);
		return StringUtils.join(list, separator);
	}

	/**
	 * 转换Collection所有元素(通过toString())为String, 中间以 separator分隔。
	 */
	public static String convertToString(final Collection collection, final String separator) {
		return StringUtils.join(collection, separator);
	}

	/**
	 * 转换Collection所有元素(通过toString())为String, 每个元素的前面加入prefix，后面加入postfix，如<div>mymessage</div>。
	 */
	public static String convertToString(final Collection collection, final String prefix, final String postfix) {
		StringBuilder builder = new StringBuilder();
		for (Object o : collection) {
			builder.append(prefix).append(o).append(postfix);
		}
		return builder.toString();
	}

	public static boolean isEmpty(Collection collection) {
		return (collection == null || collection.isEmpty());
	}

	public static boolean isNotEmpty(Collection collection) {
		return (collection != null && !(collection.isEmpty()));
	}

	public static boolean isNotEmpty(Map map) {
		return (map != null && !(map.isEmpty()));
	}

	/**
	 * 取得Collection的第一个元素，如果collection为空返回null.
	 */
	public static <T> T getFirst(Collection<T> collection) {
		if (isEmpty(collection)) {
			return null;
		}

		return collection.iterator().next();
	}

	/**
	 * 获取Collection的最后一个元素 ，如果collection为空返回null.
	 */
	public static <T> T getLast(Collection<T> collection) {
		if (isEmpty(collection)) {
			return null;
		}

		// 当类型为List时，直接取得最后一个元素 。
		if (collection instanceof List) {
			List<T> list = (List<T>) collection;
			return list.get(list.size() - 1);
		}

		// 其他类型通过iterator滚动到最后一个元素.
		Iterator<T> iterator = collection.iterator();
		while (true) {
			T current = iterator.next();
			if (!iterator.hasNext()) {
				return current;
			}
		}
	}

	/**
	 * 返回a+b的新List.
	 */
	public static <T> List<T> union(final Collection<T> a, final Collection<T> b) {
		List<T> result = new ArrayList<T>(a);
		result.addAll(b);
		return result;
	}

	/**
	 * 返回a-b的新List.
	 */
	public static <T> List<T> subtract(final Collection<T> a, final Collection<T> b) {
		List<T> list = new ArrayList<T>(a);
		for (T element : b) {
			if (list.contains(element)) {
				list.remove(element);
			}
		}
		return list;
	}

	/**
	 * 返回a与b的交集的新List.
	 */
	public static <T> List<T> intersection(Collection<T> a, Collection<T> b) {
		List<T> list = new ArrayList<T>();

		for (T element : a) {
			if (b.contains(element)) {
				list.add(element);
			}
		}
		return list;
	}

	// add 
	private static final String[] otherSeps = new String[] { ",", "，", ";", "；", "、", "|" };
	private static final String defaultSeps = "/";

	public static String[] splitString(String string) {
		return splitString(string, defaultSeps, otherSeps);
	}

	public static List<String> splitNotEmpty2List(String string, String targetSep) {
		if (StringUtils.isEmpty(string)) {
			return Collections.emptyList();
		}
		String[] parts = StringUtils.split(string, targetSep);
		return Arrays.asList(parts);
	}

	public static String[] splitNotEmpty2Array(String string, String targetSep) {
		if (StringUtils.isEmpty(string)) {
			return new String[0];
		}
		return StringUtils.split(string, targetSep);
	}

	public static String[] splitNotEmpty2ArrayTrim(String string, String targetSep) {
		if (StringUtils.isEmpty(string)) {
			return new String[0];
		}
		return clean(StringUtils.split(string, targetSep));
	}

	public static List<String> splitNotEmpty2ListTrim(String string, String targetSep) {
		if (StringUtils.isEmpty(string)) {
			return Collections.emptyList();
		}
		String[] parts = StringUtils.split(string, targetSep);

		return clean2List(parts);
	}

	public static String[] splitString(String string, String targetSep, String... otherSeps) {
		if (StringUtils.isEmpty(string)) {
			return new String[] { "" };
		}
		String newString = string;
		for (String sep : otherSeps) {
			if (newString.indexOf(sep) >= 0) {
				newString = newString.replaceAll(sep, targetSep);
			}
		}
		return newString.split(targetSep);
	}

	/**
	 * <p>Checks if an array of Objects is empty or <code>null</code>.</p>
	 *
	 * @param array  the array to factories
	 * @return <code>true</code> if the array is empty or <code>null</code>
	 * @since 2.1
	 */
	public static boolean isEmpty(Object[] array) {
		if (array == null || array.length == 0) {
			return true;
		}
		return false;
	}

	/**
	 * <p>Checks if an array of primitive longs is empty or <code>null</code>.</p>
	 *
	 * @param array  the array to factories
	 * @return <code>true</code> if the array is empty or <code>null</code>
	 * @since 2.1
	 */
	public static boolean isEmpty(long[] array) {
		if (array == null || array.length == 0) {
			return true;
		}
		return false;
	}

	/**
	 * <p>Checks if an array of primitive ints is empty or <code>null</code>.</p>
	 *
	 * @param array  the array to factories
	 * @return <code>true</code> if the array is empty or <code>null</code>
	 * @since 2.1
	 */
	public static boolean isEmpty(int[] array) {
		if (array == null || array.length == 0) {
			return true;
		}
		return false;
	}

	/**
	 * <p>Checks if an array of primitive shorts is empty or <code>null</code>.</p>
	 *
	 * @param array  the array to factories
	 * @return <code>true</code> if the array is empty or <code>null</code>
	 * @since 2.1
	 */
	public static boolean isEmpty(short[] array) {
		if (array == null || array.length == 0) {
			return true;
		}
		return false;
	}

	/**
	 * <p>Checks if an array of primitive chars is empty or <code>null</code>.</p>
	 *
	 * @param array  the array to factories
	 * @return <code>true</code> if the array is empty or <code>null</code>
	 * @since 2.1
	 */
	public static boolean isEmpty(char[] array) {
		if (array == null || array.length == 0) {
			return true;
		}
		return false;
	}

	/**
	 * <p>Checks if an array of primitive bytes is empty or <code>null</code>.</p>
	 *
	 * @param array  the array to factories
	 * @return <code>true</code> if the array is empty or <code>null</code>
	 * @since 2.1
	 */
	public static boolean isEmpty(byte[] array) {
		if (array == null || array.length == 0) {
			return true;
		}
		return false;
	}

	/**
	 * <p>Checks if an array of primitive doubles is empty or <code>null</code>.</p>
	 *
	 * @param array  the array to factories
	 * @return <code>true</code> if the array is empty or <code>null</code>
	 * @since 2.1
	 */
	public static boolean isEmpty(double[] array) {
		if (array == null || array.length == 0) {
			return true;
		}
		return false;
	}

	/**
	 * <p>Checks if an array of primitive floats is empty or <code>null</code>.</p>
	 *
	 * @param array  the array to factories
	 * @return <code>true</code> if the array is empty or <code>null</code>
	 * @since 2.1
	 */
	public static boolean isEmpty(float[] array) {
		if (array == null || array.length == 0) {
			return true;
		}
		return false;
	}

	/**
	 * <p>Checks if an array of primitive booleans is empty or <code>null</code>.</p>
	 *
	 * @param array  the array to factories
	 * @return <code>true</code> if the array is empty or <code>null</code>
	 * @since 2.1
	 */
	public static boolean isEmpty(boolean[] array) {
		if (array == null || array.length == 0) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(Map map) {
		if (map == null || map.size() == 0) {
			return true;
		}
		return false;
	}

	public static List<String> clean2List(String[] arr) {
		if (!isEmpty(arr)) {
			List<String> list = new ArrayList<String>();
			for (String string : arr) {
				String trim = StringUtils.trimToEmpty(string);
				if (StringUtils.isNotEmpty(trim)) {
					list.add(trim);
				}
			}
			return list;
		}
		return Collections.emptyList();
	}

	public static String[] clean(String[] arr) {
		if (!isEmpty(arr)) {
			List<String> list = new ArrayList<String>();
			for (String string : arr) {
				String trim = StringUtils.trimToEmpty(string);
				if (StringUtils.isNotEmpty(trim)) {
					list.add(trim);
				}
			}
			return list.toArray(new String[0]);
		}
		return new String[0];
	}

	public static <K, T> Map<K, List<T>> groups(final Collection<T> collection, final String groupByField) {
		Map<K, List<T>> map = new HashMap<K, List<T>>();
		try {
			if (CollectionUtils.isEmpty(collection)) {
				return map;
			}
			for (T obj : collection) {
				if (obj != null) {
					K key = (K) PropertyUtils.getProperty(obj, groupByField);
					List<T> list = (List<T>) map.get(key);
					if (list == null) {
						list = new ArrayList<T>();
						map.put(key, list);
					}
					list.add(obj);
				}
			}
		} catch (Exception e) {
			throw ReflectionUtils.convertReflectionExceptionToUnchecked(e);
		}
		return map;

	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> extract(List<Map<String, Object>> list, String key) {
		List<T> result = new ArrayList<T>();
		if (list == null) {
			return result;
		}

		for (Map<String, Object> map : list) {
			if (map == null) {
				continue;
			}
			result.add((T) map.get(key));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public static <T> Set<T> extractToSet(List<Map<String, Object>> list, String key) {
		Set<T> result = new TreeSet<T>();
		if (list == null) {
			return result;
		}
		for (Map<String, Object> map : list) {
			if (map == null) {
				continue;
			}
			result.add((T) map.get(key));
		}
		return result;
	}

	public static <T> Set<T> newSet(T... values) {
		Set<T> set = new HashSet<T>();
		for (T value : values) {
			set.add(value);
		}
		return set;
	}

	public static <T extends Number> List<Long> guardTypeAsLong(List<T> numbers) {
		List<Long> longs = new ArrayList<Long>();
		for (int i = 0; i < numbers.size(); i++) {
			Number n = numbers.get(i);
			if (n instanceof Long) {
				longs.add(n.longValue());
			} else {
				longs.add(Long.parseLong(n.toString()));
			}
		}
		return longs;
	}

	public static List<Long> fromStringToLong(List<String> numbers) {

		List<Long> longs = new ArrayList<Long>();
		for (int i = 0; i < numbers.size(); i++) {
			String n = numbers.get(i);
			longs.add(Long.parseLong(n));
		}
		return longs;
	}

	public static List<Long> fromStringToLong(String strLongs, String separator) {
		List<String> numbers = splitNotEmpty2ListTrim(strLongs, separator);
		return fromStringToLong(numbers);
	}
}
