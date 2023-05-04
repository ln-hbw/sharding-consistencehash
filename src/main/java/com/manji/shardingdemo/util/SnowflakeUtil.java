package com.manji.shardingdemo.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 *
 * @author manji
 * @Date 2023/5/4
 */
public class SnowflakeUtil {

    public static Long snowflake(){
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        return snowflake.nextId();
    }

    public static void main(String[] args) {
        long id = snowflake();
        System.out.println(id);
    }

}
