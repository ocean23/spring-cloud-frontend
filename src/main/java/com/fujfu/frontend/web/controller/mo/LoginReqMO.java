package com.fujfu.frontend.web.controller.mo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @author Jun Luo
 * @date 2021/4/1 2:38 PM
 */
@Getter
@Setter
@ToString
public class LoginReqMO {
    /**
     * 手机号码
     */
    @NotBlank(message = "手机号不能为空")
    private String mobile;

}
