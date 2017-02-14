package test.testMockUtils;

import org.junit.Assert;
import org.junit.Test;
import org.powermock.core.classloader.annotations.PrepareForTest;

import base.TestInit;
import classDefine.EnumClass;
import classDefine.FinalClass;
import classDefine.InstanceClass;
import classDefine.StaticClass;
import test.testMockUtils.bean.SomeClass;
import test.testMockUtils.bean.DependencyClass;
import utils.UncleMock;

/**
 * Created by Administrator on 2017/2/13.
 */
@PrepareForTest({FinalClass.class,StaticClass.class,EnumClass.class})
public class TestMock extends TestInit{
    @Test
    public void test_mock(){
        SomeClass unMock = new SomeClass();
        DependencyClass dependencyClass = UncleMock.getValue(unMock,"mOne");
        Assert.assertTrue(dependencyClass != null);
        SomeClass mock = UncleMock.mock(SomeClass.class);
        DependencyClass dependencyClassInMock = UncleMock.getValue(mock,"mOne");
        Assert.assertTrue(dependencyClassInMock == null);
    }

    @Test
    public void test_mock_final(){
        FinalClass unMock = new FinalClass();
        int mValue = UncleMock.getValue(unMock,"mValue");
        Assert.assertTrue(mValue == 100);
        FinalClass mock = UncleMock.mock(FinalClass.class);
        int mockValue = UncleMock.getValue(mock,"mValue");
        Assert.assertTrue(mockValue == 0);
    }

    @Test
    public void test_mock_static(){
        int mValue = StaticClass.getMockValue();
        Assert.assertTrue(mValue == 20);
        UncleMock.mockStatic(StaticClass.class);
        int  mockValue = StaticClass.getMockValue();
        Assert.assertTrue(mockValue == 0);
    }

    @Test
    public void test_mock_instance(){
        Assert.assertTrue(InstanceClass.getInstance().getValue() == 10);
        InstanceClass mock = UncleMock.mockInstance(InstanceClass.class);
        Assert.assertTrue(InstanceClass.getInstance().getValue() == 0);
        Assert.assertTrue(mock == InstanceClass.getInstance());
    }

    @Test
    public void test_mock_enumInstance(){
        Assert.assertTrue(EnumClass.INSTANCE.getValue() == 100);
        EnumClass mock = UncleMock.mockInstance(EnumClass.class);
        Assert.assertTrue(EnumClass.INSTANCE.getValue() == 0);
        Assert.assertTrue(mock == EnumClass.INSTANCE);
    }
}
