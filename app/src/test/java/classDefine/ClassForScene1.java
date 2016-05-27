package classDefine;

/**
 * Created by cb on 2016/5/27.
 */
public class ClassForScene1 {
    private static int mValue;

    public static boolean isOdd(){
        if (mValue == 0){
            mValue = initValue();
        }
        return mValue % 2 != 0;
    }

    private static int initValue(){
        return 20;
    }
}
