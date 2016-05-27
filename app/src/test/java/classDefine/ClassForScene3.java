package classDefine;

/**
 * Created by cb on 2016/5/27.
 */
public class ClassForScene3 {
    private static int mValue;

    public static void changeValue(int input){
        if (input > 0){
            mValue = input;
        }else{
            mValue = -input;
        }
    }
}
