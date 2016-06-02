package classDefine;

/**
 * Created by cb on 2016/5/31.
 */
public class ClassForScene12_Dependency {

    private int mValue;

    public ClassForScene12_Dependency(int param){
        mValue = param;
    }

    public int getValue(){
        return mValue * 2;
    }

    public int getValueByInput(int input){
        return input * 3;
    }
}
