package test.testMockUtils;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import base.TestInit;
import classDefine.StaticClass;
import test.testMockUtils.bean.CallOrigin;
import utils.UncleMock;
import utils.UncleMockException;

/**
 * Created by Administrator on 2017/2/11.
 */
@PrepareForTest({StaticClass.class,CallOrigin.class})
public class TestVerify extends TestInit{
    @Test
    public void test_callWithNoArgument() throws Exception {
        final CallOrigin callOrigin = UncleMock.mock(CallOrigin.class);
        callOrigin.callVoid();
        UncleMock.verify(callOrigin,1).call("callVoid");
    }

    @Test
    public void test() throws Exception {
        final CallOrigin callOrigin = UncleMock.mock(CallOrigin.class);
        callOrigin.callTwo("1","2");
        UncleMock.verify(callOrigin,1).call("callTwo","1","2");
    }

    @Test
    public void testStatic() throws Exception {
        UncleMock.mockStatic(StaticClass.class);
        StaticClass.getRealValue(1,1);
        UncleMock.verify(StaticClass.class,1).call("getRealValue",Mockito.anyInt(),Mockito.anyInt());
    }
    @Test
    public void testStatic_noArgument() throws Exception {
        UncleMock.mockStatic(StaticClass.class);
        StaticClass.getMockValue();
        UncleMock.verify(StaticClass.class,1).call("getMockValue");
    }

    @Test
    public void testUnMock() throws Exception {
        final CallOrigin callOrigin = new CallOrigin();
        callOrigin.callTwo("1","2");
        boolean isError = false;
        try {
            UncleMock.verify(callOrigin,1).call("callTwo","1","2");
        }catch (UncleMockException e){
            e.printStackTrace();
            isError = true;
        }
        Assert.assertTrue(isError);
    }

}
