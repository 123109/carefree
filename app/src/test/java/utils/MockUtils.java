package utils;

import android.text.TextUtils;

import org.powermock.api.mockito.PowerMockito;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cb on 2016/5/20.
 */
@SuppressWarnings("NullArgumentToVariableArgMethod")
public class MockUtils {

    public static void setToObject(Object target, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        if (target == null) {
            return;
        }
        if (fieldName == null) {
            return;
        }
        Field field = target.getClass().getDeclaredField(fieldName);
        if (field != null){
            field.setAccessible(true);
            field.set(target,value);
        }
    }

    public static void setToStaticField(Class<?> target, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        if (target == null) {
            return;
        }
        if (fieldName == null) {
            return;
        }
        Field field = target.getDeclaredField(fieldName);
        if (field != null){
            field.setAccessible(true);
            field.set(target,value);
        }
    }

    public static <T> T getFromObject(Object object, String fieldName) throws IllegalAccessException, NoSuchFieldException {
        if (object == null || fieldName == null) {
            return null;
        }
        Field field = object.getClass().getDeclaredField(fieldName);
        if (field == null) {
            return null;
        }
        field.setAccessible(true);
        return (T) field.get(object);
    }

    public static <T> T getFromClass(Class<?> target, String staticFieldName) throws IllegalAccessException, NoSuchFieldException {
        if (target == null || staticFieldName == null) {
            return null;
        }
        Field field = target.getDeclaredField(staticFieldName);
        if (field == null) {
            return null;
        }
        field.setAccessible(true);
        return (T) field.get(target);
    }

    public static void spyInstanceObject(Object target) throws Exception{
        Constructor constructor = target.getClass().getDeclaredConstructor();
        if (constructor == null) {
            throw new NoSuchMethodException("no valid constructor");
        }
        constructor.setAccessible(true);
        Object spy = PowerMockito.spy(constructor.newInstance());
        Field[] fields = target.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (containsIgnoreCase(field.getName(),"instance")){
                field.setAccessible(true);
                field.set(target,spy);
                return;
            }
        }
    }

    public static void spyInstanceClass(Class<?> target) throws Exception{
        Constructor constructor = target.getDeclaredConstructor();
        if (constructor == null) {
            throw new NoSuchMethodException("no valid constructor");
        }
        constructor.setAccessible(true);
        Method[] methods = target.getMethods();
        Object ori = null;
        for (Method method : methods) {
            if (method.getReturnType().equals(target)){
                ori = target.getMethod(method.getName(),method.getParameterTypes()).invoke(target);
                break;
            }
        }
        Object spy = PowerMockito.spy(constructor.newInstance());
        Field[] fields = target.getDeclaredFields();
        for (Field field : fields) {
            if (containsIgnoreCase(field.getName(), "instance")){
                field.setAccessible(true);
                field.set(ori,spy);
                return;
            }
        }
    }

    public static void spyInstanceClass(Class<?> target,Object... constructorParams) throws Exception{
        Constructor constructor = target.getDeclaredConstructor();
        if (constructor == null) {
            throw new NoSuchMethodException("no valid constructor");
        }
        constructor.setAccessible(true);
        Method[] methods = target.getMethods();
        Object ori = null;
        for (Method method : methods) {
            if (method.getReturnType().equals(target)){
                ori = target.getMethod(method.getName(),method.getParameterTypes()).invoke(target,constructorParams);
                break;
            }
        }
        Object spy = PowerMockito.spy(constructor.newInstance());
        Field[] fields = target.getDeclaredFields();
        for (Field field : fields) {
            if (containsIgnoreCase(field.getName(), "instance")){
                field.setAccessible(true);
                field.set(ori,spy);
                return;
            }
        }
    }

    public static void mockInstanceClass(Class<?> target,Object... constructorParams) throws Exception{
        Method[] methods = target.getMethods();
        Object ori = null;
        for (Method method : methods) {
            if (method.getReturnType().equals(target) && !method.getName().contains("valueOf")){
                ori = target.getMethod(method.getName(),method.getParameterTypes()).invoke(target,constructorParams);
                break;
            }
        }
        Object mock = PowerMockito.mock(target);
        Field[] fields = target.getDeclaredFields();
        for (Field field : fields) {
            if (containsIgnoreCase(field.getName(), "instance")){
                field.setAccessible(true);
                field.set(ori,mock);
                return;
            }
        }
    }

    static boolean containsIgnoreCase(String src,String target){
        if (TextUtils.isEmpty(src) || TextUtils.isEmpty(target)){
            return false;
        }
        Pattern pattern = Pattern.compile(target, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(src);
        return matcher.find();
    }

    public static void mockEnumInstance(Class<?> enumClass,String instanceName) throws Exception{
        Object object = PowerMockito.mock(enumClass);
        setToStaticField(enumClass,instanceName,object);
    }

    public static void mockEnumInstance(Class<?> enumClass) throws Exception{
        Object object = PowerMockito.mock(enumClass);
        Field[] fields = enumClass.getFields();
        for (Field field : fields) {
            if (containsIgnoreCase(field.getName(),"instance")){
                setToStaticField(enumClass,field.getName(),object);
                return;
            }
        }
    }

    public static void spyEnumInstance(Class<?> enumClass) throws Exception{
        Object object = PowerMockito.spy(enumClass.newInstance());
        Field[] fields = enumClass.getFields();
        for (Field field : fields) {
            if (containsIgnoreCase(field.getName(),"instance")){
                setToStaticField(enumClass,field.getName(),object);
                return;
            }
        }
    }
}
