package test.testLimit.classDefine;

import java.util.ArrayList;

/**
 * Created by cb on 2016/6/2.
 */
public enum  TestCache {
    INSTANCE;

    private ArrayList<TestMessage> mTestMessages = new ArrayList<>();

    public void addToCache(TestMessage message){
        mTestMessages.add(message);
    }
}
