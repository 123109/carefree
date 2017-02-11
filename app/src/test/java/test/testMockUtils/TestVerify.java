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
@PrepareForTest(StaticClass.class)
public class TestVerify extends TestInit{
    @Test
    public void test_callWithNoArgument() throws Exception {
        final CallOrigin callOrigin = MockUtils.mock(CallOrigin.class);
        callOrigin.callVoid();
        MockBuilder.verify(callOrigin,1).call("callVoid");
    }

    @Test
    public void test() throws Exception {
        final CallOrigin callOrigin = MockUtils.mock(CallOrigin.class);
        callOrigin.callTwo("1","2");
        MockBuilder.verify(callOrigin,1).callWithArguments("callTwo").withArguments("1","2");
    }

    @Test
    public void testStatic() throws Exception {
        MockUtils.mockStatic(StaticClass.class);
        StaticClass.getRealValue(1,1);
//        PowerMockito.verifyPrivate(StaticClass.class,times(1)).invoke("getRealValue",Mockito.anyInt(),Mockito.anyInt());
        MockBuilder.verify(StaticClass.class,1).callWithArguments("getRealValue").withArguments(Mockito.anyInt(),Mockito.anyInt());
    }
    @Test
    public void testStatic_noArgument() throws Exception {
        MockUtils.mockStatic(StaticClass.class);
        StaticClass.getRealValue();
//        PowerMockito.verifyPrivate(StaticClass.class,times(1)).invoke("getRealValue",Mockito.anyInt(),Mockito.anyInt());
        MockBuilder.verify(StaticClass.class,1).call("getRealValue");
    }
}
