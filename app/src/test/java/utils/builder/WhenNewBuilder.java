package utils.builder;

import org.mockito.stubbing.OngoingStubbing;
import org.powermock.api.mockito.PowerMockito;

import utils.MockUtils;

/**
 * Created by Administrator on 2017/2/10.
 */

public class WhenNewBuilder<T> extends CoreBuilder<T>{
    OngoingStubbing<T> mSetup;
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

    @Override
    void addConstructorArgument(final Object firstArgument, final Object... arguments) throws Exception {
        mSetup = PowerMockito.whenNew(mClass).withArguments(firstArgument,arguments);
    }

    @Override
    void addArguments(final Object... arguments) throws Exception {

    }

    @Override
    void addReturn(final T value, final T... otherValue) throws Exception {
        if (mSetup == null){
            mSetup = PowerMockito.whenNew(mClass).withAnyArguments();
        }
        mSetup.thenReturn(value,otherValue);
    }

    @Override
    void noArgument() throws Exception {
        mSetup = PowerMockito.whenNew(mClass).withNoArguments();
    }
}
