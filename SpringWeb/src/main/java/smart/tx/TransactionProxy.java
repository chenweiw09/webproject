package smart.tx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import smart.aop.proxy.Proxy;
import smart.aop.proxy.ProxyChain;
import smart.tx.annotation.Transaction;

import java.lang.reflect.Method;

/**
 * Created by Cruise on 2017/7/10.
 */
public class TransactionProxy implements Proxy{
    private static final Logger logger = LoggerFactory.getLogger(TransactionProxy.class);

    //定义线程局部变量，标志是否开启事务
    private static final ThreadLocal<Boolean> flagContainer = new ThreadLocal<Boolean>() {
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };

    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result;
        boolean transactionFlag = flagContainer.get();
        Method method = proxyChain.getTargetMethod();

        if(!transactionFlag && method.isAnnotationPresent(Transaction.class)){

            try {
                //set transaction flag
                flagContainer.set(true);
                // start the transaction
                result = proxyChain.doProxyChain();
                //commit the transaction
            } catch (Throwable throwable) {
                logger.debug("rollback transaction");
                //rollback tranaction
                throw throwable;
            }finally {
                flagContainer.remove();
            }
        }else {
            // 执行目标方法
            result = proxyChain.doProxyChain();
        }
        return result;
    }
}
