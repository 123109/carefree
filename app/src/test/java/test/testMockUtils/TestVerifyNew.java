package test.testMockUtils;

import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import base.TestInit;
import utils.UncleMock;

/**
 * Created by Administrator on 2017/2/12.
 */
@PrepareForTest({CallOrigin.class,DependencyClass.class})
public class TestVerifyNew extends TestInit{
    @Test
    public void test_withNoArgument() throws Exception {
        final DependencyClass value = new DependencyClass();
        UncleMock.whenNew(DependencyClass.class).inClass(CallOrigin.class).withNoArgument().thenReturn(value);
        new CallOrigin();
        UncleMock.verifyNew(DependencyClass.class,1).withNoArgument();
        UncleMock.verifyNew(DependencyClass.class,1).withArguments(Mockito.anyString());
    }

    @Test
    public void test_withOneArgument() throws Exception {
        final DependencyClass value = new DependencyClass();
        UncleMock.whenNew(DependencyClass.class).inClass(CallOrigin.class).withArguments(Mockito.anyString()).thenReturn(value);
        new CallOrigin();
        UncleMock.verifyNew(DependencyClass.class,1).withArguments(Mockito.anyString());
    }
}
