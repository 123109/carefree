package utils.builder;

import org.mockito.stubbing.OngoingStubbing;
import org.powermock.api.mockito.PowerMockito;

/**
 * Created by Administrator on 2017/2/11.
 */

public class WhenBuilder<T> extends Returnable<T> {

    private OngoingStubbing<T> when;
    private Object mObject;

    WhenBuilder(final Object object) {
        mObject = object;
    }

    public ReturnBuilder<T> call(String methodName,Object... arguments) throws Exception {
        when = PowerMockito.when(mObject,methodName,arguments);
        return new ReturnBuilder<>(this);
    }

    final void addReturn(final T value, final T... otherValue) throws Exception {
        when.thenReturn(value,otherValue);
    }

    @Override
    void addThrow(final Class<? extends  Throwable>... throwableList) throws Exception {
        when.thenThrow(throwableList);
    }
}
