package com.gtp.demo.interfaces;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LimitRequest {
    //毫秒，分钟，小时 之间的转换用算数
    long time() default 5000; // 限制时间 单位：毫秒
    int count() default Integer.MAX_VALUE; // 允许请求的次数

}
