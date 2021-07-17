package com.fujfu.frontend.entity.root;

import com.fujfu.frontend.entity.BaseDO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

/**
 * @author ocean
 * @date 2021/7/14 18:12
 */
@Getter
@Setter
@Document(collection = "candidate")
public class CandidateDO extends BaseDO {

    /**
     * 手机号
     */
    private String mobile;
    /**
     * 姓名
     */
    private String name;
    /**
     * 喜欢的卡片
     */
    private Set<String> likedPositions = new HashSet<>();
}
