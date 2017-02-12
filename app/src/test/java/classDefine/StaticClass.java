package classDefine;

/**
 * Created by cb on 2016/5/20.
 */
public class StaticClass {
    private static int mPrivateValue = 0;

    public static int getMockValue() {
        mPrivateValue = mPrivateValue + 10;
        System.out.print("getMockValue\n");
        privateStatic();
        return mPrivateValue;
    }

    public static void setRealValue(int value){
        System.out.print("setRealValue:"+value+"\n");
        mPrivateValue = value;
    }

    public static int getRealValue(){
        return mPrivateValue;
    }

    public static int getRealValue(int input,int in){
        return mPrivateValue;
    }

    private static void privateStatic(){
        System.out.print("privateStatic\n");
    }
}
