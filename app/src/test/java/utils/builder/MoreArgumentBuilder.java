package utils.builder;

import org.powermock.api.mockito.PowerMockito;

import java.util.ArrayList;

import utils.MockUtils;

/**
 * Created by Administrator on 2017/2/10.
 */

public class MoreArgumentBuilder<T> {
    private LessOneArgumentBuilder<T> mBuilder;
    private ArrayList<Object> mArguments = new ArrayList<>();

    public MoreArgumentBuilder(LessOneArgumentBuilder<T> builder) {
        mBuilder = builder;
    }

    public MoreArgumentBuilder<T> and(Object argument){
        mArguments.add(argument);
        return this;
    }

    public T thenReturnCurrent() throws Exception {
        final Class<T> tClass = mBuilder.mBuilder.mTClass;
        T t = MockUtils.mock(tClass);
        if (mArguments.isEmpty()){
            PowerMockito.whenNew(tClass).withArguments(mBuilder.mFirstArgument).thenReturn(t);
        }else{
            PowerMockito.whenNew(tClass).withArguments(mBuilder.mFirstArgument,mArguments.toArray()).thenReturn(t);
        }
        return t;
    }

    public void thenReturn(T t) throws Exception {
        final Class<T> tClass = mBuilder.mBuilder.mTClass;
        if (mArguments.isEmpty()){
            PowerMockito.whenNew(tClass).withArguments(mBuilder.mFirstArgument).thenReturn(t);
        }else{
            PowerMockito.whenNew(tClass).withArguments(mBuilder.mFirstArgument,mArguments.toArray()).thenReturn(t);
        }
    }
}
