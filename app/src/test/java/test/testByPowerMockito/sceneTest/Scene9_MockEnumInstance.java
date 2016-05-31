package test.testByPowerMockito.sceneTest;

import junit.framework.Assert;

import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import base.TestInit;
import classDefine.ClassForScene9;
import classDefine.EnumClass;
import utils.MockUtils;

/**
 * 场景9：mock一个枚举单例
 * Created by cb on 2016/5/30.
 */
@PrepareForTest({EnumClass.class})
public class Scene9_MockEnumInstance extends TestInit{
    @Test
    public void mockEnumInstance() throws Exception{
        //在ClassForScene9里面getValue方法依赖了EnumClass的getValue方法，我们需要对EnumClass进行mock
        MockUtils.mockEnumInstance(EnumClass.class);
        PowerMockito.when(EnumClass.INSTANCE.getValue()).thenReturn(10);
        ClassForScene9 test = new ClassForScene9();
        Assert.assertTrue(test.getValue() == 10);
        PowerMockito.when(EnumClass.INSTANCE.getValue()).thenReturn(20);
        Assert.assertTrue(test.getValue() == 20);
    }

    @Test
    public void mockEnumInstanceByPowerMockito() throws Exception{
        // 我们按mock一个单例的套路来mock一个枚举单例的时候就苦B了……
        //下面这几行代码是OK的
        EnumClass enumClass = PowerMockito.mock(EnumClass.class);
        PowerMockito.when(enumClass.getValue()).thenReturn(12);
        Assert.assertTrue(enumClass.getValue() == 12);
        System.out.print(enumClass.toString()+"\n");
        //但是要想把mock出来的enumClass丢给EnumClass.INSTANCE，这个powerMockito表示做不到哇~
        //比如下面这样(mock单例的套路)：EnumClass.INSTANCE实际上是一个对象，而不是一个方法 ，所以没办法用when...thenReturn
//        PowerMockito.mockStatic(EnumClass.class);
//        PowerMockito.when(EnumClass.INSTANCE).thenReturn(enumClass);

        //用whiteBox也不行
//        Whitebox.setInternalState(EnumClass.INSTANCE, "INSTANCE", enumClass);
//        Assert.assertTrue(EnumClass.INSTANCE.getValue() == 12);
        org.powermock.reflect.Whitebox.setInternalState(EnumClass.INSTANCE, "INSTANCE", enumClass, EnumClass.class);
        System.out.print(EnumClass.INSTANCE.toString());
        EnumClass result = EnumClass.INSTANCE;
        Assert.assertTrue(EnumClass.INSTANCE.getValue() == 12);
////        MockUtils.mockEnumInstance(EnumClass.class);
//        PowerMockito.when(enumClass.getValue()).thenReturn(10);
//        ClassForScene9 test = new ClassForScene9();
//        Assert.assertTrue(test.getValue() == 10);
//        PowerMockito.when(enumClass.getValue()).thenReturn(20);
//        Assert.assertTrue(test.getValue() == 20);
    }
}
