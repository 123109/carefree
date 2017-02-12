package utils.builder;

/**
 * Created by Administrator on 2017/2/11.
 */

public class ConstructorArgumentBuilder<T> extends ReturnBuilder<T>{

    ConstructorArgumentBuilder(AbstractBuilder<T> builder){
        super(builder);
    }

    public ReturnBuilder<T> withArguments(Object argument,Object... arguments) throws Exception {
        mCoreBuilder.addConstructorArgument(argument,arguments);
        return new ReturnBuilder<>(mCoreBuilder);
    }

    public ReturnBuilder<T> withNoArgument() throws Exception {
        mCoreBuilder.noArgument();
        return new ReturnBuilder<>(mCoreBuilder);
    }
}
