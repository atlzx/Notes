package Listener;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.*;

import java.io.Serializable;

/* 通过注解的方式使其监听对应的Servlet，只需要写上该注解即可，但不写监听不会生效 */
@WebListener
public class SessionListener implements HttpSessionListener, HttpSessionAttributeListener, HttpSessionBindingListener, HttpSessionActivationListener , Serializable {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("session对象"+se.getSession().getId()+"被创建了");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("session被销毁了");
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent se) {
        System.out.println("向session中添加了属性");
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent se) {
        System.out.println("修改了session中的属性");
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent se) {
        System.out.println("移除了session中的属性");
    }

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        System.out.println("对"+event.getName()+"添加了事件绑定");
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        System.out.println("对"+event.getName()+"解除了事件绑定");
    }

    @Override
    public void sessionWillPassivate(HttpSessionEvent se) {
        System.out.println("将要进行钝化了");
    }


    /*
        这玩意看的我很迷，之前不输出什么也没改然后就又输出了
    */
    @Override
    public void sessionDidActivate(HttpSessionEvent se) {
        System.out.println("已经进行了活化");
    }
}
