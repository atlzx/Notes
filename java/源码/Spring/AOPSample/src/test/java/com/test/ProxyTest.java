package com.test;

import com.spring.sample.ProxyInterfaceImpl;
import com.spring.sample.ProxyTestInterface;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;



public class ProxyTest {
    @Test
    public void test1(){
        ProxyInterfaceImpl proxyImpl=new ProxyInterfaceImpl();
        // Spring也有一个Proxy类，不要导错包
        Object proxy = Proxy.newProxyInstance(
            ProxyInterfaceImpl.class.getClassLoader(),  // 传入类加载器对象
            proxyImpl.getClass().getInterfaces(),  // 传入接口数组
            // 传入InvocationHandler对象，由于这玩意是个接口，因此可以使用lambda表达式
            (obj, method, args) -> {
                System.out.println("方法开始执行");
                Object result = method.invoke(proxyImpl, args);
                System.out.println("方法执行完成");
                return result;
            }
        );
        // 强转时需要使用接口来进行强转，不能用实现类进行强转，不然会报错
        int sum = ((ProxyTestInterface) proxy).sum(1, 2);
        System.out.println(sum);
    }
}
