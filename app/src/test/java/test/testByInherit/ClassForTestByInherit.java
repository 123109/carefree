package test.testByInherit;

/**
 * Created by cb on 2016/6/3.
 */
public class ClassForTestByInherit {
    public boolean test(DependencyClass dependency){
        if (dependency.isValid()){
            return true;
        }
        dependency.doSomething();
        if (dependency.isValid()){
            //一些其它的操作
        }else{
            //另外一些操作
        }
        return dependency.isValid();
    }
}
