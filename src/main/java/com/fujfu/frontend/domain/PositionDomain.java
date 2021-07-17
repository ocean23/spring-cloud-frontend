package com.fujfu.frontend.domain;

import com.fujfu.frontend.annotation.Domain;
import com.fujfu.frontend.entity.root.PositionDO;
import com.fujfu.frontend.repo.PositionAutoRepo;
import lombok.Data;

@Domain
@Data
public class PositionDomain {
    private PositionDO entity;
    private final PositionAutoRepo positionAutoRepo;


    /**
     * 喜爱值+1
     */
    public void increaseLikeCount() {
        entity.setLikeCount(entity.getLikeCount() + 1);
        positionAutoRepo.save(entity);
    }

    /**
     * 喜爱值-1
     */
    public void decreaseLikeCount() {
        entity.setLikeCount(entity.getLikeCount() - 1);
        positionAutoRepo.save(entity);
    }
}
