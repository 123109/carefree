package utils.builder;

import org.mockito.exceptions.base.MockitoException;
import org.powermock.api.mockito.PowerMockito;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/10.
 */

public class CallMethodByNameBuilder {
    Object mObject;
    ArrayList<Object> mArguments;
    String mMethodName;
    Object mReturnValue;
    CallMethodByNameBuilder(Object object,String methodName) {
        mObject = object;
        mArguments = new ArrayList<>();
        mMethodName = methodName;
    }

    public CallMethodByNameBuilder argument(Object argument){
        mArguments.add(argument);
        return this;
    }

    public ReturnBuilder thenReturn(Object value) throws Exception {
        mReturnValue = value;
        return new ReturnBuilder(this);
    }

    public void doNothing() throws Exception {
        try {
            if (mArguments.size() > 0){
                PowerMockito.doNothing().when(mObject, mMethodName,mArguments.toArray());
            }else{
                PowerMockito.doNothing().when(mObject, mMethodName);
            }
        }catch (MockitoException e){
            if (e.getMessage().indexOf("Only void") >= 0){
                String message = "只有void方法才可以doNothing";
                throw new MockitoException(message);
            }
            throw e;
        }
    }

    void build() throws Exception {
        if (mArguments.size() > 0){
            PowerMockito.when(mObject, mMethodName,mArguments.toArray()).thenReturn(mReturnValue);
        }else{
            PowerMockito.when(mObject, mMethodName).thenReturn(mReturnValue);
        }
    }

    void build(Object[] returnValues) throws Exception {
        if (mArguments.size() > 0){
            PowerMockito.when(mObject, mMethodName,mArguments.toArray()).thenReturn(mReturnValue,returnValues);
        }else{
            PowerMockito.when(mObject, mMethodName).thenReturn(mReturnValue,returnValues);
        }
    }
}
