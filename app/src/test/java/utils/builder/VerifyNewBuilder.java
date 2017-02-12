package utils.builder;

import org.mockito.Mockito;
import org.mockito.exceptions.misusing.NotAMockException;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.mockito.verification.ConstructorArgumentsVerification;

import utils.UncleMockException;

/**
 * Created by Administrator on 2017/2/12.
 */
public class VerifyNewBuilder{
    ConstructorArgumentsVerification mVerification;
    VerifyNewBuilder(Class tClass,int times) {
        try {
            mVerification = PowerMockito.verifyNew(tClass, Mockito.times(times));
        }catch (IllegalStateException e){
            String message = e.getMessage();
            if (message.contains("whenNew(..)")){
                throw new UncleMockException("verifyNew只有在whenNew之后才会起作用");
            }
            throw e;
        }catch (NotAMockException e){
            throw new UncleMockException("\nverifyNew方法里的withArguments()传入的参数，必须是whenNew方法里的传给withArguments()的参数或者any，但二者不能同时为any，例如：\n" +
                    "1.OK:whenNew(xxx.class).withArguments(1).thenReturn(a); verifyNew(xxx.class,1).withArgument(1)\n" +
                    "2.OK:whenNew(xxx.class).withArguments(1).thenReturn(a); verifyNew(xxx.class,1).withArgument(Mockito.anyInt())\n" +
                    "3.NG:whenNew(xxx.class).withArguments(Mockito.anyInt()).thenReturn(a); verifyNew(xxx.class,1).withArgument(Mockito.anyInt())");
        }
    }

    public void withArguments(Object argument,Object... arguments) throws Exception {
        mVerification.withArguments(argument,arguments);
    }

    public void withNoArgument() throws Exception {
        mVerification.withNoArguments();
    }
}
