package test.testMockUtils;

import org.junit.Assert;
import org.junit.Test;

import base.TestInit;
import classDefine.EnumClass;
import classDefine.FinalClass;
import classDefine.InstanceClass;
import classDefine.StaticClass;
import utils.UncleMock;
import utils.UncleMockException;

/**
 * Created by Administrator on 2017/2/13.
 */
public class TestBadMock extends TestInit{

    @Test
    public void test_mock_final(){
        FinalClass unMock = new FinalClass();
        int mValue = UncleMock.getValue(unMock,"mValue");
        Assert.assertTrue(mValue == 100);
        int mockValue = 0;
        boolean isError = false;
        try {
            FinalClass mock = UncleMock.mock(FinalClass.class);
            mockValue = mock.getMockValue();
        }catch (UncleMockException e){
            e.printStackTrace();
            isError = true;
        }
        Assert.assertTrue(mockValue == 0);
        Assert.assertTrue(isError);
    }

    @Test
    public void test_mock_static(){
        int mValue = StaticClass.getMockValue();
        Assert.assertTrue(mValue == 20);
        int mockValue = 1;
        boolean isError = false;
        try {
            UncleMock.mockStatic(StaticClass.class);
            mockValue = StaticClass.getMockValue();
        }catch (UncleMockException e){
            e.printStackTrace();
            isError = true;
        }
        Assert.assertTrue(mockValue == 1);
        Assert.assertTrue(isError);
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
        int mockValue = 1;
        boolean isError = false;
        try {
            EnumClass mock = UncleMock.mockInstance(EnumClass.class);
            mockValue = mock.getValue();
        }catch (UncleMockException e){
            e.printStackTrace();
            isError = true;
        }
        Assert.assertTrue(mockValue == 1);
        Assert.assertTrue(isError);
    }
}
