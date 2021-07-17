package com.fujfu.frontend.repo;


import com.fujfu.frontend.entity.subordinate.ApplyRecordDO;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author ocean
 * @date 2021/7/14 19:01
 */
public interface ApplyRecordAutoRepo extends MongoRepository<ApplyRecordDO, String> {
}
