package utils.builder;

/**
 * Created by Administrator on 2017/2/10.
 */

public class ReturnBuilder<T> {
    CoreBuilder<T> mCoreBuilder;

    ReturnBuilder(CoreBuilder<T> builder) {
        mCoreBuilder = builder;
    }

    public void thenReturnValues(T value,T... otherValue) throws Exception {
        mCoreBuilder.addReturn(value,otherValue);
    }

    public void thenReturn(T value) throws Exception {
        mCoreBuilder.addReturn(value);
    }
}
