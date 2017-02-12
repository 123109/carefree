package utils.builder;

/**
 * Created by Administrator on 2017/2/10.
 */

public class ReturnBuilder<T> {
    Returnable<T> mCoreBuilder;

    ReturnBuilder(Returnable<T> builder) {
        mCoreBuilder = builder;
    }

    @SafeVarargs
    public final void thenReturnValues(T value, T... otherValue) throws Exception {
        mCoreBuilder.addReturn(value,otherValue);
    }

    public void thenReturn(T value) throws Exception {
        mCoreBuilder.addReturn(value);
    }

    public void thenThrow(final Class<? extends  Throwable>... throwableList) throws Throwable {
        mCoreBuilder.addThrow(throwableList);
    }
}
