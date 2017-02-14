package test.testMockUtils;

import org.junit.Assert;
import org.junit.Test;

import base.TestInit;
import classDefine.StaticClass;
import test.testMockUtils.bean.SomeClass;
import utils.UncleMock;

/**
 * Created by Administrator on 2017/2/13.
 */

public class TestSetValue extends TestInit{
    @Test
    public void test_setInObject_normalField(){
        SomeClass someClass = new SomeClass();
        Object object = UncleMock.getValue(someClass,"mOne");
        Assert.assertTrue(object != null);
        UncleMock.setValue(someClass,"mOne",null);
        object = UncleMock.getValue(someClass,"mOne");
        Assert.assertTrue(object == null);
    }

    @Test
    public void test_setInObject_finalField(){
        SomeClass someClass = new SomeClass();
        int value = someClass.getFinalValue();
        Assert.assertTrue(value == 2);
        UncleMock.setValue(someClass,"mFinalValue",3);
        //以下代码说明final的值是不能改变的……
        value = someClass.getFinalValue();
        Assert.assertTrue(value == 2);
        int wantedValue = UncleMock.getValue(someClass,"mFinalValue");
        Assert.assertTrue(wantedValue == 3);
    }

    @Test
    public void test_setInObject_staticField(){
        SomeClass someClass = new SomeClass();
        int value = someClass.getStaticValue();
        Assert.assertTrue(value == 1);
        UncleMock.setValue(someClass,"mStaticValue",2);
        value = someClass.getStaticValue();
        Assert.assertTrue(value == 2);
    }

    @Test
    public void test_setInStatic(){
        int value = StaticClass.getRealValue();
        Assert.assertTrue(value == 10);
        UncleMock.setValue(StaticClass.class,"mValue",2);
        value = StaticClass.getRealValue();
        Assert.assertTrue(value == 2);
    }
}
