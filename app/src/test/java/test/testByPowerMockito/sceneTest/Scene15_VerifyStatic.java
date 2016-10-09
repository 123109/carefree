package test.testByPowerMockito.sceneTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import classDefine.ClassForScene15;
import classDefine.StaticClass;

/**
 * 场景16：确认某个静态方法被调用或者未被调用
 * 注意：被verify的类必须PrepareForTest
 * Created by cb on 2016/10/9.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({StaticClass.class})
public class Scene15_VerifyStatic {

    @Test
    public void testVerifyStatic(){
        PowerMockito.mockStatic(StaticClass.class);
        ClassForScene15 scene = new ClassForScene15();
        scene.doSomething();
        PowerMockito.verifyStatic(Mockito.times(1));
        StaticClass.getMockValue();
    }

    @Test
    public void testVerifyStatic_unCalled(){
        PowerMockito.mockStatic(StaticClass.class);
        ClassForScene15 scene = new ClassForScene15();
        scene.doSomething();
        PowerMockito.verifyStatic(Mockito.times(0));
        StaticClass.getRealValue();
    }
}
