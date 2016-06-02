package classDefine;

/**
 * Created by cb on 2016/5/31.
 */
public class ClassForScene12 {
    public static int getValue(int input){
        ClassForScene12_Dependency dependency = new ClassForScene12_Dependency(input);
        if (dependency.getValue() < 0){
            return dependency.getValue();
        }else if (dependency.getValue() < 10){
            return dependency.getValue() - 2;
        }else {
            return dependency.getValue();
        }
    }
}
