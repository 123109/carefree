package classDefine;

/**
 * Created by cb on 2016/5/19.
 */
public class InstanceClass {

    public static final int TEST_VALUE = 10;

    private int mValue;

    private static InstanceClass sInstance;

    public static InstanceClass getInstance(){
        if (sInstance == null){
            sInstance = new InstanceClass();
        }
        return sInstance;
    }


    private InstanceClass(){
        mValue = TEST_VALUE;
    }

    public int getValue() {
        return mValue;
    }

    public int getDoubleValue(){
        return mValue * 2;
    }
}
