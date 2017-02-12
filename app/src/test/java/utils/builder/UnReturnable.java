package utils.builder;

/**
 * Created by Administrator on 2017/2/11.
 */

abstract class UnReturnable {

    abstract void withArguments(Object... arguments) throws Exception;

    abstract void withNoArgument() throws Exception;
}
