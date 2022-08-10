package com.frizo.lab.mybatis.utils;

import com.frizo.lab.mybatis.model.Country;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BeanUtils {

    // 檢查 JavaBean 的 Field 是否恰當填值 (不為空字串或 null)
    // 除了標註 @NonEssential 的 Field 不需要檢查
    public static boolean isBeanFilledProperly(Object object) throws InvocationTargetException, IllegalAccessException {
        Field[] fields = object.getClass().getDeclaredFields();
        List<String> skipMathodNames = new ArrayList<>();
        for (Field field : fields) {
            if (field.getAnnotation(NonEssential.class) != null) {
                if (field.getType().toString().equals("boolean") || field.getType().toString().equals("class java.lang.Boolean")) {
                    skipMathodNames.add("is" + upperCaseFirst(field.getName()));
                } else {
                    skipMathodNames.add("get" + upperCaseFirst(field.getName()));
                }
            }
        }

        Method[] methods = object.getClass().getDeclaredMethods();
        for (Method m : methods) {
            if (m.getName().startsWith("get") || m.getName().startsWith("is")) {
                if (skipMathodNames.contains(m.getName())) {
                    continue;
                }
                Object value = m.invoke(object);
                if (value == null) {
                    return false;
                }
                if (value instanceof String) {
                    if (value.equals("")) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // 暫不支援 boolean
    public static boolean isBeanFilledProperly(Object object, List<String> nonEssentialFieldNames) throws InvocationTargetException, IllegalAccessException {
        List<String> skipMathodNames = new ArrayList<>();
        for (String fieldName : nonEssentialFieldNames) {
            skipMathodNames.add("get" + upperCaseFirst(fieldName));
        }

        Method[] methods = object.getClass().getDeclaredMethods();
        for (Method m : methods) {
            if (m.getName().startsWith("get")) {
                if (skipMathodNames.contains(m.getName())) {
                    continue;
                }
                Object value = m.invoke(object);
                if (value == null) {
                    return false;
                }
                if (value instanceof String) {
                    if (value.equals("")) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    public static String upperCaseFirst(String val) {
        char[] arr = val.toCharArray();
        arr[0] = Character.toUpperCase(arr[0]);
        return new String(arr);
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Country country = new Country();
        //country.setCountrycode("TW");
        country.setCountryname("台灣");
        country.setId(1L);
        country.setA(true);
        country.setB(true);
        List<String> nonEssentialFields = new ArrayList<>();
        nonEssentialFields.add("countrycode");
        System.out.println(isBeanFilledProperly(country, nonEssentialFields));
    }
}
