package com.fujfu.frontend.web.controller;

import com.fujfu.frontend.client.ServiceClient;
import com.fujfu.frontend.web.controller.mo.ConsumerMO;
import com.fujfu.frontend.web.controller.mo.ResponseMO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/consumer")
public class ConsumerController {
    private final ServiceClient serviceClient;

    @GetMapping
    public ResponseMO<List<ConsumerMO>> allConsumer() {
        final ResponseMO<List<ConsumerMO>> allConsumer = serviceClient.allConsumer();
        log.info("get data from service:{}", allConsumer);
        return allConsumer;
    }
}
