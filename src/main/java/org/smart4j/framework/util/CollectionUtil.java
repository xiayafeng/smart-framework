package org.smart4j.framework.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.Collection;
import java.util.Map;


/**
 * 集合工具类
 */
public final class CollectionUtil {
    /**
     * 判断collection是否为空
     */
    public static boolean isEmpty(Collection<?> collection) {
        return CollectionUtils.isEmpty(collection);
    }
    /**
     * 判断collection是否非空
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !CollectionUtils.isEmpty(collection);
    }
    /**
     * 判断map是否为空
     */
    public static boolean isEmpty(Map<?, ?> map){
        return MapUtils.isEmpty(map);
    }
    /**
     * 判断map是否非空
     */
    public static boolean isNotEmpty(Map<?, ?> map){
        return !MapUtils.isEmpty(map);
    }







}
