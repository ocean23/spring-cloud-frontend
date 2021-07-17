package com.fujfu.frontend.listener.protect;

import com.fujfu.frontend.repo.EventRecordReceiveAutoRepo;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.context.annotation.Configuration;

/**
 * @author Beldon
 */
@Configuration
public class OnceConsumeCutAdvisor extends AbstractPointcutAdvisor {

    private final Pointcut pointcut;
    private final Advice advice;

    public OnceConsumeCutAdvisor(EventRecordReceiveAutoRepo eventRecordReceiveAutoRepo) {
        this.pointcut = new OnceConsumeMatchingPointcut();
        this.advice = new OnceConsumeAdvice(eventRecordReceiveAutoRepo);
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
