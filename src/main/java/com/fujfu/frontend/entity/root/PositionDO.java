package com.fujfu.frontend.entity.root;

import com.fujfu.frontend.entity.BaseDO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author ocean
 * @date 2021/7/14 17:26
 */
@Getter
@Setter
@Document(collection = "position")
public class PositionDO extends BaseDO {

    /**
     * 岗位工作内容
     */
    private String workContent;
    /**
     * 喜欢人数
     */
    private int likeCount;
    /**
     * 是否上架
     */
    private boolean published = false;
}
