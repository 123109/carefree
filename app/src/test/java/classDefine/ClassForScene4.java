package classDefine;

/**
 * Created by cb on 2016/5/27.
 */
public class ClassForScene4 {
    private int mValue;

    public void changeValue(int input){
        if (input > 0){
            mValue = input;
        }else{
            mValue = -input;
        }
    }
}
