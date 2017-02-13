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
    boolean mIsKnown;
    VerifyNewBuilder(Class tClass,int times) {
        try {
            mVerification = PowerMockito.verifyNew(tClass, Mockito.times(times));
        }catch (IllegalStateException e){
            String message = e.getMessage();
            if (message.contains("whenNew(..)")){
                throw new UncleMockException("\n\nverifyNew只有在whenNew之后才会起作用");
            }
            throw e;
        }catch (NotAMockException e){
            throw new UncleMockException("\n\nverifyNew方法里的withArguments()传入的参数，必须是whenNew方法里的传给withArguments()的参数或者any，但二者不能同时为any，例如：\n" +
                    "1.OK:whenNew(xxx.class).withArguments(1).thenReturn(a); verifyNew(xxx.class,1).withArgument(1)\n" +
                    "2.OK:whenNew(xxx.class).withArguments(1).thenReturn(a); verifyNew(xxx.class,1).withArgument(Mockito.anyInt())\n" +
                    "3.NG:whenNew(xxx.class).withArguments(Mockito.anyInt()).thenReturn(a); verifyNew(xxx.class,1).withArgument(Mockito.anyInt())");
        }
    }

    public VerifyNewBuilder justDoIt(){
        mIsKnown = true;
        return this;
    }

    public void withArguments(Object argument,Object... arguments) throws Exception {
        if (mIsKnown){
            mVerification.withArguments(argument,arguments);
        }else {
            throw new UncleMockException("\n\n强烈不建议使用verifyNew\n" +
                    "PowerMockito本身的bug,会导致verifyNew单个文件测试的时候能通过，一旦和其它测试一起跑就会挂\n" +
                    "如果你一定要用，要这样：justDoIt().withNoArgument()\n" +
                    "祝你好运年轻人~");
        }
    }

    public void withNoArgument() throws Exception {
        if (mIsKnown){
            mVerification.withNoArguments();
        }else {
            throw new UncleMockException("\n\n强烈不建议使用verifyNew\n" +
                    "PowerMockito本身的bug,会导致verifyNew单个文件测试的时候能通过，一旦和其它测试一起跑就会挂\n" +
                    "如果你一定要用，要这样：justDoIt().withNoArgument()\n" +
                    "祝你好运年轻人~");
        }
    }
}
