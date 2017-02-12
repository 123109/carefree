package utils.builder;

import org.mockito.exceptions.base.MockitoAssertionError;
import org.mockito.exceptions.misusing.InvalidUseOfMatchersException;
import org.mockito.stubbing.OngoingStubbing;
import org.powermock.api.mockito.PowerMockito;

/**
 * Created by Administrator on 2017/2/11.
 */

public class WhenBuilder<T> extends Returnable<T> {

    private OngoingStubbing<T> when;
    private CallMethodBuilder<T, ArgumentBuilder<T>> mCallMethodBuilder;

    WhenBuilder(final Object object) {
        mCallMethodBuilder = new CallMethodBuilder<>(object,new ArgumentBuilder<>(this));
    }

    public ArgumentBuilder<T> call(String methodName){
        return mCallMethodBuilder.call(methodName);
    }


    void addArguments(final Object... arguments) throws Exception {
        try {
            when = PowerMockito.when(mCallMethodBuilder.mObject,mCallMethodBuilder.mMethodName,arguments);
        }catch (InvalidUseOfMatchersException e){
            throw new MockitoAssertionError("传入多个参数时，要么全部用any，要么全部不用");
        }
    }

    final void addReturn(final T value, final T... otherValue) throws Exception {
        if (when == null){
            when = PowerMockito.when(mCallMethodBuilder.mObject,mCallMethodBuilder.mMethodName);
        }
        when.thenReturn(value,otherValue);
    }

    @Override
    void addThrow(final Class<? extends  Throwable>... throwableList) throws Exception {
        if (when == null){
            when = PowerMockito.when(mCallMethodBuilder.mObject,mCallMethodBuilder.mMethodName);
        }
        when.thenThrow(throwableList);
    }
}
