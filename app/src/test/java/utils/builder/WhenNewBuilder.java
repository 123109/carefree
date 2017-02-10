package utils.builder;

import utils.MockUtils;

/**
 * Created by Administrator on 2017/2/10.
 */

public class WhenNewBuilder<T> {
    Class<T> mTClass;
    private LessOneArgumentBuilder<T> mBuilder;
    WhenNewBuilder(Class<T> tClass) {
        mTClass = tClass;
        mBuilder = new LessOneArgumentBuilder<>(this);
    }

    public LessOneArgumentBuilder<T> inClass(Class<?> target,Class<?>... otherTargets){
        MockUtils.checkPrepare(target);
        for (Class<?> otherTarget : otherTargets) {
            MockUtils.checkPrepare(otherTarget);
        }
        return mBuilder;
    }
}
