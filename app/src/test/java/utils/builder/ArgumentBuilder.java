package utils.builder;

/**
 * Created by Administrator on 2017/2/11.
 */

public class ArgumentBuilder<T> extends ReturnBuilder<T>{

    private WhenBuilder<T> mBuilder;

    ArgumentBuilder(WhenBuilder<T> builder){
        super(builder);
        mBuilder = builder;
    }

    public ReturnBuilder<T> withArguments(Object... arguments) throws Exception {
        mBuilder.addArguments(arguments);
        return new ReturnBuilder<>(mBuilder);
    }
}
