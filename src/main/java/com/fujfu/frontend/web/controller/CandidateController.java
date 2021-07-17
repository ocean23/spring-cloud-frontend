package com.fujfu.frontend.web.controller;

import com.fujfu.frontend.domain.CandidateDomain;
import com.fujfu.frontend.entity.root.PositionDO;
import com.fujfu.frontend.repo.ApplyRecordAutoRepo;
import com.fujfu.frontend.repo.PositionAutoRepo;
import com.fujfu.frontend.web.controller.mo.PositionMO;
import com.fujfu.frontend.web.controller.mo.ResponseMO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/candidate")
@AllArgsConstructor
@Slf4j
public class CandidateController extends BaseController {

    private final PositionAutoRepo positionAutoRepo;
    private final ApplyRecordAutoRepo applyRecordAutoRepo;

    @GetMapping("/position/liked")
    public ResponseMO<List<PositionMO>> listLikedPosition() {
        final CandidateDomain candidateDomain = getCurrentLoginCandidateDomain();
        final Set<String> likedPositionIds = candidateDomain.likedPositions();
        List<PositionMO> likedMOs = new ArrayList<>();
        for (String positionId : likedPositionIds) {
            final Optional<PositionDO> positionDOOptional = positionAutoRepo.findById(positionId);
            if (positionDOOptional.isPresent()) {
                final PositionDO positionDO = positionDOOptional.get();
                PositionMO positionMO = new PositionMO();
                positionMO.setId(positionDO.getId());
                positionMO.setLike(true);
                positionMO.setWorkContent(positionDO.getWorkContent());
                positionMO.setLikeCount(positionDO.getLikeCount());
                likedMOs.add(positionMO);
            }
        }

        return ResponseMO.successWithData(likedMOs);
    }

    @PutMapping("/position/{positionId}/apply")
    public ResponseMO applyPosition(@PathVariable String positionId) {

        final Optional<PositionDO> positionDOOptional = positionAutoRepo.findById(positionId);
        if (positionDOOptional.isEmpty()) {
            return ResponseMO.errorWithMessage("找不到应聘岗位");
        }

        final CandidateDomain candidateDomain = getCurrentLoginCandidateDomain();
        candidateDomain.applyPosition(positionId);
        return ResponseMO.success();
    }

    @PutMapping("/position/applied")
    public ResponseMO<List<PositionMO>> appliedPosition() {
        final CandidateDomain candidateDomain = getCurrentLoginCandidateDomain();
        final Set<String> appliedPositionIds = candidateDomain.appliedPositions();
        List<PositionMO> positionMOS = new ArrayList<>();
        for (String positionId : appliedPositionIds) {
            final Optional<PositionDO> positionDOOptional = positionAutoRepo.findById(positionId);
            if (positionDOOptional.isPresent()) {
                final PositionDO positionDO = positionDOOptional.get();
                PositionMO positionMO = new PositionMO();
                positionMO.setId(positionDO.getId());
                positionMO.setLike(true);
                positionMO.setWorkContent(positionDO.getWorkContent());
                positionMO.setLikeCount(positionDO.getLikeCount());
                positionMOS.add(positionMO);
            }
        }

        return ResponseMO.successWithData(positionMOS);
    }
}
