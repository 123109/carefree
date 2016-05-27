package test.testByMockito;

import junit.framework.Assert;

import org.junit.Test;
import org.mockito.Mockito;

import classDefine.FinalClass;

/**
 * Created by cb on 2016/5/26.
 */
public class MockFinalClass {
    @Test
    public void testMockFinal(){
        //实际上这个用例是跑不过的，会报下面这个错误：
//        org.mockito.exceptions.base.MockitoException:
//        Cannot mock/spy class classDefine.FinalClass
//        Mockito cannot mock/spy following:
//        - final classes
//        - anonymous classes
//                - primitive types
        FinalClass finalClass = Mockito.mock(FinalClass.class);
        Mockito.when(finalClass.getMockValue()).thenReturn(20);
        Assert.assertTrue(finalClass.getMockValue() == 20);
    }
}
