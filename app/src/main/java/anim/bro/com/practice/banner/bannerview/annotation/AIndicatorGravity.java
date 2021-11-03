package anim.bro.com.practice.banner.bannerview.annotation;

import static anim.bro.com.practice.banner.bannerview.constants.IndicatorGravity.END;
import static anim.bro.com.practice.banner.bannerview.constants.IndicatorGravity.START;
import static anim.bro.com.practice.goods.banner.IndicatorGravity.CENTER;

import androidx.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 *   Created by zhangpan on 2019-10-18.
 *   Description:指示器显示位置
 * </pre>
 */
@IntDef({ CENTER, START, END })
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.PARAMETER)
public @interface AIndicatorGravity {

}
