package dodola.patch;

import javassist.ClassPool
import javassist.CtClass
import javassist.CtConstructor
import javassist.CtMethod
import javassist.CtNewConstructor
import javassist.CtNewMethod

public class PatchClass {
    public static void process(String buildDir, String lib) {

        println(lib)
        ClassPool classes = ClassPool.getDefault()
        classes.appendClassPath("/Users/baidu/Library/Android/sdk/platforms/android-19/android.jar")
        classes.appendClassPath(buildDir)
        classes.appendClassPath(lib)



        CtClass c = classes.getCtClass("dodola.hotfix.BugClass")
        println("====添加构造方法====")
        def constructor = c.getConstructors()[0];
        constructor.insertBefore("System.out.println(dodola.hackdex.AntilazyLoad.class);")
        c.writeFile(buildDir)



        CtClass c1 = classes.getCtClass("dodola.hotfix.LoadBugClass")
        println("====添加构造方法====")
        def constructor1 = c1.getConstructors()[0];
        constructor1.insertBefore("System.out.println(dodola.hackdex.AntilazyLoad.class);")
        c1.writeFile(buildDir)


        growl("ClassDumper", "${c.frozen}")
    }

    static void growl(String title, String message) {
        def proc = ["osascript", "-e", "display notification \"${message}\" with title \"${title}\""].execute()
        if (proc.waitFor() != 0) {
            println "[WARNING] ${proc.err.text.trim()}"
        }
    }
}
