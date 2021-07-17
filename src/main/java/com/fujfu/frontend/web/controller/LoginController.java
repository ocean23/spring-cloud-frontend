package com.fujfu.frontend.web.controller;

import com.fujfu.frontend.entity.root.CandidateDO;
import com.fujfu.frontend.repo.CandidateAutoRepo;
import com.fujfu.frontend.service.JwtService;
import com.fujfu.frontend.web.controller.mo.DemoMO;
import com.fujfu.frontend.web.controller.mo.LoginReqMO;
import com.fujfu.frontend.web.controller.mo.LoginResMO;
import com.fujfu.frontend.web.controller.mo.ResponseMO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * @author ocean
 * @date 2021/7/15 15:47
 */
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Slf4j
public class LoginController {

    private final CandidateAutoRepo candidateAutoRepo;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseMO<LoginResMO> login(@RequestBody @Valid LoginReqMO reqMO) {
        final Optional<CandidateDO> candidateDOOptional = candidateAutoRepo.findByMobile(reqMO.getMobile());
        if (candidateDOOptional.isEmpty()) {
            return ResponseMO.errorWithMessage("用户不存在");
        }
        final CandidateDO candidateDO = candidateDOOptional.get();
        LoginResMO loginResMO = new LoginResMO();
        String token = jwtService.generateToken(candidateDO.getId(), candidateDO.getMobile());
        loginResMO.setToken(token);
        loginResMO.setMobile(reqMO.getMobile());
        return ResponseMO.successWithData(loginResMO);
    }

    @GetMapping("/hello")
    public ResponseMO<DemoMO> hello() {
        DemoMO demo = new DemoMO();
        demo.setName("hello world");
        return ResponseMO.successWithData(demo);
    }
}
