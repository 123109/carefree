package test.testMockUtils;

import android.support.annotation.NonNull;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import base.TestInit;
import classDefine.StaticClass;

/**
 * Created by Administrator on 2017/2/12.
 */
@PrepareForTest({StaticClass.class})
public class TestAnswer extends TestInit{
    @Test
    public void test() throws Exception {
        PowerMockito.spy(StaticClass.class);
        PowerMockito.when(StaticClass.class,"setRealValue",1).then(getAnswer());
        StaticClass.setRealValue(1);
    }

    @Test
    public void testObject() throws Exception {
        CallOrigin origin = PowerMockito.mock(CallOrigin.class);
        origin.callVoid();
        PowerMockito.doAnswer(getAnswer()).when(origin,"callVoid");
        origin.callVoid();
    }

    @NonNull
    private Answer<Object> getAnswer() {
        return new Answer<Object>() {
            @Override
            public Object answer(final InvocationOnMock invocation) throws Throwable {
                System.out.print("answer");
                return "111";
            }
        };
    }

}
