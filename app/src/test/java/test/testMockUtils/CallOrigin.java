package test.testMockUtils;

/**
 * Created by Administrator on 2017/2/9.
 */

public class CallOrigin {

    private DependencyClass mArguments = new DependencyClass();
    private DependencyClass mOne = new DependencyClass(null);
    private DependencyClass mTwo = new DependencyClass(null,null);

    public String callOne(){
        mArguments.one("123");
        mArguments.one("123");
        return "123";
    }

    public void callVoid(){
        mArguments.one("callVoid");
    }

    public String callTwo(String param1,String param2){
        return param1+param2;
    }
}
