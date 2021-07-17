package com.fujfu.frontend.repo;


import com.fujfu.frontend.entity.root.PositionDO;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author ocean
 * @date 2021/7/14 19:00
 */
public interface PositionAutoRepo extends MongoRepository<PositionDO, String> {
}
