package utils.builder;

/**
 * Created by Administrator on 2017/2/11.
 */

public class VerifyArgumentBuilder{

    private UnReturnable mBuilder;
    VerifyArgumentBuilder(UnReturnable builder){
        mBuilder = builder;
    }

    public void withArguments(Object... arguments) throws Exception {
        mBuilder.withArguments(arguments);
    }

    public void withNoArgument() throws Exception {
        mBuilder.withNoArgument();
    }
}
