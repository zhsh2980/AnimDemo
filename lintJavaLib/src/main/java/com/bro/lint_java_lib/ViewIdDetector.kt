package com.bro.lint_java_lib

import com.android.SdkConstants
import com.android.tools.lint.detector.api.*
import com.android.tools.lint.detector.api.Category.Companion.CORRECTNESS
import com.android.tools.lint.detector.api.Scope.Companion.RESOURCE_FILE_SCOPE
import org.w3c.dom.Element

/**
 * Created by zhangshan on 2022/4/22 17:39.
 * Description：本地创建个资源 id 命名检查规则，用来规范项目中的id统一命名
 */
class ViewIdDetector : LayoutDetector() {

    override fun getApplicableElements(): Collection<String>? {
        return listOf(
            SdkConstants.TEXT_VIEW,
            SdkConstants.IMAGE_VIEW,
            SdkConstants.BUTTON)
    }

    override fun visitElement(context: XmlContext, element: Element) {
        //处理 id 前缀
        if (element.hasAttributeNS(SdkConstants.ANDROID_URI, SdkConstants.ATTR_ID)) {
            dealIdError(context, element)
        }
        //处理 textsize 单位必须为 dp
        if (element.hasAttributeNS(SdkConstants.ANDROID_URI, SdkConstants.ATTR_TEXT_SIZE)) {
            dealTextsizeError(context, element)
        }
    }

    private fun dealTextsizeError(context: XmlContext, element: Element) {
        val attr = element.getAttributeNodeNS(SdkConstants.ANDROID_URI, SdkConstants.ATTR_TEXT_SIZE)
        val value = attr.value
//        if (value.startsWith(SdkConstants.ATTR_TEXT_SIZE)) {
        if (!value.contains("dp")) {
            context.report(
                ISSUE_TEXTSIZE,
                attr,
                context.getLocation(attr),
                "${element.tagName}  textsize 单位必须为 dp"
            )
//            }
        }
    }

    private fun dealIdError(context: XmlContext, element: Element) {
        val attr = element.getAttributeNodeNS(SdkConstants.ANDROID_URI, SdkConstants.ATTR_ID)
        val value = attr.value
        if (value.startsWith(SdkConstants.NEW_ID_PREFIX)) {
            val idValue = value.substring(SdkConstants.NEW_ID_PREFIX.length)
            var matchRule = true
            var expMsg = ""
            when (element.tagName) {
                SdkConstants.TEXT_VIEW -> {
                    expMsg = "tv"
                    matchRule = idValue.startsWith(expMsg)
                }
                SdkConstants.IMAGE_VIEW -> {
                    expMsg = "iv"
                    matchRule = idValue.startsWith(expMsg)
                }
                SdkConstants.BUTTON -> {
                    expMsg = "btn"
                    matchRule = idValue.startsWith(expMsg)
                }
            }
            if (!matchRule) {
                context.report(
                    ISSUE_ID,
                    attr,
                    context.getLocation(attr),
                    "ViewIdName 建议使用 view 的缩写 _xxx; ${element.tagName} 建议使用 `${expMsg}_xxx`"
                )
            }
        }

    }

    companion object {
        val ISSUE_ID: Issue = Issue.create(
            "ViewIdCheck",
            "ViewId命名不规范",
            "ViewIdName建议使用 view的缩写加上_xxx,例如tv_xxx, iv_xxx",
            CORRECTNESS,
            6, Severity.ERROR,
            Implementation(
                ViewIdDetector::class.java,
                RESOURCE_FILE_SCOPE
            )
        )

        val ISSUE_TEXTSIZE: Issue = Issue.create(
            "ViewIdCheck",
            "textsize 单位必须为dp",
            "textsize 单位必须为dp, 例如 xxdp",
            CORRECTNESS,
            6, Severity.ERROR,
            Implementation(
                ViewIdDetector::class.java,
                RESOURCE_FILE_SCOPE
            )
        )
    }
}