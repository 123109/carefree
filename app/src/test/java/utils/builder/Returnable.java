package utils.builder;

/**
 * Created by Administrator on 2017/2/11.
 */

abstract class Returnable<T> {
    abstract void addReturn(T value,T... otherValue) throws Exception;

    abstract void addThrow(final Class<? extends  Throwable>... throwableList) throws Throwable;
}
