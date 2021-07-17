package com.fujfu.frontend.web.controller;

import com.fujfu.frontend.entity.root.CandidateDO;
import com.fujfu.frontend.entity.root.PositionDO;
import com.fujfu.frontend.repo.ApplyRecordAutoRepo;
import com.fujfu.frontend.repo.CandidateAutoRepo;
import com.fujfu.frontend.repo.PositionAutoRepo;
import com.fujfu.frontend.web.controller.mo.ResponseMO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Beldon
 * 数据初始化
 */
@RestController
@RequestMapping("/api/data")
@AllArgsConstructor
@Slf4j
public class DataInitController {

    private final CandidateAutoRepo candidateAutoRepo;
    private final PositionAutoRepo positionAutoRepo;
    private final ApplyRecordAutoRepo applyRecordAutoRepo;


    @GetMapping("/init")
    public ResponseMO init() {
        initCandidate();
        initPosition();
        initApplyRecord();
        return ResponseMO.success();
    }

    private void initCandidate() {
        candidateAutoRepo.deleteAll();
        CandidateDO candidateDO = new CandidateDO();
        candidateDO.setMobile("13800000000");
        candidateDO.setName("求职者");
        candidateAutoRepo.save(candidateDO);

        CandidateDO candidateDO2 = new CandidateDO();
        candidateDO2.setMobile("13800000001");
        candidateDO2.setName("求职者2");
        candidateAutoRepo.save(candidateDO2);
    }

    private void initPosition() {
        positionAutoRepo.deleteAll();
        PositionDO positionDO = new PositionDO();
        positionDO.setPublished(true);
        positionDO.setWorkContent("月结，观澜京东,22元/小时");
        positionDO.setLikeCount(0);
        positionAutoRepo.save(positionDO);
    }

    private void initApplyRecord() {
        applyRecordAutoRepo.deleteAll();
    }
}
