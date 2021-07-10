package com.fujfu.frontend.client;

import com.fujfu.frontend.web.controller.mo.ConsumerMO;
import com.fujfu.frontend.web.controller.mo.ResponseMO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author Beldon
 * <p>
 * spring-cloud-service 为需要请求的目标服务名
 */
@FeignClient("spring-cloud-service")
public interface ServiceClient {

    /**
     * 请求的目标接口，写法跟目标 controller 写法一致
     *
     * @return
     */
    @GetMapping("/api/consumer")
    ResponseMO<List<ConsumerMO>> allConsumer();
}
