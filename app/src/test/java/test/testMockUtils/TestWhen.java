package test.testMockUtils;

import org.junit.Assert;
import org.junit.Test;
import org.powermock.core.classloader.annotations.PrepareForTest;

import base.TestInit;
import classDefine.StaticClass;
import utils.MockUtils;
import utils.builder.MockBuilder;

/**
 * Created by Administrator on 2017/2/10.
 */
@PrepareForTest({StaticClass.class})
public class TestWhen extends TestInit{
    @Test
    public void testWhen() throws Exception {
        final CallOrigin mock = MockUtils.mock(CallOrigin.class);
        MockBuilder.when(mock).call("callOne").thenReturn("1234");
        Assert.assertTrue(mock.callOne().equals("1234"));
    }

    @Test
    public void testWhen_returnNullSecond() throws Exception {
        final CallOrigin mock = MockUtils.mock(CallOrigin.class);
        final Object otherValue = null;
        MockBuilder.when(mock).call("callOne").thenReturnValues("1234", otherValue);
        Assert.assertTrue(mock.callOne().equals("1234"));
        Assert.assertTrue(mock.callOne() == null);
    }

    @Test
    public void testWhen_returnTwo() throws Exception {
        final CallOrigin mock = MockUtils.mock(CallOrigin.class);
        MockBuilder.when(mock).call("callOne").thenReturnValues("1234","1");
        Assert.assertTrue(mock.callOne().equals("1234"));
        Assert.assertTrue(mock.callOne().equals("1"));
    }

    @Test
    public void testWhen_more() throws Exception {
        final CallOrigin mock = MockUtils.mock(CallOrigin.class);
        MockBuilder.when(mock).call("callOne").thenReturnValues("1234","1");
        Assert.assertTrue(mock.callOne().equals("1234"));
        Assert.assertTrue(mock.callOne().equals("1"));
        Assert.assertTrue(mock.callOne().equals("1"));
    }

    @Test
    public void testWhen_callTwo() throws Exception{
        CallOrigin mock = MockUtils.mock(CallOrigin.class);
        MockBuilder.when(mock).call("callTwo").withArguments("1","2").thenReturn("1");
        Assert.assertTrue(mock.callTwo("1","2").equals("1"));
    }

    @Test
    public void testWhen_static() throws Exception{
        MockUtils.mockStatic(StaticClass.class);
        MockBuilder.when(StaticClass.class).call("getRealValue").thenReturn(1000);
        Assert.assertTrue(StaticClass.getRealValue() == 1000);
    }
}
