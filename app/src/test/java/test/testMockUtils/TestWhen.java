package test.testMockUtils;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import base.TestInit;
import classDefine.StaticClass;
import test.testMockUtils.bean.SomeClass;
import utils.UncleMock;
import utils.builder.IAnswer;

/**
 * Created by Administrator on 2017/2/10.
 */
@PrepareForTest({StaticClass.class})
public class TestWhen extends TestInit{
    @Test
    public void testWhen_returnOneValue() throws Exception {
        final SomeClass mock = UncleMock.mock(SomeClass.class);
        UncleMock.when(mock).call("callOne").thenReturn("1234");
        Assert.assertTrue(mock.callOne().equals("1234"));
    }

    @Test
    public void testWhen_returnTwoValue() throws Exception {
        final SomeClass mock = UncleMock.mock(SomeClass.class);
        UncleMock.when(mock).call("callOne").thenReturnValues("1234","1");
        Assert.assertTrue(mock.callOne().equals("1234"));
        Assert.assertTrue(mock.callOne().equals("1"));
    }

    @Test
    public void testWhen_returnTwoValue_secondNull() throws Exception {
        final SomeClass mock = UncleMock.mock(SomeClass.class);
        final Object otherValue = null;
        UncleMock.when(mock).call("callOne").thenReturnValues("1234", otherValue);
        Assert.assertTrue(mock.callOne().equals("1234"));
        Assert.assertTrue(mock.callOne() == null);
    }

    @Test
    public void testWhen_returnTwoValue_assertThreeTimes() throws Exception {
        final SomeClass mock = UncleMock.mock(SomeClass.class);
        UncleMock.when(mock).call("callOne").thenReturnValues("1234","1");
        Assert.assertTrue(mock.callOne().equals("1234"));
        Assert.assertTrue(mock.callOne().equals("1"));
        Assert.assertTrue(mock.callOne().equals("1"));
    }

    @Test
    public void testWhen_static() throws Exception{
        UncleMock.mockStatic(StaticClass.class);
        UncleMock.when(StaticClass.class).call("getRealValue").thenReturnValues(1000,1001);
        Assert.assertTrue(StaticClass.getRealValue() == 1000);
        Assert.assertTrue(StaticClass.getRealValue() == 1001);
    }

    @Test
    public void  testWhen_withArgument() throws Exception {
        SomeClass mock  = UncleMock.mock(SomeClass.class);
        UncleMock.when(mock).call("callTwo","1","2").thenReturn("test");
        Assert.assertTrue(mock.callTwo("1","2").equals("test"));
    }

    @Test
    public void  testWhen_withArgument_any() throws Exception {
        SomeClass mock  = UncleMock.mock(SomeClass.class);
        UncleMock.when(mock).call("callTwo",Mockito.anyString(),Mockito.anyString()).thenReturn("test");
        Assert.assertTrue(mock.callTwo("1","2").equals("test"));
        Assert.assertTrue(mock.callTwo("1ttttt","2").equals("test"));
    }

    @Test
    public void  testWhen_static_withArgument() throws Exception {
        UncleMock.mockStatic(StaticClass.class);
        UncleMock.when(StaticClass.class).call("getRealValue",1,100).thenReturn(300);
        Assert.assertTrue(StaticClass.getRealValue(1,100) == 300);
    }

    @Test
    public void  testWhen_static_withArgument_any() throws Exception {
        UncleMock.mockStatic(StaticClass.class);
        UncleMock.when(StaticClass.class).call("getRealValue",Mockito.anyInt(),Mockito.anyInt()).thenReturn(300);
        Assert.assertTrue(StaticClass.getRealValue(1,100) == 300);
        Assert.assertTrue(StaticClass.getRealValue(2,100) == 300);
    }

    @Test
    public void  testWhen_static_withArgument_any_answer() throws Exception {
        UncleMock.mockStatic(StaticClass.class);
        UncleMock.when(StaticClass.class).call("getRealValue",Mockito.anyInt(),Mockito.anyInt()).thenAnswer(new IAnswer() {
            @Override
            public Object answer(final Object[] arguments) {
                return 300;
            }
        });
        Assert.assertTrue(StaticClass.getRealValue(1,100) == 300);
        Assert.assertTrue(StaticClass.getRealValue(2,100) == 300);
    }

    @Test
    public void test_multiSet() throws Throwable {
        SomeClass someClass  = UncleMock.mock(SomeClass.class);
        UncleMock.when(someClass).call("getValue").thenReturn("100");
        UncleMock.when(someClass).call("getValue","1").thenReturn("10");
        UncleMock.when(someClass).call("getValue","2").thenReturnValues("10","20");
        UncleMock.when(someClass).call("getValue","3").thenThrow(new NullPointerException());
        Assert.assertTrue(someClass.getValue().equals("100"));
        Assert.assertTrue(someClass.getValue("1").equals("10"));
        Assert.assertTrue(someClass.getValue("2").equals("10"));
        Assert.assertTrue(someClass.getValue("2").equals("20"));
        Assert.assertTrue(someClass.getValue("2").equals("20"));
        boolean isException = false;
        try {
            someClass.getValue("3");
        }catch (NullPointerException e){
            isException = true;
        }
        Assert.assertTrue(isException);

        UncleMock.mockStatic(StaticClass.class);
        UncleMock.when(StaticClass.class).call("getRealValue",Mockito.anyInt(),Mockito.anyInt()).thenReturn(1);
        Assert.assertTrue(StaticClass.getRealValue(1,1) == 1);
        Assert.assertTrue(StaticClass.getRealValue(1,2) == 1);
    }
}
