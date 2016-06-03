package test.testByInherit;

import org.junit.Test;

/**
 * Created by cb on 2016/6/3.
 */
public class TestByInherit {
    @Test
    public void test() throws Exception{
        //在ClassForTestByInherit的test方法里，我们传入一个DependencyClass的实例，根据这个实例进行进行一些操作
        //在这个case里，有个很蛋疼的问题就是，有两个地方需要对dependency.isValid()进行判断，而如果要做到全覆盖，这两个值必须是不一样的。
        //而由于这个判断是在被测方法内部进行的，我们的一个桩对象在被使用之前，只能被赋一个值，要么true,要么false，所以不管我们怎么mock传入的dependency对象，
        //都没有办法让它的值在被测的过程中发生变化。在这种情况下，只有两种解决方案：
        //1.这个test方法是有问题的，我们去重写这个方法，把第一个isValid判断拿到test方法之外做。在这个简单的demo里这样做是合适的，但是如果在一个真实的应用里，
        //而这个方法被N个地方用到，甚至可能为了把第一个判断提出来需要改变非常多的业务逻辑，那么这种为了测试而做的修改的代价就太大了
        //2.通过继承的方式，我们从DependencyClass类派生出一个子类，重写它的doSomething方法来达到我们的测试目的
    }
}
