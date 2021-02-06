package com.fei.aspectjdemo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: CheckNet
 * @Description: 检测网络
 * @Author: Fei
 * @CreateDate: 2021-02-06 16:55
 * @UpdateUser: 更新者
 * @UpdateDate: 2021-02-06 16:55
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckNet {
}
