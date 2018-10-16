package com.zhilink.utils;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 对象copy
 * 需要get，set方法
 *
 * @author copy的
 * @date 2018-9-22 16:08
 */
public class BeanPropertiesUtils {
    /**
     * 利用反射实现对象之间属性复制--父类属性无法copy
     */
    public static void copyProperties(Object from, Object to) throws Exception {
        copyPropertiesExclude(from, to, null);
    }

    /**
     * 复制对象属性--父类属性无法copy
     */
    @SuppressWarnings("unchecked")
    public static void copyPropertiesExclude(Object from, Object to, String[] excludesArray) {
        List<String> excludesList = null;
        if (excludesArray != null && excludesArray.length > 0) {
            excludesList = Arrays.asList(excludesArray);    //构造列表对象
        }
        Method[] fromMethods = from.getClass().getDeclaredMethods();
        Method[] toMethods = to.getClass().getDeclaredMethods();
        Method fromMethod = null, toMethod = null;
        String fromMethodName = null, toMethodName = null;
        for (int i = 0; i < fromMethods.length; i++) {
            fromMethod = fromMethods[i];
            fromMethodName = fromMethod.getName();
            if (!fromMethodName.contains("get") || fromMethodName.contains("getId")) {
                continue;
            }
            //排除列表检测
            if (excludesList != null && excludesList.contains(fromMethodName.substring(3).toLowerCase())) {
                continue;
            }
            toMethodName = "set" + fromMethodName.substring(3);
            toMethod = findMethodByName(toMethods, toMethodName);
            if (toMethod == null) {
                continue;
            }
            Object value = null;
            try {
                value = fromMethod.invoke(from, new Object[0]);
                if (value == null) {
                    continue;
                }
                //集合类判空处理
                if (value instanceof Collection) {
                    Collection newValue = (Collection) value;
                    if (newValue.size() <= 0) {
                        continue;
                    }
                }
                toMethod.invoke(to, new Object[]{value});
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }
    }


    /**
     * 对象属性值复制，仅复制指定名称的属性值 --父类属性无法copy
     */
    @SuppressWarnings("unchecked")
    public static void copyPropertiesInclude(Class from, Class to, String[] includesArray) {
        List<String> includesList = null;
        if (includesArray != null && includesArray.length > 0) {
            includesList = Arrays.asList(includesArray);    //构造列表对象
        } else {
            return;
        }
        Method[] fromMethods = from.getClass().getDeclaredMethods();
        Method[] toMethods = to.getClass().getDeclaredMethods();
        Method fromMethod = null, toMethod = null;
        String fromMethodName = null, toMethodName = null;
        for (int i = 0; i < fromMethods.length; i++) {
            fromMethod = fromMethods[i];
            fromMethodName = fromMethod.getName();
            if (!fromMethodName.contains("get")) {
                continue;
            }
            //排除列表检测
            String str = fromMethodName.substring(3);
            if (!includesList.contains(str.substring(0, 1).toLowerCase() + str.substring(1))) {
                continue;
            }
            toMethodName = "set" + fromMethodName.substring(3);
            toMethod = findMethodByName(toMethods, toMethodName);
            if (toMethod == null) {
                continue;
            }
            Object value = null;
            try {
                value = fromMethod.invoke(from, new Object[0]);
                if (value == null) {
                    continue;
                }
                //集合类判空处理
                if (value instanceof Collection) {
                    Collection newValue = (Collection) value;
                    if (newValue.size() <= 0) {
                        continue;
                    }
                }
                toMethod.invoke(to, new Object[]{value});
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 从方法数组中获取指定名称的方法
     */
    public static Method findMethodByName(Method[] methods, String name) {
        for (int j = 0; j < methods.length; j++) {
            if (methods[j].getName().equals(name)) {
                return methods[j];
            }
        }
        return null;
    }


    /**
     * 复制字段的值
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyFields(Object source, Object target) {
        HashMap<String, Field> sourceFieldMap = getAllFields(source);
        HashMap<String, Field> targetFieldMap = getAllFields(target);
        Iterator entries = sourceFieldMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            String key = (String) entry.getKey();
            Field value = (Field) entry.getValue();
            value.setAccessible(true);
            try {
                if (value.get(source) != null && targetFieldMap.containsKey(key)) {
                    targetFieldMap.get(key).setAccessible(true);
                    targetFieldMap.get(key).set(target, value.get(source));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 复制字段的值
     *
     * @param source         源对象
     * @param target         目标对象
     * @param skipFieldNames 过滤的字段
     */
    public static void copyFields(Object source, Object target, String[] skipFieldNames) {
        Set<String> skips = new HashSet<>();
        if (skipFieldNames != null) {
            skips.addAll(Arrays.asList(skipFieldNames));
        }
        HashMap<String, Field> sourceFieldMap = getAllFields(source);
        HashMap<String, Field> targetFieldMap = getAllFields(target);
        Iterator entries = sourceFieldMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            String key = (String) entry.getKey();
            Field value = (Field) entry.getValue();
            value.setAccessible(true);
            try {
                if (value.get(source) != null && !skips.contains(key) && targetFieldMap.containsKey(key)) {
                    targetFieldMap.get(key).setAccessible(true);
                    targetFieldMap.get(key).set(target, value.get(source));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 获取对象所有的字段 包括父类
     *
     * @param obj 对象
     * @return 字段名与Field对象的映射map
     */
    private static HashMap<String, Field> getAllFields(Object obj) {
        Class sourceClass = obj.getClass();
        //获取对象所有字段 包括父类
        ArrayList<Field> sourceFields = new ArrayList<>();
        while (sourceClass != null) {
            sourceFields.addAll(Arrays.asList(sourceClass.getDeclaredFields()));
            sourceClass = sourceClass.getSuperclass();
        }
        //字段名去重
        HashMap<String, Field> sourceFieldMap = new HashMap<>(16);
        for (Field field : sourceFields) {
            sourceFieldMap.put(field.getName(), field);
        }
        return sourceFieldMap;
    }

}
