package com.fujfu.frontend.listener.protect;

import com.fujfu.frontend.listener.mo.EventMO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.StaticMethodMatcher;
import org.springframework.aop.support.annotation.AnnotationClassFilter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author Beldon.
 */
public class OnceConsumeMatchingPointcut implements Pointcut {

    private final ClassFilter classFilter;

    private final MethodMatcher methodMatcher;

    public OnceConsumeMatchingPointcut() {
        this.classFilter = new AnnotationClassFilter(Component.class, true);
        this.methodMatcher = new StaticMethodMatcher() {
            @Override
            public boolean matches(Method method, Class<?> aClass) {
                return AnnotatedElementUtils.hasAnnotation(method, RabbitListener.class)
                        && AnnotatedElementUtils.hasAnnotation(method, OnceConsume.class)
                        && hasEventMO(method);
            }

            private boolean hasEventMO(Method method) {
                final Class<?>[] parameterTypes = method.getParameterTypes();
                return parameterTypes.length == 1 && EventMO.class.equals(parameterTypes[0]);
            }
        };
    }

    @Override
    public ClassFilter getClassFilter() {
        return classFilter;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return methodMatcher;
    }
}
