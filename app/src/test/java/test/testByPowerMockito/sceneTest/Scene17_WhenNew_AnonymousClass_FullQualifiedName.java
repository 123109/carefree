package test.testByPowerMockito.sceneTest;

import org.junit.Assert;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import base.TestInit;
import classDefine.ClassForScene17;
import classDefine.NormalClass;

/**
 * 场景17：模拟被测方法里创建了一个匿名内部类，在这个匿名内部类里修建了某个类的新的对象
 * 在这个测试中，prepareForTest使用的是fullyQualifiedNames这种形式，可以控制匿名内部类的mock
 * Created by cb on 2016/10/9.
 */
@PrepareForTest(fullyQualifiedNames = {
        "classDefine.*"
})
public class Scene17_WhenNew_AnonymousClass_FullQualifiedName extends TestInit{
    @Test
    public void testWhenNew() throws Exception {
        final NormalClass normalClass = PowerMockito.mock(NormalClass.class);
        PowerMockito.whenNew(NormalClass.class).withNoArguments().thenReturn(normalClass);
        ClassForScene17 test = new ClassForScene17();
        test.doSomething();
        Assert.assertTrue(test.getMock() == normalClass);
    }
}
