package utils.builder;

import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.mockito.verification.PrivateMethodVerification;

/**
 * Created by Administrator on 2017/2/11.
 */

public class VerifyBuilder{

    //默认一次
    private PrivateMethodVerification privateMethodVerification;
    VerifyBuilder(Object object,int times) throws Exception {
        if (object instanceof Class){
            privateMethodVerification = PowerMockito.verifyPrivate(((Class) object), Mockito.times(times));
        }else {
            privateMethodVerification = PowerMockito.verifyPrivate((object), Mockito.times(times));
        }
    }

    public void call(String methodName,Object... arguments) throws Exception {
        privateMethodVerification.invoke(methodName,arguments);
    }
}
