package utils.builder;

import org.powermock.api.mockito.expectation.PowerMockitoStubber;

/**
 * Created by Administrator on 2017/2/12.
 */

public class CallBuilder {
    private Object mObject;
    private PowerMockitoStubber mStubber;
    CallBuilder(Object object,PowerMockitoStubber stubber) {
        MockUtils.check(object);
        mObject = object;
        mStubber = stubber;
    }

    public void call(String methodName,Object... arguments) throws Exception {
        if (mObject instanceof Class){
            mStubber.when(((Class) mObject),methodName,arguments);
        }else {
            mStubber.when(mObject,methodName,arguments);
        }
    }
}
