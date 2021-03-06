package test.testMockUtils;

import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import base.TestInit;
import classDefine.StaticClass;
import test.testMockUtils.bean.SomeClass;
import utils.UncleMock;

/**
 * Created by Administrator on 2017/2/12.
 */
@PrepareForTest({StaticClass.class})
public class TestDoNothing extends TestInit{
    @Test
    public void test_class() throws Exception {
        System.out.print("before doNothing\n");
        StaticClass.setRealValue(1);
        PowerMockito.spy(StaticClass.class);
        UncleMock.doNothing().when(StaticClass.class).call("setRealValue",1);
        System.out.print("after doNothing\n");
        StaticClass.setRealValue(1);
    }

    @Test
    public void test_class_any() throws Exception {
        StaticClass.setRealValue(1);
        PowerMockito.spy(StaticClass.class);
        UncleMock.doNothing().when(StaticClass.class).call("setRealValue", Mockito.anyInt());
        StaticClass.setRealValue(1);
    }

    @Test
    public void test_object() throws Exception{
        SomeClass origin = UncleMock.spy(new SomeClass());
        origin.callOne();
        origin.callVoid();
        UncleMock.doNothing().when(origin).call("callVoid");
        origin.callVoid();
    }
}
