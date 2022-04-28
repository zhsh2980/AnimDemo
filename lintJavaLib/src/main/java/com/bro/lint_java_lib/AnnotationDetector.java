package com.bro.lint_java_lib;

import com.android.tools.lint.client.api.UElementHandler;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.uast.UAnnotation;
import org.jetbrains.uast.UElement;

import java.util.ArrayList;
import java.util.List;

public class AnnotationDetector extends Detector implements Detector.UastScanner {

    static final Issue ISSUE = Issue.create(
            "router_annotation_issue",    //唯一 ID
            "测试代码",    //简单描述
            "上线前删除!",  //详细描述
            Category.CORRECTNESS,   //问题种类（正确性、安全性等）
            6,  //权重
            Severity.ERROR,   //问题严重程度（忽略、警告、错误）
            new Implementation(     //实现，包括处理实例和作用域
                    AnnotationDetector.class,
                    Scope.JAVA_FILE_SCOPE));

    @Override
    public List<Class<? extends UElement>> getApplicableUastTypes() {
        List<Class<? extends UElement>> types = new ArrayList<>();
        types.add(UAnnotation.class);
        return types;
    }

    @Override
    public UElementHandler createUastHandler(@NotNull JavaContext context) {
        return new UElementHandler() {

            @Override
            public void visitAnnotation(@NotNull UAnnotation node) {
                isAnnotation(node);
            }

            private void isAnnotation(UAnnotation node) {
                String type = node.getQualifiedName();
                String packageName = "anim.bro.com.practice.annotation.";
                if (type.equals(packageName + "MustDeleteMethod")
                        || type.equals(packageName + "MustDeleteClass")) {
                    context.report(ISSUE, node, context.getLocation(node),
                            "测试代码, 上线前务必删除!");
                }
            }
        };
    }

}
