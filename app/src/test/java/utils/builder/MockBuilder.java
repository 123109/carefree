package utils.builder;

/**
 * Created by Administrator on 2017/2/10.
 */

public class MockBuilder {

    private MockBuilder(){}
    
    public <T> WhenBuilder<T> when(Object object){
        check(object);
        return new WhenBuilder<>(object);
    }

    public <T> WhenNewBuilder<T> whenNew(Class<T> tClass){
        return new WhenNewBuilder<>(tClass);
    }

    public VerifyBuilder verify(Object object,int times) throws Exception {
        check(object);
        return new VerifyBuilder(object,times);
    }

    public <T> VerifyNewBuilder verifyNew(Class<T> tClass,int times){
        return new VerifyNewBuilder(tClass,times);
    }

    public DoNothingBuilder doNothing(Object object){
        check(object);
        return new DoNothingBuilder(object);
    }

    private void check(Object object){
        if (object instanceof Class){
            //要测试一个静态方法
            MockUtils.checkStaticMock((Class) object);
        }else{
            MockUtils.checkMocked(object);
        }
    }
}
