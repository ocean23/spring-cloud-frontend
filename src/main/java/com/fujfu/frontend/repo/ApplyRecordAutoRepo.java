package com.fujfu.frontend.repo;


import com.fujfu.frontend.entity.subordinate.ApplyRecordDO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author ocean
 * @date 2021/7/14 19:01
 */
public interface ApplyRecordAutoRepo extends MongoRepository<ApplyRecordDO, String> {
    Optional<ApplyRecordDO> findByCandidateIdAndPositionId(String candidateId, String positionId);
    List<ApplyRecordDO> findByCandidateId(String candidateId);
}
