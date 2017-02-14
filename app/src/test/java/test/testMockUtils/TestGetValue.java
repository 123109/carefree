package test.testMockUtils;

import org.junit.Assert;
import org.junit.Test;

import base.TestInit;
import classDefine.StaticClass;
import test.testMockUtils.bean.SomeClass;
import utils.UncleMock;

/**
 * Created by Administrator on 2017/2/14.
 */

public class TestGetValue extends TestInit{
    @Test
    public void test_object(){
        SomeClass someClass = new SomeClass();
        Assert.assertTrue(UncleMock.getValue(someClass,"mValue").equals("default"));
    }

    @Test
    public void test_object_staticField(){
        SomeClass someClass = new SomeClass();
        final int staticValue = UncleMock.getValue(someClass, "mStaticValue");
        Assert.assertTrue(1 == staticValue);
    }

    @Test
    public void test_staticClass(){
        final int value = UncleMock.getValue(StaticClass.class, "mValue");
        Assert.assertTrue(10 == value);
    }
}
