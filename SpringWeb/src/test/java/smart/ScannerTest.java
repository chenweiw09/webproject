package smart;

import smart.common.InstanceFactory;
import smart.core.ClassHelper;
import smart.core.ClassScanner;
import smart.core.support.DefaultClassScanner;
import smart.ioc.BeanHelper;
import smart.ioc.annotation.Bean;

import java.util.List;

/**
 * Created by Cruise on 2017/7/9.
 */
public class ScannerTest {
    public static void main(String[] args) {

        List<Class<?>> list = ClassHelper.getClassList();

        BeanTest bean = BeanHelper.getBean(BeanTest.class);
    }
}
