package test.testByPowerMockito.sceneTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import classDefine.ClassForScene16;

/**
 * 场景16：模拟某个方法里调用了这个对象的某个私有方法
 * Created by cb on 2016/10/9.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ClassForScene16.class})
public class Scene16_VerifyPrivate {
    @Test
    public void testVerifyPrivate() throws Exception {
        ClassForScene16 scene = PowerMockito.spy(new ClassForScene16());
        scene.doSomething();
        PowerMockito.verifyPrivate(scene, Mockito.times(1)).invoke("doPrivate");
    }

    @Test
    public void testVerifyPrivate_unCalled() throws Exception {
        ClassForScene16 scene = PowerMockito.spy(new ClassForScene16());
        scene.doSomethingWithoutPrivate();
        PowerMockito.verifyPrivate(scene, Mockito.times(0)).invoke("doPrivate");
    }
}
