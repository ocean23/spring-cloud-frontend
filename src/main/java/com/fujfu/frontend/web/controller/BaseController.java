package com.fujfu.frontend.web.controller;

import com.fujfu.frontend.domain.CandidateDomain;
import com.fujfu.frontend.domain.DomainFactory;
import com.fujfu.frontend.entity.root.CandidateDO;
import com.fujfu.frontend.exception.LoginValidException;
import com.fujfu.frontend.repo.CandidateAutoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public abstract class BaseController {
    private static final String MOBILE_KEY = "mobile";
    private static final String USER_ID_KEY = "userId";

    @Autowired
    private CandidateAutoRepo candidateAutoRepo;
    @Autowired
    private DomainFactory domainFactory;

    protected String currentUserId() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getHeader(USER_ID_KEY);
    }


    public CandidateDomain getCurrentLoginCandidateDomain() {
        final Optional<CandidateDO> candidateDOOptional = candidateAutoRepo.findById(currentUserId());
        if (candidateDOOptional.isEmpty()) {
            throw new LoginValidException("当前登录用户异常，找不到指定用户");
        }

        final CandidateDO candidateDO = candidateDOOptional.get();
        final CandidateDomain candidateDomain = domainFactory.createDomain(CandidateDomain.class);
        candidateDomain.setEntity(candidateDO);
        return candidateDomain;
    }

}
