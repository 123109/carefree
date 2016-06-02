package test.testByPowerMockito.sceneTest;

import junit.framework.Assert;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;

import classDefine.ClassForScene12_Dependency;

/**
 * 场景13：mock一个方法，让它在不同的输入的情况下返回不同的值
 * Created by cb on 2016/5/31.
 */
public class Scene13_MockValueByVariableInput {
    @Test
    public void testSeparate() throws Exception{
        ClassForScene12_Dependency dependency = PowerMockito.mock(ClassForScene12_Dependency.class);
        PowerMockito.when(dependency.getValueByInput(1)).thenReturn(2);
        PowerMockito.when(dependency.getValueByInput(2)).thenReturn(3);
        PowerMockito.when(dependency.getValueByInput(3)).thenReturn(4);
        Assert.assertTrue(dependency.getValueByInput(1) == 2);
        Assert.assertTrue(dependency.getValueByInput(2) == 3);
        Assert.assertTrue(dependency.getValueByInput(3) == 4);
    }

    @Test
    public void testCompound() throws Exception{
        ClassForScene12_Dependency dependency = PowerMockito.mock(ClassForScene12_Dependency.class);
        PowerMockito.when(dependency,"getValueByInput", Matchers.anyInt()).thenAnswer(new Answer<Integer>() {
            @Override
            public Integer answer(final InvocationOnMock invocation) throws Throwable {
                Object[] params = invocation.getArguments();
                int value = (int) params[0];
                switch (value){
                    case 1:
                        return 2;
                    case 2:
                        return 3;
                    case 3:
                        return 4;
                    default:
                        return 0;
                }
            }
        });
        Assert.assertTrue(dependency.getValueByInput(1) == 2);
        Assert.assertTrue(dependency.getValueByInput(2) == 3);
        Assert.assertTrue(dependency.getValueByInput(3) == 4);
        Assert.assertTrue(dependency.getValueByInput(4) == 0);
    }
}
