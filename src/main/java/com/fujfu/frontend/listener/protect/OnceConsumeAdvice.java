package com.fujfu.frontend.listener.protect;

import com.fujfu.frontend.entity.EventRecordReceiveDO;
import com.fujfu.frontend.listener.mo.EventMO;
import com.fujfu.frontend.repo.EventRecordReceiveAutoRepo;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Beldon
 */
@Slf4j
public class OnceConsumeAdvice implements MethodInterceptor {

    private final Lock lock = new ReentrantLock();
    private final EventRecordReceiveAutoRepo eventRecordReceiveAutoRepo;

    public OnceConsumeAdvice(EventRecordReceiveAutoRepo eventRecordReceiveAutoRepo) {
        this.eventRecordReceiveAutoRepo = eventRecordReceiveAutoRepo;
    }

    /**
     * 防止重复消费逻辑
     *
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        final EventMO event = (EventMO) invocation.getArguments()[0];

        //分布式环境改为分布式锁，如 redis，而且根据 eventId 来进锁
        if (!lock.tryLock()) {
            log.info("the record is being processed, eventId = {}", event.getEventId());
            return null;
        }
        try {
            boolean processed = eventRecordReceiveAutoRepo.existsByEventId(event.getEventId());
            if (processed) {
                log.info("此消息已经处理过，跳过。eventId = {}", event.getEventId());
                return null;
            }

            //处理主逻辑
            final Object result = invocation.proceed();

            //标记为已完成
            markEventReceived(event.getEventId());

            return result;
        } finally {
            lock.unlock();
        }
    }

    public void markEventReceived(String eventId) {
        EventRecordReceiveDO eventRecordReceiveDO = new EventRecordReceiveDO();
        eventRecordReceiveDO.setEventId(eventId);
        eventRecordReceiveAutoRepo.save(eventRecordReceiveDO);
    }
}
