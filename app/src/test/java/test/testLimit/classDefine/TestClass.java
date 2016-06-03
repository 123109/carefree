package test.testLimit.classDefine;

/**
 * Created by cb on 2016/6/2.
 */
public class TestClass {
    public boolean onMessageReceived(TestMessage message){
        if (message.isValid()){
            //....很多其它的处理
            //....
            TestCache.INSTANCE.addToCache(message);
        }
        return message.isValid();
    }
}
