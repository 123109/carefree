package test.testMockUtils.bean;

/**
 * Created by Administrator on 2017/2/9.
 */

public class DependencyClass {

    public DependencyClass(){

    }
    public DependencyClass(String s){

    }
    public DependencyClass(String s,String s1){

    }

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
