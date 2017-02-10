package utils.builder;

import org.powermock.api.mockito.PowerMockito;

import utils.MockUtils;

/**
 * Created by Administrator on 2017/2/10.
 */

public class LessOneArgumentBuilder<T> {
    WhenNewBuilder<T> mBuilder;
    Object mFirstArgument;

    public LessOneArgumentBuilder(WhenNewBuilder<T> builder) {
        mBuilder = builder;
    }

    public MoreArgumentBuilder<T> withArgument(Object object){
        mFirstArgument = object;
        return new MoreArgumentBuilder<>(this);
    }

    public T thenReturnCurrent() throws Exception {
        T t = MockUtils.mock(mBuilder.mTClass);
        PowerMockito.whenNew(mBuilder.mTClass).withAnyArguments().thenReturn(t);
        return t;
    }

    public void thenReturn(T t) throws Exception {
        PowerMockito.whenNew(mBuilder.mTClass).withAnyArguments().thenReturn(t);
    }
}
