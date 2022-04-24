package com.bro.lint_tools

import com.android.SdkConstants
import com.android.tools.lint.detector.api.*
import com.android.tools.lint.detector.api.Category.Companion.CORRECTNESS
import com.android.tools.lint.detector.api.Scope.Companion.RESOURCE_FILE_SCOPE
import org.w3c.dom.Element

/**
 * Created by zhangshan on 2022/4/22 17:39.
 * Description：本地创建个资源id命名检查规则，用来规范项目中的id统一命名
 */
class ViewIdDetector : LayoutDetector() {

    override fun getApplicableElements(): Collection<String>? {
        return listOf(
            SdkConstants.TEXTURE_VIEW,
            SdkConstants.IMAGE_VIEW,
            SdkConstants.BUTTON)
    }

    override fun visitElement(context: XmlContext, element: Element) {
        if (!element.hasAttributeNS(SdkConstants.ANDROID_URI, SdkConstants.ATTR_ID)) {
            return
        }
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
                    ISSUE,
                    attr,
                    context.getLocation(attr),
                    "ViewIdName建议使用view的缩写_xxx; ${element.tagName} 建议使用 `${expMsg}_xxx`"
                )
            }
        }
    }

    companion object {
        val ISSUE: Issue = Issue.create(
            "ViewIdCheck",
            "ViewId命名不规范",
            "ViewIdName建议使用 view的缩写加上_xxx,例如tv_xxx, iv_xxx",
            CORRECTNESS,
            5, Severity.ERROR,
            Implementation(
                ViewIdDetector::class.java,
                RESOURCE_FILE_SCOPE
            )
        )
    }
}