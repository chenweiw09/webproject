package smart.core.support;

/**
 * Created by Cruise on 2017/7/2.
 * ��ȡ
 */
public abstract class SuperClassTemplate extends ClassTemplate {

    protected final Class<?> superClass;

    protected SuperClassTemplate(String packageName,  Class<?> superClass){
        super(packageName);
        this.superClass = superClass;
    }
}
