package utils;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import org.mockito.exceptions.base.MockitoAssertionError;
import org.mockito.exceptions.verification.TooLittleActualInvocations;
import org.mockito.exceptions.verification.TooManyActualInvocations;
import org.mockito.internal.creation.util.SearchingClassLoader;
import org.mockito.stubbing.OngoingStubbing;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.mockito.verification.PrivateMethodVerification;
import org.powermock.core.MockRepository;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.spi.MethodInvocationControl;
import org.powermock.reflect.Whitebox;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import rx.Observable;

import static org.mockito.Mockito.times;

/**
 * Created by Administrator on 2017/2/9.
 */
public class MockUtils {

    public static <T> T mock(Class<T> tClass){
        if (tClass == null) {
            throw new MockitoAssertionError("mock对象类不能为空");
        }
        if (Modifier.isFinal(tClass.getModifiers())){
            //被模拟的类是个final的，需要确认它是否被mock
            checkPrepare(tClass);
        }
        T t = PowerMockito.mock(tClass);
        return t;
    }

    /**
     * @param willBeNew 将要被创建新实例的类
     * @param callNew 执行创建新实例的代码所在的类，比如在B类里会创建一个A类的实例，那么willBeNew是A，callNew是B
     * @param <T>
     * @return mock出来的实例
     * @throws Exception
     */
    public static <T> T whenNew(Class<T> willBeNew,Class<?> callNew) throws Exception {
        T t = mock(willBeNew);
        checkPrepare(willBeNew);
        if (callNew != null) {
            checkPrepare(callNew);
        }
        PowerMockito.whenNew(willBeNew).withAnyArguments().thenReturn(t);
        return t;
    }

    /**
     * @param willBeNew 将要被创建新实例的类
     * @param callNew 执行创建新实例的代码所在的类，比如在B类里会创建一个A类的实例，那么willBeNew是A，callNew是B
     * @param t   指定的实例
     * @param <T>
     * @throws Exception
     */
    public static <T> void whenNewWithSpecifiedObject(Class<T> willBeNew,T t,Class<?> callNew) throws Exception {
        checkPrepare(willBeNew);
        if (callNew != null) {
            checkPrepare(callNew);
        }
        PowerMockito.whenNew(willBeNew).withAnyArguments().thenReturn(t);
    }

    public static <T> void whenNewWithSpecifiedObject(T t,Class<?> callNew) throws Exception {
        final Class<?> aClass = t.getClass();
        checkPrepare(aClass);
        if (callNew != null) {
            checkPrepare(callNew);
        }
        final OngoingStubbing<T> stubbing = (OngoingStubbing<T>) PowerMockito.whenNew(aClass).withAnyArguments();
        stubbing.thenReturn(t);
    }


    public static void setValue(Object object,String fieldName,Object value){
        if (object == null || TextUtils.isEmpty(fieldName )) {
            return;
        }
        Whitebox.setInternalState(object,fieldName,value,object.getClass());
    }

    public static <T> T getValue(Object object,String fieldName){
        if (object == null || TextUtils.isEmpty(fieldName )) {
            throw new IllegalArgumentException("error input");
        }
        return Whitebox.getInternalState(object,fieldName,object.getClass());
    }

    public static <T> T mockInstance(Class<T> tClass){
        T t = mock(tClass);
        if (tClass.isEnum()){
            return mockEnumInstance(t,tClass);
        }else {
            return mockNormalInstance(t,tClass);
        }
    }

