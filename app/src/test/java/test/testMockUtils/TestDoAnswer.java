package test.testMockUtils;

import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import base.TestInit;
import classDefine.StaticClass;
import test.testMockUtils.bean.SomeClass;
import utils.UncleMock;
import utils.builder.IAnswer;

/**
 * Created by Administrator on 2017/2/12.
 */
@PrepareForTest({StaticClass.class})
public class TestDoAnswer extends TestInit{
    @Test
    public void test() throws Exception {
        PowerMockito.spy(StaticClass.class);
        UncleMock.doAnswer(new IAnswer() {
            @Override
            public Object answer(final Object[] arguments) {
                System.out.print("answer");
                return null;
            }
        }).when(StaticClass.class).call("setRealValue",1);
        StaticClass.setRealValue(1);
    }

    @Test
    public void testObject() throws Exception {
        SomeClass someClass = PowerMockito.spy(new SomeClass());
        someClass.callVoid();
        UncleMock.doAnswer(new IAnswer() {
            @Override
            public Object answer(final Object[] arguments) {
                System.out.print("answer");
                return null;
            }
        }).when(someClass).call("callVoid");
        someClass.callVoid();
    }
}
