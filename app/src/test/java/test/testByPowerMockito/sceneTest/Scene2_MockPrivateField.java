package test.testByPowerMockito.sceneTest;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import classDefine.ClassForScene2;
import classDefine.StaticClass;

/**
 * 场景2：mock一个成员变量。
 * 比如有一个类，这个类里有一个私有的成员变量，这个成员变量只能通过这个类的某些方法来改变，这种情况下，有两个方法来解决：
 * 1.通过spy这个能改变这个成员变量的方法去mock我们想要的值，但是有可能这个方法需要context，或者是其它的依赖，那么就有可能最后Mock出来了，但是为了实现这一mock写几十行的代码；
 * 2.通过powerMockito提供的一个Whitebox.setInternalState方法（在这个场景里，这个方法其实和我们自己的mock工具的方法是一样的）
 * Created by cb on 2016/5/20.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({StaticClass.class,ClassForScene2.class})
public class Scene2_MockPrivateField {
    @Test
    public void testMockPrivateFieldBySpy() throws Exception {
        ClassForScene2 test1 = PowerMockito.spy(new ClassForScene2());
        PowerMockito.when(test1,"initValue").thenReturn(20);
        Assert.assertTrue(test1.isOdd() == false);
        ClassForScene2 test2 = PowerMockito.spy(new ClassForScene2());
        PowerMockito.when(test2,"initValue").thenReturn(11);
        Assert.assertTrue(test2.isOdd() == true);

    }

    @Test
    public void testMockPrivateFieldByPowerMockito() throws Exception{
        ClassForScene2 classForScene2 = new ClassForScene2();
        Whitebox.setInternalState(classForScene2, "mValue", 2);
        Assert.assertTrue(classForScene2.isOdd() == false);
        Whitebox.setInternalState(classForScene2, "mValue", 1);
        Assert.assertTrue(classForScene2.isOdd() == true);
    }
}
