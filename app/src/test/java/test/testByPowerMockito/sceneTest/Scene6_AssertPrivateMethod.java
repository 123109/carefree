package test.testByPowerMockito.sceneTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import classDefine.ClassForScene5;
import classDefine.ClassForScene6;

/**
 * 场景6：确认某个类里的某个私有方法被跑到了
 * 1.powerMockito提供的verifyPrivate方法
 * 2.机智的叔想出了另一个办法……
 * Created by cb on 2016/5/27.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ClassForScene5.class})
public class Scene6_AssertPrivateMethod {
    @Test
    public void verifyPrivateByPowerMockito() throws Exception {
        ClassForScene6 test = new ClassForScene6();
        test.test(0);
        PowerMockito.verifyPrivate(test).invoke("doSomething");
        // 有两个很蛋疼的问题，
        // 1.下面这三行都能通过……times()函数表示这个方法 被执行几次，never()则意味着这个方法 从未被执行，但是下面这三行分别跑都能通过……叔的打开方式不对吗？
        // 2.任意两个verify方法先后执行都会报错……比如上面的那一行和下面的任何一行一起跑，就会报错
//        PowerMockito.verifyPrivate(test, times(1)).invoke("doSomething");
//        PowerMockito.verifyPrivate(test, times(3)).invoke("doSomething");
//        PowerMockito.verifyPrivate(test, never()).invoke("doSomething");


        //下面这一行是跑不过的，会提示：
//        org.mockito.exceptions.misusing.UnfinishedVerificationException:
//        Missing method callWithNoArguments for verify(mock) here:
//        -> at test.testByPowerMockito.sceneTest.Scene6_AssertPrivateMethod.verifyPrivateByPowerMockito(Scene6_AssertPrivateMethod.java:28)
//        PowerMockito.verifyPrivate(test).invoke("doNothing");
    }
}
