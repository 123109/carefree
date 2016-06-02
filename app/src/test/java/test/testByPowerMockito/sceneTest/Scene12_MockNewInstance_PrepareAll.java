package test.testByPowerMockito.sceneTest;

import junit.framework.Assert;

import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import base.TestInit;
import classDefine.ClassForScene12;
import classDefine.ClassForScene12_Dependency;

/**
 * 场景12：在创建某个对象时，抛出我们预先mock好的那一个
 * Created by cb on 2016/5/31.
 */
@PrepareForTest({ClassForScene12_Dependency.class, ClassForScene12.class})
public class Scene12_MockNewInstance_PrepareAll extends TestInit{
    @Test
    public void testMockNewInstance() throws Exception {
        ClassForScene12_Dependency mock = PowerMockito.mock(ClassForScene12_Dependency.class);
        PowerMockito.whenNew(ClassForScene12_Dependency.class).withArguments(10).thenReturn(mock);
        PowerMockito.when(mock.getValue()).thenReturn(0);
        ClassForScene12_Dependency dependency = new ClassForScene12_Dependency(10);
        Assert.assertTrue(dependency.getValue() == 0);
        Assert.assertTrue(ClassForScene12.getValue(10) == -2);
    }
}
