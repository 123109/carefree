package utils.builder;

import org.mockito.exceptions.base.MockitoAssertionError;
import org.mockito.exceptions.misusing.InvalidUseOfMatchersException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.OngoingStubbing;
import org.powermock.api.mockito.PowerMockito;

/**
 * Created by Administrator on 2017/2/10.
 */

public class WhenNewBuilder<T> extends Returnable<T> {
    private OngoingStubbing<T> mSetup;
    private Class<T> mClass;
    WhenNewBuilder(Class<T> tClass) {
        mClass = tClass;
    }

    public ConstructorArgumentBuilder<T> inClass(Class<?> target,Class<?>... otherTargets){
        MockUtils.checkPrepare(target);
        for (Class<?> otherTarget : otherTargets) {
            MockUtils.checkPrepare(otherTarget);
        }
        return new ConstructorArgumentBuilder<>(this);
    }

    void addConstructorArgument(final Object firstArgument, final Object... arguments) throws Exception {
        try {
            mSetup = PowerMockito.whenNew(mClass).withArguments(firstArgument,arguments);
        }catch (InvalidUseOfMatchersException e){
            throw new MockitoAssertionError("传入多个参数时，要么全部用any，要么全部不用");
        }
    }

    @Override
    void addReturn(final T value, final T... otherValue) throws Exception {
        if (mSetup == null){
            mSetup = PowerMockito.whenNew(mClass).withAnyArguments();
        }
        mSetup.thenReturn(value,otherValue);
    }

    @Override
    void addThrow(final Class<? extends  Throwable>... throwableList) throws Throwable {
        if (mSetup == null){
            mSetup = PowerMockito.whenNew(mClass).withAnyArguments();
        }
        mSetup.thenThrow(throwableList);
    }

    @Override
    void addAnswer(final IAnswer answer) throws Exception {
        mSetup.thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(final InvocationOnMock invocation) throws Throwable {
                return answer.answer(invocation.getArguments());
            }
        });
    }

    void noArgument() throws Exception {
        mSetup = PowerMockito.whenNew(mClass).withNoArguments();
    }
}
