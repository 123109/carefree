package utils;

/**
 * Created by Administrator on 2016/2/25.
 */
public class MockLog {
    
    public static void v(String tag, String message) {
        System.out.print("\n v" + "----" + tag + "---" + message);
    }

    public static void d(String tag, String message) {
        System.out.print("\n d" + "----" + tag + "---" + message);
    }

    public static void i(String tag, String message) {
        System.out.print("\n i" + "----" + tag + "---" + message);
    }

    public static void w(String tag, String message) {
        System.out.print("\n w" + "----" + tag + "---" + message);
    }

    public static void e(String tag, String message) {
        System.out.print("\n e" + "----" + tag + "---" + message);
    }

    public static void write(int level, String tag, String message) {
        System.out.print("\n write" + "----" + tag + "---" + message);
    }

    private void print(String type, String tag, String message) {
        System.out.print("\n " + type + "----" + tag + "---" + message);
    }
}
