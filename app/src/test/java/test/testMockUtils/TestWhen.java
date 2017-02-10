package test.testMockUtils;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
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
        CallOrigin origin = MockUtils.spy(new CallOrigin());
        MockUtils.when(origin,"callOne").thenReturn("123");
        Assert.assertTrue(origin.callOne().equals("123"));
    }

    @Test
    public void testDoNothingWhen() throws Exception {
        CallOrigin origin = MockUtils.spy(new CallOrigin());
        origin.callVoid();
        MockUtils.doNothingWhen(origin,"callVoid");
    }

    @Test
    public void testCallMethodBuilder() throws Exception {
//        CallOrigin origin = MockUtils.spy(new CallOrigin());
        CallOrigin origin = new CallOrigin();
        Assert.assertTrue(origin.callTwo("123","456").equals("123456"));
        MockBuilder.when(origin.callTwo("123","456")).thenReturn("123").build();
        Assert.assertTrue(origin.callTwo("123","789").equals("123"));
    }

    @Test
    public void testCallMethodBuilder_staticMethod() throws Exception {
        PowerMockito.mockStatic(StaticClass.class);
        MockBuilder.when(StaticClass.getRealValue(Mockito.anyInt(),Mockito.anyInt())).thenReturn(1).build();
        Assert.assertTrue(StaticClass.getRealValue(1,2) == 1);
    }

    @Test
    public void testCallMethodBuilder_doNothing() throws Exception {
        CallOrigin origin = MockUtils.spy(new CallOrigin());
        origin.callVoid();
        System.out.print("\nafter mock");
        MockBuilder.when(origin,"callVoid").doNothing();
        origin.callVoid();
    }

    @Test
    public void testCallMethodBuilder_doNothingOnReturn() throws Exception {
        CallOrigin origin = MockUtils.spy(new CallOrigin());
        origin.callVoid();
        System.out.print("\nafter mock");
        MockBuilder.when(origin,"callOne").doNothing();
        origin.callOne();
    }

    @Test
    public void testCallMethodByNameBuilder_staticMethod() throws Exception {
        PowerMockito.mockStatic(StaticClass.class);
        MockBuilder.when(StaticClass.class,"getMockValue").thenReturn(1).build();
        Assert.assertTrue(StaticClass.getMockValue() == 1);
    }

    @Test
    public void testCallMethodByNameBuilder() throws Exception {
        CallOrigin origin = new CallOrigin();
//        CallOrigin origin = MockUtils.spy(new CallOrigin());
        Assert.assertTrue(origin.callTwo("123","456").equals("123456"));
        MockBuilder.when(origin,"callTwo")
                .argument("123")
                .argument("789")
                .thenReturn("123")
                .returnNextTime("124").build();
        Assert.assertTrue(origin.callTwo("123","789").equals("123"));
        Assert.assertTrue(origin.callTwo("123","789").equals("124"));
    }
}
