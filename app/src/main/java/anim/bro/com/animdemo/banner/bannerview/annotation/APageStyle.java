package anim.bro.com.animdemo.banner.bannerview.annotation;

import static anim.bro.com.animdemo.banner.bannerview.constants.PageStyle.MULTI_PAGE;
import static anim.bro.com.animdemo.banner.bannerview.constants.PageStyle.MULTI_PAGE_OVERLAP;
import static anim.bro.com.animdemo.banner.bannerview.constants.PageStyle.MULTI_PAGE_SCALE;
import static anim.bro.com.animdemo.banner.bannerview.constants.PageStyle.NORMAL;

import androidx.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 *   Created by zhangpan on 2019-11-06.
 *   Description:
 * </pre>
 */
@IntDef({ NORMAL, MULTI_PAGE, MULTI_PAGE_OVERLAP, MULTI_PAGE_SCALE })
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.PARAMETER)
public @interface APageStyle {
}
