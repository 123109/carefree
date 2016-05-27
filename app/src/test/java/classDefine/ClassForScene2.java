package classDefine;

/**
 * Created by cb on 2016/5/27.
 */
public class ClassForScene2 {
    private int mValue;

    public boolean isOdd(){
        if (mValue == 0){
            mValue = initValue();
        }
        return mValue % 2 != 0;
    }

    private int initValue(){
        return 20;
    }
}
