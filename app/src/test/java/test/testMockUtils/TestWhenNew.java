package test.testMockUtils;

import org.junit.Assert;
import org.junit.Test;
import org.powermock.core.classloader.annotations.PrepareForTest;

import base.TestInit;
import test.testMockUtils.bean.CallOrigin;
import test.testMockUtils.bean.DependencyClass;
import utils.UncleMock;

/**
 * Created by Administrator on 2017/2/10.
 */
@PrepareForTest(CallOrigin.class)
public class TestWhenNew extends TestInit{
    @Test
    public void testWhenNew_anyArgument() throws Exception {
        DependencyClass mock = UncleMock.mock(DependencyClass.class);
        UncleMock.whenNew(DependencyClass.class).inClass(CallOrigin.class).thenReturn(mock);
        CallOrigin origin = new CallOrigin();
        DependencyClass dependencyClass = UncleMock.getValue(origin,"mArguments");
        DependencyClass dependencyClass1 = UncleMock.getValue(origin,"mOne");
        DependencyClass dependencyClass2 = UncleMock.getValue(origin,"mTwo");
        Assert.assertTrue(mock == dependencyClass);
        Assert.assertTrue(mock == dependencyClass1);
        Assert.assertTrue(mock == dependencyClass2);
    }

    @Test
    public void testWhenNewOne() throws Exception {
        DependencyClass mock = UncleMock.mock(DependencyClass.class);
        final Object argument = null;
        UncleMock.whenNew(DependencyClass.class).inClass(CallOrigin.class).withArguments(argument).thenReturn(mock);
        CallOrigin origin = new CallOrigin();
        DependencyClass dependencyClass = UncleMock.getValue(origin,"mArguments");
        DependencyClass dependencyClass1 = UncleMock.getValue(origin,"mOne");
        DependencyClass dependencyClass2 = UncleMock.getValue(origin,"mTwo");
        Assert.assertTrue(mock != dependencyClass);
        Assert.assertTrue(mock == dependencyClass1);
        Assert.assertTrue(mock != dependencyClass2);
    }

    @Test
    public void testWhenNewTwo() throws Exception {
        DependencyClass mock = UncleMock.mock(DependencyClass.class);
        final Object arguments = null;
        UncleMock.whenNew(DependencyClass.class).inClass(CallOrigin.class).withArguments(null, arguments).thenReturn(mock);
        CallOrigin origin = new CallOrigin();
        DependencyClass dependencyClass = UncleMock.getValue(origin,"mArguments");
        DependencyClass dependencyClass1 = UncleMock.getValue(origin,"mOne");
        DependencyClass dependencyClass2 = UncleMock.getValue(origin,"mTwo");
        Assert.assertTrue(mock != dependencyClass);
        Assert.assertTrue(mock != dependencyClass1);
        Assert.assertTrue(mock == dependencyClass2);
    }

    @Test
    public void testWhenNew_withNoArgument() throws Exception {
        DependencyClass mock = new DependencyClass();
        UncleMock.whenNew(DependencyClass.class).inClass(CallOrigin.class).withNoArgument().thenReturn(mock);
        CallOrigin origin = new CallOrigin();
        DependencyClass dependencyClass = UncleMock.getValue(origin,"mArguments");
        DependencyClass dependencyClass1 = UncleMock.getValue(origin,"mOne");
        DependencyClass dependencyClass2 = UncleMock.getValue(origin,"mTwo");
        Assert.assertTrue(mock == dependencyClass);
        Assert.assertTrue(mock != dependencyClass1);
        Assert.assertTrue(mock != dependencyClass2);
    }

}
