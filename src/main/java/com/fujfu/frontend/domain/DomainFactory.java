package com.fujfu.frontend.domain;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author Beldon
 * @create 2019-04-26 15:21
 */
@Component
public class DomainFactory {

    private final ApplicationContext applicationContext;

    public DomainFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public <T> T createDomain(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }
}
