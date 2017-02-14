package test.testMockUtils;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import base.TestInit;
import classDefine.StaticClass;
import test.testMockUtils.bean.SomeClass;
import utils.UncleMock;
import utils.UncleMockException;

/**
 * Created by Administrator on 2017/2/11.
 */
@PrepareForTest({StaticClass.class,SomeClass.class})
public class TestVerify extends TestInit{
    @Test
    public void test_callWithNoArgument() throws Exception {
        final SomeClass someClass = UncleMock.mock(SomeClass.class);
        someClass.callVoid();
        UncleMock.verify(someClass,1).call("callVoid");
    }

    @Test
    public void test() throws Exception {
        final SomeClass someClass = UncleMock.mock(SomeClass.class);
        someClass.callTwo("1","2");
        UncleMock.verify(someClass,1).call("callTwo","1","2");
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
        final SomeClass someClass = new SomeClass();
        someClass.callTwo("1","2");
        boolean isError = false;
        try {
            UncleMock.verify(someClass,1).call("callTwo","1","2");
        }catch (UncleMockException e){
            e.printStackTrace();
            isError = true;
        }
        Assert.assertTrue(isError);
    }

}
