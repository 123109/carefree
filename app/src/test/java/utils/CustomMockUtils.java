package utils;

import org.junit.Assert;
import org.mockito.exceptions.base.MockitoAssertionError;

import java.lang.reflect.Field;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;

/**
 * Created by Administrator on 2017/2/9.
 */
public class CustomMockUtils {
    public static <T> T prepareVerify(String name,String methodName,Object... arguments) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.getCtClass(name);
        CtField field = new CtField(CtClass.intType,"$ctInjectCount",ctClass);
        ctClass.addField(field);
        String simpleName = name;
        CtMethod[] methods = ctClass.getDeclaredMethods(methodName);
        if (methods == null || methods.length == 0) {
            throw new MockitoAssertionError(simpleName+"类里没有找到方法："+methodName);
        }
        CtMethod verifyMethod = null;
        for (CtMethod method : methods) {
            CtClass[] params = method.getParameterTypes();
            if (arguments.length == 0){
                //不考虑输入参数
                if (methods.length > 1){
                    //未指定参数，但是指定类里有多个同名的方法，无法确认是哪个方法要计数
                    throw new MockitoAssertionError(simpleName+"类里有多个重载方法："+methodName+",请修改代码或者指定参数");
                }
                //默认是所有的执行都计数
                verifyMethod = method;
                break;
            }
            if (params.length == arguments.length){

            }
        }
        if (verifyMethod == null) {
            throw new MockitoAssertionError(simpleName+"类里有多个重载方法："+methodName+",但所有同名方法的参数都与输入的参数类型不匹配，请确认");
        }
        verifyMethod.insertBefore("$ctInjectCount++;");
//        ClassLoader loader = clazz.getClassLoader();
        Exception exception = new Exception();
        final String className = exception.getStackTrace()[1].getClassName();
        Class cl = Class.forName(className);
        ClassLoader loader = cl.getClassLoader();
        T t = (T) ctClass.toClass(loader).newInstance();
        return t;
    }

    public static void verifyNormal(Object object,int times) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField("$ctInjectCount");
        field.setAccessible(true);
        final int count = (int) field.get(object);
        if (count != times){
            throw  new MockitoAssertionError("期望执行次数是"+times+"，实际上执行了"+count+"次");
        }
        Assert.assertTrue(count == times);
    }
}
