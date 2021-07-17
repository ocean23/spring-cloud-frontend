package com.fujfu.frontend.permission;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;

/**
 * @author Beldon
 */
public class PermissionPointCutAdvisor extends AbstractPointcutAdvisor {

    private final Pointcut pointcut;
    private final Advice advice;

    public PermissionPointCutAdvisor() {
        this.pointcut = new PermissionAnnotationMatchingPointcut();
        this.advice = new PermissionAdvice();
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }
}
