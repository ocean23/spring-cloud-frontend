package com.fujfu.frontend.web.controller;

import com.fujfu.frontend.domain.CandidateDomain;
import com.fujfu.frontend.domain.DomainFactory;
import com.fujfu.frontend.domain.PositionDomain;
import com.fujfu.frontend.entity.root.CandidateDO;
import com.fujfu.frontend.entity.root.PositionDO;
import com.fujfu.frontend.exception.LoginValidException;
import com.fujfu.frontend.permission.Permission;
import com.fujfu.frontend.repo.PositionAutoRepo;
import com.fujfu.frontend.web.controller.mo.PositionMO;
import com.fujfu.frontend.web.controller.mo.ResponseMO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/position")
@AllArgsConstructor
@Slf4j
public class PositionController extends BaseController {
    private final PositionAutoRepo positionAutoRepo;
    private final DomainFactory domainFactory;

    @GetMapping
    public ResponseMO<List<PositionMO>> list() {

        final CandidateDomain candidateDomain = getCurrentLoginCandidateDomain();

        List<PositionMO> positionMOs = new ArrayList<>();

        final List<PositionDO> all = positionAutoRepo.findAll();
        for (PositionDO positionDO : all) {
            PositionMO positionMO = new PositionMO();
            positionMO.setId(positionDO.getId());
            positionMO.setWorkContent(positionDO.getWorkContent());
            positionMO.setLikeCount(positionDO.getLikeCount());
            positionMO.setLike(candidateDomain.isLikePosition(positionDO.getId()));
            positionMO.setPublished(positionDO.isPublished());
            positionMOs.add(positionMO);
        }

        return ResponseMO.successWithData(positionMOs);
    }

    @Permission(permissions = "position-publish")
    @PutMapping("/{positionId}/publish")
    public ResponseMO markPublish(@PathVariable String positionId) {
        final PositionDomain positionDomain = getPositionDomain(positionId);
        if (positionDomain == null) {
            return ResponseMO.errorWithMessage("岗位不存在");
        }
        positionDomain.markPublish();

        return ResponseMO.success();
    }

    @Permission(permissions = "position-private")
    @PutMapping("/{positionId}/private")
    public ResponseMO markPrivate(@PathVariable String positionId) {
        final PositionDomain positionDomain = getPositionDomain(positionId);
        if (positionDomain == null) {
            return ResponseMO.errorWithMessage("岗位不存在");
        }
        positionDomain.markPrivate();

        return ResponseMO.success();
    }

    public PositionDomain getPositionDomain(String positionId) {
        final Optional<PositionDO> positionDOOptional = positionAutoRepo.findById(positionId);
        if (positionDOOptional.isEmpty()) {
            throw new LoginValidException("当前登录用户异常，找不到指定用户");
        }

        final PositionDO positionDO = positionDOOptional.get();
        final PositionDomain positionDomain = domainFactory.createDomain(PositionDomain.class);
        positionDomain.setEntity(positionDO);
        return positionDomain;
    }

}
