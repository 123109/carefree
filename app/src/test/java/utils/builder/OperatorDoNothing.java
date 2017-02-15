package utils.builder;

import org.powermock.api.mockito.PowerMockito;

/**
 * Created by Administrator on 2017/2/12.
 */

public class OperatorDoNothing {

    public CallBuilder when(Object object){
        return new CallBuilder(object,PowerMockito.doNothing());
    }
}
