package test.testMockUtils;

import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import base.TestInit;
import classDefine.StaticClass;
import utils.MockUtils;
import utils.builder.MockBuilder;

/**
 * Created by Administrator on 2017/2/11.
 */
@PrepareForTest({StaticClass.class,CallOrigin.class})
public class TestVerify extends TestInit{
    @Test
    public void test_callWithNoArgument() throws Exception {
        final CallOrigin callOrigin = MockUtils.mock(CallOrigin.class);
        callOrigin.callVoid();
        MockBuilder.verify(callOrigin,1).callWithNoArguments("callVoid");
    }

    @Test
    public void test() throws Exception {
        final CallOrigin callOrigin = MockUtils.mock(CallOrigin.class);
        callOrigin.callTwo("1","2");
        MockBuilder.verify(callOrigin,1).call("callTwo").withArguments("1","2");
    }

    @Test
    public void testStatic() throws Exception {
        MockUtils.mockStatic(StaticClass.class);
        StaticClass.getRealValue(1,1);
        MockBuilder.verify(StaticClass.class,1).call("getRealValue").withArguments(Mockito.anyInt(),Mockito.anyInt());
    }
    @Test
    public void testStatic_noArgument() throws Exception {
        MockUtils.mockStatic(StaticClass.class);
        StaticClass.getMockValue();
        MockBuilder.verify(StaticClass.class,1).callWithNoArguments("getMockValue");
    }

    @Test
    public void testUnMock() throws Exception {
        final CallOrigin callOrigin = new CallOrigin();
        callOrigin.callTwo("1","2");
        MockBuilder.verify(callOrigin,1).call("callTwo").withArguments("1","2");
    }

}
