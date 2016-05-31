package test.testByPowerMockito.sceneTest;

import junit.framework.Assert;

import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import base.TestInit;
import classDefine.ClassForScene7;
import classDefine.InstanceClass;
import utils.MockUtils;

/**
 * 场景7：mock一个单例
 * Created by cb on 2016/5/30.
 */
@PrepareForTest(InstanceClass.class)
public class Scene7_MockInstance extends TestInit{
    @Test
    public void mockInstance() throws Exception{
        //在ClassForScene7里面getValue方法依赖了InstanceClass的getValue方法，我们需要对InstanceClass进行mock
        MockUtils.mockInstanceClass(InstanceClass.class);
        PowerMockito.when(InstanceClass.getInstance().getValue()).thenReturn(10);
        ClassForScene7 test = new ClassForScene7();
        Assert.assertTrue(test.getValue() == 10);
        PowerMockito.when(InstanceClass.getInstance().getValue()).thenReturn(20);
        Assert.assertTrue(test.getValue() == 20);
    }

    @Test
    public void mockInstanceByPowerMockito() throws Exception{
        InstanceClass mInstanceClass = PowerMockito.mock(InstanceClass.class);
        PowerMockito.mockStatic(InstanceClass.class);
        PowerMockito.when(InstanceClass.getInstance()).thenReturn(mInstanceClass);
        PowerMockito.when(mInstanceClass.getValue()).thenReturn(2);
//        PowerMockito.when(InstanceClass.getInstance().getValue()).thenReturn(10);
        ClassForScene7 test = new ClassForScene7();
        Assert.assertTrue(test.getValue() == 2);
        PowerMockito.when(mInstanceClass.getValue()).thenReturn(20);
        Assert.assertTrue(test.getValue() == 20);
    }
}
