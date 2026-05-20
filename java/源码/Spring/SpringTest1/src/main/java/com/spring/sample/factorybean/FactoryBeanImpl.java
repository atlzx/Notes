package com.spring.sample.factorybean;

import com.spring.sample.User;
import org.springframework.beans.factory.FactoryBean;

public class FactoryBeanImpl implements FactoryBean<User> {
    @Override
    public User getObject() throws Exception {
        return new User();
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }
}
