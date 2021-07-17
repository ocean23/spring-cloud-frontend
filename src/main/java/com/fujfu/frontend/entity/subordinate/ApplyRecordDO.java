package com.fujfu.frontend.entity.subordinate;

import com.fujfu.frontend.entity.BaseDO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author ocean
 * @date 2021/7/14 18:31
 */
@Getter
@Setter
@Document(collection = "apply_record")
public class ApplyRecordDO extends BaseDO {
    /**
     * 求职者 ID
     */
    private String candidateId;
    /**
     * 岗位 ID
     */
    private String positionId;
}
