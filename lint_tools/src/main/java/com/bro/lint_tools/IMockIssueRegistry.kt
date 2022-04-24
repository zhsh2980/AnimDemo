package com.bro.lint_tools

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.Issue

/**
 * Created by zhangshan on 2022/4/22 17:47.
 * Description：实现IssueRegistry并添加对应的自定义Issue
 */
class IMockIssueRegistry : IssueRegistry() {
    override val issues: List<Issue>
        get() = listOf(
            ViewIdDetector.ISSUE
        )
}