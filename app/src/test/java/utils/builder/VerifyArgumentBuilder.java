package utils.builder;

/**
 * Created by Administrator on 2017/2/11.
 */

public class VerifyArgumentBuilder<T>{

    private VerifyBuilder<T> mBuilder;
    VerifyArgumentBuilder(VerifyBuilder<T> builder){
        mBuilder = builder;
    }

    public void withArguments(Object... arguments) throws Exception {
        mBuilder.mPrivateMethodVerification.invoke(mBuilder.mMethodName,arguments);
    }
}
