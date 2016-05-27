package test.testByMockito;

import junit.framework.Assert;

import org.junit.Test;
import org.mockito.Mockito;

import classDefine.StaticClass;

/**
 * Created by cb on 2016/5/26.
 */
public class MockStaticClass {
    @Test
    public void testMockStatic(){
        //实际上这个用例是跑不过的，会报下面这个错误：
//        org.mockito.exceptions.misusing.MissingMethodInvocationException:
//        when() requires an argument which has to be 'a method call on a mock'.
//                For example:
//        when(mock.getArticles()).thenReturn(articles);
//
//        Also, this error might show up because:
//        1. you stub either of: final/private/equals()/hashCode() methods.
//                Those methods *cannot* be stubbed/verified.
//                Mocking methods declared on non-public parent classes is not supported.
//        2. inside when() you don't call method on mock but on some other object.
        Mockito.mock(StaticClass.class);
        Mockito.when(StaticClass.getMockValue()).thenReturn(20);
        Assert.assertTrue(StaticClass.getMockValue() == 20);
    }
}
