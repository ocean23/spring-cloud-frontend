package com.fujfu.frontend.web.controller;

import com.fujfu.frontend.domain.CandidateDomain;
import com.fujfu.frontend.domain.DomainFactory;
import com.fujfu.frontend.entity.root.PositionDO;
import com.fujfu.frontend.repo.CandidateAutoRepo;
import com.fujfu.frontend.repo.PositionAutoRepo;
import com.fujfu.frontend.web.controller.mo.PositionMO;
import com.fujfu.frontend.web.controller.mo.ResponseMO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/position")
@AllArgsConstructor
@Slf4j
public class PositionController extends BaseController{
    private final PositionAutoRepo positionAutoRepo;
    private final CandidateAutoRepo candidateAutoRepo;
    private final DomainFactory domainFactory;

    @GetMapping
    public ResponseMO<List<PositionMO>> list(){

        final CandidateDomain candidateDomain = getCurrentLoginCandidateDomain();

        List<PositionMO> positionMOs = new ArrayList<>();

        final List<PositionDO> all = positionAutoRepo.findAll();
        for (PositionDO positionDO : all) {
            PositionMO positionMO = new PositionMO();
            positionMO.setId(positionDO.getId());
            positionMO.setWorkContent(positionDO.getWorkContent());
            positionMO.setLikeCount(positionDO.getLikeCount());
            positionMO.setLike(candidateDomain.isLikePosition(positionDO.getId()));
            positionMOs.add(positionMO);
        }

        return ResponseMO.successWithData(positionMOs);
    }


    @PutMapping("/{positionId}/like")
    public ResponseMO like(@PathVariable String positionId){
        final CandidateDomain candidateDomain = getCurrentLoginCandidateDomain();
        candidateDomain.like(positionId);
        return ResponseMO.success();
    }

    @PutMapping("/{positionId}/unlike")
    public ResponseMO unlike(@PathVariable String positionId){
        final CandidateDomain candidateDomain = getCurrentLoginCandidateDomain();
        candidateDomain.unlike(positionId);
        return ResponseMO.success();
    }
}
