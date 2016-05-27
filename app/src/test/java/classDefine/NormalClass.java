package classDefine;

/**
 * Created by cb on 2016/5/20.
 */
public class NormalClass {
    int mValue;

    public void setRealValue(final int value) {
        mValue = value;
    }

    public int getMockValue(){
        return mValue;
    }

    public int getRealValue(){
        return mValue;
    }

    public int getAnswerValue(int input){
        return mValue * input;
    }
}
