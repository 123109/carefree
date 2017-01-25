package classDefine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by cb on 2016/10/9.
 */
public class ClassForScene18 {

    public void doSomething(Context context){
        Intent intent = new Intent(context,ServiceFor18.class);
        Bundle bundle = new Bundle();
        bundle.putString("test","value");
        intent.putExtras(bundle);
        context.startService(intent);
    }

}
