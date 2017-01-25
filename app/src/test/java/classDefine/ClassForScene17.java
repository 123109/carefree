package classDefine;

/**
 * Created by cb on 2016/10/9.
 */
public class ClassForScene17 {

    private NormalClass mMock;


    public void doSomething(){
        final InterfaceFor17 mock = new InterfaceFor17() {
            @Override
            public void test() {
                mMock = new NormalClass();
            }
        };
        mock.test();
    }

    public NormalClass getMock() {
        return mMock;
    }
}
