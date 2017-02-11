package utils.builder;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.mockito.verification.PrivateMethodVerification;

import static org.mockito.Mockito.times;

/**
 * Created by Administrator on 2017/2/11.
 */

public class VerifyBuilder<T>{

    //默认一次
    private int mTimes = 1;
    private CallMethodBuilder<T,VerifyArgumentBuilder<T>> mCallMethodBuilder;
    PrivateMethodVerification mPrivateMethodVerification;
    String mMethodName;

    public VerifyBuilder(Object object,int times) throws Exception {
        mTimes = times;
        mCallMethodBuilder = new CallMethodBuilder<>(object,new VerifyArgumentBuilder<>(this));
        mPrivateMethodVerification = PowerMockito.verifyPrivate(object);
    }

    public VerifyArgumentBuilder<T> callWithArguments(String methodName) throws Exception {
        mMethodName = methodName;
        return mCallMethodBuilder.call(methodName);
    }

    public void call(String methodName) throws Exception {
        PowerMockito.verifyPrivate(mCallMethodBuilder.mObject,times(mTimes)).invoke(methodName);
    }
}
