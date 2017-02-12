package utils.builder;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.mockito.verification.PrivateMethodVerification;

import static org.mockito.Mockito.times;

/**
 * Created by Administrator on 2017/2/11.
 */

public class VerifyBuilder<T> extends UnReturnable{

    //默认一次
    private CallMethodBuilder<T,VerifyArgumentBuilder> mCallMethodBuilder;
    private PrivateMethodVerification mPrivateMethodVerification;

    VerifyBuilder(Object object,int times) throws Exception {
        mCallMethodBuilder = new CallMethodBuilder<>(object,new VerifyArgumentBuilder(this));
        mPrivateMethodVerification = PowerMockito.verifyPrivate(object,times(times));
    }

    public VerifyArgumentBuilder call(String methodName){
        return mCallMethodBuilder.call(methodName);
    }

    public void callWithNoArgument(String methodName) throws Exception{
        mPrivateMethodVerification.invoke(methodName);
    }

    @Override
    void withArguments(final Object... arguments) throws Exception {
        mPrivateMethodVerification.invoke(mCallMethodBuilder.mMethodName,arguments);
    }

    @Override
    void withNoArgument() throws Exception {
        mPrivateMethodVerification.invoke(mCallMethodBuilder.mMethodName);
    }
}
