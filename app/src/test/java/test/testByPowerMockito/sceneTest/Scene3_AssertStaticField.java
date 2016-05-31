package test.testByPowerMockito.sceneTest;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import classDefine.ClassForScene3;

/**
 * 场景3：确认某个静态类的成员变量的值是我们预期的
 * 比如某个静态类有一个私有的成员变量，它有一个公共的void方法 会使这个值发生变化，我们要测试这个公共方法，就需要确认调用这个公共的方法之后，该变量确实发生了变化。
 * 要实现这个测试，有两个方法：1.修改方法的返回值，把成员变量丢出来（当然这是不靠谱的，因为有可能这个成员变量是个很复杂的数据结构）
 * 2.直接取出这个变量的值进行判断
 * powerMockito提供了一个Whitebox.setInternalState方法，可惜这个方法不能对静态类使用，只能对具体对象用
 * Created by cb on 2016/5/20.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ClassForScene3.class})
public class Scene3_AssertStaticField {

    @Test
    public void tesAssertStaticFieldByPowerMockito() throws Exception{
        ClassForScene3.changeValue(10);
        int value = org.powermock.reflect.Whitebox.getInternalState(ClassForScene3.class, "mValue",ClassForScene3.class);
        Assert.assertTrue(value == 10);
        ClassForScene3.changeValue(-10);
        value = org.powermock.reflect.Whitebox.getInternalState(ClassForScene3.class, "mValue", ClassForScene3.class);
        Assert.assertTrue(value == 10);
    }
}
