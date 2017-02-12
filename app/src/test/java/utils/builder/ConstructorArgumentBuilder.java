package utils.builder;

/**
 * Created by Administrator on 2017/2/11.
 */

public class ConstructorArgumentBuilder<T> extends ReturnBuilder<T>{

    private WhenNewBuilder<T> mBuilder;

    ConstructorArgumentBuilder(WhenNewBuilder<T> builder){
        super(builder);
        mBuilder = builder;
    }

    public ReturnBuilder<T> withArguments(Object argument,Object... arguments) throws Exception {
        mBuilder.addConstructorArgument(argument,arguments);
        return new ReturnBuilder<>(mCoreBuilder);
    }

    public ReturnBuilder<T> withNoArgument() throws Exception {
        mBuilder.noArgument();
        return new ReturnBuilder<>(mCoreBuilder);
    }
}
