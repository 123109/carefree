package classDefine;

/**
 * Created by cb on 2016/5/20.
 */
public final class FinalClass {

    private static int mStaticValue = 100;

    private int mValue = 100;
    public static  int getValue(){
        return mStaticValue;
    }

    public int getMockValue(){
        return 200;
    }
}
