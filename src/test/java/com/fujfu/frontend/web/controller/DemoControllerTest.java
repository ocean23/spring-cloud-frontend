package com.fujfu.frontend.web.controller;

import com.fujfu.frontend.web.controller.mo.DemoMO;
import com.fujfu.frontend.web.controller.mo.ResponseMO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author ocean
 * @date 2021/7/10 11:10
 */
public class DemoControllerTest extends BaseControllerTest{

    @Test
    public void hello() {
        String url = "/demo/hello";
        ResponseMO<DemoMO> demoMOResponseMO = this.performGetSingle(url, DemoMO.class);
        DemoMO data = demoMOResponseMO.getData();
        Assertions.assertEquals("hello world", data.getName());
    }
}
