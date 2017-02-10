package test.testMockUtils;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import base.TestInit;
import classDefine.ClassForScene1;
import classDefine.ClassForScene15;
import classDefine.ClassForScene2;
import classDefine.EnumClass;
import classDefine.InstanceClass;
import classDefine.StaticClass;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import rx.Observable;
import utils.CustomMockUtils;
import utils.MockSubscriber;
import utils.MockUtils;

/**
 * Created by Administrator on 2017/2/9.
 */
@PrepareForTest({InstanceClass.class,EnumClass.class,StaticClass.class,DependencyClass.class,CallOrigin.class})
public class TestMockUtils extends TestInit{
    @Test
    public void testSetField(){
        ClassForScene2 test = new ClassForScene2();
        MockUtils.setValue(test,"mValue",123);
        int value = Whitebox.getInternalState(test,"mValue",ClassForScene2.class);
        Assert.assertTrue(value == 123);
    }

    @Test
    public void testSetStaticField(){
        ClassForScene1 test = new ClassForScene1();
        MockUtils.setValue(test,"mValue",123);
        int value = Whitebox.getInternalState(test,"mValue",ClassForScene1.class);
        Assert.assertTrue(value == 123);
    }

    @Test
    public void testGetField(){
        ClassForScene2 test = new ClassForScene2();
        MockUtils.setValue(test,"mValue",123);
        int value = MockUtils.getValue(test,"mValue");
        Assert.assertTrue(value == 123);
    }

    @Test
    public void testGetStaticField(){
        ClassForScene1 test = new ClassForScene1();
        MockUtils.setValue(test,"mValue",123);
        int value = MockUtils.getValue(test,"mValue");
        Assert.assertTrue(value == 123);
    }

    @Test
    public void testMockNormalInstance(){
        InstanceClass instanceClass = MockUtils.mockInstance(InstanceClass.class);
        Assert.assertTrue(instanceClass != null);
        Assert.assertTrue(InstanceClass.getInstance() == instanceClass);
    }

    @Test
    public void testMockEnumInstance(){
        Assert.assertTrue(EnumClass.INSTANCE.getValue() == 100);
        EnumClass mock = MockUtils.mockInstance(EnumClass.class);
        Assert.assertTrue(mock != null);
        Assert.assertTrue(EnumClass.INSTANCE == mock);
        PowerMockito.when(mock.getValue()).thenReturn(124);
        Assert.assertTrue(EnumClass.INSTANCE.getValue() == 124);
    }

    @Test
    public void testVerifyStatic() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        PowerMockito.mockStatic(StaticClass.class);
        ClassForScene15 scene = new ClassForScene15();
        for (int i = 0; i < 2; i++) {
            scene.doSomething();
        }
        MockUtils.verifyStatic(2,StaticClass.class, new MockUtils.ICallTestMethod() {
            @Override
            public void call() {
                StaticClass.getMockValue();
            }
        });
    }

    @Test
    public void testVerifyMock() throws Exception {
        DependencyClass arguments = PowerMockito.mock(DependencyClass.class);
        PowerMockito.whenNew(DependencyClass.class).withNoArguments().thenReturn(arguments);
        CallOrigin origin = new CallOrigin();
        origin.callOne();

        MockUtils.verify(arguments,2).invoke("one", Mockito.anyString());
    }

    @Test
    public void testWhenNew() throws Exception {
        DependencyClass arguments = MockUtils.whenNew(DependencyClass.class,CallOrigin.class);
        CallOrigin origin = new CallOrigin();
        origin.callOne();
        MockUtils.verify(arguments,2).invoke("one", Mockito.anyString());
    }

    @Test
    public void testWhenNewWithSpecifiedObject() throws Exception {
        final DependencyClass calledClass = new DependencyClass();
        MockUtils.whenNewWithSpecifiedObject(calledClass,CallOrigin.class);
        CallOrigin origin = new CallOrigin();
        DependencyClass newOne = MockUtils.getValue(origin,"mArguments");
        Assert.assertTrue(newOne == calledClass);
    }

    @Test
    public void testSubscriber() throws InterruptedException {
        MockSubscriber<Integer> subscriber = MockUtils.getMockSubscription(Observable.just(1));
        Assert.assertTrue(subscriber.getData() == 1);
        Assert.assertTrue(subscriber.isCompleted());
    }

    @Test
    public void test() throws NotFoundException, CannotCompileException, IOException, IllegalAccessException, InstantiationException {
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.getCtClass(CallOrigin.class.getName());
//        CtClass ctClass = pool.getCtClass("test.testMockUtils.CallOrigin");
        CtMethod method = ctClass.getDeclaredMethod("callOne");
        method.insertBefore("mArguments.one(\"456\");");
        ctClass.writeFile();
        ClassLoader loader = this.getClass().getClassLoader();
        CallOrigin callOrigin = (CallOrigin) ctClass.toClass(loader).newInstance();
        callOrigin.callOne();
    }

    @Test
    public void testPrepareVerify() throws Exception {
        CallOrigin origin = CustomMockUtils.prepareVerify("test.testMockUtils.CallOrigin","callOne");
        origin.callOne();
        CustomMockUtils.verifyNormal(origin,1);
    }
}
