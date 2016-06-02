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
@PrepareForTest(ClassForScene12_Dependency.class)
public class Scene12_MockNewInstance extends TestInit{
    @Test
    public void testMockNewInstance() throws Exception {
        ClassForScene12_Dependency mock = PowerMockito.mock(ClassForScene12_Dependency.class);
        PowerMockito.whenNew(ClassForScene12_Dependency.class).withArguments(10).thenReturn(mock);
        PowerMockito.when(mock.getValue()).thenReturn(0);
        ClassForScene12_Dependency dependency = new ClassForScene12_Dependency(10);
        Assert.assertTrue(dependency.getValue() == 0);
        //注意下面这个断言，ClassForScene12.getValue(10)并没有返回-2而是返回20，说明在ClassForScene12内的创建的ClassForScene12_Dependency实例未被mock
        //这是因为这个测试里没有在PrepareForTest里增加ClassForScene12.class（见Scene12_MockNewInstance_PrepareAll）
        Assert.assertTrue(ClassForScene12.getValue(10) == 20);

        //注意下面这个断言，我们会发现传入的参数不为10的时候，是无法创建新的实例的
        ClassForScene12_Dependency anotherDependency = new ClassForScene12_Dependency(20);
        Assert.assertNull(anotherDependency);

        //所以一旦在前面使用了whenNew并且定义了一个返回之后，要想再整个新的……就只能再写一条这样的语句了
        PowerMockito.whenNew(ClassForScene12_Dependency.class).withArguments(20).thenReturn(mock);
        Assert.assertTrue(new ClassForScene12_Dependency(20).getValue() == 0);

    }

    @Test
    public void testMockNewInstanceWithAnyArguments() throws Exception {
        ClassForScene12_Dependency mock = PowerMockito.mock(ClassForScene12_Dependency.class);
        PowerMockito.whenNew(ClassForScene12_Dependency.class).withAnyArguments().thenReturn(mock);
        PowerMockito.when(mock.getValue()).thenReturn(0);
        ClassForScene12_Dependency dependency = new ClassForScene12_Dependency(10);
        Assert.assertTrue(dependency.getValue() == 0);
        ClassForScene12_Dependency anotherDependency = new ClassForScene12_Dependency(10);
        Assert.assertTrue(anotherDependency.getValue() == 0);
    }
}
