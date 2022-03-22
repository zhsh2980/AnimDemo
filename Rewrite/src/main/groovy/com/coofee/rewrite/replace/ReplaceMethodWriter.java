package com.coofee.rewrite.replace;

import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.TransformInvocation;
import com.coofee.rewrite.Rewriter;
import com.coofee.rewrite.util.StringUtil;
import com.google.gson.Gson;

import org.apache.commons.io.FileUtils;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.Method;
import org.objectweb.asm.tree.*;

import java.io.File;
import java.util.*;

public class ReplaceMethodWriter extends Rewriter.Adapter {
    private final ReplaceMethodExtension mReplaceMethodExtension;
    private final ClassLoader mClassLoader;
    private final File rootFolder;

    private final Map<String, Class<?>> mLoadedClassMap = new HashMap<>();
    private final ReplaceMethodResult mReplaceMethodResult = new ReplaceMethodResult();

    public ReplaceMethodWriter(File rootFolder, ReplaceMethodExtension replaceMethodExtension, ClassLoader classLoader) {
        this.rootFolder = rootFolder;
        this.mReplaceMethodExtension = replaceMethodExtension;
        this.mClassLoader = classLoader;
    }

    private Class<?> findClassBy(String name) {
        try {
            Class<?> clazz = mLoadedClassMap.get(name);
            if (clazz == null) {
                name = name.replace('/', '.');
                clazz = mClassLoader.loadClass(name);
                mLoadedClassMap.put(name, clazz);
            }
            return clazz;
        } catch (Throwable e) {
            System.out.println("[RewritePlugin] cannot find class name=" + name);
            e.printStackTrace();
        }
        return null;
    }

    public ReplaceMethodInfo findReplaceMethodInfo(String className, String methodName, String methodDesc) {
        for (ReplaceMethodInfo methodInfo : this.mReplaceMethodExtension.replaceMethodInfoSet) {
            Method srcMethod = methodInfo.srcMethod;
            if (StringUtil.equals(srcMethod.getName(), methodName) && StringUtil.equals(srcMethod.getDescriptor(), methodDesc)) {
                Class<?> clazz = findClassBy(className);
                Class<?> configClass = findClassBy(methodInfo.srcClass);
                if (configClass != null && clazz != null && configClass.isAssignableFrom(clazz)) {
                    return methodInfo;
                }
            }
        }

        return null;
    }

    @Override
    public ClassNode transform(QualifiedContent input, ClassNode classNode) {
        final String className = classNode.name;
        if (this.mReplaceMethodExtension.isExcluded(className)) {
            return classNode;
        }

        final String moduleName = input.getName();
        final String sourceFile = classNode.sourceFile;

        List<MethodNode> methods = classNode.methods;
        for (MethodNode methodNode : methods) {
            InsnList instructions = methodNode.instructions;
            ListIterator<AbstractInsnNode> iterator = instructions.iterator();
            int lineNo = -1;
            while (iterator.hasNext()) {
                AbstractInsnNode next = iterator.next();

                if (next instanceof LineNumberNode) {
                    lineNo = ((LineNumberNode) next).line;
                }

                if (next instanceof MethodInsnNode) {
                    MethodInsnNode insnNode = (MethodInsnNode) next;
                    if (this.mReplaceMethodExtension.isExcluded(insnNode.owner)) {
                        continue;
                    }

                    ReplaceMethodInfo matchedMethodInfo = findReplaceMethodInfo(insnNode.owner, insnNode.name, insnNode.desc);
                    if (matchedMethodInfo != null) {
                        String methodName = methodNode.name;
                        String methodDesc = methodNode.desc;
                        String methodSignature = methodNode.signature;
                        System.out.println("[RewritePlugin] replace moduleName=" + moduleName + ", sourceFile=" + sourceFile + ", lineNo=" + lineNo +
                                ", className=" + StringUtil.replace(className, '/', '.') +
                                ", methodName=" + methodName + ", methodDesc=" + methodDesc + ", methodSignature=" + methodSignature +
                                "; owner=" + insnNode.owner + ", method=" + insnNode.name + ", desc=" + insnNode.desc +
                                " by owner=" + matchedMethodInfo.destClass + ", method=" + matchedMethodInfo.destMethod.getName() + ", desc=" + matchedMethodInfo.destMethod.getDescriptor()
                        );
                        mReplaceMethodResult.add(moduleName,
                                replaceWithDot(className),
                                methodName,
                                lineNo,
                                replaceWithDot(insnNode.owner)+"#"+insnNode.name,
                                replaceWithDot(matchedMethodInfo.destClass)+"#"+matchedMethodInfo.destMethod.getName());
//                        String shadowOwner = "com/coofee/rewrite/Shadow";
//                        String shadowName = "transform";
//                        String shadowDesc = "(Lcom/coofee/rewrite/Rewriter;Lcom/android/build/api/transform/QualifiedContent;Lorg/objectweb/asm/tree/ClassNode;)Lorg/objectweb/asm/tree/ClassNode;";
                        insnNode.setOpcode(Opcodes.INVOKESTATIC);
                        insnNode.owner = matchedMethodInfo.destClass;
                        insnNode.name = matchedMethodInfo.destMethod.getName();
                        insnNode.desc = matchedMethodInfo.destMethod.getDescriptor();
                    }
                }
            }
        }

        return classNode;
    }

    @Override
    public void postTransform(TransformInvocation transformInvocation) {
        super.postTransform(transformInvocation);
        File resultFile;
        if (mReplaceMethodExtension.outputFile == null || mReplaceMethodExtension.outputFile.isEmpty()) {
            resultFile = new File(rootFolder, "replace_method_result.json");
        } else {
            resultFile = new File(mReplaceMethodExtension.outputFile);
        }
        try {
            String json = new Gson().toJson(mReplaceMethodResult.resultByCallMethodMap);
            FileUtils.writeStringToFile(resultFile, json);
            System.out.println("[RewritePlugin] replace method result success write to file " + resultFile.getAbsolutePath());
        } catch (Throwable e) {
            new Throwable("[RewritePlugin] replace method result fail write to file " + resultFile.getAbsolutePath(), e).printStackTrace();
        }

        File resultByModuleFile = new File(resultFile.getParentFile(), "replace_method_result_by_module.json");
        try {
            String json = new Gson().toJson(mReplaceMethodResult.resultByModuleMap);
            FileUtils.writeStringToFile(resultByModuleFile, json);
            System.out.println("[RewritePlugin] replace method result success write to file " + resultByModuleFile.getAbsolutePath());
        } catch (Throwable e) {
            new Throwable("[RewritePlugin] replace method result fail write to file " + resultByModuleFile.getAbsolutePath(), e).printStackTrace();
        }
    }

    private static String replaceWithDot(String str) {
        return StringUtil.replace(str, '/', '.');
    }
}
