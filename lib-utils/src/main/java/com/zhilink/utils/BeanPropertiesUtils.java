package com.zhilink.utils;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 对象copy
 * 需要get，set方法
 *
 * @author copy的
 * @date 2018-9-22 16:08
 */
public class BeanPropertiesUtils {
    /**
     * 利用反射实现对象之间属性复制
     */
    public static void copyProperties(Object from, Object to) throws Exception {
        copyPropertiesExclude(from, to, null);
    }

    /**
     * 复制对象属性
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
     * 对象属性值复制，仅复制指定名称的属性值
     */
    @SuppressWarnings("unchecked")
    public static void copyPropertiesInclude(Object from, Object to, String[] includesArray) {
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
}
