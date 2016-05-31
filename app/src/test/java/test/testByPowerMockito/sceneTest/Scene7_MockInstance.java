package test.testByPowerMockito.sceneTest;

import junit.framework.Assert;

import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import base.TestInit;
import classDefine.ClassForScene7;
import classDefine.InstanceClass;

/**
 * 场景7：mock一个单例
 * Created by cb on 2016/5/30.
 */
@PrepareForTest(InstanceClass.class)
public class Scene7_MockInstance extends TestInit{
    @Test
    public void mockInstance() throws Exception{
        //在ClassForScene7里面getValue方法依赖了InstanceClass的getValue方法，我们需要对InstanceClass进行mock
        InstanceClass mInstanceClass = PowerMockito.mock(InstanceClass.class);
        PowerMockito.mockStatic(InstanceClass.class);
        PowerMockito.when(InstanceClass.getInstance()).thenReturn(mInstanceClass);
        PowerMockito.when(mInstanceClass.getValue()).thenReturn(2);
        ClassForScene7 test = new ClassForScene7();
        Assert.assertTrue(test.getValue() == 2);
        PowerMockito.when(mInstanceClass.getValue()).thenReturn(20);
        Assert.assertTrue(test.getValue() == 20);
    }

    @Test
    public void mockInstance_2() throws Exception{
        //这边是另一种方法来mock一个单例
        InstanceClass mInstanceClass = PowerMockito.mock(InstanceClass.class);
        org.powermock.reflect.Whitebox.setInternalState(InstanceClass.getInstance(),"sInstance",mInstanceClass,InstanceClass.class);
        PowerMockito.when(mInstanceClass.getValue()).thenReturn(2);
        ClassForScene7 test = new ClassForScene7();
        Assert.assertTrue(test.getValue() == 2);
        PowerMockito.when(mInstanceClass.getValue()).thenReturn(20);
        Assert.assertTrue(test.getValue() == 20);
    }
}
