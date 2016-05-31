package classDefine;

import java.util.Random;

/**
 * Created by cb on 2016/5/30.
 */
public enum  ClassForScene10 {
    INSTANCE;

    private int mValue;

    ClassForScene10(){

    }

    public int getValue(){
        mValue = changeValue();
        if (mValue >= 0 && mValue <= 10){
            return mValue;
        }else if (mValue < 0){
            return mValue * 2;
        }else {
            return mValue - 2;
        }
    }

    public int changeValue(){
        return new Random().nextInt();
    }
}
