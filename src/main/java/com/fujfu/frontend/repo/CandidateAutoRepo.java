package com.fujfu.frontend.repo;


import com.fujfu.frontend.entity.root.CandidateDO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * @author ocean
 * @date 2021/7/14 18:59
 */
public interface CandidateAutoRepo extends MongoRepository<CandidateDO, String> {
    Optional<CandidateDO> findByMobile(String mobile);
}
