package com.ykx.baselibs.https;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * <p>
 * Description：用来获取泛型类里面泛型的实际类型
 * </p>
 *
 * @author wangxiaohu
 */
public class TTypeUtils {

    /**
     * 获取泛型类型(包名)，如果是List类型，则获取List中泛型的类型的包名
     *
     * @param clazz
     * @return
     */
    public static String getTypePackage(Class<?> clazz) {
        Type type = getTType(clazz);
        if (type != null) {
            if (type instanceof ParameterizedType) {//泛型是List<T>，则返回List中泛型的包名
//                Type rawType = ((ParameterizedType) type).getRawType();
//                if (rawType == List.class) {
//                    //types[0].toString()="java.util.List<com.xxx.xxx.xxx>"
//                    String s = type.toString();
//                    String typePackageInList = s.substring(s.indexOf("<") + 1, s.indexOf(">"));
//                    return typePackageInList;
//                }
                Type[] types = ((ParameterizedType) type).getActualTypeArguments();
                if (types != null && types.length > 0) {
                    String typePackage = ((Class) types[0]).getName();
                    return typePackage;
                }
            } else {//泛型是Bean
                //types[0].toString()="class com.xxx.xxx.xxx"
                String typePackage = ((Class) type).getName();
                return typePackage;
            }
        }
        return null;
    }

    /**
     * 获取泛型类型，获取类似HttpCallBack<T>中T的类型
     *
     * @param clazz
     * @return
     */
    public static Type getTType(Class<?> clazz) {
        Type mySuperClassType = clazz.getGenericSuperclass();
        //ParameterizedType 简单说来就是形如“ 类型<> ”的类型，如HttpCallBack<T>，List<T>
        //例子：getActualTypeArguments返回Map<String,User>里的String和User，返回[String.class,User.class]
        Type[] types = ((ParameterizedType) mySuperClassType).getActualTypeArguments();
        if (types != null && types.length > 0) {
            //T
            return types[0];
        }
        return null;
    }

    /**
     * 判断是否是List
     *
     * @param clazz
     * @return
     */
    public static boolean isList(Class<?> clazz) {
        Type type = getTType(clazz);
        if (type != null) {
            if (type instanceof ParameterizedType) {//支持返回值是List<User>类型的
                //例子：getRawType获取Map<String,User>里的Map,所以返回值是Map.class
                //这里是获取List<T>里的List,所以返回值是List.class
                Type rawType = ((ParameterizedType) type).getRawType();
                if (rawType == List.class) {
                    return true;
                }
            }
        }
        return false;
    }
}
