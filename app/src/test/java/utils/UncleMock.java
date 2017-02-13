package utils;

import org.mockito.exceptions.base.MockitoAssertionError;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.concurrent.Semaphore;

import rx.Observable;
import utils.builder.DoAnswerBuilder;
import utils.builder.DoNothingBuilder;
import utils.builder.IAnswer;
import utils.builder.MockBuilder;
import utils.builder.MockUtils;
import utils.builder.VerifyBuilder;
import utils.builder.VerifyNewBuilder;
import utils.builder.WhenBuilder;
import utils.builder.WhenNewBuilder;

/**
 * Created by Administrator on 2017/2/9.
 */
public class UncleMock {

    private static MockBuilder mMockBuilder = Whitebox.newInstance(MockBuilder.class);

    public static <T> T mock(Class<T> tClass){
        if (tClass == null) {
            throw new MockitoAssertionError("mock对象类不能为空");
        }
        checkPrepareFinal(tClass);
        return PowerMockito.mock(tClass);
    }

    public static void mockStatic(Class<?> tClass){
        if (tClass == null) {
            throw new MockitoAssertionError("mock对象类不能为空");
        }
        MockUtils.checkPrepare(tClass);
        PowerMockito.mockStatic(tClass);
    }

    public static <T> T spy(T t){
        if (t == null) {
            throw new MockitoAssertionError("spy对象类不能为空");
        }
        if (t instanceof Class){
            checkPrepareFinal((Class) t);
        }else {
            checkPrepareFinal(t.getClass());
        }
        return PowerMockito.spy(t);
    }

    public static <T> void spyStatic(Class<T> t){
        if (t == null) {
            throw new MockitoAssertionError("spy类不能为空");
        }
        checkPrepareFinal(t);
        MockUtils.checkPrepare(t);
        PowerMockito.spy(t);
    }

    private static void checkPrepareFinal(final Class tClass) {
        if (Modifier.isFinal(tClass.getModifiers())  || tClass.isEnum()){
            //被模拟的类是个final的，需要确认它是否被prepare
            MockUtils.checkPrepare(tClass);
        }
    }

    public static void setValue(Object object,String fieldName,Object value){
        if (object == null || isEmpty(fieldName )) {
            return;
        }
        try {
            Class clazz = object.getClass();
            if (object instanceof  Class){
                clazz = (Class) object;
            }
            Field field = clazz.getDeclaredField(fieldName);
            if (Modifier.isFinal(field.getModifiers())){
                throw new UncleMockException("年轻人,final的修饰的成员变量是值是不能被这样修改的~至于要怎么改~我也不知道哇");
            }
        } catch (NoSuchFieldException e) {
            throw new UncleMockException(e.getMessage());
        }
        if (object instanceof Class){
            Whitebox.setInternalState(object,fieldName,value,((Class) object));
        }else{
            Whitebox.setInternalState(object,fieldName,value,object.getClass());
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getValue(Object object,String fieldName){
        if (object == null || isEmpty(fieldName )) {
            throw new IllegalArgumentException("error input");
        }
        if (object instanceof Class){
            Class tClass = ((Class) object);
            return (T) Whitebox.getInternalState(object,fieldName,tClass);
        }else{
            return Whitebox.getInternalState(object,fieldName,object.getClass());
        }
    }

    public static <T> T spyInstance(Class<T> tClass){
        T t;
        if (tClass.isEnum()){
            //要先取到这个枚举单例的单例对象
            final T enumInstance = getEnumInstance(tClass);
            t = PowerMockito.spy(enumInstance);
        }else{
            //要先取到这个单例的单例对象
            final T normalInstance = getNormalInstance(tClass);
            t = PowerMockito.spy(normalInstance);
        }
        return doMock(tClass, t);
    }

    public static <T> T mockInstance(Class<T> tClass){
        T t = mock(tClass);
        return doMock(tClass,t);
    }

    private static <T> T doMock(final Class<T> tClass, final T t) {
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

    @SuppressWarnings("unchecked")
    private static <T> T getEnumInstance(Class<T> tClass){
        Field[] fields = tClass.getFields();
        for (Field field : fields) {
            Class type = field.getType();
            if (type == tClass) {
                try {
                    return (T) field.get(tClass);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private static <T> T mockNormalInstance(T t,Class<T> tClass){
        Object object = getNormalInstance(tClass);
        Field[] fields = tClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.getType() == tClass){
                setValue(object,field.getName(),t);
                break;
            }
        }
        return t;
    }

    @SuppressWarnings("unchecked")
    private static <T> T getNormalInstance(final Class<T> tClass) {
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
        return (T) object;
    }

    public static <T> UncleSubscriber<T> mockSubscriber(Observable<T> observable) throws InterruptedException {
        UncleSubscriber<T> subscriber = new UncleSubscriber<>();
        final Semaphore semaphore = new Semaphore(0);
        subscriber.setSemaphore(semaphore);
        observable.subscribe(subscriber.getSubscriber());
        return subscriber;
    }


    public static <T> WhenBuilder<T> when(Object object){
        return mMockBuilder.when(object);
    }

    public static <T> WhenNewBuilder<T> whenNew(Class<T> tClass){
        return mMockBuilder.whenNew(tClass);
    }

    public static VerifyBuilder verify(Object object, int times) throws Exception {
        return mMockBuilder.verify(object,times);
    }

    public static <T> VerifyNewBuilder verifyNew(Class<T> tClass, int times){
        return mMockBuilder.verifyNew(tClass,times);
    }

    public static DoNothingBuilder doNothing(){
        return mMockBuilder.doNothing();
    }

    public static DoAnswerBuilder doAnswer(IAnswer answer){
        return mMockBuilder.doAnswer(answer);
    }

    private static boolean isEmpty(String input){
        return input == null || input.equals("");
    }
}
