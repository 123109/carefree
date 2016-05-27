package classDefine;

/**
 * Created by cb on 2016/5/27.
 */
public class ClassForScene5 {

    private static int mValue;

    public static boolean test(){
        if (mValue == 0){
            doSomething();
        }
        return mValue % 2 != 0;
    }

    public static void testPublic(){
        doPublicSomething();
    }

    private static void doSomething(){
        //随便干点啥事吧
    }

    public static void doPublicSomething(){

    }

    private static void doNothing(){}

}
