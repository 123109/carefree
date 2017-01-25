package test.testByPowerMockito.sceneTest;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.robolectric.RuntimeEnvironment;

import base.TestInit;
import classDefine.ClassForScene1;
import classDefine.ClassForScene18;
import classDefine.StaticClass;

import static org.robolectric.Shadows.shadowOf;

/**
 * 场景18：确认某个服务被启动
 * 由于目前我们无法Mock一个context或者一个bundle（至少我不知道要怎么玩），所以在涉及到context和bundle的mock的时候会有问题。
 * 比如有这样一个场景，在待测方法里生成 了一个Intent,并往这个intent里面丢了一个Bundle，然后通过这个intent开户服务，
 * 我们需要确认这个bundle里面的数据是预期的，由于我们无法控制Intent，context，bundle，所以必须在startService之后取到相应的Intent才能进行assert
 * 我们可以通过shadowOf来获取到被启动的Service
 * Created by cb on 2016/5/20.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({StaticClass.class,ClassForScene1.class})
public class Scene18_AssertService extends TestInit{

    @Test
    public void testService() throws Exception {
        Application application = RuntimeEnvironment.application;
        ClassForScene18 test = new ClassForScene18();
        test.doSomething(application);
        // 在使用shadowOf()方法时有可能出现以下错误：
        // Error:(38, 32) 错误: 无法访问AndroidHttpClient
        // 找不到android.net.http.AndroidHttpClient的类文件
        // 解决方法：从sdk\platforms\android-23\optional目录下拷贝org.apache.http.legacy.jar到工程中并加入依赖中
        Intent serviceIntent = shadowOf(application).getNextStartedService();
        Bundle bundle = serviceIntent.getExtras();
        Assert.assertTrue(bundle.getString("test").equals("value"));
    }
}