    private static <T> T mockEnumInstance(T t,Class<T> tClass){
        Field[] fields = tClass.getFields();
        for (Field field : fields) {
            Class type = field.getType();
            if (type == tClass){
                field.setAccessible(true);
                try {
                    Object object = field.get(tClass);
                    setValue(object,field.getName(),t);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return t;
    }

    private static <T> T mockNormalInstance(T t,Class<T> tClass){
        Method[] methods = tClass.getMethods();
        Object object = null;
        for (Method method : methods) {
            if (method.getReturnType() == tClass){
                try{
                    object = method.invoke(tClass);
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        Field[] fields = tClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.getType() == tClass){
                setValue(object,field.getName(),t);
                break;
            }
        }
        return t;
    }

    public static void verifyStatic(int time,Class mock,ICallTestMethod callBack){
        //需要判断被verify的类是不是被prepare的
        checkPrepare(mock);
        Map<Class<?>, MethodInvocationControl> classMocks = Whitebox.getInternalState(MockRepository.class,"classMocks",MockRepository.class);
        MockRepository.getMethodProxy(null);
        if (!classMocks.containsKey(mock)){
            throw new MockitoAssertionError("verifyStatic之前，要先调用PowerMockito.mockStatic("+mock.getSimpleName()+".class)");
        }
        PowerMockito.verifyStatic(times(time));
        try {
            callBack.call();
        }catch (TooManyActualInvocations e){
            translateException(time, e);
        }catch (TooLittleActualInvocations e){
            translateException(time, e);
        }
    }

    public static PrivateMethodVerification verify(Object mock, int times ) throws Exception {
        if (mock == null) {
            throw new MockitoAssertionError("要verify的对象不能为null");
        }
        if (!isMocked(mock)){
            //这不是一个被mock的对象
            String name = mock.getClass().getSimpleName();
            throw new MockitoAssertionError("\n要verify的对象必须是mock出来或者spy出来的，例如：\n" +
                    name + " mock = PowerMockito.Mock("+name+".class) 或者\n" +
                    name + " spy = PowerMockito.Mock(new "+name+"())") ;
        }
        return PowerMockito.verifyPrivate(mock,times(times));
    }

    public static <T> MockSubscriber<T> getMockSubscription(Observable<T> observable) throws InterruptedException {
        Semaphore semaphore = new Semaphore(0);
        final MockSubscriber<T> subscriber = new MockSubscriber<>();
        subscriber.setSemaphore(semaphore);
        observable.subscribe(subscriber);
        semaphore.tryAcquire(30, TimeUnit.SECONDS);
        return subscriber;
    }

    public static  <T> OngoingStubbing<T> when(Class clazz, String methodName, Object... arguments) throws Exception {
        return PowerMockito.when(clazz,methodName,arguments);
    }

    private static void checkPrepare(final Class mock) {
        Class from = getTestOriginClass();
        PrepareForTest test = (PrepareForTest) from.getAnnotation(PrepareForTest.class);
        if (test != null){
            Class[] prepared = test.value();
            for (Class aClass : prepared) {
                if (aClass == mock){
                    return;
                }
            }
        }
        throw new MockitoAssertionError("\n请在"+from.getSimpleName()+"类声明处添加以下注解\n" +
                "\"@PrepareForTest("+mock.getSimpleName()+".class)\"，\n" +
                "如果已有该注解，请在注解体里添加"+mock.getSimpleName()+".class");
    }

    @Nullable
    private static Class getTestOriginClass() {
        StackTraceElement[] trace = new Exception("").getStackTrace();
        String callOriginClass = null;
        for (StackTraceElement element : trace) {
            String name = element.getClassName();
            if (!name.equals(MockUtils.class.getName())) {
                callOriginClass = name;
                break;
            }
        }
        try {
            Class from = Class.forName(callOriginClass);
            return from;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new MockitoAssertionError(e.getMessage());
        }
    }

    private static void translateException(final int time, final MockitoAssertionError e) {
        final String message = e.getMessage();
        int index = message.indexOf("but was ");
        if (index > 0 ){
            final String substring = message.substring(index + 8,index + 9);
            int count = Integer.parseInt(substring);
            throw  new MockitoAssertionError("期望执行次数是"+time+"，实际上执行了"+count+"次");
        }
    }

    private static boolean isMocked(Object object){
        Class clazz = object.getClass();
        ClassLoader classloader = clazz .getClassLoader();
        return classloader instanceof SearchingClassLoader;
    }

    public interface ICallTestMethod {
        void call();
    }

}
