/** Header Comment */
package sample;

import java.util.List;

import com.google.common.annotations.Beta;
import com.google.common.eventbus.Subscribe;

/**
 * class doc<br>
 * @author ko2ic
 */
@Beta
public class ParserTarget {

    // comment1

    /**
     * enum doc<br>
     * @author ko2ic
     */
    private enum EnumType {
        Type_A, Type_B, Type_C
    };

    /** String doc */
    private static final String A_STR = "a";

    /** list doc */
    protected List<EnumType> list;

    // comment2

    /**
     * main method doc<br>
     * @param args
     */
    @Subscribe
    public static void main(String[] args) {
        // comment3
        ParserTarget target = new ParserTarget();
        target.method1("method", 1);
        System.out.println(target.method2());
        target.method3(EnumType.Type_B);
    }

    /**
     * method1 doc<br>
     * @param str string
     * @param one integer
     */
    void method1(String str, int one) {
        System.out.println(str + one);
    }

    /**
     * method2 doc<br>
     * @return a str
     */
    protected String method2() {
        return A_STR;
    }

    /**
     * method3 doc<br>
     * @param type B
     */
    private void method3(EnumType type) {
        System.out.println(type.name());
    }
}