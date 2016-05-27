package test.testByPowerMockito.sceneTest;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.modules.junit4.PowerMockRunner;

import classDefine.ClassForScene4;

/**
 * 场景4：确认某个对象的成员变量的值是我们预期的
 * 比如某个对象里有一个私有的成员变量，它有一个公共的void方法 会使这个值发生变化，我们要测试这个公共方法，就需要确认调用这个公共的方法之后，该变量确实发生了变化。
 * 要实现这个测试，有两个方法：1.修改方法的返回值，把成员变量丢出来（当然这是不靠谱的，因为有可能这个成员变量是个很复杂的数据结构）
 * 2.直接取出这个变量的值进行判断
 * Created by cb on 2016/5/20.
 */
@RunWith(PowerMockRunner.class)
public class Scene4_AssertPrivateField {

    @Test
    public void tesAssertPrivateFieldByPowerMockito() throws Exception{
        ClassForScene4 test = new ClassForScene4();
        test.changeValue(10);
        int value = (int) Whitebox.getInternalState(test, "mValue");
        Assert.assertTrue(value == 10);
        test.changeValue(-2);
        value = (int) Whitebox.getInternalState(test, "mValue");
        Assert.assertTrue(value == 2);
    }
}
