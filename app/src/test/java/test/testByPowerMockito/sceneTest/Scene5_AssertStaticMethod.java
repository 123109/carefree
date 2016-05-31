package test.testByPowerMockito.sceneTest;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import classDefine.ClassForScene5;

/**
 * 场景5：确认某个静态类里的某个方法被跑到了
 * 1.powerMockito提供了一个verifyStatic的方法，可惜直到目前我还不会用，完全不知道要怎么玩，看了网上的各种示例依然一头雾水
 * 2.使用verifyPrivate……这是何等的卧槽
 * 3.机智的叔想出了另一个办法……
 * Created by cb on 2016/5/27.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ClassForScene5.class})
public class Scene5_AssertStaticMethod {
    @Test
    public void verifyStaticPrivateByPowerMockito() throws Exception {
        ClassForScene5.test();
        PowerMockito.verifyPrivate(ClassForScene5.class).invoke("doSomething");
        //下面这行代码是跑不过的，因为这个方法没被调到
//        PowerMockito.verifyPrivate(ClassForScene5.class).invoke("doNothing");
    }

    @Test
    public void verifyStaticPublicByPowerMockito() throws Exception {
        //通过这个case我们会惊(懵)喜(B)地发现，verifyPrivate方法也能确认一个public方法 ……
        ClassForScene5.testPublic();
        PowerMockito.verifyPrivate(ClassForScene5.class).invoke("doPublicSomething");
    }

    @Test
    public void verifyTwoMethodByPowerMockito() throws Exception {
        //这个case会给我们更大的惊(懵)喜(B)：上面两个可以单独跑过的case合到一起就挂了
//        ClassForScene5.test();
//        PowerMockito.verifyPrivate(ClassForScene5.class).invoke("doSomething");
//        ClassForScene5.testPublic();
//        PowerMockito.verifyPrivate(ClassForScene5.class).invoke("doPublicSomething");
        //除此之外，还有一个大惊(懵)喜(B)：上面两个可以单独跑过的case，连续跑的时候也会挂……
    }


    @Test
    public void verifyStaticByUncle() throws Exception {
        //ClassForScene5里面当mValue=0时会去调一次doSomething，那么我们怎么确认mValue=0时它确实走了一次initValue呢？
        PowerMockito.spy(ClassForScene5.class); //注意这里能不用mockStatic
        PowerMockito.when(ClassForScene5.class,"doSomething").thenThrow(new NullPointerException("doSomethingInvoked"));
        boolean doSomethingInvoked = false;
        try {
            ClassForScene5.test();
        }catch (Exception e){
            if (e.getMessage().equals("doSomethingInvoked")){
                doSomethingInvoked = true;
            }
        }
        Assert.assertTrue(doSomethingInvoked);

        //下面我们把mValue的值改成1，这样就不会调到doSomething了
        Whitebox.setInternalState(ClassForScene5.class, "mValue", 1, ClassForScene5.class);
        doSomethingInvoked = false;
        try {
            ClassForScene5.test();
        }catch (Exception e){
            if (e.getMessage().equals("doSomethingInvoked")){
                doSomethingInvoked = true;
            }
        }
        Assert.assertTrue(!doSomethingInvoked);

    }
}
