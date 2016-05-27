package test.testByPowerMockito.sceneTest;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import classDefine.ClassForScene5;
import utils.MockUtils;

/**
 * 场景5：确认某个静态类里的某个方法被跑到了
 * 1.powerMockito提供了一个verifyStatic的方法，可惜直到目前我还不会用，完全不知道要怎么玩，看了网上的各种示例依然一头雾水
 * 2.机智的叔想出了另一个办法……
 * Created by cb on 2016/5/27.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ClassForScene5.class})
public class Scene5_AssertStaticMethod {
    @Test
    public void verifyStaticByPowerMockito(){
        //.......
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
        MockUtils.setToStaticField(ClassForScene5.class,"mValue",1);
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
