package test.testMockUtils;

import org.junit.Assert;
import org.junit.Test;
import org.powermock.core.classloader.annotations.PrepareForTest;

import base.TestInit;
import utils.MockUtils;
import utils.builder.MockBuilder;

/**
 * Created by Administrator on 2017/2/10.
 */
@PrepareForTest(CallOrigin.class)
public class TestWhenNew extends TestInit{
    @Test
    public void testWhenNew() throws Exception {
        DependencyClass mock = MockUtils.mock(DependencyClass.class);
        MockBuilder.whenNew(DependencyClass.class).inClass(CallOrigin.class).thenReturn(mock);
        CallOrigin origin = new CallOrigin();
        DependencyClass dependencyClass = MockUtils.getValue(origin,"mArguments");
        DependencyClass dependencyClass1 = MockUtils.getValue(origin,"mOne");
        DependencyClass dependencyClass2 = MockUtils.getValue(origin,"mTwo");
        Assert.assertTrue(mock == dependencyClass);
        Assert.assertTrue(mock == dependencyClass1);
        Assert.assertTrue(mock == dependencyClass2);
    }

    @Test
    public void testWhenNewOne() throws Exception {
        DependencyClass mock = MockUtils.mock(DependencyClass.class);
        final Object argument = null;
        MockBuilder.whenNew(DependencyClass.class).inClass(CallOrigin.class).withArguments(argument).thenReturn(mock);
        CallOrigin origin = new CallOrigin();
        DependencyClass dependencyClass = MockUtils.getValue(origin,"mArguments");
        DependencyClass dependencyClass1 = MockUtils.getValue(origin,"mOne");
        DependencyClass dependencyClass2 = MockUtils.getValue(origin,"mTwo");
        Assert.assertTrue(mock != dependencyClass);
        Assert.assertTrue(mock == dependencyClass1);
        Assert.assertTrue(mock != dependencyClass2);
    }

    @Test
    public void testWhenNewTwo() throws Exception {
        DependencyClass mock = MockUtils.mock(DependencyClass.class);
        final Object arguments = null;
        MockBuilder.whenNew(DependencyClass.class).inClass(CallOrigin.class).withArguments(null, arguments).thenReturn(mock);
        CallOrigin origin = new CallOrigin();
        DependencyClass dependencyClass = MockUtils.getValue(origin,"mArguments");
        DependencyClass dependencyClass1 = MockUtils.getValue(origin,"mOne");
        DependencyClass dependencyClass2 = MockUtils.getValue(origin,"mTwo");
        Assert.assertTrue(mock != dependencyClass);
        Assert.assertTrue(mock != dependencyClass1);
        Assert.assertTrue(mock == dependencyClass2);
    }

    @Test
    public void testWhenNewOneBySpecified() throws Exception {
        DependencyClass mock = new DependencyClass();
        final Object argument = null;
        MockBuilder.whenNew(DependencyClass.class).inClass(CallOrigin.class).withArguments(argument).thenReturn(mock);
        CallOrigin origin = new CallOrigin();
        DependencyClass dependencyClass = MockUtils.getValue(origin,"mArguments");
        DependencyClass dependencyClass1 = MockUtils.getValue(origin,"mOne");
        DependencyClass dependencyClass2 = MockUtils.getValue(origin,"mTwo");
        Assert.assertTrue(mock != dependencyClass);
        Assert.assertTrue(mock == dependencyClass1);
        Assert.assertTrue(mock != dependencyClass2);
    }

}
