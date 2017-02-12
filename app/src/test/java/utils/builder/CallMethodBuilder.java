package utils.builder;

/**
 * Created by Administrator on 2017/2/10.
 */

public class CallMethodBuilder<T,E>{
    Object mObject;
    private E mE;
    String mMethodName;

    CallMethodBuilder(Object object,E e) {
        mObject = object;
        mE = e;
        if (object instanceof Class){
            //要测试一个静态方法
            MockUtils.checkStaticMock((Class) object);
        }else{
            MockUtils.checkMocked(object);
        }
    }

    public E call(String methodName){
        mMethodName = methodName;
        return mE;
    }
}
