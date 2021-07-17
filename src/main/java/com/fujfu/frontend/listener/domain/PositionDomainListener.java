package com.fujfu.frontend.listener.domain;

import com.alibaba.fastjson.JSON;
import com.fujfu.frontend.constant.DomainMQConstant;
import com.fujfu.frontend.domain.DomainFactory;
import com.fujfu.frontend.domain.PositionDomain;
import com.fujfu.frontend.entity.root.PositionDO;
import com.fujfu.frontend.listener.mo.EventMO;
import com.fujfu.frontend.listener.protect.OnceConsume;
import com.fujfu.frontend.repo.PositionAutoRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
@AllArgsConstructor
public class PositionDomainListener {

    private final DomainFactory domainFactory;
    private final PositionAutoRepo positionAutoRepo;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = DomainMQConstant.MQ_POSITION_CARD_INCREASE_LIKE_COUNT, durable = "true"),
            exchange = @Exchange(value = "amq.direct")
    ))
    @OnceConsume
    public void increaseLikeCount(EventMO event) {
        final PositionDomain positionDomain = getPositionDomain(event.getData());
        if (positionDomain == null) {
            return;
        }
        positionDomain.increaseLikeCount();
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = DomainMQConstant.MQ_POSITION_CARD_DECREASE_LIKE_COUNT, durable = "true"),
            exchange = @Exchange(value = "amq.direct")
    ))
    @OnceConsume
    public void decreaseLikeCount(EventMO event) {
        final PositionDomain positionDomain = getPositionDomain(event.getData());
        if (positionDomain == null) {
            return;
        }
        positionDomain.decreaseLikeCount();
    }

    private PositionDomain getPositionDomain(String positionId){
        final Optional<PositionDO> positionDOOptional = positionAutoRepo.findById(positionId);
        if (positionDOOptional.isEmpty()) {
            return null;
        }
        final PositionDomain domain = domainFactory.createDomain(PositionDomain.class);
        domain.setEntity(positionDOOptional.get());
        return domain;
    }
}
