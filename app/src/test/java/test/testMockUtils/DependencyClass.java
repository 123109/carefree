package test.testMockUtils;

/**
 * Created by Administrator on 2017/2/9.
 */

public class DependencyClass {
    public final String one(String input){
        System.out.print(input+"\n");
        return input;
    }

    public String two(String input,String output){
        return output;
    }

    public String two(String input,int output){
        return input;
    }
}
