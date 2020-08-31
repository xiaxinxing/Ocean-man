package com.ocean.core.commons.conf;

import java.time.LocalDate;

/**
 * @author : xxx
 * @date : 2020-04-29 14:22
 * 分表之后的查询条件 日期判断
 **/

public class SharingTableContext {
    private static final ThreadLocal<LocalDate> contextHolder = new ThreadLocal<>();

    public static void setDate(LocalDate value) {
        contextHolder.set(value);
    }

    public static LocalDate getDate() {
        return contextHolder.get();
    }

    public static void clearDate() {
        contextHolder.remove();
    }
}
