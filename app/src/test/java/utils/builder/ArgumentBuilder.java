package utils.builder;

/**
 * Created by Administrator on 2017/2/11.
 */

public class ArgumentBuilder<T> extends ReturnBuilder<T>{

    ArgumentBuilder(AbstractBuilder<T> builder){
        super(builder);
    }

    public ReturnBuilder<T> withArguments(Object... arguments) throws Exception {
        mCoreBuilder.addArguments(arguments);
        return new ReturnBuilder<>(mCoreBuilder);
    }
}
