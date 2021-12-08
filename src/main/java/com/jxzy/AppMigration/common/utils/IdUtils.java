package com.jxzy.AppMigration.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class IdUtils {

    private static AtomicInteger counter = new AtomicInteger(0);

    /**
     * seqid
     */
    public static long getSeqIds() {
        if (counter.get() > 999999) {
            counter.set(1);
        }
        String returnValue = String.valueOf(System.currentTimeMillis()) + counter.incrementAndGet();
        return Long.parseLong(returnValue);
    }

    /**
     * UUID
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }



    /**
     * 获取数据区ID  15位
     * @Title: getDate
     * @date:   2019年12月7日 下午8:24:47
     * @author: 曲绍备
     * @param: @param sformat
     * @param: @return
     * @return: String
     * @throws
     */
    public static String getDate(String sformat) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(sformat);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static String getRandomNum(int num) {
        String numStr = "";
        for (int i = 0; i < num; i++) {
            numStr += (int) (10 * (Math.random()));
        }
        return numStr;
    }
    /**
     * 生成主键ID
     * @param:
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/5 0005
     */
    public static Long getSeqId() {
        String sformat = "MMddhhmmssSSS";
        int num = 2;
        String idStr = getDate(sformat) + getRandomNum(num);
        Long id = Long.valueOf(idStr);
        return id;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1; i++) {
            System.out.println(getSeqId());
        }
    }
}

