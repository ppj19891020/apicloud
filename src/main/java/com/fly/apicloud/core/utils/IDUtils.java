package com.fly.apicloud.core.utils;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

/**
 * 主键生成器
 */
public class IDUtils {

    private final static String CODE = "9214035678";

    public static Long decode(String eid) {
        try {
            Long.parseLong(eid);
        } catch (NumberFormatException e) {
            return null;
        }

        try {
            eid = eid.substring(2, eid.length());
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < eid.length(); i++) {
                builder.append(CODE.indexOf(eid.charAt(i)));
            }
            Long v = Long.valueOf(builder.toString());
            return (v - 183729) / 37;
        } catch (Exception e) {
            return null;
        }

    }

    public static String encode(Long id) {
        String eid = String.valueOf(id * 37 + 183729);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < eid.length(); i++) {
            builder.append(CODE.charAt(Integer.parseInt(String.valueOf(eid.charAt(i)))));
        }
        return "10" + builder.toString();
    }

    //获取UUID
    public static String getUUIDFromGenerators() {
        TimeBasedGenerator gen = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
        String uuid = gen.generate().toString();
        return uuid.replaceAll("-", "");
    }

}
