package utils.builder;

import org.powermock.api.mockito.PowerMockito;

/**
 * Created by Administrator on 2017/2/10.
 */

public class MockBuilder {

    public void test() throws Exception {
        PowerMockito.when(new Object(),"").thenReturn(null);
        final Object arguments = null;
        PowerMockito.verifyPrivate(new Object()).invoke("", arguments);
        PowerMockito.whenNew(Object.class).withAnyArguments().thenReturn(123);
    }

    public static <T> WhenBuilder<T> when(Object object){
        return new WhenBuilder<>(object);
    }

    public static <T> WhenNewBuilder<T> whenNew(Class<T> tClass){
        return new WhenNewBuilder<>(tClass);
    }

    public static VerifyBuilder verify(Object object,int times) throws Exception {
        return new VerifyBuilder(object,times);
    }
}
