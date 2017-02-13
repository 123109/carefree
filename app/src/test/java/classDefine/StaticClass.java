package classDefine;

/**
 * Created by cb on 2016/5/20.
 */
public class StaticClass {
    private static int mValue = 10;

    public static int getMockValue() {
        mValue = mValue + 10;
        System.out.print("getMockValue\n");
        privateStatic();
        return mValue;
    }

    public static void setRealValue(int value){
        System.out.print("setRealValue:"+value+"\n");
        mValue = value;
    }

    public static int getRealValue(){
        return mValue;
    }

    public static int getRealValue(int input,int in){
        return mValue;
    }

    private static void privateStatic(){
        System.out.print("privateStatic\n");
    }
}
