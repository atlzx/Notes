package Listener;

import jakarta.servlet.ServletContextAttributeEvent;
import jakarta.servlet.ServletContextAttributeListener;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;


/* 通过注解的方式使其监听对应的Servlet，只需要写上该注解即可，但不写监听不会生效 */
@WebListener
public class ApplicationListener implements ServletContextListener, ServletContextAttributeListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("ServletContext被创建了");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("ServletContext被销毁了");
    }

    @Override
    public void attributeAdded(ServletContextAttributeEvent scae) {
        System.out.println("向应用域添加了属性");
    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent scae) {
        System.out.println("修改了应用域中的属性");
    }

    @Override
    public void attributeRemoved(ServletContextAttributeEvent scae) {
        System.out.println("移除了应用域中的属性");
    }
}
