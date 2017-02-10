package utils.builder;

/**
 * Created by Administrator on 2017/2/10.
 */

public class MockBuilder {

    public static CallMethodByNameBuilder when(Object object,String methodName){
        return new CallMethodByNameBuilder(object,methodName);
    }

    @Deprecated
    public static CallMethodBuilder when(Object object){
        return new CallMethodBuilder(object);
    }

    public static <T> WhenNewBuilder<T> whenNew(Class<T> tClass){
        return new WhenNewBuilder<>(tClass);
    }
}
