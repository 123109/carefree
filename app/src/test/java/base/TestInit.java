package base;

import android.text.TextUtils;
import android.util.Log;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Created by cb on 2016/5/26.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({TextUtils.class})
public class TestInit {
    @Before
    public void setUp() throws Exception{
        PowerMockito.mockStatic(TextUtils.class);
        Object[] argsEmpty = new Object[1];
        argsEmpty[0] = "";
        PowerMockito.when(TextUtils.class,"isEmpty","").thenReturn(true);
        Object[] args = new Object[1];
        args[0] = null;
        PowerMockito.when(TextUtils.class,"isEmpty",args).thenReturn(true);
        PowerMockito.mockStatic(Log.class);
    }
}
