package test.testByPowerMockito.sceneTest;

import junit.framework.Assert;

import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import base.TestInit;
import classDefine.EnumClass;

/**
 * 场景9：mock一个枚举单例
 * Created by cb on 2016/5/30.
 */
@PrepareForTest({EnumClass.class})
public class Scene9_MockEnumInstance extends TestInit{
    @Test
    public void mockEnumInstance() throws Exception{
        //在ClassForScene9里面getValue方法依赖了EnumClass的getValue方法，我们需要对EnumClass进行mock
        EnumClass enumClass = PowerMockito.mock(EnumClass.class);
        PowerMockito.when(enumClass.getValue()).thenReturn(12);
        Assert.assertTrue(enumClass.getValue() == 12);
        org.powermock.reflect.Whitebox.setInternalState(EnumClass.INSTANCE, "INSTANCE", enumClass, EnumClass.class);
        Assert.assertTrue(EnumClass.INSTANCE.getValue() == 12);
        PowerMockito.when(enumClass.getValue()).thenReturn(10);
        Assert.assertTrue(EnumClass.INSTANCE.getValue() == 10);
    }
}
