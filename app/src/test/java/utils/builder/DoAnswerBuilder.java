package utils.builder;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.mockito.expectation.PowerMockitoStubber;

/**
 * Created by Administrator on 2017/2/12.
 */

public class DoAnswerBuilder {
    private IAnswer mAnswer;

    DoAnswerBuilder(final IAnswer answer) {
        mAnswer = answer;
    }

    public CallBuilder when(Object object){
        PowerMockitoStubber stubber = PowerMockito.doAnswer(new Answer<Object>() {
            @Override
            public Object answer(final InvocationOnMock invocation) throws Throwable {
                return mAnswer.answer(invocation.getArguments());
            }
        });
        return new CallBuilder(object,stubber);
    }

}
