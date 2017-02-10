package utils.builder;

import org.powermock.api.mockito.PowerMockito;

/**
 * Created by Administrator on 2017/2/10.
 */
@Deprecated
public class CallMethodBuilder{
    Object mObject;
    Object mReturnValue;
    CallMethodBuilder(Object object) {
        mObject = object;
    }

    @Deprecated
    public ReturnBuilder thenReturn(Object value) throws Exception {
        mReturnValue = value;
        return new ReturnBuilder(this);
    }

    @Deprecated
    void build(){
        PowerMockito.when(mObject).thenReturn(mReturnValue);
    }

    @Deprecated
    void build(Object[] arguments){
        PowerMockito.when(mObject).thenReturn(mReturnValue,arguments);
    }
}
