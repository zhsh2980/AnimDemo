package anim.bro.com.practice.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zhangshan on 2022/4/26 11:24.
 * Description：测试代码提示
 * 作用于方法
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface MustDeleteMethod {
}