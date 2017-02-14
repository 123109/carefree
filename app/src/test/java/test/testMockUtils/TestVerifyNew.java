package test.testMockUtils;

import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import base.TestInit;
import test.testMockUtils.bean.SomeClass;
import test.testMockUtils.bean.DependencyClass;
import utils.UncleMock;

/**
 * Created by Administrator on 2017/2/12.
 */
@PrepareForTest({SomeClass.class,DependencyClass.class})
public class TestVerifyNew extends TestInit{
    @Test
    public void test_withNoArgument() throws Exception {
        final DependencyClass value = new DependencyClass();
        UncleMock.whenNew(DependencyClass.class).inClass(SomeClass.class).withNoArgument().thenReturn(value);
        new SomeClass();
        PowerMockito.verifyNew(DependencyClass.class,Mockito.times(1)).withNoArguments();
        PowerMockito.verifyNew(DependencyClass.class,Mockito.times(1)).withArguments(Mockito.anyString());
//        UncleMock.verifyNew(DependencyClass.class,1).withNoArgument();
//        UncleMock.verifyNew(DependencyClass.class,1).withArguments(Mockito.anyString());
    }

    @Test
    public void test_withOneArgument() throws Exception {
        final DependencyClass value = new DependencyClass();
        UncleMock.whenNew(DependencyClass.class).inClass(SomeClass.class).withArguments("1").thenReturn(value);
        new SomeClass();
        UncleMock.verifyNew(DependencyClass.class,1).justDoIt().withArguments(Mockito.anyString());
    }
}
