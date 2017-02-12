package utils.builder;

import org.mockito.stubbing.OngoingStubbing;
import org.powermock.api.mockito.PowerMockito;

/**
 * Created by Administrator on 2017/2/10.
 */

public class WhenNewBuilder<T> extends Returnable<T> {
    private OngoingStubbing<T> mSetup;
    private Class<T> mClass;
    WhenNewBuilder(Class<T> tClass) {
        mClass = tClass;
    }

    public ConstructorArgumentBuilder<T> inClass(Class<?> target,Class<?>... otherTargets){
        MockUtils.checkPrepare(target);
        for (Class<?> otherTarget : otherTargets) {
            MockUtils.checkPrepare(otherTarget);
        }
        return new ConstructorArgumentBuilder<>(this);
    }

    void addConstructorArgument(final Object firstArgument, final Object... arguments) throws Exception {
        mSetup = PowerMockito.whenNew(mClass).withArguments(firstArgument,arguments);
    }

    @Override
    void addReturn(final T value, final T... otherValue) throws Exception {
        if (mSetup == null){
            mSetup = PowerMockito.whenNew(mClass).withAnyArguments();
        }
        mSetup.thenReturn(value,otherValue);
    }

    @Override
    void addThrow(final Class<? extends  Throwable>... throwableList) throws Throwable {
        if (mSetup == null){
            mSetup = PowerMockito.whenNew(mClass).withAnyArguments();
        }
        mSetup.thenThrow(throwableList);
    }

    void noArgument() throws Exception {
        mSetup = PowerMockito.whenNew(mClass).withNoArguments();
    }
}
