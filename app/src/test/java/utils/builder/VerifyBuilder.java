package utils.builder;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.mockito.verification.PrivateMethodVerification;

import static org.mockito.Mockito.times;

/**
 * Created by Administrator on 2017/2/11.
 */

public class VerifyBuilder<T>{

    //默认一次
    private CallMethodBuilder<T,VerifyArgumentBuilder<T>> mCallMethodBuilder;
    PrivateMethodVerification mPrivateMethodVerification;
    String mMethodName;

    VerifyBuilder(Object object,int times) throws Exception {
        mCallMethodBuilder = new CallMethodBuilder<>(object,new VerifyArgumentBuilder<>(this));
        mPrivateMethodVerification = PowerMockito.verifyPrivate(object,times(times));
    }

    public VerifyArgumentBuilder<T> call(String methodName) throws Exception {
        mMethodName = methodName;
        return mCallMethodBuilder.call(methodName);
    }

    public void callWithNoArguments(String methodName) throws Exception {
        mPrivateMethodVerification.invoke(methodName);
    }

}
