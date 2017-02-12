package utils;

import org.mockito.exceptions.base.MockitoAssertionError;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

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

    private static MockBuilder mMockBuilder;

    static {
        try {
            Constructor<MockBuilder> constructor = MockBuilder.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            mMockBuilder = constructor.newInstance();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static <T> T mock(Class<T> tClass){
        if (tClass == null) {
            throw new MockitoAssertionError("mock对象类不能为空");
        }
        checkPrepareFinal(tClass);
        T t = PowerMockito.mock(tClass);
        return t;
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
        checkPrepareFinal(t.getClass());
        return PowerMockito.spy(t);
    }

    public static <T> void spy(Class<T> t){
        if (t == null) {
            throw new MockitoAssertionError("spy类不能为空");
        }
        checkPrepareFinal(t.getClass());
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
        Whitebox.setInternalState(object,fieldName,value,object.getClass());
    }

    public static <T> T getValue(Object object,String fieldName){
        if (object == null || isEmpty(fieldName )) {
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
