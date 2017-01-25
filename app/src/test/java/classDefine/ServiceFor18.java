package classDefine;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2017/1/25.
 */

public class ServiceFor18 extends Service {
    @Nullable
    @Override
    public IBinder onBind(final Intent intent) {
        return null;
    }
}
