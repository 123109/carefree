package test.testByMockito;

import junit.framework.Assert;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import classDefine.NormalClass;

/**
 * Created by cb on 2016/5/26.
 */
public class MockNormalClass {
    @Test
    public void testMockNormal(){
        NormalClass normalClass = Mockito.mock(NormalClass.class);
        Mockito.when(normalClass.getMockValue()).thenReturn(20);
        Assert.assertTrue(normalClass.getMockValue() == 20);
    }

    @Test
    public void testVerify(){
        NormalClass normalClass = Mockito.mock(NormalClass.class);
        Mockito.when(normalClass.getMockValue()).thenReturn(20);
        Assert.assertTrue(normalClass.getMockValue() == 20);
        Mockito.verify(normalClass,Mockito.times(1)).getMockValue();
    }

    @Test
    public void testWhenAnswer(){
        NormalClass normalClass = Mockito.mock(NormalClass.class);
        Mockito.when(normalClass.getAnswerValue(Mockito.anyInt())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(final InvocationOnMock invocation) throws Throwable {
                Object[] arguments= invocation.getArguments();
                if (arguments[0].equals(Integer.valueOf(1))){
                    return 20;
                }else if (arguments[0].equals(Integer.valueOf(2))){
                    return 30;
                }else {
                    return 40;
                }
            }
        });
        Assert.assertTrue(normalClass.getAnswerValue(1) == 20);
        Assert.assertTrue(normalClass.getAnswerValue(2) == 30);
        Assert.assertTrue(normalClass.getAnswerValue(7) == 40);
        //如果是times(2)，就会报错，因为它实际上执行了3次
        //Mockito.verify(normalClass, Mockito.times(2)).getAnswerValue(Mockito.anyInt());
        Mockito.verify(normalClass, Mockito.times(3)).getAnswerValue(Mockito.anyInt());
    }
}
