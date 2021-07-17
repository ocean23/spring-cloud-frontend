package com.fujfu.frontend.domain;

import com.fujfu.frontend.annotation.Domain;
import com.fujfu.frontend.constant.DomainMQConstant;
import com.fujfu.frontend.entity.root.CandidateDO;
import com.fujfu.frontend.entity.subordinate.ApplyRecordDO;
import com.fujfu.frontend.repo.ApplyRecordAutoRepo;
import com.fujfu.frontend.repo.CandidateAutoRepo;
import com.fujfu.frontend.service.EventService;
import lombok.Data;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Domain
@Data
public class CandidateDomain {
    private final CandidateAutoRepo candidateAutoRepo;
    private final ApplyRecordAutoRepo applyRecordAutoRepo;
    private final EventService eventService;
    private CandidateDO entity;

    public boolean isLikePosition(String positionId) {
        return entity.getLikedPositions().contains(positionId);
    }

    public void like(String positionId) {
        if (isLikePosition(positionId)) {
            return;
        }
        entity.getLikedPositions().add(positionId);
        candidateAutoRepo.save(entity);
        eventService.send(DomainMQConstant.MQ_POSITION_CARD_INCREASE_LIKE_COUNT, positionId);
    }

    public void unlike(String positionId) {
        if (!isLikePosition(positionId)) {
            return;
        }
        entity.getLikedPositions().remove(positionId);
        candidateAutoRepo.save(entity);
        eventService.send(DomainMQConstant.MQ_POSITION_CARD_DECREASE_LIKE_COUNT, positionId);
    }

    public Set<String> likedPositions() {
        return entity.getLikedPositions();
    }

    public void applyPosition(String positionId) {
        final Optional<ApplyRecordDO> recordOptional = applyRecordAutoRepo.findByCandidateIdAndPositionId(entity.getId(), positionId);
        if (recordOptional.isPresent()) {
            return;
        }
        ApplyRecordDO applyRecordDO = new ApplyRecordDO();
        applyRecordDO.setCandidateId(entity.getId());
        applyRecordDO.setPositionId(positionId);
        applyRecordAutoRepo.save(applyRecordDO);
    }

    public Set<String> appliedPositions() {
        final List<ApplyRecordDO> recordDOS = applyRecordAutoRepo.findByCandidateId(entity.getId());
        return recordDOS.stream().map(ApplyRecordDO::getPositionId).collect(Collectors.toSet());
    }

}
