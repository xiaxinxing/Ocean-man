package com.ocean.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @date 2019-11-07
 * @Author xxx
 * @desc java8 对于时间的处理
 */


public class LocalDateUtils {

    /**
     * 获取当前的时间
     *
     * @return
     */
    public LocalDate now() {
        return LocalDate.now();
    }


    public LocalDate getDate(int year, int month, int day) {
        return LocalDate.of(year, month, day);
    }

    public LocalDateTime getDateTime(int year, int month, int day, int hour, int min, int second) {
        return LocalDateTime.of(year, month, day, hour, min, second);
    }

    /**
     * 获取未来几天的时间
     *
     * @param day
     * @return
     */
    public LocalDateTime getFutureDay(Long day) {
        LocalDateTime now = LocalDateTime.now();
        return now.plusDays(day);
    }

    /**
     * 获取之前某天的
     *
     * @param day
     * @return
     */
    public LocalDateTime getBeforeDay(Long day) {
        LocalDateTime now = LocalDateTime.now();
        return now.minusDays(day);
    }

    public LocalDateTime getBeforeYearToday(Long day) {
        LocalDateTime now = LocalDateTime.now();
        return now.minusDays(day);
    }


}
