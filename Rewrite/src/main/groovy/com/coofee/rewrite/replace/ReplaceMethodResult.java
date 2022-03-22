package com.coofee.rewrite.replace;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ReplaceMethodResult {

    public static class Item {
        public final String moduleName;
        public final String className;
        public final String methodName;
        public final int lineNo;

        public final String callMethod;
        public final String replaceMethod;

        public Item(String moduleName, String className, String methodName, int lineNo, String callMethod, String replaceMethod) {
            this.moduleName = moduleName;
            this.className = className;
            this.methodName = methodName;
            this.lineNo = lineNo;
            this.callMethod = callMethod;
            this.replaceMethod = replaceMethod;
        }
    }

    public final Map<String, List<Item>> resultByCallMethodMap = new LinkedHashMap<>();

    public final Map<String, List<Item>> resultByModuleMap = new LinkedHashMap<>();

    public void add(String moduleName, String className, String methodName, int lineNo, String callMethod, String replaceMethod) {
        Item item = new Item(moduleName, className, methodName, lineNo, callMethod, replaceMethod);
        addItemByCallMethod(callMethod, item);
        addItemByModule(moduleName, item);
    }

    private void addItemByCallMethod(String callMethod, Item item) {
        List<Item> items = resultByCallMethodMap.get(callMethod);
        if (items == null) {
            items = new ArrayList<>();
            resultByCallMethodMap.put(callMethod, items);
        }
        items.add(item);
    }

    private void addItemByModule(String module, Item item) {
        List<Item> items = resultByModuleMap.get(module);
        if (items == null) {
            items = new ArrayList<>();
            resultByModuleMap.put(module, items);
        }
        items.add(item);
    }

}
