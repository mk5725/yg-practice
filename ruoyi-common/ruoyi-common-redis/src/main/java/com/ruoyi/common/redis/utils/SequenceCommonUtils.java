package com.ruoyi.common.redis.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SequenceCommonUtils {

    // private final static DateTimeFormatter sjbh = DateTimeFormatter.ofPattern("yyMMddHHmm");
    private final static DateTimeFormatter sjbh = DateTimeFormatter.ofPattern("yyMMdd");
    private final static DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");
    // private final static DateTimeFormatter md = DateTimeFormatter.ofPattern("MMdd");


    /**
     * 获取当天内的序列
     *
     * @param key    rediskey
     * @param length 字符长度，不足前补0
     * @return
     */
    public static String getTodaySequence(String key, int length) {
        key = "seq:day:" + key;
        if (RedisUtils.hasKey(key)) {
            return String.format("%0" + length + "d", RedisUtils.incrAtomicValue(key));
        } else {
            RedisUtils.setAtomicValue(key, 1);
            long expireTimes = getExpireTimes(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
            RedisUtils.expire(key, expireTimes);
            return String.format("%0" + length + "d", 1);
        }
    }

    /**
     * 获取超时秒数
     *
     * @param date
     * @return Long
     * @author xiao
     * @date 2023/6/21
     **/
    private static Long getExpireTimes(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        // 设置时间为第二天零点
        calendar.add(Calendar.DATE, 1);
        long expireTime = calendar.getTimeInMillis() - date.getTime();
        if (expireTime != 0) {
            expireTime = expireTime / 1000;
        }
        return expireTime;
    }


    /*-------------------------------------------------   编号    --------------------------------------------------------*/

    /**
     * 生成项目编号
     */
    public static String getProjectNo() {
        return "XM".concat(yyyyMMdd.format(LocalDateTime.now())).concat(getTodaySequence("XM", 4));
    }

}
