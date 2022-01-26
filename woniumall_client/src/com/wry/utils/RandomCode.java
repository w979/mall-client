package com.wry.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.logging.SimpleFormatter;

/**
 * 生成随机码
 */
public class RandomCode {

    /**
     * 生成13位商品编号
     * @return
     */
    public static String getGoodsNo(){
        String[] codes = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String code = "";
        Random random = new Random();
        for (int i = 0; i < 13; i++) {
            String str = codes[random.nextInt(codes.length)];
            code += str;
        }
        return code;
    }

    /**
     * 邮箱六位激活码
     * @return
     */
    public static String getEmailCode(){
        String[] codes = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
                "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
        String code = "";
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            String str = codes[random.nextInt(codes.length)];
            code += str;
        }
        return code;
    }

    /**
     * 生成订单编号
     * @return
     */
    public static String getOrderNo(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssssss");
        String no ="WN"+ sdf.format(new Date());
        return no;
    }
}
