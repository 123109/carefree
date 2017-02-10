package test.testMockUtils;

/**
 * Created by Administrator on 2017/2/9.
 */

public class CallOrigin {

    private DependencyClass mArguments = new DependencyClass();

    public void callOne(){
        mArguments.one("123");
        mArguments.one("123");
    }
}
