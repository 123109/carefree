package utils.builder;

import android.support.annotation.Nullable;

import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.mockito.exceptions.base.MockitoAssertionError;
import org.mockito.internal.creation.util.SearchingClassLoader;
import org.powermock.core.MockRepository;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.spi.MethodInvocationControl;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.lang.reflect.Field;
import java.util.Map;

import utils.UncleMockException;

/**
 * mock utils
 * Created by Administrator on 2017/2/12.
 */

public class MockUtils {
    static void checkStaticMock(final Class mock) {
        Map<Class<?>, MethodInvocationControl> classMocks = Whitebox.getInternalState(MockRepository.class,"classMocks",MockRepository.class);
        if (!classMocks.containsKey(mock)){
            throw new UncleMockException("\n\n静态方法要先调用MockUtils.mockStatic("+mock.getSimpleName()+".class)");
        }
    }

    static void checkMocked(Object object){
        Class clazz = object.getClass();
        ClassLoader classloader = clazz .getClassLoader();
        if (!(classloader instanceof SearchingClassLoader)){
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.getType().getName().startsWith("org.mockito")){
                    return;
                }
            }

            //这不是一个被mock的对象
            String name = clazz.getSimpleName();
            throw new UncleMockException("\n\n模拟的对象必须是mock出来或者spy出来的，例如：\n" +
                    name + " mock = MockUtils.Mock("+name+".class) 或者\n" +
                    name + " spy = MockUtils.spy(new "+name+"())") ;
        }
    }

    public static void checkPrepare(final Class mock) {
        Class from = getTestOriginClass();
        assert from != null;
        RunWith runWith = (RunWith) from.getAnnotation(RunWith.class);
        if (runWith != null){
            Class<? extends Runner> runner = runWith.value();
            if (runner != PowerMockRunner.class){
                throw new UncleMockException("\n\n请在"+from.getSimpleName()+"类声明处添加以下注解\n" +
                        "\"@RunWith(PowerMockRunner.class)");
            }
        }else{
            throw new UncleMockException("\n\n请在"+from.getSimpleName()+"类声明处添加以下注解\n" +
                    "\"@RunWith(PowerMockRunner.class)");
        }
        PrepareForTest test = (PrepareForTest) from.getAnnotation(PrepareForTest.class);
        if (test != null){
            Class[] prepared = test.value();
            for (Class aClass : prepared) {
                if (aClass == mock){
                    return;
                }
            }
        }
        throw new UncleMockException("\n\n请在"+from.getSimpleName()+"类声明处添加以下注解\n" +
                "\"@PrepareForTest("+mock.getSimpleName()+".class)\"，\n" +
                "如果已有该注解，请在注解体里添加"+mock.getSimpleName()+".class");
    }

    @Nullable
    private static Class getTestOriginClass() {
        StackTraceElement origin = getCallOrginStackTraceElement();
        try {
            return Class.forName(origin.getClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new MockitoAssertionError(e.getMessage());
        }
    }

    private static String getCallOrginMethodName(){
        StackTraceElement origin = getCallOrginStackTraceElement();
        return origin.getMethodName();
    }

    @Nullable
    private static StackTraceElement getCallOrginStackTraceElement() {
        StackTraceElement[] trace = new Exception("").getStackTrace();
        StackTraceElement origin = null;
        final int length = trace.length;
        for (int i = 0; i < length; i++) {
            StackTraceElement element = trace[i];
            String name = element.getClassName();
            if (name.equals("sun.reflect.NativeMethodAccessorImpl")) {
                origin = trace[i- 1];
                break;
            }
        }
        return origin;
    }


    public static void check(Object object){
        if (object instanceof Class){
            //要测试一个静态方法
            checkStaticMock((Class) object);
        }else{
            checkMocked(object);
        }
    }

    public static boolean isEmpty(String input){
        return input == null || input.equals("");
    }
}
