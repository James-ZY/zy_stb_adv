package com.gospell.aas.common.utils;

/**
 * Created by wdl on 14-3-4.
 */
import java.util.Collection;

public class ElFunctions {

    // 函数必须是 public static 修饰
    public static boolean contains(@SuppressWarnings("rawtypes") Collection collection, Object obj) {
        if (collection == null || collection.isEmpty()) return false;
        return collection.contains(obj);
    }
}