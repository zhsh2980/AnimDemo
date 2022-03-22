package com.coofee.rewrite.manifest.handler

import com.coofee.rewrite.manifest.Util
import groovy.util.slurpersupport.GPathResult

class ReplaceContentProviderHandler extends BaseAndroidManifestHandler {

    private Map<String, String> replaceContentProviders

    ReplaceContentProviderHandler(String packageName, Map<String, String> replaceContentProviders) {
        super(packageName)
        this.replaceContentProviders = replaceContentProviders
    }

    @Override
    GPathResult process(GPathResult rootNode) {
        println("[RewritePlugin.AndroidManifestHandler.ReplaceContentProviderHandler] start replace content provider.")

        if (replaceContentProviders != null && replaceContentProviders.size() > 0) {
            rootNode.'**'.findAll { node ->
                def tagName = node.name()
                return 'provider' == tagName

            }.each { node ->
                def className = Util.fullClassName(this.packageName, node.'@android:name' as String)
                if (className != null) {
                    def replaceClassName = replaceContentProviders.get(className)
                    if (replaceClassName != null && replaceClassName.length() > 0) {
                        node.'@android:name' = replaceClassName
                        println("[RewritePlugin.AndroidManifestHandler.ReplaceContentProviderHandler] repalce $className with $replaceClassName.")
                    }
                }
            }
        }

        println("[RewritePlugin.AndroidManifestHandler.ReplaceContentProviderHandler] end replace content provider.")
        return rootNode
    }
}
