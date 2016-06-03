package test.testByInherit;

import java.util.Random;

/**
 * Created by cb on 2016/6/3.
 */
public class DependencyClass {

    private boolean mIsValid = false;

    public boolean isValid(){
        return mIsValid;
    }

    public void doSomething(){
        int value = new Random().nextInt();
        if (value > 100){
            mIsValid = true;
        }else {
            mIsValid = false;
        }
    }

}
