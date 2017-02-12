package utils.builder;

import org.powermock.api.mockito.PowerMockito;

/**
 * Created by Administrator on 2017/2/12.
 */

public class DoNothingBuilder{

    private Object mObject;

    DoNothingBuilder(Object object){
        mObject = object;
    }

    public void call(String methodName,Object... arguments) throws Exception {
        if (mObject instanceof Class){
            PowerMockito.doNothing().when(((Class) mObject),methodName,arguments);
        }else {
            PowerMockito.doNothing().when(mObject,methodName,arguments);
        }
    }
}
