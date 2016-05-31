package classDefine;

/**
 * Created by cb on 2016/5/30.
 */
public class ClassForScene7 {
    private int mValue;

    public int getValue(){
        mValue = InstanceClass.getInstance().getValue();
        return mValue;
    }
}
