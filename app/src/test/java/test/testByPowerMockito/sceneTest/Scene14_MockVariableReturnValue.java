package test.testByPowerMockito.sceneTest;

import junit.framework.Assert;

import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import classDefine.ClassForScene14;
import classDefine.ClassForScene14_Dependency;

/**
 * 场景14：mock一个对象，让它在不同的处理阶段返回不同的值
 * Created by cb on 2016/5/31.
 */
public class Scene14_MockVariableReturnValue {
    @Test
    public void testReturnDifferentValueInOneCase() throws Exception{
        //在ClassForScene14的test方法里，我们传入一个ClassForScene14_Dependency的实例，根据这个实例进行进行一些操作
        //在这个case里，有个很蛋疼的问题就是，有两个地方需要对dependency.isValid()进行判断，而如果要做到全覆盖，这两个值必须是不一样的。
        ClassForScene14_Dependency dependencyClass = PowerMockito.mock(ClassForScene14_Dependency.class);
        PowerMockito.when(dependencyClass.isValid()).thenReturn(false, true);
        ClassForScene14 inherit = new ClassForScene14();
        Assert.assertTrue(inherit.test(dependencyClass));
    }
}
