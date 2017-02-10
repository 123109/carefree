package utils.builder;

import org.mockito.exceptions.base.MockitoAssertionError;
import org.mockito.exceptions.misusing.InvalidUseOfMatchersException;
import org.mockito.exceptions.misusing.MissingMethodInvocationException;

import java.util.ArrayList;

import utils.MockUtils;

/**
 * Created by Administrator on 2017/2/10.
 */

public class ReturnBuilder {
    private CallMethodBuilder mCallMethodBuilder;
    private CallMethodByNameBuilder mCallMethodByNameBuilder;
    private ArrayList<Object> mNextValue = new ArrayList<>();
    ReturnBuilder(CallMethodBuilder callMethodBuilder) {
        mCallMethodBuilder = callMethodBuilder;
    }

    ReturnBuilder(CallMethodByNameBuilder callMethodByNameBuilder) {
        mCallMethodByNameBuilder = callMethodByNameBuilder;
    }

    public ReturnBuilder returnNextTime(Object value){
        mNextValue.add(value);
        return this;
    }

    public void build() throws Exception {
        if (mCallMethodBuilder != null){
            buildCallMethod();
        }else{
            buildCallMethodByName();
        }
    }

    private void buildCallMethodByName() throws Exception {
        try {
            if (mNextValue.isEmpty()){
                mCallMethodByNameBuilder.build();
            }else {
                mCallMethodByNameBuilder.build(mNextValue.toArray());
            }
        }catch (MissingMethodInvocationException e){
            Object mObject = mCallMethodByNameBuilder.mObject;
            if (mObject instanceof Class){
                //调用静态方法
                MockUtils.checkStaticMock((Class) mObject,"调用when方法之前");
                MockUtils.checkPrepare((Class) mObject);
            }else{
                MockUtils.checkMocked(mObject,"when");
            }
        }catch (InvalidUseOfMatchersException e){
            throw new MockitoAssertionError("传入多个参数时，要么全部用any，要么全部不用");
        }
    }

    private void buildCallMethod() {
        try {
            if (mNextValue.isEmpty()){
                mCallMethodBuilder.build();
            }else {
                mCallMethodBuilder.build(mNextValue.toArray());
            }
        }catch (MissingMethodInvocationException | InvalidUseOfMatchersException e){
            String message = "\n1.如果被测的是一个静态方法，请确认在测试类声明的位置添加注解@PrepareForTest(xxxx.class)，并且在调用when之前调用了MockUtils.mockStatic(xxxx.class)" +
                    "\n2.如果被测的是一个对象的公共方法，请确认在它是被mock出来的，比如MockUtils.mock(xxxx.class)";
            throw new MissingMethodInvocationException(message);
        }
    }
}
