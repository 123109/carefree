package base;

import android.content.Context;

import com.carefree.unittestdemo.BuildConfig;

import org.junit.Rule;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

/**
 * Created by cb on 2016/5/26.
 */
//第一，在PowerMock与Robolectric共用的情况下，主的Runner应该是PowerMock,
@RunWith(PowerMockRunner.class)
//第二, 需要做这个代理，把某些操作代理给RobolectricGradleTestRunner
@PowerMockRunnerDelegate(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
//第三，ignore掉某些包，具体不清楚原因。
@PowerMockIgnore({ "org.mockito.*", "org.robolectric.*", "android.*" })
public class TestInit {

    //非常重要：这个Rule要声明，我不知道这个Rule是干嘛用的，但是不定义这个变量。也是没办法混用的。
    @Rule
    public PowerMockRule rule = new PowerMockRule();

    protected Context getContext(){
        ShadowApplication shadowApplication = ShadowApplication.getInstance();
        return shadowApplication.getApplicationContext();
    }
}
