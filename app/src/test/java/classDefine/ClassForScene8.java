package classDefine;

import java.util.Random;

/**
 * Created by cb on 2016/5/30.
 */
public class ClassForScene8 {
    private static ClassForScene8 sInstance;

    private int mValue;

    public static ClassForScene8 getInstance(){
        if (sInstance == null){
            sInstance = new ClassForScene8();
        }
        return sInstance;
    }

    private ClassForScene8(){

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
