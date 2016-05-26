package unit;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.nd.module_im.BuildConfig;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.powermock.api.mockito.PowerMockito;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.lang.Exception;

import nd.sdp.android.im.core.IMSDKGlobalVariable;
import nd.sdp.android.im.core.orm.frame.exception.DbException;
import nd.sdp.android.im.sdk.exception.IMException;

/**
 * Created by HuangYK on 16/4/8.
 */
//@RunWith(PowerMockRunner.class)
//@PrepareForTest({TextUtils.class,Log.class})
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ChatBaseUnitTest {

    protected Context getContext() {
        return UnitTestUtils.getContext();
    }

    @Before
    public void setUp() throws Exception {
    }

    protected void setUpWithPowerMock() throws Exception {
        PowerMockito.mockStatic(TextUtils.class);
        Object[] argsEmpty = new Object[1];
        argsEmpty[0] = "";
        PowerMockito.when(TextUtils.class,"isEmpty",argsEmpty).thenReturn(true);
        Object[] args = new Object[1];
        args[0] = null;
        PowerMockito.when(TextUtils.class,"isEmpty",args).thenReturn(true);
        PowerMockito.mockStatic(Log.class);
    }


    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testSomething() throws Exception {

    }

}
