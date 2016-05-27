package test.testByPowerMockito.sceneTest;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import classDefine.ClassForScene1;
import classDefine.StaticClass;
import utils.MockUtils;

/**
 * 场景1：mock一个静态的成员变量。
 * 比如有一个静态类，这个静态类里有一个私有的（共有的就不用说了，直接丢个值进去就完事）成员变量，这个成员变量只能通过这个类的某些方法来改变，那么就只有两个方法来解决：
 * 1.通过spy这个能改变这个成员变量的方法去mock我们想要的值，但是有可能这个方法需要context，或者是其它的依赖，那么就有可能最后Mock出来了，但是为了实现这一mock写几十行的代码；
 * 另外这还有种方法还有可能是没办法mock出每一个我们想要的值
 * 2.通过我们自己的mock工具直接丢一个我们想要的值进去
 * powerMockito提供了一个Whitebox.setInternalState方法，可惜这个方法不能对静态类使用，只能对具体对象用
 * Created by cb on 2016/5/20.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({StaticClass.class,ClassForScene1.class})
public class Scene1_MockStaticField {

    @Test
    public void testMockStaticPrivateFieldBySpy() throws Exception {
        PowerMockito.spy(ClassForScene1.class);
        PowerMockito.when(ClassForScene1.class, "initValue").thenReturn(20);
        Assert.assertTrue(ClassForScene1.isOdd() == false);
        //这是一个很简单的case，如果initValue里面涉及到非常 复杂的外部依赖或者业务操作，则这个mock将会变得非常复杂
    }

    @Test
    public void testMockStaticPrivateFieldByPowerMockito() throws Exception{
        //实际上这个用例是跑不过的，会报以下错误：
        //java.lang.RuntimeException: Unable to set internal state on a private field. Please report to mockito mailing list.
        PowerMockito.mockStatic(StaticClass.class);
        Whitebox.setInternalState(StaticClass.class, "mPrivateValue", 2);
        PowerMockito.when(StaticClass.getMockValue()).thenReturn(12);
    }

    @Test
    public void testMockStaticPrivateFieldByLocalUtils() throws Exception{
        MockUtils.setToStaticField(StaticClass.class, "mPrivateValue", 2);
        Assert.assertTrue(StaticClass.getMockValue() == 12);
    }


}
