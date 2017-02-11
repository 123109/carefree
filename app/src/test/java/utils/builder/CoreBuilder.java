package utils.builder;

/**
 * Created by Administrator on 2017/2/11.
 */

abstract class CoreBuilder<T> {

    abstract void addConstructorArgument(Object firstArgument, Object... arguments) throws Exception;

    abstract void addArguments(Object... arguments) throws Exception;

    abstract void addReturn(T value,T... otherValue) throws Exception;

    abstract void noArgument() throws Exception;
}
