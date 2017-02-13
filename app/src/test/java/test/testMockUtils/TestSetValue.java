package test.testMockUtils;

import org.junit.Assert;
import org.junit.Test;

import base.TestInit;
import classDefine.StaticClass;
import utils.UncleMock;

/**
 * Created by Administrator on 2017/2/13.
 */

public class TestSetValue extends TestInit{
    @Test
    public void test_setInObject_normalField(){
        CallOrigin callOrigin = new CallOrigin();
        Object object = UncleMock.getValue(callOrigin,"mOne");
        Assert.assertTrue(object != null);
        UncleMock.setValue(callOrigin,"mOne",null);
        object = UncleMock.getValue(callOrigin,"mOne");
        Assert.assertTrue(object == null);
    }

    @Test
    public void test_setInObject_finalField(){
        CallOrigin callOrigin = new CallOrigin();
        int value = callOrigin.getFinalValue();
        Assert.assertTrue(value == 2);
        UncleMock.setValue(callOrigin,"mFinalValue",3);
        //以下代码说明final的值是不能改变的……
        value = callOrigin.getFinalValue();
        Assert.assertTrue(value == 2);
        int wantedValue = UncleMock.getValue(callOrigin,"mFinalValue");
        Assert.assertTrue(wantedValue == 3);
    }

    @Test
    public void test_setInObject_staticField(){
        CallOrigin callOrigin = new CallOrigin();
        int value = callOrigin.getStaticValue();
        Assert.assertTrue(value == 1);
        UncleMock.setValue(callOrigin,"mStaticValue",2);
        value = callOrigin.getStaticValue();
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
