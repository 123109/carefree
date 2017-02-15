package utils.builder;

import static utils.builder.MockUtils.check;

/**
 * Created by Administrator on 2017/2/10.
 */

public class MockOperator {

    private MockOperator(){}
    
    public <T> OperatorWhen<T> when(Object object){
        check(object);
        return new OperatorWhen<>(object);
    }

    public <T> OperatorWhenNew<T> whenNew(Class<T> tClass){
        return new OperatorWhenNew<>(tClass);
    }

    public OperatorVerify verify(Object object, int times) throws Exception {
        check(object);
        return new OperatorVerify(object,times);
    }

    public <T> OperatorVerifyNew verifyNew(Class<T> tClass, int times){
        return new OperatorVerifyNew(tClass,times);
    }

    public OperatorDoNothing doNothing(){
        return new OperatorDoNothing();
    }

    public OperatorDoAnswer doAnswer(IAnswer answer){
        return new OperatorDoAnswer(answer);
    }
}
