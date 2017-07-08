package smart.aop;

import org.apache.commons.lang3.StringUtils;
import smart.InstanceFactory;
import smart.aop.annotation.Aspect;
import smart.aop.annotation.AspectOrder;
import smart.aop.proxy.Proxy;
import smart.core.ClassHelper;
import smart.core.ClassScanner;
import smart.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * Created by chenwei23 on 2017/7/8.
 */
public class AopHelper {
    private static final ClassScanner classScanner = InstanceFactory.getClassScanner();

    static{
        try {
            // 创建 Proxy Map（用于 存放代理类 与 目标类列表 的映射关系）
            Map<Class<?>, List<Class<?>>> proxyMap = createProxyMap();

            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 找到目标类对应的代理类列表
     * @return
     * @throws Exception
     */
    private static Map<Class<?>, List<Class<?>>> createProxyMap() throws Exception{
        Map<Class<?>, List<Class<?>>> proxyMap = new LinkedHashMap<Class<?>, List<Class<?>>>();
        // 添加相关代理
        addPluginProxy(proxyMap);      // 插件代理
        addAspectProxy(proxyMap);      // 切面代理
        addTransactionProxy(proxyMap); // 事务代理
        return proxyMap;
    }

    private static void addPluginProxy(Map<Class<?>, List<Class<?>>> proxyMap) throws Exception{

    }

    private static void addAspectProxy(Map<Class<?>, List<Class<?>>> proxyMap) throws Exception{
        //所有的切面都是继承自AspectProxy
        List<Class<?>> aspectProxyClassList = ClassHelper.getClassListBySuper(AspectProxy.class);

        sortAspectProxyClassList(aspectProxyClassList);

        for(Class<?> cls : aspectProxyClassList){
            if(cls.isAnnotationPresent(Aspect.class)){
                Aspect aspect = cls.getAnnotation(Aspect.class);

                //create the target class
                List<Class<?>> targetClassList = createTargetClassList(aspect);
                proxyMap.put(cls,targetClassList);
            }
        }

    }

    private static void addTransactionProxy(Map<Class<?>, List<Class<?>>> proxyMap) throws Exception{

    }

    private static void sortAspectProxyClassList(List<Class<?>> aspectProxyClassList){
        Collections.sort(aspectProxyClassList, new Comparator<Class<?>>() {
            public int compare(Class<?> aspect1, Class<?> aspect2) {
                if(aspect1.isAnnotationPresent(AspectOrder.class) || aspect2.isAnnotationPresent(AspectOrder.class)){
                    // 若有 Order 注解，则优先比较（序号的值越小越靠前）
                    if (aspect1.isAnnotationPresent(AspectOrder.class)) {
                        return getOrderValue(aspect1) - getOrderValue(aspect2);
                    } else {
                        return getOrderValue(aspect2) - getOrderValue(aspect1);
                    }
                } else {
                    // 若无 Order 注解，则比较类名（按字母顺序升序排列）
                    return aspect1.hashCode() - aspect2.hashCode();
                }
            }

            private int getOrderValue(Class<?> aspect) {
                return aspect.getAnnotation(AspectOrder.class) != null ? aspect.getAnnotation(AspectOrder.class).value() : 0;
            }
        });
    }

    private static List<Class<?>> createTargetClassList(Aspect aspect){
        List<Class<?>> targetClassList = new ArrayList<Class<?>>();
        String pkg = aspect.pkg();
        String cls = aspect.cls();
        Class<? extends Annotation> annotation = aspect.annotation();
        // 若包名不为空，则需进一步判断类名是否为空
        if(StringUtils.isNotBlank(pkg)){
            if(StringUtils.isNoneBlank(cls)){
                targetClassList.add(ClassUtil.loadClass(pkg+"."+cls,false));
            }else{
                // 若注解不为空且不是 Aspect 注解，则添加指定包名下带有该注解的所有类
                if(annotation != null && !annotation.equals(Aspect.class)){
                    targetClassList.addAll(classScanner.getAnnotationClassByPackageName(pkg, annotation));
                }else{
                    // 否则添加该包名下所有类
                    targetClassList.addAll(classScanner.getClassByPackageName(pkg));
                }
            }

        }else{
            // 若注解不为空且不是 Aspect 注解，则添加应用包名下带有该注解的所有类
            if (annotation != null && !annotation.equals(Aspect.class)) {
                targetClassList.addAll(ClassHelper.getClassListByAnnotation(annotation));
            }
        }
        return targetClassList;
    }

    /**
     * 找到代理类对应的目标类列表
     * @param proxyMap
     * @return
     * @throws Exception
     */
    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, List<Class<?>>> proxyMap) throws Exception{
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<Class<?>, List<Proxy>>();
        for(Map.Entry<Class<?>, List<Class<?>>> entry : proxyMap.entrySet()){
            Class<?> proxyClass = entry.getKey();
            List<Class<?>> targetClassList = entry.getValue();
            for(Class<?> targetClass : targetClassList){
                // 创建代理类（切面类）实例
                Proxy baseAspect = (Proxy) proxyClass.newInstance();
                if(targetMap.containsKey(targetClass)){
                    targetMap.get(targetClass).add(baseAspect);
                }else{
                    List<Proxy> baseAspectList = new ArrayList<Proxy>();
                    baseAspectList.add(baseAspect);
                    targetMap.put(proxyClass, baseAspectList);
                }
            }
        }
        return targetMap;
    }
}
